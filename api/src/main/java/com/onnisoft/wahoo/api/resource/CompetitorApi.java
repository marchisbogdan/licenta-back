package com.onnisoft.wahoo.api.resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.ApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.CompetitorCreationRequestDTO;
import com.onnisoft.wahoo.api.request.TeamFormationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;
import com.onnisoft.wahoo.model.document.enums.FieldFormationEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerGamePositionEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/competitors")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Competitor API")
public class CompetitorApi extends AbstractApi {

	@Autowired
	private Dao<VirtualCompetitor> competitorDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<VirtualCompetitorTeam> virtualCompetitorTeamDao;

	@Autowired
	private Dao<VirtualCompetition> competitionDao;

	@Autowired
	private Dao<VirtualCompetitor> virtualCompetitorDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private ApiValidationUtil validator;

	@POST
	@Path("competitor/create")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<VirtualCompetitor> createVirtualCompetitor(CompetitorCreationRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		try {
			this.validator.validateCompetitorRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		VirtualCompetition competition = competitionDao.retrieveById(request.getCompetitionId());

		VirtualCompetitor competitor = new VirtualCompetitor.Builder().name(request.getName()).subscriber(subscriber).rank(0).totalPoints(0).value(0)
				.virtualCompetition(competition).toCreate().build();
		competitor = this.competitorDao.create(competitor);

		return GenericResponseDTO.createSuccess(this.competitorDao.retrieve(competitor));
	}

	@POST
	@Path("competitor/team/create")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<VirtualCompetitor> createTeamForVirtualCompetitor(TeamFormationRequestDTO request,
			@Context HttpServletRequest servletRequest) {

		try {
			this.validator.validateTeamFormationRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		Subscriber subscriber = super.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		VirtualCompetitor competitor = this.competitorDao.retrieveById(request.getCompetitorId());

		if (!subscriber.getId().equals(competitor.getSubscriber().getId())) {
			logger.warn("This subscriber can't make modifications to the specified competitor! subscriber:" + subscriber.getId() + " ,competitor:"
					+ competitor.getId());
			return GenericResponseDTO.createFailed("This subscriber can't make modifications to the specified competitor!");
		}

		Round round = this.roundDao.retrieveById(request.getRoundId());

		List<Player> team = new LinkedList<>();
		Map<String, PlayerGamePositionEnum> positions = new HashMap<>();
		for (String playerId : request.getPlayersPositions().keySet()) {
			Player player = playerDao.retrieveById(playerId);
			PlayerGamePositionEnum position = PlayerGamePositionEnum.valueOf(request.getPlayersPositions().get(playerId));

			team.add(player);
			positions.put(playerId, position);
		}

		VirtualCompetitorTeam virtualCompetitorTeam = this.virtualCompetitorTeamDao.create(new VirtualCompetitorTeam.Builder().virtualCompetitor(competitor)
				.round(round).team(team).positions(positions).formation(FieldFormationEnum.valueOf(request.getFormation())).toCreate().build());

		if (virtualCompetitorTeam == null) {
			logger.warn("Couldn't create a VirtualCompetitorTeam entity for competitor:" + competitor.getId() + " and round:" + round.getId());
			GenericResponseDTO.createFailed("Couldn't create the a team for competitor:" + competitor.getId() + " and round:" + round.getId());
		}

		return GenericResponseDTO.createSuccess(this.competitorDao.retrieve(competitor));
	}

	@GET
	@Path("/virtual-competitors/round/id/{roundId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<VirtualCompetitor>> getVirtualCompetitorsByRound(@Context HttpServletRequest servletRequest,
			@PathParam("roundId") String roundId) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		if (StringUtils.isEmpty(roundId)) {
			logger.warn("The roundId is empty!");
			return GenericResponseDTO.createFailed("The roundId is empty!");
		}

		Round round = this.roundDao.retrieveById(roundId);

		if (round == null) {
			logger.warn("Invalid roundId!");
			return GenericResponseDTO.createFailed("Invalid roundId!");
		}

		List<VirtualCompetitor> result = this.virtualCompetitorTeamDao.retrieveList(new VirtualCompetitorTeam.Builder().round(round).build()).stream()
				.map(vct -> vct.getVirtualCompetitor()).distinct().collect(Collectors.toList());

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There are no virtual competitors in this round!");
			return GenericResponseDTO.createFailed("There are no virtual competitors in this round!");
		}

		return GenericResponseDTO.createSuccess(result);
	}

