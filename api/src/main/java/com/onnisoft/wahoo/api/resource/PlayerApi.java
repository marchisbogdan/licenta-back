package com.onnisoft.wahoo.api.resource;

import java.util.Comparator;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.ApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.PlayerCustomDao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerStats;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Subscriber;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Player API")
public class PlayerApi extends AbstractApi {

	@Autowired
	private PlayerCustomDao playerDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private Dao<PlayerStats> playerStatsDao;

	@Autowired
	private ApiValidationUtil validator;

	@GET
	@Path("/by/competitor/id/{competitorId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Player>> getPlayersByCompetitorId(@PathParam("competitorId") String competitorId) {

		if (StringUtils.isEmpty(competitorId)) {
			logger.warn("The competitorId parameter is empty or null:" + competitorId);
			return GenericResponseDTO.createFailed("The competitorId parameter is empty or null:" + competitorId);
		}

		RealCompetitor realCompetitor = realCompetitorDao.retrieveById(competitorId);

		if (realCompetitor == null) {
			logger.warn("There is no competitor with the provided id:" + competitorId);
			return GenericResponseDTO.createFailed("There is no competitor with the provided id:" + competitorId);
		}

		List<Player> playersList = playerDao.retrieveList(new Player.Builder().competitor(realCompetitor).build());

		if (playersList == null || playersList.isEmpty()) {
			logger.warn("There are no players playing for the specified competitor");
			return GenericResponseDTO.createFailed("There are no players playing for the specified competitor");
		}

		return GenericResponseDTO.createSuccess(playersList);
	}

	@GET
	@Path("/by/name/competitor")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Player>> getPlayersByNameAndCompetitorId(@QueryParam("competitorId") String competitorId, @QueryParam("name") String name) {

		if (StringUtils.isEmpty(competitorId)) {
			logger.warn("The competitorId parameter is empty or null:" + competitorId);
			return GenericResponseDTO.createFailed("The competitorId parameter is empty or null:" + competitorId);
		}

		RealCompetitor realCompetitor = realCompetitorDao.retrieveById(competitorId);

		if (realCompetitor == null) {
			logger.warn("There is no competitor with the provided id:" + competitorId);
			return GenericResponseDTO.createFailed("There is no competitor with the provided id:" + competitorId);
		}

		if (StringUtils.isEmpty(name)) {
			logger.warn("The name parameter is empty or null:" + name);
			return GenericResponseDTO.createFailed("The name parameter is empty or null:" + name);
		}

		List<Player> playersList = playerDao.getPlayersByNameRegexAndCompetitor(name, realCompetitor);

		if (CollectionUtils.isEmpty(playersList)) {
			logger.warn("There are no players found for the specified competitor");
			return GenericResponseDTO.createFailed("There are no players found for the specified competitor");
		}

		return GenericResponseDTO.createSuccess(playersList);
	}

	@GET
	@Path("/by/round/id/{roundId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Player>> getPlayersByTeamsInRound(@PathParam("roundId") String roundId) {

		if (StringUtils.isEmpty(roundId)) {
			logger.warn("The roundId parameter is empty or null:" + roundId);
			return GenericResponseDTO.createFailed("The roundId parameter is empty or null:" + roundId);
		}

		Round round = this.roundDao.retrieveById(roundId);

		if (round == null) {
			logger.warn("There is no round with the provided id:" + roundId);
			return GenericResponseDTO.createFailed("There is no round with the provided id:" + roundId);
		}

		List<Event> events = this.eventDao.retrieveList(new Event.Builder().round(round).build());
		List<RealCompetitor> competitors = events.stream().map(e -> this.eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(e).build()))
				.flatMap(List::stream).map(ec -> ec.getCompetitor()).distinct().collect(Collectors.toList());

