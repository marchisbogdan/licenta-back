package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.RealCompetitor;

@Component
public class EventCompetitorMapper extends AbstractWahooObjectMapper<EventCompetitor> {

	@Autowired
	private WahooObjectMapper<Event> eventMapper;

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	protected EventCompetitorMapper() {
		super(EventCompetitor.class);
	}

	@Override
	public EventCompetitor objectBuilder(String t, JsonParser jp) {
		EventCompetitor.Builder builder = new EventCompetitor.Builder();
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
					case "event":
						Event event = eventMapper.objectBuilder(t, jp);
						builder.event(event);
						break;
					case "competitor":
						RealCompetitor realCompetitor = realCompetitorMapper.objectBuilder(t, jp);
						builder.competitior(realCompetitor);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + EventCompetitor.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + EventCompetitor.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCrate().build();
	}

}
