package com.onnisoft.wahoo.api.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Subscriber;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/real-competitors")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Real Competitors Api")
public class RealCompetitorApi extends AbstractApi {

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@GET
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<RealCompetitor>> getRealCompetitors() {

		List<RealCompetitor> realCompetitors = this.realCompetitorDao.retrieveList(null);

		return GenericResponseDTO.createSuccess(realCompetitors);
	}

	@GET
	@Path("/by/season/id/{seasonId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<Set<RealCompetitor>> getRealCompetitorsBySeasonId(@Context HttpServletRequest servletRequest,
			@PathParam("seasonId") String seasonId) {

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		Set<RealCompetitor> teamsInSeason = new HashSet<>();
		// validate seasonID
		Season season = seasonDao.retrieveById(seasonId);

		if (season == null) {
			logger.warn("There is no season with the following ID:" + seasonId);
			return GenericResponseDTO.createFailed("There is no season with the following ID:" + seasonId);
		}
		// get a list of rounds with that id
		List<Round> roundsList = roundDao.retrieveList(new Round.Builder().season(season).build());
		// get a list of events with the rounds ids
		for (Round round : roundsList) {
			List<Event> eventsList = eventDao.retrieveList(new Event.Builder().round(round).build());
			for (Event event : eventsList) {
				// get a list of eventCompetitors with the events ids
				List<EventCompetitor> eventCompetitorList = eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(event).build());
				for (EventCompetitor eventCompetitor : eventCompetitorList) {
					// duplicates removed by using a Set
					teamsInSeason.add(eventCompetitor.getCompetitor());
				}
			}
		}
		if (teamsInSeason.isEmpty()) {
			logger.warn("There are no teams in the season with the following ID:" + seasonId);
			return GenericResponseDTO.createFailed("There are no teams in the season with the following ID:" + seasonId);
		}
		return GenericResponseDTO.createSuccess(teamsInSeason);
	}

	@GET
	@Path("/by/event/id/{eventId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<RealCompetitor>> getRealCompetitorsByEventId(@Context HttpServletRequest servletRequest,
			@PathParam("eventId") String eventId) {

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		Event event = eventDao.retrieveById(eventId);

		if (event == null) {
			logger.warn("There is no event with the following ID:" + eventId);
			return GenericResponseDTO.createFailed("There is no event with the following ID:" + eventId);
		}

		List<EventCompetitor> eventCompetitors = eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(event).build());
		List<RealCompetitor> realCompetitors = eventCompetitors.stream().map(e -> e.getCompetitor()).distinct().collect(Collectors.toList());

		if (realCompetitors.isEmpty()) {
			logger.warn("There are no teams in the event with the following ID:" + eventId);
			return GenericResponseDTO.createFailed("There are no teams in the event with the following ID:" + eventId);
		}

		return GenericResponseDTO.createSuccess(realCompetitors);
	}

	@GET
	@Path("/search/by-name/{expression}")
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<RealCompetitor>> getRealCompetitorsByRegExp(@Context HttpServletRequest servletRequest,
			@PathParam("expression") String expression) {

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		if (StringUtils.isEmpty(expression)) {
			logger.warn("Invalid search expression." + expression);
			return GenericResponseDTO.createFailed("Invalid Request: Invalid search argument.");
		}

		List<RealCompetitor> result = realCompetitorDao.retireveByRegex(expression, "name", RealCompetitor.class);

		if (result == null || result.isEmpty()) {
			logger.warn("There are no subscribers that match the expression:" + expression);
			return GenericResponseDTO.createFailed("There are not subscribers that match the expression.");
		}
		return GenericResponseDTO.createSuccess(result);
	}

	// TODO: add end-point for getting the realCompetitors by season with their
	// statistics.
}
