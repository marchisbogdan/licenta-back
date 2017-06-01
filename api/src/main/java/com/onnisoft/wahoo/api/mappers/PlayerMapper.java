package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.FinancialHistory;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Wage;
import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerStateEnum;

@Component
public class PlayerMapper extends AbstractWahooObjectMapper<Player> {

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Country> countryMapper;

	@Autowired
	private WahooObjectMapper<Wage> wageMapper;

	@Autowired
	private WahooObjectMapper<FinancialHistory> financialHistoryMapper;

	@Autowired
	private Dao<Wage> wageDao;

	@Autowired
	private Dao<FinancialHistory> financialHistoryDao;

	protected PlayerMapper() {
		super(Player.class);
	}

	@Override
	public Player objectBuilder(String t, JsonParser jp) {
		Player.Builder builder = new Player.Builder();
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
					case "firstName":
						builder.firstName(jp.nextTextValue());
						break;
					case "lastName":
						builder.lastName(jp.nextTextValue());
						break;
					case "nickName":
						builder.nickName(jp.nextTextValue());
						break;
					case "placeOfBirth":
						Country placeOfBirth = countryMapper.objectBuilder(t, jp);
						builder.country(placeOfBirth);
						break;
					case "avatarLink":
						builder.avatarLink(jp.nextTextValue());
						break;
					case "dateOfBirth":
						String numberToParse = jp.nextTextValue();
						if (numberToParse != null) {
							if (numberToParse.contains("null")) {
								long date = Long.parseLong(numberToParse);
								Date birthDate = new Date(date);
								builder.birthDate(birthDate);
							}
						}
						break;
					case "height":
						builder.height(Double.valueOf(jp.nextTextValue()));
						break;
					case "weight":
						builder.weight(Double.valueOf(jp.nextTextValue()));
						break;
					case "position":
						String position = jp.nextTextValue();
						if (position != null) {
							if (!position.contains("null")) {
								builder.position(PlayerPositionEnum.valueOf(position));
							}
						}
						break;
					case "state":
						String state = jp.nextTextValue();
						if (state != null) {
							if (!state.contains("null")) {
								builder.state(PlayerStateEnum.valueOf(state));
							}
						}
						break;
					case "competitor":
						RealCompetitor competitor = realCompetitorMapper.objectBuilder(t, jp);
						builder.competitor(competitor);
						break;
					case "wage":
						Wage wage = this.wageMapper.objectBuilder(t, jp);
						if (wage != null) {
							wageMapper.saveMappedObject(wage, wageDao);
						}
						builder.wage(wage);
						break;
					case "financialHistories":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<FinancialHistory> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								FinancialHistory fh = this.financialHistoryMapper.objectBuilder(t, jp);
								if (fh != null) {
									set.add(fh);
								}
							}
							List<FinancialHistory> list = new ArrayList<>(set);
							financialHistoryMapper.saveMappedObjects(list, financialHistoryDao);
							builder.financialHistories(set);
						}
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
			logger.error("Error when PARSING the object of type " + Player.class.getName() + " and object:" + t + "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the object of type " + Player.class.getName() + " and object:" + t + "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}