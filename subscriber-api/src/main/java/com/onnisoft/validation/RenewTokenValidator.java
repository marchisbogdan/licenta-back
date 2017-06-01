package com.onnisoft.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.RenewTokenRequestDTO;

/**
 * Validates the renew token request by checking for inconsistent and wrong
 * input data.
 *
 * @author alexandru.mos
 * @date Mar 6, 2016 - 7:22:05 PM
 *
 */
@Component
public class RenewTokenValidator implements Validator<RenewTokenRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(RenewTokenValidator.class);

	@Autowired
	private Validator<String> userNameValidator;

	@Override
	public final void validate(RenewTokenRequestDTO request) throws ValidationException {
		if (request == null || StringUtils.isEmpty(request.getRenewTokenId())) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}
		this.userNameValidator.validate(request.getEmailOrUsername());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.alexsoft.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidationException {
	}
}
