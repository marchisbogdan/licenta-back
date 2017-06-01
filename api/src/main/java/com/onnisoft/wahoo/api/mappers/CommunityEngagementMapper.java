package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.CommunityEngagement;
import com.onnisoft.wahoo.model.document.enums.CommunityEngagementTypeEnum;

@Component
public class CommunityEngagementMapper extends AbstractWahooObjectMapper<CommunityEngagement> {

	protected CommunityEngagementMapper() {
		super(CommunityEngagement.class);
	}

	@Override
	public CommunityEngagement objectBuilder(String t, JsonParser jp) {
		CommunityEngagement.Builder builder = new CommunityEngagement.Builder();
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
					case "url":
						builder.url(jp.nextTextValue());
						break;
					case "numberOfSubscribers":
						builder.numberOfSubscribers(Integer.parseInt(jp.nextTextValue()));
						break;
					case "language":
						builder.language(jp.nextTextValue());
						break;
					case "type":
						String type = jp.nextTextValue();
						if (type != null) {
							if (!type.contains("null")) {
								builder.type(CommunityEngagementTypeEnum.valueOf(type));
							}
						}
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + CommunityEngagement.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + CommunityEngagement.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
