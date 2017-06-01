package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.StatisticsFilterRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

/**
 * 
 * Validate the DTO for statistical data requests.
 *
 * @author bogdan.marchis
 * @date 12 Jan 2017 - 11:59:46
 *
 */
@Component
public class StatisticsFilterValidator implements Validator<StatisticsFilterRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(StatisticsFilterValidator.class);

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private Dao<Subscriber> partnerDao;

	@Override
	public void validate(StatisticsFilterRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (request.getYear() != -1) {
			if (request.getYear() < -1) {
				logger.warn("The 'Year' field must be a valid number!");
				throw new ValidationException("The 'Year' field must be a valid number!");
			}
		}
		if (request.getMonth() != -1) {
			if (request.getYear() == -1) {
				logger.warn("When filtering by month, the year must be set!");
				throw new ValidationException("When filtering by month, the year must be set!");
			}
			if (request.getMonth() < 0 || request.getMonth() > 11) {
				logger.warn("The 'Month' field must be between 0 and 11");
				throw new ValidationException("The 'Month' field must be between 0 and 11");
			}
		}
		if (request.getDay() != -1) {
			if (request.getMonth() == -1 || request.getYear() == -1) {
				logger.warn("When filtering by day, the year and month must be set!");
				throw new ValidationException("When filtering by day, the year and month must be set!");
			}
			if (request.getDay() < 1 || request.getDay() > 31) {
				logger.warn("The 'Day' field must be between 1 and 31");
				throw new ValidationException("The 'Day' field must be between 1 and 31");
			}
		}
		if (request.getHour() != -1) {
			if (request.getDay() == -1 || request.getMonth() == -1 || request.getYear() == -1) {
				logger.warn("When filtering by hour, the year, month and day must be set!");
				throw new ValidationException("When filtering by hour, the year, month and day must be set!");
			}
			if (request.getHour() < 0 || request.getHour() > 23) {
				logger.warn("The 'Hour' field must be between 0 and 23");
				throw new ValidationException("The 'Hour' field must be between 0 and 23");
			}
		}
		if (!StringUtils.isEmpty(request.getCountryId())) {
			validateCountryId(request.getCountryId());
		}
		if (!StringUtils.isEmpty(request.getPartnerId())) {
			validatePartnerId(request.getPartnerId());
		}
	}

	private void validatePartnerId(String partnerId) throws ValidationException {
		Subscriber partner = this.partnerDao.retrieveById(partnerId);
		if (partner == null || partner.getRole().equals(SubscriberRoleEnum.CLIENT_ADMIN)) {
			logger.warn("There is no partner with the following ID:" + partnerId);
			throw new ValidationException("There is no partner with the following ID:" + partnerId);
		}
	}

	private void validateCountryId(String countryId) throws ValidationException {
		Country country = this.countryDao.retrieveById(countryId);
		if (country == null) {
			logger.warn("There is no country with the following ID:" + countryId);
			throw new ValidationException("There is no country with the following ID:" + countryId);
		}
	}

	@Override
	public void validate() throws ValidationException {
	}
}
