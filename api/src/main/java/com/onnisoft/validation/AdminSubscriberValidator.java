package com.onnisoft.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

@Component
public class AdminSubscriberValidator implements Validator<Subscriber> {

	private final Logger logger = LoggerFactory.getLogger(AdminSubscriberValidator.class);

	@Override
	public void validate(Subscriber subscriber) throws ValidationException {
		if (subscriber == null) {
			logger.warn("The subscriber couldn't be retrieved!");
			throw new ValidationException("The subscriber couldn't be retrieved!");
		}
		if (subscriber.getRole().compareTo(SubscriberRoleEnum.MASTER_ADMIN) != 0 && subscriber.getRole().compareTo(SubscriberRoleEnum.CLIENT_ADMIN) != 0) {
			logger.warn("This section is retricted to admin users!");
			throw new ValidationException("This section is retricted to admin users!");
		}
	}

	@Override
	public void validate() throws ValidationException {

	}

}
