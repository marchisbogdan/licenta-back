package com.onnisoft.wahoo.api.mappers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Brand;
import com.onnisoft.wahoo.model.document.Sponsorship;
import com.onnisoft.wahoo.model.document.enums.SponsorshipTypeEnum;

@Component
public class SponsorshipMapper extends AbstractWahooObjectMapper<Sponsorship> {

	@Autowired
	private WahooObjectMapper<Brand> brandMapper;

	@Autowired
	private Dao<Brand> brandDao;

	protected SponsorshipMapper() {
		super(Sponsorship.class);
	}

	@Override
	public Sponsorship objectBuilder(String t, JsonParser jp) {
		Sponsorship.Builder builder = new Sponsorship.Builder();
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
					case "cost":
						builder.cost(Double.parseDouble(jp.nextTextValue()));
						break;
					case "type":
						String type = jp.nextTextValue();
						if (type != null) {
							if (!type.contains("null")) {
								builder.type(SponsorshipTypeEnum.valueOf(type));
							}
						}
						break;
					case "brand":
						Brand brand = brandMapper.objectBuilder(t, jp);
						if (brand != null) {
							brandMapper.saveMappedObject(brand, brandDao);
						}
						builder.brand(brand);
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
			logger.error("Error when PARSING the field:" + fieldName + " of the object of type " + Sponsorship.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error when MAPPING the field:" + fieldName + " of the object of type " + Sponsorship.class.getName() + " and object:" + t + "error:"
					+ e.getMessage());
			return null;
		}
		return builder.toCreate().build();
	}

}
