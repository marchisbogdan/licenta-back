package com.onnisoft.validation;

import org.springframework.stereotype.Component;

import com.onnisoft.validation.exception.ValidationException;
import com.onnisoft.wahoo.api.request.CompetitionCreationRequestDTO;
import com.onnisoft.wahoo.api.request.CompetitorCreationRequestDTO;
import com.onnisoft.wahoo.api.request.EventsSelectionRequestDTO;
import com.onnisoft.wahoo.api.request.GroupCreationRequestDTO;
import com.onnisoft.wahoo.api.request.MarketAssignationRequestDTO;
import com.onnisoft.wahoo.api.request.PrizeSettingDTO;
import com.onnisoft.wahoo.api.request.RoundsSelectionRequestDTO;
import com.onnisoft.wahoo.api.request.StatisticsFilterRequestDTO;
import com.onnisoft.wahoo.api.request.TeamFormationRequestDTO;
import com.onnisoft.wahoo.model.document.Subscriber;

/**
 * 
 * Utility class for input parameters using AOP. This class should be used only
 * in rest services. For other classes please use the @Validate annotation
 * directly.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:47:06
 *
 */
@Component
public class ApiValidationUtil {

	public void validateCompetitorRequest(@Validate(CompetitorCreationValidator.class) CompetitorCreationRequestDTO request) throws ValidationException {
	}

	public void validateGroupCreationRequest(@Validate(GroupCreationValidator.class) GroupCreationRequestDTO request) throws ValidationException {
	}

	public void validateCompetitionCreationRequest(@Validate(CompetitionCreationValidator.class) CompetitionCreationRequestDTO request)
			throws ValidationException {
	}

	public void validateRoundsSelectionRequest(@Validate(RoundsSelectionValidator.class) RoundsSelectionRequestDTO request) throws ValidationException {
	}

	public void validateEventsSelectionRequest(@Validate(EventsSelectionValidator.class) EventsSelectionRequestDTO request) throws ValidationException {
	}

	public void validateMarketAssignationRequest(@Validate(MarketAssignationValidator.class) MarketAssignationRequestDTO request) throws ValidationException {
	}

	public void validatePrizeSettingRequest(@Validate(PrizeSettingValidator.class) PrizeSettingDTO request) throws ValidationException {
	}

	public void validateStatisticsFilterRequest(@Validate(StatisticsFilterValidator.class) StatisticsFilterRequestDTO request) throws ValidationException {

	}

	public void validateAdminSubscriberValidator(@Validate(AdminSubscriberValidator.class) Subscriber subscriber) throws ValidationException {

	}

	public void validateTeamFormationRequest(@Validate(TeamFormationValidator.class) TeamFormationRequestDTO request) throws ValidationException {

	}
}