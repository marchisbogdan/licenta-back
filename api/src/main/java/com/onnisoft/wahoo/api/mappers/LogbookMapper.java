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
import com.onnisoft.wahoo.model.document.Logbook;
import com.onnisoft.wahoo.model.document.MarketingHistory;
import com.onnisoft.wahoo.model.document.StatisticalHistory;

@SuppressWarnings("deprecation")
@Component
public class LogbookMapper extends AbstractWahooObjectMapper<Logbook> {

	@Autowired
	private WahooObjectMapper<FinancialHistory> financialHistoryMapper;

	@Autowired
	private WahooObjectMapper<StatisticalHistory> statisticalHistoryMapper;

	@Autowired
	private WahooObjectMapper<MarketingHistory> marketingHistoryMapper;

	@Autowired
	private Dao<FinancialHistory> financialHistoryDao;

	@Autowired
	private Dao<StatisticalHistory> statisticalHistoryDao;

	@Autowired
	private Dao<MarketingHistory> marketingHistoryDao;

	protected LogbookMapper() {
		super(Logbook.class);
	}

	@Override
	public Logbook objectBuilder(String t, JsonParser jp) {
		Logbook.Builder builder = new Logbook.Builder();
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
					case "statisticalHistories":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<StatisticalHistory> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								StatisticalHistory sh = this.statisticalHistoryMapper.objectBuilder(t, jp);
								if (sh != null) {
									set.add(sh);
								}
							}
							List<StatisticalHistory> list = new ArrayList<>(set);
							statisticalHistoryMapper.saveMappedObjects(list, statisticalHistoryDao);
							builder.statisticalHistories(set);
						}
						break;
					case "marketingHistories":
						if (jp.nextToken() == JsonToken.START_ARRAY) {
							Set<MarketingHistory> set = new HashSet<>();
							while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
								MarketingHistory mh = this.marketingHistoryMapper.objectBuilder(t, jp);
								if (mh != null) {
									set.add(mh);
								}
							}
							List<MarketingHistory> list = new ArrayList<>(set);
							marketingHistoryMapper.saveMappedObjects(list, marketingHistoryDao);
							builder.marketingHistories(set);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Logbook.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Logbook.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}
}
