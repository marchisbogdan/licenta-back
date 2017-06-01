package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.PerformanceStatistic;

@Deprecated
@Component
public class PerformanceStatisticMapper extends AbstractWahooObjectMapper<PerformanceStatistic> {

	protected PerformanceStatisticMapper() {
		super(PerformanceStatistic.class);
	}

	@Override
	public PerformanceStatistic objectBuilder(String t, JsonParser jp) {

		PerformanceStatistic.Builder builder = new PerformanceStatistic.Builder();
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
					case "pointPerGame":
						builder.pointPerGame(Integer.parseInt(jp.nextTextValue()));
						break;
					case "projectedPointPerGame":
						builder.projectedPointPerGame(Integer.parseInt(jp.nextTextValue()));
						break;
					case "currentPosition":
						builder.currentPosition(Integer.parseInt(jp.nextTextValue()));
						break;
					case "projectedFinishingPosition":
						builder.projectedFinishingPosition(Integer.parseInt(jp.nextTextValue()));
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + PerformanceStatistic.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + PerformanceStatistic.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();

	}

}