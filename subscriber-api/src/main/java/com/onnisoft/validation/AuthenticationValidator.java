package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.AuthenticationRequestDTO;

/**
 * 
 * Validates the authentication request by checking for inconsistent and wrong
 * input data.
 *
 * @author mbozesan
 * @date 21 Oct 2016 - 10:54:40
 *
 */

@Component
public class AuthenticationValidator implements Validator<AuthenticationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);

	@Autowired
	private Validator<String> userNameValidator;

	@Override
	public final void validate(AuthenticationRequestDTO request) throws ValidationException {
		if (request == null || StringUtils.isEmpty(request.getPassword())) {
			logger.warn("Invalid request or empty password");
			throw new ValidationException("Invalid request or empty password");
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
