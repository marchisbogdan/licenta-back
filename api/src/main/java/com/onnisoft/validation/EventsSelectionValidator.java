package com.onnisoft.validation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.EventsSelectionRequestDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;

/**
 * 
 * Validates the events selection request by checking for inconsistent and wrong
 * input data.
 *
 * @author mbozesan
 * @date 7 Nov 2016 - 12:06:37
 *
 */
@Component
public class EventsSelectionValidator implements Validator<EventsSelectionRequestDTO> {

	private final Logger logger = LoggerFactory.getLogger(EventsSelectionValidator.class);

	@Autowired
	private Dao<Event> eventDao;

	@Override
	public void validate(EventsSelectionRequestDTO request) throws ValidationException {
		if (request == null) {
			logger.warn("Invalid request");
			throw new ValidationException("Invalid request");
		}

		if (!request.isSelectedAll()) {
			validateEvents(request.getEventIds());
		}
	}

	private void validateEvents(List<String> eventIds) throws ValidationException {
		for (String eventId : eventIds) {
			if (StringUtils.isEmpty(eventId)) {
				logger.warn("Invalid eventId " + eventId);
				throw new ValidationException("Invalid eventId " + eventId);
			}

			Event event = eventDao.retrieveById(eventId);
			if (event == null) {
				logger.warn("No event with this id " + eventId);
				throw new ValidationException("No event with this id " + eventId);
			}
		}
	}

	@Override
	public void validate() throws ValidationException {

	}
}
