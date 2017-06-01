package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Broadcaster;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.EventStatus;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Venue;

@Component
public class EventMapper extends AbstractWahooObjectMapper<Event> {

	@Autowired
	private WahooObjectMapper<Round> roundMapper;

	@Autowired
	private WahooObjectMapper<EventStatus> eventStatusMapper;

	@Autowired
	private WahooObjectMapper<Venue> venueMapper;

	@Autowired
	private WahooObjectMapper<EventCompetitor> eventCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Broadcaster> broadcasterMapper;

	@Autowired
	private Dao<Venue> venueDao;

	@Autowired
	private Dao<Broadcaster> boradcasterDao;

	protected EventMapper() {
		super(Event.class);
	}

	@Override
	public Event objectBuilder(String t, JsonParser jp) {
		Event.Builder builder = new Event.Builder();
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
					case "name":
						builder.name(jp.nextTextValue());
						break;
					case "date":
						String numberToParse = jp.nextTextValue();
						if (numberToParse != null) {
							if (numberToParse.contains("null")) {
								long date = Long.parseLong(numberToParse);
								Date startDateTime = new Date(date);
								builder.startDateTime(startDateTime);
							}
						}
						break;
					case "description":
						builder.description(jp.nextTextValue());
						break;
					case "round":
						Round round = roundMapper.objectBuilder(t, jp);
						builder.round(round);
						break;
					case "eventStatus":
						EventStatus eventStatus = eventStatusMapper.objectBuilder(t, jp);
						builder.eventStatus(eventStatus);
						break;
					case "venue":
						Venue venue = this.venueMapper.objectBuilder(t, jp);
						if (venue != null) {
							venueMapper.saveMappedObject(venue, venueDao);
						}
						builder.venue(venue);
						break;
					case "eventCompetitors":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							List<EventCompetitor> list = new LinkedList<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								EventCompetitor ec = this.eventCompetitorMapper.objectBuilder(t, jp);
								if (ec != null) {
									list.add(ec);
								}
							}
							builder.eventCompetitors(list);
						}
						break;
					case "broadcasters":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							List<Broadcaster> list = new LinkedList<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								Broadcaster b = this.broadcasterMapper.objectBuilder(t, jp);
								if (b != null) {
									list.add(b);
								}
							}
							broadcasterMapper.saveMappedObjects(list, boradcasterDao);
							builder.broadcasters(list);
						}
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Event.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Event.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
