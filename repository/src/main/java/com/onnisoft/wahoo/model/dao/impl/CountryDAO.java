package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Country;

@Repository
public class CountryDAO extends AbstractDao<Country> {

	@Override
	public Country retrieve(Country country) {
		return super.retrieve(country, Country.class);
	}

	@Override
	public Country retrieveById(String id) {
		return super.retrieveById(id, Country.class);
	}

	@Override
	public List<Country> retrieveList(Country country) {
		return super.retrieveList(country, Country.class);
	}

	@Override
	public boolean update(Country country) {
		return super.update(country, Criteria.where("id").is(country.getId()), Country.class);
	}

	@Override
	public boolean delete(Country country) {
		Query query = new Query(Criteria.where("id").is(country.getId()));
		boolean deleted = super.delete(query, Country.class);
		if (!deleted) {
			logger.warn("Country " + country.getName() + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Country country) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(country.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(country.getName()));
		}
		if (!StringUtils.isEmpty(country.getAbbreviation())) {
			searchQuery.addCriteria(Criteria.where("abbreviation").is(country.getAbbreviation()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Country country) {
		Update update = new Update();

		if (!StringUtils.isEmpty(country.getAbbreviation() != null)) {
			update.set("abbreviation", country.getAbbreviation());
		}

		if (!StringUtils.isEmpty(country.getFlagLink() != null)) {
			update.set("flagLink", country.getFlagLink());
		}
		if (country.getUpdateDate() != null) {
			update.set("updateDate", country.getUpdateDate());
		}
		return update;
	}
}
