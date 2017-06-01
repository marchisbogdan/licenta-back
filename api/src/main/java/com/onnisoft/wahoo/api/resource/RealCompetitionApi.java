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
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.Sport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/real-competitions")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Real Competitions Api")
public class RealCompetitionApi extends AbstractApi {

	@Autowired
	private Dao<Sport> sportDao;

	@Autowired
	private Dao<RealCompetition> realCompetitionDao;

	@GET
	@Path("/sport/id/{sportId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<RealCompetition>> retrieveRealCompetitionsBySportId(@PathParam("sportId") String sportId) {

		if (StringUtils.isEmpty(sportId)) {
			logger.warn("Sport id is empty or null!");
			return GenericResponseDTO.createFailed("Sport id is empty or null!");
		}

		Sport sport = this.sportDao.retrieveById(sportId);

		if (sport == null) {
			logger.warn("There is no sport with the provided id.");
			return GenericResponseDTO.createFailed("There is no sport with the provided id.");
		}

		List<RealCompetition> realCompetitions = this.realCompetitionDao.retrieveList(new RealCompetition.Builder().sport(sport).build());

		if (CollectionUtils.isEmpty(realCompetitions)) {
			logger.warn("There are no real competitions for the provided sport id.");
			return GenericResponseDTO.createFailed("There are no real competitions for the provided sport id.");
		}

		return GenericResponseDTO.createSuccess(realCompetitions);
	}
}
