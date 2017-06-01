package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.CommunityEngagement;
import com.onnisoft.wahoo.model.document.MarketingHistory;

@Component
public class MarketingHistoryMapper extends AbstractWahooObjectMapper<MarketingHistory> {

	@Autowired
	private WahooObjectMapper<CommunityEngagement> communityEngagementMapper;

	@Autowired
	private Dao<CommunityEngagement> communityEngagementDao;

	protected MarketingHistoryMapper() {
		super(MarketingHistory.class);
	}

	@Override
	public MarketingHistory objectBuilder(String t, JsonParser jp) {
		MarketingHistory.Builder builder = new MarketingHistory.Builder();
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
					case "communityEngagement":
						CommunityEngagement communityEngagement = communityEngagementMapper.objectBuilder(t, jp);
						if (communityEngagement != null) {
							communityEngagementMapper.saveMappedObject(communityEngagement, communityEngagementDao);
						}
						builder.communityEngagement(communityEngagement);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + MarketingHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + MarketingHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
