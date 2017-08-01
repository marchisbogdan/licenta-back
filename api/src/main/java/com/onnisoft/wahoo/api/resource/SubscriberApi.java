package com.onnisoft.wahoo.api.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.ApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.StatisticsFilterRequestDTO;
import com.onnisoft.wahoo.api.utils.ApiDateTimeUtils;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.SubscriberCustomDao;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/subscribers")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Subscribers API")
public class SubscriberApi extends AbstractApi {

	@Autowired
	private SubscriberCustomDao subscriberCustomDao;

	@Autowired
	private ApiValidationUtil validator;

	@GET
	@Path("/search/expression/{expression}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Subscriber>> getSubscribersByName(@PathParam("expression") String expression) {

		if (StringUtils.isEmpty(expression)) {
			logger.warn("Invalid search expression." + expression);
			return GenericResponseDTO.createFailed("Invalid Request: Invalid search argument.");
		}

		List<Subscriber> resultedList = subscriberCustomDao.searchUserSubscribersByRegExp(expression);

		if (resultedList == null || resultedList.isEmpty()) {
			logger.warn("There are no subscribers that match the expression:" + expression);
			return GenericResponseDTO.createFailed("There are not subscribers that match the expression.");
		}

		return GenericResponseDTO.createSuccess(resultedList);
	}

	@POST
	@Path("/online")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<Subscriber>> getOnlineSubscriberslist(StatisticsFilterRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
			this.validator.validateStatisticsFilterRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		List<Subscriber> onlineSubscribers = this.subscriberCustomDao.getOnlineSubscribers(request.getCountryId(), request.getPartnerId());

		if (onlineSubscribers == null || onlineSubscribers.isEmpty()) {
			logger.warn("There aren't any subscribers online");
			return GenericResponseDTO.createFailed("There aren't any subscribers online");
		}

		return GenericResponseDTO.createSuccess(onlineSubscribers);
	}

	@POST
	@Path("/newly-signed")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<Subscriber>> getNewlySignedUpSubscribersList(StatisticsFilterRequestDTO request,
			@Context HttpServletRequest servlerRequest) {
		Subscriber subscriber = this.retrieveUserFromToken(servlerRequest);
		Subscriber partner = null;
		Date start_date = null;
		Date end_date = null;
		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
			this.validator.validateStatisticsFilterRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		List<Date> dates = new ArrayList<>();

		if (request.getYear() > 0) {
			dates = ApiDateTimeUtils.getDateIntervalBy(request.getYear(), request.getMonth(), request.getDay(), request.getHour());
			start_date = dates.get(0);
			end_date = dates.get(1);
		}

		if (!StringUtils.isEmpty(request.getPartnerId())) {
			partner = this.subscriberCustomDao.retrieveById(request.getPartnerId());
		}

		List<Subscriber> list = this.subscriberCustomDao.getNewlySignedUpSubscribers(start_date, end_date,
				new Subscriber.SubscriberBuilder().partner(partner).role(SubscriberRoleEnum.USER).build());

		if (list == null || list.isEmpty()) {
			logger.warn("There aren't any newly signed up subscribers!");
			return GenericResponseDTO.createFailed("There aren't any newly signed up subscribers!");
		}

		return GenericResponseDTO.createSuccess(list);

	}
}
