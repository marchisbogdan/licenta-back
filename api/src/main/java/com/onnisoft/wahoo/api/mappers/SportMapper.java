package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Sport;

@Component
public class SportMapper extends AbstractWahooObjectMapper<Sport> {

	protected SportMapper() {
		super(Sport.class);
	}

	@Override
	public Sport objectBuilder(String t, JsonParser jp) {
		Sport.Builder builder = new Sport.Builder();
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
					case "description":
						builder.description(jp.nextTextValue());
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Sport.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Sport.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
