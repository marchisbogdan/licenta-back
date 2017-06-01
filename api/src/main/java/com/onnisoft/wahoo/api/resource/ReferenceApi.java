package com.onnisoft.wahoo.api.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/references")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Reference API")
public class ReferenceApi extends AbstractApi {

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<Sport> sportDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<RealCompetition> realCompetitionDao;

	@GET
	@Path("countries")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Country>> getCountries() {

		List<Country> countries = this.countryDao.retrieveList(null);

		return GenericResponseDTO.createSuccess(countries);
	}

	@GET
	@Path("players")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Player>> getPlayers() {

		List<Player> players = this.playerDao.retrieveList(null);

		return GenericResponseDTO.createSuccess(players);
	}

	@GET
	@Path("sports")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Sport>> getSports() {

		List<Sport> sports = this.sportDao.retrieveList(null);

		return GenericResponseDTO.createSuccess(sports);
	}

	@GET
	@Path("seasons/competition/id/{competitionId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Season>> getSeasons(@PathParam("competitionId") String competitionId) {

		if (StringUtils.isEmpty(competitionId)) {
			logger.warn("Competition id is empty or null!");
			return GenericResponseDTO.createFailed("Competition id is empty or null!");
		}

		RealCompetition realCompetition = this.realCompetitionDao.retrieveById(competitionId);

		if (realCompetition == null) {
			logger.warn("There are no competitions with the id:" + competitionId);
			return GenericResponseDTO.createFailed("There are no competitions with the id:" + competitionId);
		}

		List<Season> seasons = this.seasonDao.retrieveList(new Season.Builder().competition(realCompetition).builder());

		if (CollectionUtils.isEmpty(seasons)) {
			logger.warn("There are no seasons for the provided competition.");
			return GenericResponseDTO.createFailed("There are no seasons for the provided competition.");
		}

		return GenericResponseDTO.createSuccess(seasons);
	}

	@GET
	@Path("rounds/season/id/{seasonId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Round>> getRounds(@PathParam("seasonId") String seasonId) {

		if (StringUtils.isEmpty(seasonId)) {
			logger.warn("Season id is empty or null!");
			return GenericResponseDTO.createFailed("Season id is empty or null!");
		}

		Season season = this.seasonDao.retrieveById(seasonId);

		if (season == null) {
			logger.warn("There is no season with the id:" + seasonId);
			return GenericResponseDTO.createFailed("There is no season with the id:" + seasonId);
		}

		List<Round> rounds = this.roundDao.retrieveList(new Round.Builder().season(season).build());

		if (CollectionUtils.isEmpty(rounds)) {
			logger.warn("There are no rounds for the provided season.");
			return GenericResponseDTO.createFailed("There are no rounds for the provided season.");
		}

		return GenericResponseDTO.createSuccess(rounds);
	}

	@GET
	@Path("events/round/id/{roundId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Event>> getEvents(@PathParam("roundId") String roundId) {

		if (StringUtils.isEmpty(roundId)) {
			logger.warn("Round id is empty or null!");
			return GenericResponseDTO.createFailed("Round id is empty or null!");
		}

		Round round = this.roundDao.retrieveById(roundId);

		if (round == null) {
			logger.warn("There is no round with the id:" + roundId);
			return GenericResponseDTO.createFailed("There is no round with the id:" + roundId);
		}

		List<Event> events = this.eventDao.retrieveList(new Event.Builder().round(round).build());

		if (CollectionUtils.isEmpty(events)) {
			logger.warn("There are no events for the provided round.");
			return GenericResponseDTO.createFailed("There are no events for the provided round.");
		}

		return GenericResponseDTO.createSuccess(events);
	}
}