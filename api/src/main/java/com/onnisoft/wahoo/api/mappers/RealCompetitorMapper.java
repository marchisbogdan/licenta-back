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
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Logbook;
import com.onnisoft.wahoo.model.document.RealCompetitor;

@Component
public class RealCompetitorMapper extends AbstractWahooObjectMapper<RealCompetitor> {

	@Autowired
	private WahooObjectMapper<Country> countryMapper;

	@Autowired
	private WahooObjectMapper<Logbook> logbookMapper;

	@Autowired
	private Dao<Logbook> loogbookDao;

	public RealCompetitorMapper() {
		super(RealCompetitor.class);
	}

	@Override
	public RealCompetitor objectBuilder(String t, JsonParser jp) {
		RealCompetitor.Builder builder = new RealCompetitor.Builder();
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
					case "country":
						Country country = countryMapper.objectBuilder(t, jp);
						builder.country(country);
						break;
					case "logoUrl":
						builder.logoUrl(jp.nextTextValue());
						break;
					case "shirtUrl":
						builder.shirtUrl(jp.nextTextValue());
						break;
					case "clubUrl":
						builder.clubUrl(jp.nextTextValue());
						break;
					case "logbooks":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<Logbook> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								Logbook logbook = this.logbookMapper.objectBuilder(t, jp);
								if (logbook != null) {
									set.add(logbook);
								}
							}
							List<Logbook> list = new ArrayList<>(set);
							logbookMapper.saveMappedObjects(list, loogbookDao);
							builder.logbooks(set);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + RealCompetitor.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + RealCompetitor.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
