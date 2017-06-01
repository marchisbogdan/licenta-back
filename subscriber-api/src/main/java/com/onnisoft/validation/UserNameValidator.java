package com.onnisoft.validation;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;

/**
 * User name validator.
 *
 * @author alexandru.mos
 * @date Apr 11, 2016 - 3:17:29 PM
 *
 */
@Component
public class UserNameValidator implements Validator<String> {

	private final Logger logger = LoggerFactory.getLogger(UserNameValidator.class);

	private static final String REGEX_EMAIL_PATTERN = "([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})+";

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro.alexsoft.validation.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(String emailOrUsername) throws ValidationException {
		if (emailOrUsername == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}
		if (!StringUtils.isAlphanumeric(emailOrUsername) && !Pattern.matches(REGEX_EMAIL_PATTERN, emailOrUsername)) {
			logger.warn("Invalid email or username");
			throw new ValidationException("Invalid email or username");
		}
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
