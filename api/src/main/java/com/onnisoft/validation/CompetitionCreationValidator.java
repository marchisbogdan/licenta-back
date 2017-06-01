package com.onnisoft.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.CompetitionCreationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.Prize;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

/**
 * 
 * Validates the competition creation request by checking for inconsistent and
 * wrong input data.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 16:52:41
 *
 */
@Component
public class CompetitionCreationValidator implements Validator<CompetitionCreationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(GroupCreationValidator.class);

	@Autowired
	private Dao<VirtualCompetition> competitionDao;

	@Autowired
	private Dao<Market> marketDao;

	@Autowired
	private Dao<Prize> prizeDao;

	@Autowired
	private Dao<Event> eventDao;

	@Override
	public void validate(CompetitionCreationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (!StringUtils.isEmpty(request.getAvatarLink())) {
			String url = request.getAvatarLink().trim();
			validateLink(url);
		}
		if (!StringUtils.isEmpty(request.getSecondaryName())) {
			if (request.getSecondaryName().length() > 15 || request.getSecondaryName().length() < 3) {
				logger.warn("The secondary name must be between 3 and 15 characters long");
				throw new ValidationException("The secondary name must be between 3 and 15 characters long");
			}
		}
		if (!StringUtils.isEmpty(request.getName())) {
			if (request.getName().length() < 10) {
				logger.warn("Name must be at least 10 characters long");
				throw new ValidationException("Name must be at least 10 characters long");
			} else if (request.getName().length() > 65) {
				logger.warn("Length exceeded. Please enter a value of at most 65 characters in length");
				throw new ValidationException("Length exceeded. Please enter a value of at most 65 characters in length");
			}
		}

		if (request.getLaunchDateTime() == null) {
			logger.warn("The launching date for the competition is not set.");
			throw new ValidationException("The launching date for the competition is not set.");
		}
		validateName(request.getName());
		if (!StringUtils.isEmpty(request.getPrizeId())) {
			validatePrizeId(request.getPrizeId());
		}
		if (!StringUtils.isEmpty(request.getMarketId())) {
			validateMarketId(request.getMarketId());
		}

		if (CollectionUtils.isEmpty(request.getEventsIds())) {
			logger.warn("There were no events with the provided IDs.");
			throw new ValidationException("There were no events with the provided IDs.");
		} else {
			validateEvents(request.getEventsIds());
		}
	}

	/**
	 * Check if the events are part of the same season
	 * 
	 * @param eventsIds
	 * @throws ValidationException
	 */
	private void validateEvents(List<String> eventsIds) throws ValidationException {
		List<Season> seasons = eventsIds.stream().map(e_id -> this.eventDao.retrieveById(e_id).getRound().getSeason()).distinct().collect(Collectors.toList());
		if (seasons.size() != 1) {
			logger.warn("The events must be part of the same season!");
			throw new ValidationException("The events must be part of the same season!");
		}
	}

	private void validateMarketId(String marketId) throws ValidationException {
		Market market = this.marketDao.retrieveById(marketId);
		if (market == null) {
			logger.warn("There is no market with the id:" + marketId);
			throw new ValidationException("There is no market with the id:" + marketId);
		}

	}

	private void validatePrizeId(String prizeId) throws ValidationException {
		Prize prize = this.prizeDao.retrieveById(prizeId);
		if (prize == null) {
			logger.warn("There is no prize with the id:" + prizeId);
			throw new ValidationException("There is no prize with the id:" + prizeId);
		}
	}

	private void validateName(String name) throws ValidationException {
		VirtualCompetition competition = competitionDao.retrieve(new VirtualCompetition.VirtualCompetitionBuilder().name(name).build());
		if (competition != null) {
			logger.warn("A competition with this name already exists");
			throw new ValidationException("A competition with this name already exists");
		}
	}

	@Override
	public void validate() throws ValidationException {
	}

	private void validateLink(String avatarUrl) throws ValidationException {
		if (avatarUrl.isEmpty()) {
			logger.warn("URL is empty!");
			throw new ValidationException("URL is empty!");
		}

		try {
			new URL(avatarUrl);
		} catch (MalformedURLException e) {
			logger.warn("Invalid url");
			throw new ValidationException("Invalid URL");
		}
	}
}
