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
import com.onnisoft.wahoo.model.document.FinancialHistory;
import com.onnisoft.wahoo.model.document.Sponsorship;
import com.onnisoft.wahoo.model.document.Wage;

@Component
public class FinancialHistoryMapper extends AbstractWahooObjectMapper<FinancialHistory> {

	@Autowired
	private WahooObjectMapper<Wage> wageMapper;

	@Autowired
	private WahooObjectMapper<Sponsorship> sponsorshipMapper;

	@Autowired
	private Dao<Wage> wageDao;

	@Autowired
	private Dao<Sponsorship> sponsorshipDao;

	protected FinancialHistoryMapper() {
		super(FinancialHistory.class);
	}

	@Override
	public FinancialHistory objectBuilder(String t, JsonParser jp) {
		FinancialHistory.Builder builder = new FinancialHistory.Builder();
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
					case "totalGateReceipt":
						builder.totalGateReceipt(Double.valueOf(jp.nextTextValue()));
						break;
					case "prizeMoney":
						builder.prizeMoney(Double.valueOf(jp.nextTextValue()));
						break;
					case "wage":
						Wage wage = wageMapper.objectBuilder(t, jp);
						if (wage != null) {
							wageMapper.saveMappedObject(wage, wageDao);
						}
						builder.wage(wage);
						break;
					case "sponsorships":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<Sponsorship> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								Sponsorship sponsorship = sponsorshipMapper.objectBuilder(t, jp);
								if (sponsorship != null) {
									set.add(sponsorship);
								}
							}
							List<Sponsorship> list = new ArrayList<>(set);
							sponsorshipMapper.saveMappedObjects(list, sponsorshipDao);
							builder.sponsorships(set);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + FinancialHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + FinancialHistory.class.getName() + " and object:" + t
					+ "error:" + e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
