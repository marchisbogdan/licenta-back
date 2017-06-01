package com.onnisoft.validation;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.PrizeSettingDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

@Component
public class PrizeSettingValidator implements Validator<PrizeSettingDTO> {

	private final Logger logger = LoggerFactory.getLogger(PrizeSettingValidator.class);

	@Autowired
	private Dao<VirtualCompetition> virtualCompetitionDao;

	@Override
	public void validate(PrizeSettingDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (request.getVirtualCompetitionId() != null) {
			validateVirtualCompetitionId(request.getVirtualCompetitionId());
		}

		if (request.getSponsorLogoLink() != null) {
			validateLink(request.getSponsorLogoLink());
		}

		if (request.getPrizeImageLink() != null) {
			validateLink(request.getPrizeImageLink());
		}
	}

	private void validateVirtualCompetitionId(String virtualCompetitionId) throws ValidationException {
		VirtualCompetition virtualCompetition = this.virtualCompetitionDao.retrieveById(virtualCompetitionId);
		if (virtualCompetition == null) {
			logger.warn("Invalid virtual competition id!");
			throw new ValidationException("Invalid virtual competition id!");
		}
	}

	private void validateLink(String link) throws ValidationException {
		if (link.isEmpty()) {
			logger.warn("URL is empty!");
			throw new ValidationException("URL is empty!");
		}

		try {
			new URL(link);
		} catch (MalformedURLException e) {
			logger.warn("Invalid url");
			throw new ValidationException("Invalid URL");
		}
	}

	@Override
	public void validate() throws ValidationException {

	}
}
