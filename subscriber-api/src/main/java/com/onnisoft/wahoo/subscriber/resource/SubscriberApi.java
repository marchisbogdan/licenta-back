package com.onnisoft.wahoo.subscriber.resource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.JsonWebToken;
import com.onnisoft.api.utils.security.PasswordHash;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.SubscriberApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Profile;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.SubscriberDevice;
import com.onnisoft.wahoo.model.document.enums.DeviceTypeEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberStatusEnum;
import com.onnisoft.wahoo.subscriber.api.request.AuthenticationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.PasswordChangeRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.ProfileCreationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RegistrationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RenewTokenRequestDTO;
import com.onnisoft.wahoo.subscriber.api.response.AuthenticationRegistrationResponseDTO;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Service
@Path("/subscriber")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Subscriber API")
public class SubscriberApi {

	private static final Logger logger = LoggerFactory.getLogger(SubscriberApi.class);

	private static final String HEADER_SECURITY_TOKEN = "X-CustomToken";
	private static final String HEADER_DEVICE_TOKEN = "X-DeviceToken";

	@Autowired
	@Qualifier("subscriberDAO")
	private Dao<Subscriber> subscriberDao;

	@Autowired
	@Qualifier("subscriberDeviceDAO")
	private Dao<SubscriberDevice> subscriberDeviceDao;

	@Autowired
	@Qualifier("countryDAO")
	private Dao<Country> countryDao;

	@Autowired
	@Qualifier("profileDAO")
	private Dao<Profile> profileDao;

	@Autowired
	private JsonWebToken jwt;

	@Autowired
	private SubscriberApiValidationUtil validator;

	@Value("${security.api.jwt.expiretime}")
	private long jwtExpirationTime;

