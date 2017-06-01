package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.enums.EventActionEnum;

@Component
public class PlayerEventTimestampStatisticsMapper extends AbstractWahooObjectMapper<PlayerEventTimestampStatistic> {

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Player> playerMapper;

	@Autowired
	private WahooObjectMapper<Event> eventMapper;

	protected PlayerEventTimestampStatisticsMapper() {
		super(PlayerEventTimestampStatistic.class);
	}

	@Override
	public PlayerEventTimestampStatistic objectBuilder(String t, JsonParser jp) {
		PlayerEventTimestampStatistic.Builder builder = new PlayerEventTimestampStatistic.Builder();
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
					case "action":
						String action = jp.nextTextValue();
						if (action != null) {
							if (!action.contains("null")) {
								builder.action(EventActionEnum.valueOf(action));
							}
						}
						break;
					case "competitor":
						RealCompetitor realCompetitor = realCompetitorMapper.objectBuilder(t, jp);
						builder.competitor(realCompetitor);
						break;
					case "player":
						Player player = playerMapper.objectBuilder(t, jp);
						builder.player(player);
						break;
					case "actionMinute":
						builder.actionMinute(Integer.parseInt(jp.nextTextValue()));
						break;
					case "actionSecond":
						builder.actionSecond(Integer.parseInt(jp.nextTextValue()));
						break;
					case "lastUpdated":
						long date = Long.parseLong(jp.nextTextValue());
						Date lastUpdated = new Date(date);
						builder.lastUpdated(lastUpdated);
						break;
					case "event":
						Event event = eventMapper.objectBuilder(t, jp);
						builder.event(event);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + PlayerEventTimestampStatistic.class.getName()
					+ " and object:" + t + "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + PlayerEventTimestampStatistic.class.getName()
					+ " and object:" + t + "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
