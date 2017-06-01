package com.onnisoft.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.RoundsSelectionRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

/**
 * 
 * Validates the rounds selection request by checking for inconsistent and wrong
 * input data.
 *
 * @author mbozesan
 * @date 7 Nov 2016 - 12:16:19
 *
 */
@Component
public class RoundsSelectionValidator implements Validator<RoundsSelectionRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(RoundsSelectionValidator.class);

	@Autowired
	private Dao<VirtualCompetition> competitionDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Override
	public void validate(RoundsSelectionRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (request.getRoundIds() == null) {
			logger.warn("No rounds chosen");
			throw new ValidationException("No round chosen");
		}

		if (StringUtils.isEmpty(request.getVirtualCompetitionId())) {
			logger.warn("Invalid competition id");
			throw new ValidationException("Invalid competition id");
		} else {
			validateVirtualCompetition(request.getVirtualCompetitionId());
		}

		if (StringUtils.isEmpty(request.getSeasonId())) {
			logger.warn("Invalid season id");
			throw new ValidationException("Invalid season id");
		} else {
			validateSeason(request.getSeasonId());
		}

		if (!request.isSelectedAll()) {
			validateRounds(request.getRoundIds());
		}
	}

	private void validateSeason(String seasonId) throws ValidationException {
		Season season = this.seasonDao.retrieveById(seasonId);
		if (season == null) {
			logger.warn("There is no season with the id:" + seasonId);
			throw new ValidationException("There is no season with the id:" + seasonId);
		}
	}

	private void validateVirtualCompetition(String virtualCompetitionId) throws ValidationException {
		VirtualCompetition competition = competitionDao.retrieveById(virtualCompetitionId);
		if (competition == null) {
			logger.warn("No competition with this id " + virtualCompetitionId);
			throw new ValidationException("No competition with this id " + virtualCompetitionId);
		}
	}

	private void validateRounds(List<String> roundIds) throws ValidationException {
		for (String roundId : roundIds) {
			if (StringUtils.isEmpty(roundId)) {
				logger.warn("Invalid round id");
				throw new ValidationException("Invalid round id");
			}

			Round round = roundDao.retrieveById(roundId);
			if (round == null) {
				logger.warn("Round for one of the ids not found " + roundId);
				throw new ValidationException("Round for one of the ids not found " + roundId);
			}
		}
	}

	@Override
	public void validate() throws ValidationException {

	}

}