	/**
	 * Authentication method.
	 *
	 * @param request
	 * @param headers
	 * @return
	 */
	@POST
	@Path("login")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Authenticate user by t's username and password", notes = "authenticate subscriber")
	public GenericResponseDTO<AuthenticationRegistrationResponseDTO> login(
			@ApiParam(value = "User credentials", required = true) AuthenticationRequestDTO request, @Context HttpServletRequest headers) {

		try {
			this.validator.validateAuthenticationRequest(request);
		} catch (ValidationException ex) {
			return GenericResponseDTO.createFailed(ex.getMessage());
		}

		Subscriber subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().email(request.getEmailOrUsername()).build());
		if (subscriber == null) {
			subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().userName(request.getEmailOrUsername()).build());
		}

		try {
			if (subscriber == null || !PasswordHash.validatePassword(request.getPassword(), subscriber.getPassword())) {
				return GenericResponseDTO.createFailed("Invalid credentials");
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			logger.warn("Cound not check password.", ex);
			return GenericResponseDTO.createFailed(ex.getMessage());
		}

		switch (subscriber.getStatus()) {
		case SUSPENDED:
		case DELETED:
			return GenericResponseDTO.createFailed("User has been " + subscriber.getStatus().name().toLowerCase());
		default:
			break;
		}

		Date tokenExpirationDate = Date.from(new Timestamp(System.currentTimeMillis() + this.jwtExpirationTime).toInstant());
		boolean tokenExpirationDateIsUpdated = this.subscriberDao
				.update(new Subscriber.SubscriberBuilder().id(subscriber.getId()).tokenExpirationDate(tokenExpirationDate).build());
		if (!tokenExpirationDateIsUpdated) {
			logger.warn("Couldn't update the token expiration date.");
			return GenericResponseDTO.createFailed("Couldn't update the token expiration date.");
		}

		String token = this.jwt.createJWT(subscriber.getId(), subscriber.getEmail() != null ? subscriber.getEmail() : subscriber.getUserName(),
				subscriber.getRole().name(), this.jwtExpirationTime);

		String userAgent = headers.getHeader("User-Agent");
		String deviceToken = headers.getHeader(HEADER_DEVICE_TOKEN);

		SubscriberDevice subscriberDevice = this.subscriberDeviceDao
				.retrieve(new SubscriberDevice.SubscriberDeviceBuilder().userAgent(userAgent).subscriber(subscriber).build());

		if (subscriberDevice == null) {
			DeviceTypeEnum deviceType = this.getDeviceTypeFromUserAgent(userAgent);

			subscriberDevice = new SubscriberDevice.SubscriberDeviceBuilder().deviceType(deviceType).userAgent(userAgent).deviceToken(deviceToken)
					.subscriber(subscriber).build();
			subscriberDevice = this.subscriberDeviceDao.create(subscriberDevice);
		}

		if (deviceToken != null && !deviceToken.equals(subscriberDevice.getDeviceToken())) {
			this.subscriberDeviceDao
					.update(new SubscriberDevice.SubscriberDeviceBuilder().deviceToken(subscriberDevice.getRenewTokenId()).deviceToken(deviceToken).build());
		}

		return GenericResponseDTO.createSuccess(new AuthenticationRegistrationResponseDTO(token, subscriberDevice.getRenewTokenId(), subscriber));
	}

	@POST
	@Path("renewToken")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody GenericResponseDTO<String> renewToken(RenewTokenRequestDTO request, @Context HttpServletRequest headers) {
		try {
			validator.validateRenewTokenRequest(request);
		} catch (ValidationException ex) {
			return GenericResponseDTO.createFailed(ex.getMessage());
		}

		Subscriber subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().email(request.getEmailOrUsername()).build());
		if (subscriber == null) {
			subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().userName(request.getEmailOrUsername()).build());
		}

		if (subscriber != null) {
			switch (subscriber.getStatus()) {
			case SUSPENDED:
			case DELETED:
				return GenericResponseDTO.createFailed("Subscriber has been " + subscriber.getStatus().name().toLowerCase());
			default:
				break;
			}

			Date tokenExpirationDate = Date.from(new Timestamp(System.currentTimeMillis() + this.jwtExpirationTime).toInstant());
			boolean tokenExpirationDateIsUpdated = this.subscriberDao
					.update(new Subscriber.SubscriberBuilder().id(subscriber.getId()).tokenExpirationDate(tokenExpirationDate).build());
			if (!tokenExpirationDateIsUpdated) {
				logger.warn("Couldn't update the token expiration date.");
				return GenericResponseDTO.createFailed("Couldn't update the token expiration date.");
			}

			SubscriberDevice subscriberDevice = this.subscriberDeviceDao
					.retrieve(new SubscriberDevice.SubscriberDeviceBuilder().renewTokenId(request.getRenewTokenId()).subscriber(subscriber).build());
			String userAgent = headers.getHeader("User-Agent");
			DeviceTypeEnum deviceType = this.getDeviceTypeFromUserAgent(userAgent);
			if (subscriberDevice != null) {
				if (!subscriberDevice.getDeviceType().equals(deviceType) || !subscriberDevice.getUserAgent().equalsIgnoreCase(userAgent)) {
					logger.warn("Wrong client. useragent_request=" + userAgent + ", current useragent =" + subscriberDevice.getUserAgent());
					return GenericResponseDTO.createFailed("Invalid request");
				}

				String deviceToken = headers.getHeader(HEADER_DEVICE_TOKEN);
				if (deviceToken != null && !deviceToken.equals(subscriberDevice.getDeviceToken())) {
					this.subscriberDeviceDao.update(
							new SubscriberDevice.SubscriberDeviceBuilder().renewTokenId(subscriberDevice.getRenewTokenId()).deviceToken(deviceToken).build());
				}

				String token = this.jwt.createJWT(subscriber.getId(), subscriber.getEmail() != null ? subscriber.getEmail() : subscriber.getUserName(),
						subscriber.getRole().name(), this.jwtExpirationTime);
				return GenericResponseDTO.createSuccess(token);
			}
			logger.warn("Invalid renew token. username_request=" + request.getEmailOrUsername() + ", renewtoken_request_id=" + request.getRenewTokenId());
		} else {
			logger.warn("User was not found!");
		}
		return GenericResponseDTO.createFailed("Invalid request");
	}

	/**
	 * Unregister device from receiving push notifications.
	 *
	 * @param servletRequest
	 * @return
	 */
	@POST
	@Path("unregisterDevice")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public @ResponseBody GenericResponseDTO<String> unregisterDevice(@Context HttpServletRequest servletRequest) {
		logger.info("Unregister device from push notification.");

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);
		if (subscriber != null) {
			String userAgent = servletRequest.getHeader("User-Agent");
			SubscriberDevice subscriberDevice = this.subscriberDeviceDao
					.retrieve(new SubscriberDevice.SubscriberDeviceBuilder().userAgent(userAgent).subscriber(subscriber).build());

			if (subscriberDevice != null) {
				this.subscriberDeviceDao
						.update(new SubscriberDevice.SubscriberDeviceBuilder().renewTokenId(subscriberDevice.getRenewTokenId()).deviceToken("").build());

				return GenericResponseDTO.createSuccess("Device undegistred");
			}
			logger.warn("Required device was not registred. subscriber_id_request=" + subscriber.getId() + ", user_agent_request=" + userAgent);
		} else {
			logger.warn("User was not found!");
		}
		return GenericResponseDTO.createFailed("Invalid request");
	}

	/**
	 * Register subscriber.
	 *
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@POST
	@Path("register")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Used if you want to create admin users. The user that makes the call must also be an admin.", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<AuthenticationRegistrationResponseDTO> register(RegistrationRequestDTO request, @Context HttpServletRequest headers)
			throws ParseException {
		try {
			this.validator.validateRegistrationRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		boolean isMasterAdmin = false;
		boolean isClientAdmin = false;
		Subscriber registerRequest = this.retrieveUserFromToken(headers);
		if (registerRequest != null && SubscriberRoleEnum.MASTER_ADMIN.compareTo(registerRequest.getRole()) == 0) {
			isMasterAdmin = true;
		}

		if (registerRequest != null && SubscriberRoleEnum.CLIENT_ADMIN.compareTo(registerRequest.getRole()) == 0) {
			isClientAdmin = true;
		}

		if (!isMasterAdmin && SubscriberRoleEnum.MASTER_ADMIN.compareTo(request.getRole()) == 0) {
			logger.warn("Can't create a master administrator user.");
			return GenericResponseDTO.createFailed("User is not master administrator.");
		}

		if (!isClientAdmin && !isMasterAdmin && SubscriberRoleEnum.CLIENT_ADMIN.compareTo(request.getRole()) == 0) {
			logger.warn("Can't create a client administrator user.");
			return GenericResponseDTO.createFailed("User is not master or client administrator.");
		}

		String password = null;
		try {
			password = PasswordHash.createHash(request.getPassword());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.warn("unable to create password hash", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Country country = countryDao.retrieve(new Country.Builder().id(request.getCountryId()).build());

		Subscriber partner = subscriberDao.retrieveById(request.getPartnerId());

		Subscriber subscriber = new Subscriber.SubscriberBuilder().userName(request.getUserName()).email(request.getEmail()).firstName(request.getFirstName())
				.lastName(request.getLastName()).country(country).partner(partner).birthDate(RegistrationRequestDTO.format.parse(request.getBirthDate()))
				.password(password).role(request.getRole()).status(SubscriberStatusEnum.INIT).toCreate().build();
		subscriber = this.subscriberDao.create(subscriber);

		return this.login(new AuthenticationRequestDTO(subscriber.getEmail(), request.getPassword()), headers);
	}

	@POST
	@Path("profile")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public @ResponseBody GenericResponseDTO<Profile> createProfile(ProfileCreationRequestDTO request, @Context HttpServletRequest headers) {
		try {
			this.validator.validateProfileRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Subscriber subscriber = this.retrieveUserFromToken(headers);
		if (subscriber.getProfile() != null) {
			Profile prf = this.profileDao.retrieve(subscriber.getProfile());
			if (prf != null) {
				return updateProfile(request, headers);
			}
		}
		Profile profile = new Profile.Builder().address(request.getAddress()).zipCode(request.getZipCode()).city(request.getCity()).gender(request.getGender())
				.language(request.getLanguage()).statement(request.getStatusMessage()).avatarLink(request.getAvatarLink()).builder();

		profile = this.profileDao.create(profile);

		boolean updatedSubscriber = this.subscriberDao.update(new Subscriber.SubscriberBuilder().id(subscriber.getId()).profile(profile).build());

		return updatedSubscriber ? GenericResponseDTO.createSuccess(this.profileDao.retrieve(profile))
				: GenericResponseDTO.createFailed("Could not create profile");
	}

	private GenericResponseDTO<Profile> updateProfile(ProfileCreationRequestDTO request, HttpServletRequest headers) {
		Subscriber subscriber = this.retrieveUserFromToken(headers);
		boolean updated = false;
		updated = this.profileDao.update(new Profile.Builder().id(subscriber.getProfile().getId()).address(request.getAddress()).zipCode(request.getZipCode())
				.city(request.getCity()).gender(request.getGender()).language(request.getLanguage()).statement(request.getStatusMessage())
				.avatarLink(request.getAvatarLink()).builder());
		return updated ? GenericResponseDTO.createSuccess(this.profileDao.retrieve(subscriber.getProfile()))
				: GenericResponseDTO.createFailed("Could not update profile");
	}

	/**
	 * Activate subscriber.
	 *
	 * @param subscriberId
	 * @param headers
	 * @return
	 */
	@POST
	@Path("activate/{subscriberId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public GenericResponseDTO<Subscriber> activateSubscriber(@PathParam("subscriberId") String subscriberId, @Context HttpServletRequest headers) {
		if (subscriberId == null) {
			logger.warn("Invalid request.");
			return GenericResponseDTO.createFailed("Invalid request.");
		}

		Subscriber subscriber = this.subscriberDao.retrieveById(subscriberId);
		if (subscriber == null) {
			logger.warn("No subscriber was found. subscriber_id=" + subscriberId);
			return GenericResponseDTO.createFailed("No subscriber was found.");
		}
		boolean updated = false;
		Subscriber activateRequest = this.retrieveUserFromToken(headers);
		if (activateRequest != null && SubscriberRoleEnum.MASTER_ADMIN.compareTo(activateRequest.getRole()) == 0) {
			updated = this.subscriberDao.update(new Subscriber.SubscriberBuilder().id(subscriberId).status(SubscriberStatusEnum.ACTIVE).build());
		}
		return updated ? GenericResponseDTO.createSuccess(this.subscriberDao.retrieve(subscriber))
				: GenericResponseDTO.createFailed("Could not activate the subscriber");
	}

	/**
	 * Suspend subscriber.
	 *
	 * @param subscriberId
	 * @param headers
	 * @return
	 */
	@POST
	@Path("suspend/{subscriberId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public GenericResponseDTO<Subscriber> suspendSubscriber(@PathParam("subscriberId") String subscriberId, @Context HttpServletRequest headers) {
		if (subscriberId == null) {
			logger.warn("Invalid request.");
			return GenericResponseDTO.createFailed("Invalid request.");
		}

		Subscriber subscriber = this.subscriberDao.retrieveById(subscriberId);
		if (subscriber == null) {
			logger.warn("No subscriber was found. subscriber_id=" + subscriberId);
			return GenericResponseDTO.createFailed("No subscriber was found.");
		}

		boolean updated = false;
		Subscriber suspendRequest = this.retrieveUserFromToken(headers);
		if (suspendRequest != null && SubscriberRoleEnum.MASTER_ADMIN.compareTo(suspendRequest.getRole()) == 0) {
			updated = this.subscriberDao.update(new Subscriber.SubscriberBuilder().id(subscriberId).status(SubscriberStatusEnum.SUSPENDED).build());
		}
		return updated ? GenericResponseDTO.createSuccess(this.subscriberDao.retrieve(subscriber))
				: GenericResponseDTO.createFailed("Could not suspend the subscriber");
	}

	/**
	 * Delete subscriber.
	 *
	 * @param subscriberId
	 * @param headers
	 * @return
	 */
	@DELETE
	@Path("delete/{subscriberId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public GenericResponseDTO<Subscriber> deleteSubscriber(@PathParam("subscriberId") String subscriberId, @Context HttpServletRequest headers) {
		if (subscriberId == null) {
			logger.warn("Invalid request.");
			return GenericResponseDTO.createFailed("Invalid request.");
		}

		Subscriber subscriber = this.subscriberDao.retrieveById(subscriberId);
		if (subscriber == null) {
			logger.warn("No subscriber was found. subscriber_id=" + subscriberId);
			return GenericResponseDTO.createFailed("No subscriber was found.");
		}

		boolean isMasterAdmin = false;
		boolean isClientAdmin = false;
		boolean isUserSelf = false;
		Subscriber deleteRequest = this.retrieveUserFromToken(headers);
		if (deleteRequest != null && SubscriberRoleEnum.MASTER_ADMIN.compareTo(deleteRequest.getRole()) == 0) {
			isMasterAdmin = true;
		}

		if (deleteRequest != null && SubscriberRoleEnum.CLIENT_ADMIN.compareTo(deleteRequest.getRole()) == 0) {
			isClientAdmin = true;
		}

		if (deleteRequest != null && subscriberId.compareTo(deleteRequest.getId()) == 0) {
			isUserSelf = true;
		}

		boolean updated = false;

		if (isMasterAdmin || isClientAdmin || isUserSelf) {
			updated = this.subscriberDao.update(new Subscriber.SubscriberBuilder().id(subscriberId).status(SubscriberStatusEnum.DELETED).build());
		}
		return updated ? GenericResponseDTO.createSuccess(this.subscriberDao.retrieve(subscriber))
				: GenericResponseDTO.createFailed("Could not delete the subscriber");
	}

	/**
	 * Activate subscriber.
	 *
	 * @param request
	 * @param headers
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	@POST
	@Path("changePassword")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = true) })
	public GenericResponseDTO<Subscriber> changePassword(PasswordChangeRequestDTO request, @Context HttpServletRequest headers)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			this.validator.validatePasswordChangeRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Subscriber changeRequest = this.retrieveUserFromToken(headers);

		boolean updated = false;

		if (PasswordHash.validatePassword(request.getCurrentPassword(), changeRequest.getPassword())) {
			String hashPassword = PasswordHash.createHash(request.getNewPassword());
			updated = this.subscriberDao.update(new Subscriber.SubscriberBuilder().id(changeRequest.getId()).password(hashPassword).build());
		}
		return updated ? GenericResponseDTO.createSuccess(this.subscriberDao.retrieve(changeRequest))
				: GenericResponseDTO.createFailed("Could not change the password");
	}
	
	/**
	 * Determine device type from user agent.
	 *
	 * @param userAgent
	 * @return
	 */
	private DeviceTypeEnum getDeviceTypeFromUserAgent(String userAgent) {
		DeviceTypeEnum deviceType = null;
		if (userAgent != null) {
			String os = this.getOS(userAgent);
			if (os != null) {
				deviceType = DeviceTypeEnum.valueOf(os.toUpperCase());
			} else {
				String browser = this.getBrowser(userAgent);
				if (browser != null) {
					deviceType = DeviceTypeEnum.BROWSER;
				} else {
					deviceType = DeviceTypeEnum.UNKNOWN;
				}
			}
		} else {
			deviceType = DeviceTypeEnum.UNKNOWN;
		}

		return deviceType;
	}

	/**
	 * Determine client operational system from user agent.
	 *
	 * @param userAgent
	 * @return
	 */
	private String getOS(String userAgent) {
		String os = null;

		logger.info("User Agent for the request is===>" + userAgent);
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = null;
		}
		logger.info("Operating System======>" + os);
		return os;
	}

	/**
	 * Determine client browser from user agent.
	 *
	 * @param userAgent
	 * @return
	 */
	private String getBrowser(String userAgent) {
		String browser = null;
		if (userAgent.toLowerCase().contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (userAgent.toLowerCase().contains("safari") && userAgent.toLowerCase().contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (userAgent.toLowerCase().contains("opr") || userAgent.toLowerCase().contains("opera")) {
			if (userAgent.toLowerCase().contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (userAgent.toLowerCase().contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
		} else if (userAgent.toLowerCase().contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((userAgent.toLowerCase().indexOf("mozilla/7.0") > -1) || (userAgent.toLowerCase().indexOf("netscape6") != -1)
				|| (userAgent.toLowerCase().indexOf("mozilla/4.7") != -1) || (userAgent.toLowerCase().indexOf("mozilla/4.78") != -1)
				|| (userAgent.toLowerCase().indexOf("mozilla/4.08") != -1) || (userAgent.toLowerCase().indexOf("mozilla/3") != -1)) {
			// browser=(userAgent.substring(userAgent.indexOf("MSIE")).split("
			// ")[0]).replace("/",
			// "-");
			browser = "Netscape-?";

		} else if (userAgent.toLowerCase().contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (userAgent.toLowerCase().contains("rv")) {
			browser = "IE";
		} else {
			browser = null;
		}
		logger.info("Browser Name==========>" + browser);
		return browser;
	}

	/**
	 * Retrieve subscriber from session token.
	 *
	 * @param request
	 * @return
	 */
	private Subscriber retrieveUserFromToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_SECURITY_TOKEN);
		if (!StringUtils.isEmpty(token)) {
			Claims claims = this.jwt.parseJWT(token);
			if (claims != null) {
				return this.subscriberDao.retrieveById(claims.getId());
			} else {
				logger.warn("Couldn't extract information from session token=" + token);

			}
		} else {
			logger.warn("Session token was not mentioned");
		}

		return null;
	}
}
