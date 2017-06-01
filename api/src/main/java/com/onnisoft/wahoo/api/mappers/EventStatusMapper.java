package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.EventStatus;
import com.onnisoft.wahoo.model.document.enums.FootballMatchPhase;

@Component
public class EventStatusMapper extends AbstractWahooObjectMapper<EventStatus> {

	protected EventStatusMapper() {
		super(EventStatus.class);
	}

	@Override
	public EventStatus objectBuilder(String t, JsonParser jp) {
		EventStatus.Builder builder = new EventStatus.Builder();
		String fieldName = "";
		try {
			if (jp.nextToken() == JsonToken.START_OBJECT) { // will return
															// JsonToken.START_OBJECT
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					fieldName = jp.getCurrentName();
					switch (fieldName) {
					case "id":
						builder.id(jp.nextTextValue());
						break;
					case "footballMatchPhase":
						String phase = jp.nextTextValue();
						logger.info("FootballMatchPhase:" + phase);
						if (phase != null) {
							if (!phase.contains("null")) {
								builder.footballMatchPhase(FootballMatchPhase.valueOf(phase));
							}
						}
						break;
					case "startTime":
						long date = Long.parseLong(jp.nextTextValue());
						Date startDateTime = new Date(date);
						builder.startTime(startDateTime);
						break;
					case "minute":
						builder.minute(Integer.parseInt(jp.nextTextValue()));
						break;
					case "homeScore":
						builder.homeScore(Integer.parseInt(jp.nextTextValue()));
						break;
					case "awayScore":
						builder.awayScore(Integer.parseInt(jp.nextTextValue()));
						break;
					case "homeRedCards":
						builder.homeRedCards(Integer.parseInt(jp.nextTextValue()));
						break;
					case "awayRedCards":
						builder.awayRedCards(Integer.parseInt(jp.nextTextValue()));
						break;
					default:
						jp.nextToken();
						break;
					}
				}
			} else {
				return null;
			}
		} catch (JsonParseException e) {
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + EventStatus.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + EventStatus.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
