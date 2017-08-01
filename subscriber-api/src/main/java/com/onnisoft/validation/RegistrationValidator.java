package com.onnisoft.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.subscriber.api.request.AuthenticationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RegistrationRequestDTO;

@Component
public class RegistrationValidator implements Validator<RegistrationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(RegistrationValidator.class);

	@Autowired
	private Validator<AuthenticationRequestDTO> authenticationValidator;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	private static final String REGEX_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$";

	@Override
	public void validate(RegistrationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}
		validateEmail(request);
		validateUsername(request);
		validatePassword(request);
		validateAgreement(request);
	}

	private void validateEmail(RegistrationRequestDTO request) throws ValidationException {
		this.authenticationValidator.validate(new AuthenticationRequestDTO(request.getEmail(), request.getPassword()));
		if (request.getEmail().length() < 6 || request.getEmail().length() > 70) {
			logger.warn("Invalid email length");
			throw new ValidationException("Invalid email length");
		}
		Subscriber subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().email(request.getEmail()).build());
		if (subscriber != null) {
			logger.warn("Email already exists for user " + subscriber.getId());
			throw new ValidationException("Email already exists.");
		}
	}

	private void validateUsername(RegistrationRequestDTO request) throws ValidationException {
		this.authenticationValidator.validate(new AuthenticationRequestDTO(request.getUserName(), request.getPassword()));
		if (request.getUserName().length() < 5 || request.getUserName().length() > 25) {
			logger.warn("Username invalid length");
			throw new ValidationException("Username invalid length");
		}
		Subscriber subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().userName(request.getUserName()).build());
		if (subscriber != null) {
			logger.warn("Username already exists for user " + subscriber.getId());
			throw new ValidationException("Username already exists.");
		}
	}

	private void validatePassword(RegistrationRequestDTO request) throws ValidationException {
		if (request.getPassword().matches(REGEX_PASSWORD_PATTERN)) {
			logger.warn("Invalid password!");
			throw new ValidationException("Invalid password!");
		}
	}

	private void validateAgreement(RegistrationRequestDTO request) throws ValidationException {
		if (!request.isAgreedToTermsAndConditions()) {
			logger.warn("Not agreed to terms and conditions");
			throw new ValidationException("Not agreed to terms and conditions");
		}
	}

	@Override
	public void validate() throws ValidationException {
	}
}
