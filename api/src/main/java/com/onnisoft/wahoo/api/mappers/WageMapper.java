package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.document.Wage;
import com.onnisoft.wahoo.model.document.enums.CurrencyEnum;

@Component
public class WageMapper extends AbstractWahooObjectMapper<Wage> {

	protected WageMapper() {
		super(Wage.class);
	}

	@Override
	public Wage objectBuilder(String t, JsonParser jp) {
		Wage.Builder builder = new Wage.Builder();
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
					case "netSalary":
						builder.netSalary(Double.parseDouble(jp.nextTextValue()));
						break;
					case "sponsorSalary":
						builder.sponsorSalary(Double.parseDouble(jp.nextTextValue()));
						break;
					case "totalBonus":
						builder.totalBonus(Double.parseDouble(jp.nextTextValue()));
						break;
					case "currency":
						String currency = jp.nextTextValue();
						if (currency != null) {
							if (!currency.contains("null")) {
								builder.currency(CurrencyEnum.valueOf(currency));
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Wage.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Wage.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