	@GET
	@Path("/virtual-competitors-teams/virtual-competitor/round/")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<VirtualCompetitorTeam>> getVirtualCompetitorsTeamsByRound(@Context HttpServletRequest servletRequest,
			@QueryParam("roundId") String roundId, @QueryParam("virtualCompetitorId") String virtualCompetitorId) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		if (StringUtils.isEmpty(roundId)) {
			logger.warn("The roundId is empty!");
			return GenericResponseDTO.createFailed("The roundId is empty!");
		}

		Round round = this.roundDao.retrieveById(roundId);

		if (round == null) {
			logger.warn("Invalid roundId!");
			return GenericResponseDTO.createFailed("Invalid roundId!");
		}

		if (StringUtils.isEmpty(virtualCompetitorId)) {
			logger.warn("The virtualCompetitorId is empty!");
			return GenericResponseDTO.createFailed("The virtualCompetitorId is empty!");
		}

		VirtualCompetitor virtualCompetitor = this.virtualCompetitorDao.retrieveById(virtualCompetitorId);

		if (virtualCompetitor == null) {
			logger.warn("Invalid virtualCompetitorId!");
			return GenericResponseDTO.createFailed("Invalid virtualCompetitorId!");
		}

		List<VirtualCompetitorTeam> result = this.virtualCompetitorTeamDao
				.retrieveList(new VirtualCompetitorTeam.Builder().virtualCompetitor(virtualCompetitor).round(round).build());

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There are no virtual competitors in this round!");
			return GenericResponseDTO.createFailed("There are no virtual competitors in this round!");
		}

		return GenericResponseDTO.createSuccess(result);
	}

	@GET
	@Path("/virtual-competition/id/{virtualCompetitionId}")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<VirtualCompetitor>> getVirtualCompetitorsByVirtualCompetitionId(@Context HttpServletRequest servletRequest,
			@PathParam("virtualCompetitionId") String virtualCompetitionId) {

		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		if (StringUtils.isEmpty(virtualCompetitionId)) {
			logger.warn("The virtualCompetitionId is empty!");
			return GenericResponseDTO.createFailed("The virtualCompetitionId is empty!");
		}

		VirtualCompetition virtualCompetition = this.competitionDao.retrieveById(virtualCompetitionId);

		if (virtualCompetition == null) {
			logger.warn("Invalid virtualCompetitionId!");
			return GenericResponseDTO.createFailed("Invalid virtualCompetitionId!");
		}

		List<VirtualCompetitor> result = this.competitorDao.retrieveList(new VirtualCompetitor.Builder().virtualCompetition(virtualCompetition).build());

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There are no virtual competitors in the virtual competition!");
			return GenericResponseDTO.createFailed("There are no virtual competitors in the virtual competition!");
		}

		return GenericResponseDTO.createSuccess(result);
	}

	@GET
	@Path("/ranking/sorted/")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<VirtualCompetitor>> getVirtualCompetitorRanking(@Context HttpServletRequest servletRequest, @QueryParam("index") int index,
			@QueryParam("amount") int amount) {
		Subscriber subscriber = this.retrieveUserFromToken(servletRequest);

		String sortParam = "totalPoints";

		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			return GenericResponseDTO.createFailed("The subscriber couldn't be retrieved!");
		}

		List<VirtualCompetitor> result = this.competitorDao.retrieveSortedListBYInterval(Sort.Direction.DESC, sortParam, index, amount,
				VirtualCompetitor.class);

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There are no Virtual Competitors in this range. skip:" + ((index - 1) * amount) + " amount:" + amount);
			return GenericResponseDTO.createFailed("There are no Virtual Competitors in this range!");
		}

		return GenericResponseDTO.createSuccess(result);
	}
}
