package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.CompetitorCreationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;

/**
 * 
 * Validates the competitor creation request by checking for inconsistent and
 * wrong input data.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 16:53:14
 *
 */
@Component
public class CompetitorCreationValidator implements Validator<CompetitorCreationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(CompetitorCreationValidator.class);

	@Autowired
	private Dao<VirtualCompetitor> virtualCompetitorDao;
	@Autowired
	private Dao<VirtualCompetition> virtualCompetitionDao;

	@Override
	public void validate(CompetitorCreationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (StringUtils.isEmpty(request.getCompetitionId())) {
			logger.warn("Competition id is null or empty!");
			throw new ValidationException("Invalid request: Competition id is null or empty!");
		}

		if (StringUtils.isEmpty(request.getName())) {
			logger.warn("Name is null or empty!");
			throw new ValidationException("Invalid request: Name is null or empty");
		}
		validateName(request.getCompetitionId(), request.getName());

		validateParticipationPossibility(request);

	}

	private void validateParticipationPossibility(CompetitorCreationRequestDTO request) throws ValidationException {

		// TODO: finish implementing the method after creating the
		// VirtualCompetitorTeam class

		VirtualCompetition virtualCompetition = this.virtualCompetitionDao.retrieveById(request.getCompetitionId());
		int nr_of_competitors = 0;
		nr_of_competitors = this.virtualCompetitorDao.retrieveList(new VirtualCompetitor.Builder().virtualCompetition(virtualCompetition).build()).size();
		if (nr_of_competitors >= virtualCompetition.getMaxNumParticipants()) {
			logger.warn("The max number of participants has been reached:" + virtualCompetition.getMaxNumParticipants());
			throw new ValidationException("The max number of participants has been reached:" + virtualCompetition.getMaxNumParticipants());
		}
	}

	private void validateName(String competitionId, String name) throws ValidationException {
		VirtualCompetition competition = virtualCompetitionDao.retrieveById(competitionId);
		if (competition == null) {
			logger.warn("Invalid competitionId");
			throw new ValidationException("Invalid competitionId");
		}
		VirtualCompetitor competitor = virtualCompetitorDao.retrieve(new VirtualCompetitor.Builder().name(name).virtualCompetition(competition).build());
		if (competitor != null) {
			logger.warn("A team with this name already exists in this competition!");
			throw new ValidationException("A team with this name already exists in this competition!");
		}

	}

	@Override
	public void validate() throws ValidationException {
	}
}
