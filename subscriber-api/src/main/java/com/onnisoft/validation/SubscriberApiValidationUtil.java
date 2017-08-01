package com.onnisoft.validation;

import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.AuthenticationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.PasswordChangeRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.ProfileCreationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RegistrationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RenewTokenRequestDTO;

@Component
public class SubscriberApiValidationUtil {

	public void validateAuthenticationRequest(@Validate(AuthenticationValidator.class) AuthenticationRequestDTO request) throws ValidationException {
	}

	public void validateRenewTokenRequest(@Validate(RenewTokenValidator.class) RenewTokenRequestDTO request) throws ValidationException {
	}

	public void validateRegistrationRequest(@Validate(RegistrationValidator.class) RegistrationRequestDTO request) throws ValidationException {
	}

	public void validatePasswordChangeRequest(@Validate(PasswordChangeValidator.class) PasswordChangeRequestDTO request) throws ValidationException {
	}

	public void validateProfileRequest(@Validate(ProfileCreationValidator.class) ProfileCreationRequestDTO request) throws ValidationException {
	}
}
