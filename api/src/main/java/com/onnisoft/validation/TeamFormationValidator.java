package com.onnisoft.validation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.TeamFormationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;
import com.onnisoft.wahoo.model.document.enums.FieldFormationEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerGamePositionEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;

@Component
public class TeamFormationValidator implements Validator<TeamFormationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(TeamFormationValidator.class);

	@Autowired
	private Dao<Player> playerDao;
	@Autowired
	private Dao<VirtualCompetitor> virtualCompetitorDao;
	@Autowired
	private Dao<Round> roundDao;
	@Autowired
	private Dao<Event> eventDao;
	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;
	@Autowired
	private Dao<VirtualCompetitorTeam> virtualCompetitorTeamDao;

	@Override
	public void validate(TeamFormationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (StringUtils.isEmpty(request.getCompetitorId())) {
			logger.warn("Competitor id is null or empty!");
			throw new ValidationException("Invalid request: Competitor id is null or empty!");
		}
		validateCompetitor(request.getCompetitorId());

		if (StringUtils.isEmpty(request.getRoundId())) {
			logger.warn("Round id is null or empty!");
			throw new ValidationException("Invalid request: Round id is null or empty!");
		}
		validateRound(request.getRoundId());

		if (StringUtils.isEmpty(request.getFormation())) {
			logger.warn("Formation is null or empty!");
			throw new ValidationException("Invalid request: Formation is null or empty!");
		}
		validateFormation(request.getFormation());

		if (request.getPlayersPositions() == null) {
			logger.warn("Player positions weren't submitted!");
			throw new ValidationException("Invalid request: player positions weren't submitted!");
		}
		validatePosition(request.getPlayersPositions());

		validatePlayersInFormation(request.getPlayersPositions(), request.getFormation());

		validatePlayersFromCurrentRoundCompetitors(request.getPlayersPositions(), request.getRoundId());

		validateParticipationPossibility(request);

	}

	private void validateParticipationPossibility(TeamFormationRequestDTO request) throws ValidationException {
		VirtualCompetitor virtualCompetitor = this.virtualCompetitorDao.retrieveById(request.getCompetitorId());
		VirtualCompetition virtualCompetition = virtualCompetitor.getCompetition();

		// check if the Virtual competitor team creation possibilities for the
		// specified round have been depleted
		Round round = this.roundDao.retrieveById(request.getRoundId());
		VirtualCompetitorTeam existentTeam = this.virtualCompetitorTeamDao
				.retrieve(new VirtualCompetitorTeam.Builder().virtualCompetitor(virtualCompetitor).round(round).build());
		if (existentTeam != null) {
			logger.warn("There is already a team created for round:" + round.getId() + " of competition:" + virtualCompetition.getId());
			throw new ValidationException("There is already a team created for round:" + round.getId() + " of competition:" + virtualCompetition.getId());
		}
	}

	private void validatePlayersFromCurrentRoundCompetitors(Map<String, String> playersPositions, String roundId) throws ValidationException {
		Round round = this.roundDao.retrieveById(roundId);

		List<Event> events = this.eventDao.retrieveList(new Event.Builder().round(round).build());

		List<RealCompetitor> competitors = events.stream().map(e -> this.eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(e).build()))
				.flatMap(List::stream).map(ec -> ec.getCompetitor()).distinct().collect(Collectors.toList());

		for (String playerId : playersPositions.keySet()) {
			Player player = playerDao.retrieveById(playerId);

			if (!competitors.contains(player.getCompetitor())) {
				logger.warn("Player is not from the available competitors of this round! Player:" + player);
				throw new ValidationException("Player is not from the available competitors of this round! PlayerId:" + playerId);
			}
		}

	}

	private void validateFormation(String formation) throws ValidationException {
		if (!EnumUtils.isValidEnum(FieldFormationEnum.class, formation)) {
			logger.warn("Invalid formation!");
			throw new ValidationException("Invalid formation!");
		}
	}

	private void validateCompetitor(String competitorId) throws ValidationException {
		VirtualCompetitor virtualCompetitor = this.virtualCompetitorDao.retrieveById(competitorId);
		if (virtualCompetitor == null) {
			logger.warn("Invalid competitor Id!");
			throw new ValidationException("Invalid competitor Id!");
		}
	}

	private void validateRound(String roundId) throws ValidationException {
		Round round = this.roundDao.retrieveById(roundId);
		if (round == null) {
			logger.warn("Invalid round id!");
			throw new ValidationException("Invalid round id!");
		}
	}

	private void validatePosition(Map<String, String> playersPositions) throws ValidationException {
		for (String playerId : playersPositions.keySet()) {
			Player player = playerDao.retrieveById(playerId);
			if (player == null) {
				logger.warn("Invalid player id:" + playerId);
				throw new ValidationException("Invalid player id:" + playerId);
			}
			if (!EnumUtils.isValidEnum(PlayerGamePositionEnum.class, playersPositions.get(player.getId()))) {
				logger.warn("Invalid position name:" + playersPositions.get(player.getId()) + " for player:" + player.getFirstName());
				throw new ValidationException("Invalid position name:" + playersPositions.get(player.getId()) + " for player:" + player.getFirstName());
			}
			if (!isCompatiblePosition(player.getPosition(), PlayerGamePositionEnum.valueOf(playersPositions.get(player.getId())))) {
				logger.warn("Invalid position for player:" + player.getFirstName());
				throw new ValidationException("Invalid position for player " + player.getFirstName());
			}
		}
	}

	private boolean isCompatiblePosition(PlayerPositionEnum realLifePosition, PlayerGamePositionEnum assignedPosition) {
		if (PlayerPositionEnum.defensePositions().contains(assignedPosition.name())) {
			if (!PlayerPositionEnum.defensePositions().contains(realLifePosition.name())) {
				return false;
			}
		}
		if (PlayerPositionEnum.midfieldPositions().contains(assignedPosition.name())) {
			if (!PlayerPositionEnum.midfieldPositions().contains(realLifePosition.name())) {
				return false;
			}
		}

		if (PlayerPositionEnum.attackPositions().contains(assignedPosition.name())) {
			if (!PlayerPositionEnum.attackPositions().contains(realLifePosition.name())) {
				return false;
			}
		}
		if (assignedPosition.name().equals("GOALKEEPER") && !realLifePosition.name().equals("GOALKEEPER")) {
			return false;
		}
		return true;
	}

	private void validatePlayersInFormation(Map<String, String> playersPositions, String formation) throws ValidationException {
		String[] pos = formation.split("");
		int def = Integer.parseInt(pos[1]), mid = Integer.parseInt(pos[2]), att = Integer.parseInt(pos[3]), actual_def = 0, actual_mid = 0, actual_att = 0,
				actual_goalkeeper = 0;
		for (String playerId : playersPositions.keySet()) {
			PlayerGamePositionEnum position = PlayerGamePositionEnum.valueOf(playersPositions.get(playerId));
			if (PlayerGamePositionEnum.defensePositions().contains(position.name())) {
				actual_def++;
			} else if (PlayerGamePositionEnum.midfieldPositions().contains(position.name())) {
				actual_mid++;
			} else if (PlayerGamePositionEnum.attackPositions().contains(position.name())) {
				actual_att++;
			} else if (position.name().equals(PlayerGamePositionEnum.GOALKEEPER.name())) {
				actual_goalkeeper++;
			}
		}
		if (actual_goalkeeper != 1 || def != actual_def || mid != actual_mid || att != actual_att) {
			logger.warn("The formation and the choosen players do not relate!");
			throw new ValidationException("The formation and the choosen players do not relate!");
		}
	}

	@Override
	public void validate() throws ValidationException {
	}

}
