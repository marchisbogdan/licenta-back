package com.onnisoft.wahoo.api.resource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.PasswordHash;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.ApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.CompetitionCreationRequestDTO;
import com.onnisoft.wahoo.api.request.GroupCreationRequestDTO;
import com.onnisoft.wahoo.api.request.GroupJoinRequestDTO;
import com.onnisoft.wahoo.api.request.MarketAssignationRequestDTO;
import com.onnisoft.wahoo.api.request.PrizeSettingDTO;
import com.onnisoft.wahoo.api.request.StatisticsFilterRequestDTO;
import com.onnisoft.wahoo.api.utils.ApiDateTimeUtils;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.VirtualCompetitonCustomDao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.Prize;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitionGroup;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/virtual-competition")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Virtual Competition API")
public class VirtualCompetitionApi extends AbstractApi {

	@Autowired
	private VirtualCompetitonCustomDao competitionDao;

	@Autowired
	private Dao<VirtualCompetitor> competitorDao;

	@Autowired
	private Dao<VirtualCompetitionGroup> competitionGroupDao;

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private Dao<Market> marketDao;

	@Autowired
	private Dao<Prize> prizeDao;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private ApiValidationUtil validator;

	@POST
	@Path("competition/create")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<VirtualCompetition> createVirtualCompetition(CompetitionCreationRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);
		Prize prize = null;
		Market market = null;

		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
			this.validator.validateCompetitionCreationRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		List<Event> selectedEvents = new ArrayList<>();
		request.getEventsIds().forEach(e -> selectedEvents.add(eventDao.retrieveById(e)));

		if (!StringUtils.isEmpty(request.getPrizeId())) {
			prize = prizeDao.retrieveById(request.getPrizeId());
		}

		if (!StringUtils.isEmpty(request.getMarketId())) {
			market = marketDao.retrieveById(request.getMarketId());
		}

		// determine the rounds and season based on the given events

		List<Round> rounds = request.getEventsIds().stream().map(id -> this.eventDao.retrieveById(id).getRound()).distinct().collect(Collectors.toList());
		Season season = rounds.get(0).getSeason();

		/*
		 * Determine the the date when the first event starts and the date when
		 * the last event ends
		 */

		LongSummaryStatistics timeStatistics = selectedEvents.stream().filter(e -> Objects.nonNull(e) && Objects.nonNull(e.getStartDateTime()))
				.mapToLong(e -> e.getStartDateTime().toInstant().toEpochMilli()).summaryStatistics();

		Date startDateTime = Date.from(new Timestamp(timeStatistics.getMin()).toInstant());
		Date endDateTime = Date.from(new Timestamp(timeStatistics.getMax()).toInstant());

		if (request.getLaunchDateTime().after(startDateTime)) {
			logger.warn("The launching date for the competition is set after the starting date of the first event.");
			return GenericResponseDTO.createFailed("The launching date for the competition is set after the starting date of the first event.");
		}

