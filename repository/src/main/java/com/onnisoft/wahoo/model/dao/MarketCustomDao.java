package com.onnisoft.wahoo.model.dao;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.onnisoft.wahoo.model.document.Market;

/***
 * 
 * Custom Market Dao operations
 *
 * @author bogdan.marchis
 * @date 16 Jan 2017 - 14:30:15
 *
 */
public abstract class MarketCustomDao extends AbstractDao<Market> {
	
	/**
	 * 
	 * @param market
	 * @return a list of Market objects based on a $or operation made between
	 *         CountryList and Partner
	 */
	public List<Market> getMarketsRestrictedByCountriesOrPartner(Market market) {
		Query searchQuery = new Query();
		
		searchQuery.addCriteria(Criteria.where("countries").in(market.getCountries())
				.orOperator(Criteria.where("partner").is(market.getPartner())));
		
		List<Market> marketList = this.mongoOperation.find(searchQuery, Market.class);
		
		return marketList;
	}
	
	public List<Market> filterMarketList(List<Market> list, Predicate<Market> firstFilter,
			Predicate<Market> secoundFilter) {
		return list.stream().filter(m -> firstFilter.test(m)).filter(m -> secoundFilter.test(m))
				.collect(Collectors.toList());
		
	}
}
