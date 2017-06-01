package com.onnisoft.wahoo.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.validation.ApiValidationUtil;
import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.EventsSelectionRequestDTO;
import com.onnisoft.wahoo.api.request.RoundsSelectionRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Event API")
public class EventApi extends AbstractApi {
	@Autowired
	private Dao<VirtualCompetition> virtualCompetitionDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private ApiValidationUtil validator;

	@POST
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public @ResponseBody GenericResponseDTO<List<Event>> selectEvents(EventsSelectionRequestDTO request, @Context HttpServletRequest servletRequest) {
		try {
			this.validator.validateEventsSelectionRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		List<Event> events = new ArrayList<>();

		if (request.isSelectedAll()) {
			virtualCompetitionDao.retrieveById(request.getVirtualCompetitionId()).getRounds()
					.forEach(r -> events.addAll(eventDao.retrieveList(new Event.Builder().round(r).build())));
		} else {
			request.getEventIds().forEach(e -> events.add(eventDao.retrieveById(e)));
		}

		boolean updated = virtualCompetitionDao
				.update(new VirtualCompetition.VirtualCompetitionBuilder().id(request.getVirtualCompetitionId()).events(events).build());
		return updated ? GenericResponseDTO.createSuccess(events) : GenericResponseDTO.createFailed("Could not add events to competition");
	}

	@POST
	@Path("/by-multiple-rounds")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Event>> getEventsByMultipleRounds(RoundsSelectionRequestDTO request, @Context HttpHeaders headers) {
		try {
			this.validator.validateRoundsSelectionRequest(request);
		} catch (ValidationException e) {
			logger.warn("Validation exception", e);
			return GenericResponseDTO.createFailed(e.getMessage());
		}

		final Season season = this.seasonDao.retrieveById(request.getSeasonId());
		final List<Round> rounds = this.roundDao.retrieveList(new Round.Builder().season(season).build());

		if (!request.isSelectedAll()) {
			rounds.removeIf(r -> !request.getRoundIds().contains(r.getId()));
		}

		List<Event> events = new ArrayList<Event>();
		for (Round round : rounds) {
			List<Event> results = this.eventDao.retrieveList(new Event.Builder().round(round).build());
			if (!CollectionUtils.isEmpty(results)) {
				events.addAll(results);
			}
		}

		RealCompetition realCompetition = season.getCompetition();

		boolean updated = virtualCompetitionDao.update(new VirtualCompetition.VirtualCompetitionBuilder().id(request.getVirtualCompetitionId())
				.realCompetition(realCompetition).season(season).rounds(rounds).build());

		return updated ? GenericResponseDTO.createSuccess(events) : GenericResponseDTO.createFailed("Could not update virtual competition");
	}
}
