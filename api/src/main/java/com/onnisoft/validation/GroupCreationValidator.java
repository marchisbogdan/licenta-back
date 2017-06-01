package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.GroupCreationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitionGroup;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;

/**
 * 
 * Validates the group creation request by checking for inconsistent and wrong
 * input data.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:49:49
 *
 */
@Component
public class GroupCreationValidator implements Validator<GroupCreationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(GroupCreationValidator.class);

	private static final String REGEX_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";

	@Autowired
	private Dao<VirtualCompetitionGroup> groupDao;

	@Autowired
	private Dao<VirtualCompetition> competitionDao;

	@Autowired
	private Dao<VirtualCompetitor> competitorDao;

	@Override
	public void validate(GroupCreationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (StringUtils.isEmpty(request.getCompetitionId())) {
			logger.warn("The competition id is empty");
			throw new ValidationException("The competition id is empty");
		}

		if (!StringUtils.isEmpty(request.getName())) {
			if (request.getName().length() > 15) {
				logger.warn("Name must be at most 15 characters long");
				throw new ValidationException("Name must be at most 15 characters long");
			}
			if (request.getName().length() < 3) {
				logger.warn("Name must be at least 3 characters long");
				throw new ValidationException("Name must be at least 3 characters long");
			}
			validateName(request.getCompetitionId(), request.getName());
		}

		if (!StringUtils.isEmpty(request.getPassword())) {
			validatePassword(request.getPassword());
		}

		if (!StringUtils.isEmpty(request.getCompetitorId())) {
			validateCompetitor(request.getCompetitorId());
		}
	}

	private void validateCompetitor(String competitorId) throws ValidationException {
		VirtualCompetitor competitor = competitorDao.retrieveById(competitorId);
		if (competitor == null) {
			logger.warn("Invalid competitorId");
			throw new ValidationException("Invalid competitorId");
		}
	}

	private void validateName(String competitionId, String name) throws ValidationException {
		VirtualCompetition competition = competitionDao.retrieveById(competitionId);
		if (competition == null) {
			logger.warn("Invalid competitionId");
			throw new ValidationException("Invalid competitionId");
		}
		VirtualCompetitionGroup existingGroup = groupDao.retrieve(new VirtualCompetitionGroup.Builder().name(name).virtualCompetition(competition).build());
		if (existingGroup != null) {
			logger.warn("A group with this name already exists in this competition!");
			throw new ValidationException("A group with this name already exists in this competition!");
		}
	}

	private void validatePassword(String password) throws ValidationException {
		if (!password.matches(REGEX_PASSWORD_PATTERN)) {
			logger.warn("Invalid password!");
			throw new ValidationException("Invalid password!");
		}
	}

	@Override
	public void validate() throws ValidationException {

	}

}
