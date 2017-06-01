package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.MarketCustomDao;
import com.onnisoft.wahoo.model.document.Market;

@Repository
public class MarketDAO extends MarketCustomDao {

	@Override
	public Market retrieve(Market market) {
		return super.retrieve(market, Market.class);
	}

	@Override
	public Market retrieveById(String id) {
		return super.retrieveById(id, Market.class);
	}

	@Override
	public List<Market> retrieveList(Market market) {
		return super.retrieveList(market, Market.class);
	}

	@Override
	public boolean update(Market market) {
		return super.update(market, Criteria.where("id").is(market.getId()), Market.class);
	}

	@Override
	public boolean delete(Market market) {
		Query query = new Query(Criteria.where("id").is(market.getId()));
		boolean deleted = super.delete(query, Market.class);
		if (!deleted) {
			logger.warn("The market record " + market.getId() + " was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Market market) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(market.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(market.getName()));
		}

		if (!CollectionUtils.isEmpty(market.getCountries())) {
			searchQuery.addCriteria(Criteria.where("countries").in(market.getCountries()));
		}
		if (market.getCountryRestricted() != null) {
			searchQuery.addCriteria(Criteria.where("countryRestricted").is(market.getCountryRestricted().booleanValue()));
		}
		if (market.getTargetGender() != null) {
			searchQuery.addCriteria(Criteria.where("targetGender").is(market.getTargetGender()));
		}

		if (market.getPartner() != null) {
			searchQuery.addCriteria(Criteria.where("partner").is(market.getPartner()));
		}

		if (market.getPartnerRestricted() != null) {
			searchQuery.addCriteria(Criteria.where("partnerRestricted").is(market.getPartnerRestricted().booleanValue()));
		}

		if (market.getTargetAge() != null) {
			searchQuery.addCriteria(Criteria.where("targetAge").is(market.getTargetAge().intValue()));
		}

		if (!StringUtils.isEmpty(market.getTargetInterests())) {
			searchQuery.addCriteria(Criteria.where("targetInterests").is(market.getTargetInterests()));
		}

		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Market market) {
		Update update = new Update();

		if (!StringUtils.isEmpty(market.getName())) {
			update.set("name", market.getName());
		}

		if (market.getCountryRestricted() != null) {
			update.set("countryRestricted", market.getCountryRestricted().booleanValue());
		}

		if (!CollectionUtils.isEmpty(market.getCountries())) {
			update.set("countries", market.getCountries());
		}

		if (market.getPartnerRestricted() != null) {
			update.set("partnerRestricted", market.getPartnerRestricted().booleanValue());
		}

		if (market.getPartner() != null) {
			update.set("partner", market.getPartner());
		}

		if (market.getTargetGender() != null) {
			update.set("targetGender", market.getTargetGender());
		}
		if (!StringUtils.isEmpty(market.getTargetInterests())) {
			update.set("targetInterests", market.getTargetInterests());
		}
		if (market.getTargetAge() != null) {
			update.set("targetAge", market.getTargetAge().intValue());
		}

		if (market.getUpdateDate() != null) {
			update.set("updateDate", market.getUpdateDate());
		}

		return update;
	}
}
