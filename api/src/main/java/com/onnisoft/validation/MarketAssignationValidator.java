package com.onnisoft.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.MarketAssignationRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

/**
 * 
 * Validates the market assignation request by checking for inconsistent and
 * wrong input data.
 *
 * @author mbozesan
 * @date 10 Nov 2016 - 17:44:30
 *
 */
@Component
public class MarketAssignationValidator implements Validator<MarketAssignationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(MarketAssignationValidator.class);

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private Dao<VirtualCompetition> virtualCompetitionDao;

	@Autowired
	private Dao<Market> marketDao;

	@Override
	public void validate(MarketAssignationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (StringUtils.isEmpty(request.getName())) {
			logger.warn("The name of the market must not be empty!");
			throw new ValidationException("The name of the market must not be empty!");
		} else {
			validateName(request.getName());
		}

		if (request.getTargetAge() < 18) {
			logger.warn("People with age lower than 18 cannot be targeted");
			throw new ValidationException("People with age lower than 18 cannot be targeted");
		}

		if (!request.getCountryIds().isEmpty()) {
			validateCountryIds(request.getCountryIds());
		}

		if (!StringUtils.isEmpty(request.getPartnerId())) {
			validatePartnerId(request.getPartnerId());
		}

		if (!StringUtils.isEmpty(request.getVirtualCompetitionId())) {
			validateVirtualCompetitionId(request.getVirtualCompetitionId());
		} else {
			logger.warn("Invalid virtual competition id!");
			throw new ValidationException("Invalid virtual competition id!");
		}
	}

	private void validateName(String name) throws ValidationException {
		Market market = this.marketDao.retrieve(new Market.Builder().name(name).build());
		if (market != null) {
			logger.warn("The name:" + name + " is currently taken.");
			throw new ValidationException("The name:" + name + " is currently taken.");
		}
	}

	private void validateVirtualCompetitionId(String virtualCompetitionId) throws ValidationException {
		VirtualCompetition competition = this.virtualCompetitionDao.retrieveById(virtualCompetitionId);
		if (competition == null) {
			logger.warn("There is no virtual competition with the id:" + virtualCompetitionId);
			throw new ValidationException("There is no virtual competition with the id:" + virtualCompetitionId);
		}
	}

	private void validatePartnerId(String partnerId) throws ValidationException {
		Subscriber partner = this.subscriberDao.retrieveById(partnerId);
		if (partner == null) {
			logger.warn("There is no partner with the id:" + partnerId);
			throw new ValidationException("There is no partner with the id:" + partnerId);
		}
	}

	private void validateCountryIds(List<String> countryIds) throws ValidationException {
		for (String c : countryIds) {
			if (countryDao.retrieveById(c) == null) {
				logger.warn("Country id " + c + " is invalid");
				throw new ValidationException("Country id " + c + " is invalid");
			}
		}
	}

	@Override
	public void validate() throws ValidationException {

	}

}
