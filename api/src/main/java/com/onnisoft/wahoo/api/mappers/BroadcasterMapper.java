package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Broadcaster;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Venue;

@Component
public class BroadcasterMapper extends AbstractWahooObjectMapper<Broadcaster> {

	@Autowired
	private WahooObjectMapper<Country> countryMapper;

	protected BroadcasterMapper() {
		super(Broadcaster.class);
	}

	@Override
	public Broadcaster objectBuilder(String t, JsonParser jp) {
		Broadcaster.Builder builder = new Broadcaster.Builder();
		String fieldName = "";
		try {
			if (jp.nextToken() == JsonToken.START_OBJECT) {// will return
														   // JsonToken.START_OBJECT
														   // or else skip
				while (jp.nextToken() != JsonToken.END_OBJECT) {
					fieldName = jp.getCurrentName();
					switch (fieldName) {
					case "id":
						builder.id(jp.nextTextValue());
						break;
					case "name":
						builder.name(jp.nextTextValue());
						break;
					case "country":
						Country country = countryMapper.objectBuilder(t, jp);
						builder.country(country);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Venue.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Venue.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
