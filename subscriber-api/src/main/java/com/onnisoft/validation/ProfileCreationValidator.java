package com.onnisoft.validation;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.subscriber.api.request.ProfileCreationRequestDTO;

@Component
public class ProfileCreationValidator implements Validator<ProfileCreationRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(ProfileCreationValidator.class);

	@Override
	public void validate(ProfileCreationRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (request.getAvatarLink() != null) {
			String url = request.getAvatarLink().trim();
			validateLink(url);
		}
	}

	@Override
	public void validate() throws ValidationException {
	}

	private void validateLink(String avatarUrl) throws ValidationException {
		if (avatarUrl.isEmpty()) {
			logger.warn("URL is empty!");
			throw new ValidationException("URL is empty!");
		}
		try {
			new URL(avatarUrl);
		} catch (MalformedURLException e) {
			logger.warn("Invalid url");
		}
	}
}
