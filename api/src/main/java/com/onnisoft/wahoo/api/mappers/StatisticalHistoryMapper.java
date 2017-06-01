package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.PerformanceStatistic;
import com.onnisoft.wahoo.model.document.StatisticalHistory;

@Deprecated
@Component
public class StatisticalHistoryMapper extends AbstractWahooObjectMapper<StatisticalHistory> {

	@Autowired
	private WahooObjectMapper<PerformanceStatistic> performanceStatisticMapper;

	@Autowired
	private Dao<PerformanceStatistic> performanceStatisticDao;

	protected StatisticalHistoryMapper() {
		super(StatisticalHistory.class);
	}

	@Override
	public StatisticalHistory objectBuilder(String t, JsonParser jp) {
		StatisticalHistory.Builder builder = new StatisticalHistory.Builder();
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
					case "performanceStatistics":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<PerformanceStatistic> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								PerformanceStatistic perf = performanceStatisticMapper.objectBuilder(t, jp);
								if (perf != null) {
									set.add(perf);
								}
							}
							List<PerformanceStatistic> list = new ArrayList<>(set);
							performanceStatisticMapper.saveMappedObjects(list, performanceStatisticDao);
							builder.performanceStatistics(set);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + StatisticalHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + StatisticalHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