		List<Player> playersList = competitors.stream()
				.map(realCompetitor -> this.playerDao.retrieveList(new Player.Builder().competitor(realCompetitor).build())).flatMap(List::stream)
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(playersList)) {
			logger.warn("There are no players found playing in the specified round!");
			return GenericResponseDTO.createFailed("There are no players found playing in the specified round!");
		}

		return GenericResponseDTO.createSuccess(playersList);
	}

	@GET
	@Path("/event-statistics/event")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<PlayerEventStatistics> getPlayerStatisticsForEvent(@QueryParam("playerId") String playerId,
			@QueryParam("eventId") String eventId) {

		Player player = playerDao.retrieveById(playerId);

		if (player == null) {
			logger.warn("There is no player with the provided id:" + playerId);
			return GenericResponseDTO.createFailed("There is no player with the provided id:" + playerId);
		}

		Event event = eventDao.retrieveById(eventId);

		if (event == null) {
			logger.warn("There is no event with the provided id:" + eventId);
			return GenericResponseDTO.createFailed("There is no event with the provided id:" + eventId);
		}

		PlayerEventStatistics playerEventStatistics = playerEventStatisticsDao
				.retrieve(new PlayerEventStatistics.Builder().player(player).event(event).build());

		if (playerEventStatistics == null) {
			logger.warn("There is no statistics for the specified event and player.");
			return GenericResponseDTO.createFailed("There is no statistics for the specified event and player.");
		}

		return GenericResponseDTO.createSuccess(playerEventStatistics);
	}

	@GET
	@Path("/event-statistics/season/limited")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<PlayerEventStatistics>> getPlayerStatisticsForSeason(@QueryParam("playerId") String playerId,
			@QueryParam("seasonId") String seasonId, @QueryParam("nrOfEventsReturned") long nrOfEventsReturned) {

		Player player = playerDao.retrieveById(playerId);

		if (player == null) {
			logger.warn("There is no player with the provided id:" + playerId);
			return GenericResponseDTO.createFailed("There is no player with the provided id:" + playerId);
		}

		Season season = seasonDao.retrieveById(seasonId);

		if (season == null) {
			logger.warn("There is no season with the provided id:" + seasonId);
			return GenericResponseDTO.createFailed("There is no season with the provided id:" + seasonId);
		}

		Comparator<Event> comparator = (e1, e2) -> Long.compare(e1.getStartDateTime().getTime(), e2.getStartDateTime().getTime());

		List<Round> roundsList = this.roundDao.retrieveList(new Round.Builder().season(season).build());

		List<Event> eventsList = roundsList.stream().map(r -> this.eventDao.retrieveList(new Event.Builder().round(r).build())).flatMap(List::stream)
				.filter(e -> e.getStartDateTime().before(new Date())).sorted(comparator.reversed())
				.limit(nrOfEventsReturned <= 0 ? Long.MAX_VALUE : nrOfEventsReturned).collect(Collectors.toList());

		List<PlayerEventStatistics> result = eventsList.stream()
				.map(e -> this.playerEventStatisticsDao.retrieve(new PlayerEventStatistics.Builder().event(e).player(player).build()))
				.collect(Collectors.toList());

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There are no statistics for the specified season:" + season.getId() + " and player:" + player.getId());
			return GenericResponseDTO.createFailed("There are no statistics for the specified season and player!");
		}

		return GenericResponseDTO.createSuccess(result);
	}

	@GET
	@Path("/player-statistics/season")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<PlayerStats> getPlayerStatistics(@QueryParam("playerId") String playerId, @QueryParam("seasonId") String seasonId) {

		Player player = playerDao.retrieveById(playerId);

		if (player == null) {
			logger.warn("There is no player with the provided id:" + playerId);
			return GenericResponseDTO.createFailed("There is no player with the provided id:" + playerId);
		}

		Season season = seasonDao.retrieveById(seasonId);

		if (season == null) {
			logger.warn("There is no season with the provided id:" + seasonId);
			return GenericResponseDTO.createFailed("There is no season with the provided id:" + seasonId);
		}

		PlayerStats playerStats = playerStatsDao.retrieve(new PlayerStats.Builder().player(player).season(season).build());

		if (playerStats == null) {
			logger.warn("There are no stats for the specified player.");
			return GenericResponseDTO.createFailed("There are no stats for the specified player.");
		}

		return GenericResponseDTO.createSuccess(playerStats);
	}

	@POST
	@Path("/update/player/value")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<Player> updatePlayerValueByPlayerId(@QueryParam("playerId") String playerId, @QueryParam("value") Double value,
			@Context HttpServletRequest servletRequest) {
		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		try {
			this.validator.validateAdminSubscriberValidator(subscriber);
		} catch (ValidationException e) {
			logger.warn("Validation exception:" + e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		if (StringUtils.isEmpty(playerId)) {
			logger.warn("Player id is null or empty!");
			return GenericResponseDTO.createFailed("Player id is null or empty!");
		}

		Player player = this.playerDao.retrieveById(playerId);

		if (player == null) {
			logger.warn("There is no player with the id:" + playerId);
			return GenericResponseDTO.createFailed("There is no player with the id:" + playerId);
		}

		boolean updated = this.playerDao.update(new Player.Builder().id(playerId).value(value).build());

		if (!updated) {
			logger.warn("The player:" + playerId + " couldn't be updated!");
			return GenericResponseDTO.createFailed("The player:" + playerId + " couldn't be updated!");
		}

		return GenericResponseDTO.createSuccess(this.playerDao.retrieveById(playerId));
	}
}
