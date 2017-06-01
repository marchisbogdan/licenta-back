package com.onnisoft.validation;

import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.AddToFavoriteRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.AuthenticationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.PasswordChangeRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.ProfileCreationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RegistrationRequestDTO;
import com.onnisoft.wahoo.subscriber.api.request.RenewTokenRequestDTO;

/**
 * Utility class for input parameters using AOP. This class should be used only
 * in rest services. For other classes please use the @Validate annotation
 * directly.
 *
 * @author alexandru.mos
 * @date Feb 26, 2015 - 10:00:45 PM
 *
 */
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

	public void validateFavoriteRequest(@Validate(AddToFavoriteValidator.class) AddToFavoriteRequestDTO request) throws ValidationException {
	}
}
