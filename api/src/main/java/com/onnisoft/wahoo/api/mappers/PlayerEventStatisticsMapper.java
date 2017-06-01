package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.RealCompetitor;

@Component
public class PlayerEventStatisticsMapper extends AbstractWahooObjectMapper<PlayerEventStatistics> {

	@Autowired
	private WahooObjectMapper<Event> eventMapper;

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Player> playerMapper;

	protected PlayerEventStatisticsMapper() {
		super(PlayerEventStatistics.class);
	}

	@Override
	public PlayerEventStatistics objectBuilder(String t, JsonParser jp) {
		PlayerEventStatistics.Builder builder = new PlayerEventStatistics.Builder();
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
					case "event":
						Event event = eventMapper.objectBuilder(t, jp);
						builder.event(event);
						break;
					case "competitor":
						RealCompetitor realCompetitor = realCompetitorMapper.objectBuilder(t, jp);
						builder.competitor(realCompetitor);
						break;
					case "player":
						Player player = playerMapper.objectBuilder(t, jp);
						builder.player(player);
						break;
					case "gameStarted":
						builder.gameStarted(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "totalSubOn":
						builder.totalSubOn(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "totalSubOff":
						builder.totalSubOff(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "minsPlayed":
						builder.minsPlayed(Integer.parseInt(jp.nextTextValue()));
						break;
					case "goals":
						builder.goals(Integer.parseInt(jp.nextTextValue()));
						break;
					case "goalAssist":
						builder.goalAssist(Integer.parseInt(jp.nextTextValue()));
						break;
					case "ownGoals":
						builder.ownGoals(Integer.parseInt(jp.nextTextValue()));
						break;
					case "cleanSheet":
						builder.cleanSheet(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "yellowCard":
						builder.yellowCard(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "redCard":
						builder.redCard(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "secondYellow":
						builder.secondYellow(Boolean.valueOf(jp.nextTextValue()));
						break;
					case "goalPlusminus":
						builder.goalPlusminus(Integer.parseInt(jp.nextTextValue()));
						break;
					case "goalPlusminusFor":
						builder.goalPlusminusFor(Integer.parseInt(jp.nextTextValue()));
						break;
					case "goalPlusminusAgainst":
						builder.goalPlusminusAgainst(Integer.parseInt(jp.nextTextValue()));
						break;
					case "penGoals":
						builder.penGoals(Integer.parseInt(jp.nextTextValue()));
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + PlayerEventStatistics.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + PlayerEventStatistics.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
