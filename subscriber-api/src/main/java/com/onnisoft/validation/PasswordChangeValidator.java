package com.onnisoft.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.PasswordChangeRequestDTO;


@Component
public class PasswordChangeValidator implements Validator<PasswordChangeRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationValidator.class);

	private static final String REGEX_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";

	@Override
	public void validate(PasswordChangeRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (StringUtils.isEmpty(request.getCurrentPassword())) {
			logger.warn("Invalid request or empty current password");
			throw new ValidationException("Invalid request or empty current password");
		}

		if (request.getNewPassword().compareTo(request.getVerifyPassword()) != 0) {
			logger.warn("Passwords do not match");
			throw new ValidationException("Passwords do not match");
		}

		validatePassword(request.getNewPassword());
	}

	private void validatePassword(String password) throws ValidationException {
		if (password.matches(REGEX_PASSWORD_PATTERN)) {
			logger.warn("Invalid password!");
			throw new ValidationException("Invalid password!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.wahoo.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidationException {

	}
}