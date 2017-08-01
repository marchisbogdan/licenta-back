package com.onnisoft.validation;

import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.StatisticsFilterRequestDTO;
import com.onnisoft.wahoo.model.document.Subscriber;

@Component
public class ApiValidationUtil {
	public void validateStatisticsFilterRequest(@Validate(StatisticsFilterValidator.class) StatisticsFilterRequestDTO request) throws ValidationException {

	}

	public void validateAdminSubscriberValidator(@Validate(AdminSubscriberValidator.class) Subscriber subscriber) throws ValidationException {

	}
}