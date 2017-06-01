package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Venue;

@Component
public class VenueMapper extends AbstractWahooObjectMapper<Venue> {

	protected VenueMapper() {
		super(Venue.class);
	}

	@Override
	public Venue objectBuilder(String t, JsonParser jp) {
		Venue.VenueBuilder builder = new Venue.VenueBuilder();
		String fieldName = "";
		try {
			if (jp.nextToken() == JsonToken.START_OBJECT) {// will return
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
					default:
						jp.nextToken();
						break;
					}
				}
			} else {
				return null; // in case there is no object to map
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