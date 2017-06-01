package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;

@Component
public class RoundMapper extends AbstractWahooObjectMapper<Round> {

	@Autowired
	private WahooObjectMapper<Season> seasonMapper;

	protected RoundMapper() {
		super(Round.class);
	}

	@Override
	public Round objectBuilder(String t, JsonParser jp) {
		Round.Builder builder = new Round.Builder();
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
					case "season":
						Season season = seasonMapper.objectBuilder(t, jp);
						builder.season(season);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Round.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Round.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
