package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Sport;
import com.onnisoft.wahoo.subscriber.api.request.AddToFavoriteRequestDTO;

/**
 * 
 * Validates the add to favorite request by checking for inconsistent and wrong
 * input data.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:55:14
 *
 */
@Component
public class AddToFavoriteValidator implements Validator<AddToFavoriteRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(RegistrationValidator.class);

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<RealCompetition> realCompetitionDao;

	@Autowired
	private Dao<Sport> sportDao;

	@Override
	public void validate(AddToFavoriteRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (!StringUtils.isEmpty(request.getFavoritePlayerId())) {
			validatePlayer(request.getFavoritePlayerId());
		}

		if (!StringUtils.isEmpty(request.getFavoriteRealCompetitorId())) {
			validateCompetitor(request.getFavoriteRealCompetitorId());
		}

		if (!StringUtils.isEmpty(request.getFavoriteRealCompetitionId())) {
			validateCompetition(request.getFavoriteRealCompetitionId());
		}

		if (!StringUtils.isEmpty(request.getFavoriteSportId())) {
			validateSport(request.getFavoriteSportId());
		}
	}

	private void validateSport(String favoriteSportId) throws ValidationException {
		Sport sport = sportDao.retrieveById(favoriteSportId);
		if (sport == null) {
			logger.warn("Invalid sport id");
			throw new ValidationException("Invalid sport id");
		}
	}

	private void validateCompetition(String favoriteRealCompetitionId) throws ValidationException {
		RealCompetition competition = realCompetitionDao.retrieveById(favoriteRealCompetitionId);
		if (competition == null) {
			logger.warn("Invalid competition id");
			throw new ValidationException("Invalid competition id");
		}
	}

	private void validateCompetitor(String favoriteRealCompetitorId) throws ValidationException {
		RealCompetitor competitor = realCompetitorDao.retrieveById(favoriteRealCompetitorId);
		if (competitor == null) {
			logger.warn("Invalid competitor id");
			throw new ValidationException("Invalid competitor id");
		}
	}

	private void validatePlayer(String favoritePlayerId) throws ValidationException {
		Player player = playerDao.retrieveById(favoritePlayerId);
		if (player == null) {
			logger.warn("Invalid player id");
			throw new ValidationException("Invalid player id");
		}
	}

	@Override
	public void validate() throws ValidationException {
		// TODO Auto-generated method stub
	}
}