		VirtualCompetition competition = new VirtualCompetition.VirtualCompetitionBuilder().name(request.getName()).secondaryName(request.getSecondaryName())
				.type(request.getType()).maxNumParticipants(request.getMaxNumParticipants()).maxEntries(request.getMaxEntries()).info(request.getInfo())
				.avatarLink(request.getAvatarLink()).season(season).rounds(rounds).events(selectedEvents).prize(prize).market(market)
				.rulesAndPointSystem(request.getRulesAndPointSystem()).territoryLimitedAccess(request.isTerritoryLimitedAccess())
				.linkLimitedAccess(request.isLinkLimitedAccess()).promotion(request.isPromotion()).startDateTime(startDateTime).endDateTime(endDateTime)
				.launchDateTime(request.getLaunchDateTime()).toCreate().build();
		competition = this.competitionDao.create(competition);
		return GenericResponseDTO.createSuccess(this.competitionDao.retrieve(competition));
	}

	@POST
	@Path("/prize")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Prize> setVirtualCompetitionPrize(PrizeSettingDTO request, @Context HttpServletRequest servletRequest) {
		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		if (subscriber.getRole().compareTo(SubscriberRoleEnum.MASTER_ADMIN) != 0) {
			logger.warn("Only the application admin can create a prize!");
			return GenericResponseDTO.createFailed("Only the application admin can create a prize!");
		}

		try {
			this.validator.validatePrizeSettingRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Prize prize = prizeDao.create(new Prize.PrizeBuilder().type(request.getType()).name(request.getName()).sponsorName(request.getSponsorName())
				.sponsorLogoLink(request.getSponsorLogoLink()).prizeImageLink(request.getPrizeImageLink()).info(request.getInfo()).value(request.getValue())
				.numberOfPrizes(request.getNumberOfPrizes()).normalPool(request.getNormalPool()).guaranteedPool(request.getGuaranteedPool())
				.satellitePool(request.getSatellitePool()).sponsorPool(request.getSponsorPool()).toCreate().build());

		if (prize == null) {
			logger.warn("The prize hasn't been saved properly!");
			return GenericResponseDTO.createFailed("The prize hasn't been saved properly!");
		}

		if (!StringUtils.isEmpty(request.getVirtualCompetitionId())) {
			boolean updated = competitionDao
					.update(new VirtualCompetition.VirtualCompetitionBuilder().id(request.getVirtualCompetitionId()).prize(prize).build());

			return updated ? GenericResponseDTO.createSuccess(prize)
					: GenericResponseDTO.createFailed("Could not assign prize:" + prize.getId() + " to competition:" + request.getVirtualCompetitionId());
		}

		return GenericResponseDTO.createSuccess(prize);
	}

	@POST
	@Path("/market")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<Market> virtualCompetitionMarketAssignation(MarketAssignationRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		if (subscriber.getRole().compareTo(SubscriberRoleEnum.MASTER_ADMIN) != 0) {
			logger.warn("Only an application admin can create a merket!");
			return GenericResponseDTO.createFailed("Only an application admin can create a merket!");
		}

		try {
			this.validator.validateMarketAssignationRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		List<Country> countries = new ArrayList<>();
		request.getCountryIds().forEach(c -> countries.add(countryDao.retrieveById(c)));

		Subscriber partner = this.subscriberDao.retrieveById(request.getPartnerId());

		Market market = marketDao.create(new Market.Builder().name(request.getName()).countries(countries).partner(partner)
				.targetGender(request.getTargetGender()).targetInterests(request.getTargetInterests()).targetAge(request.getTargetAge()).toCreate().build());

		if (!StringUtils.isEmpty(request.getVirtualCompetitionId())) {
			boolean updated = competitionDao
					.update(new VirtualCompetition.VirtualCompetitionBuilder().id(request.getVirtualCompetitionId()).market(market).build());

			return updated ? GenericResponseDTO.createSuccess(market) : GenericResponseDTO.createFailed("Could not assign market to competition");
		} else {
			return GenericResponseDTO.createSuccess(market);
		}

	}

	@GET
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<VirtualCompetition>> getVirtualCompetitions(@Context HttpServletRequest headers) {

		Subscriber subscriber = super.retrieveUserFromToken(headers);
		List<VirtualCompetition> virtualCompetitions = null;

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		if (subscriber.getRole().compareTo(SubscriberRoleEnum.MASTER_ADMIN) == 0 || subscriber.getRole().compareTo(SubscriberRoleEnum.CLIENT_ADMIN) == 0) {
			virtualCompetitions = competitionDao.retrieveList(null);
		} else {
			Country country = subscriber.getCountry();
			Subscriber partner = subscriber.getPartner();
			List<Country> countryList = Collections.singletonList(country);
			virtualCompetitions = competitionDao
					.getRestrictedVirtualCompetitionsBySubscriberInfo(new Market.Builder().partner(partner).countries(countryList).build());
		}

		if (CollectionUtils.isEmpty(virtualCompetitions)) {
			logger.warn("No competition were retrieved");
			return GenericResponseDTO.createFailed("No competition were retrieved");
		}

		return GenericResponseDTO.createSuccess(virtualCompetitions);
	}

	@POST
	@Path("group/create")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<VirtualCompetitionGroup> createGroup(GroupCreationRequestDTO request, @Context HttpServletRequest servletRequest) {
		try {
			this.validator.validateGroupCreationRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		String password = null;
		try {
			password = PasswordHash.createHash(request.getPassword());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			logger.warn("unable to create password hash", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		VirtualCompetition competition = competitionDao.retrieveById(request.getCompetitionId());

		VirtualCompetitionGroup virtualCompetitionGroup = new VirtualCompetitionGroup.Builder().name(request.getName()).password(password)
				.virtualCompetition(competition).creator(subscriber).toCreate().build();
		virtualCompetitionGroup = competitionGroupDao.create(virtualCompetitionGroup);

		if (virtualCompetitionGroup == null) {
			logger.warn("There was a DB problem and the information was not saved.");
			return GenericResponseDTO.createFailed("There was a DB problem and the information was not saved.");
		}

		boolean updated = competitorDao.update(new VirtualCompetitor.Builder().id(request.getCompetitionId()).group(virtualCompetitionGroup).build());

		return updated ? GenericResponseDTO.createSuccess(this.competitionGroupDao.retrieve(virtualCompetitionGroup))
				: GenericResponseDTO.createFailed("The team could not be registered to the group");
	}

	@POST
	@Path("group/join")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<VirtualCompetitionGroup> joinGroup(GroupJoinRequestDTO request, @Context HttpServletRequest servletRequest) {
		VirtualCompetitionGroup group = competitionGroupDao.retrieveById(request.getCompetitionGroupId());

		if (!StringUtils.isEmpty(group.getPassword())) {
			if (StringUtils.isEmpty(request.getPassword())) {
				return GenericResponseDTO.createFailed("Invalid credentials");
			}
			try {
				if (!PasswordHash.validatePassword(request.getPassword(), group.getPassword())) {
					return GenericResponseDTO.createFailed("Invalid credentials");
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
				logger.warn("Cound not check password.", ex);
				return GenericResponseDTO.createFailed(ex.getMessage());
			}
		}

		boolean updated = competitorDao.update(new VirtualCompetitor.Builder().id(request.getCompetitorId()).group(group).build());

		return updated ? GenericResponseDTO.createSuccess(this.competitionGroupDao.retrieve(group))
				: GenericResponseDTO.createFailed("The team could not be registered to the group");
	}

	@POST
	@Path("open-competitions")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<VirtualCompetition>> getOpenVirtualCompetitions(StatisticsFilterRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);
		List<Date> dates = new ArrayList<>();
		Date start_date = null;
		Date end_date = null;

		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
			this.validator.validateStatisticsFilterRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation Exception");
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		if (request.getYear() > -1) {
			dates = ApiDateTimeUtils.getDateIntervalBy(request.getYear(), request.getMonth(), request.getDay(), request.getHour());
			start_date = dates.get(0);
			end_date = dates.get(1);
		}

		List<VirtualCompetition> openVirualCompetitions = this.competitionDao.getOpenVirtualCompetitions(start_date, end_date);

		if (CollectionUtils.isEmpty(openVirualCompetitions)) {
			logger.warn("There aren't any open Virtual Competitions.");
			return GenericResponseDTO.createFailed("There aren't any open Virtual Competitions.");
		}

		return GenericResponseDTO.createSuccess(openVirualCompetitions);
	}

	@POST
	@Path("restricted-competitions")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<VirtualCompetition>> getRestrictedVirtualCompetitions(StatisticsFilterRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);
		List<Date> dates = new ArrayList<>();
		Date start_date = null;
		Date end_date = null;
		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
			this.validator.validateStatisticsFilterRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation Exception");
			return GenericResponseDTO.createFailed(e.getMessage());
		}
		Country country = this.countryDao.retrieveById(request.getCountryId());
		Subscriber partner = this.subscriberDao.retrieveById(request.getPartnerId());
		List<Country> countryList = Collections.singletonList(country);

		if (request.getYear() > -1) {
			dates = ApiDateTimeUtils.getDateIntervalBy(request.getYear(), request.getMonth(), request.getDay(), request.getHour());
			start_date = dates.get(0);
			end_date = dates.get(1);
		}

		List<VirtualCompetition> restrictedVirualCompetitions = this.competitionDao.getRestrictedVirtualCompetitions(start_date, end_date,
				new Market.Builder().countries(countryList).partner(partner).build());

		if (CollectionUtils.isEmpty(restrictedVirualCompetitions)) {
			logger.warn("There aren't any restricted Virtual Competitions.");
			return GenericResponseDTO.createFailed("There aren't any restricted Virtual Competitions.");
		}

		return GenericResponseDTO.createSuccess(restrictedVirualCompetitions);
	}
}
