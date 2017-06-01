package com.onnisoft.wahoo.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.VirtualCompetition;

/***
 * 
 * Virtual Competition Custom operations
 *
 * @author bogdan.marchis
 * @date 16 Jan 2017 - 13:48:09
 *
 */
public abstract class VirtualCompetitonCustomDao extends AbstractDao<VirtualCompetition> {
	
	@Autowired
	private MarketCustomDao marketDao;
	
	/**
	 * return a list with all the open competitions filtered by the given
	 * parameters.
	 * 
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List<VirtualCompetition> getOpenVirtualCompetitions(Date start_date, Date end_date) {
		List<Market> marketList = this.marketDao
				.retrieveList(new Market.Builder().countryRestricted(false).partnerRestricted(false).build());
		
		return this.getVirtualCompetitionsByMarket(marketList, start_date, end_date);
	}
	
	/**
	 * return a list with all the restricted competitions filtered by the given
	 * parameters.
	 * 
	 * @param end_date
	 * @param start_date
	 * @param market
	 * @return
	 */
	public List<VirtualCompetition> getRestrictedVirtualCompetitions(Date start_date, Date end_date, Market market) {
		List<Market> marketList = new LinkedList<>();
		
		if (market.getCountries() != null && market.getPartner() != null) {
			marketList = this.marketDao.getMarketsRestrictedByCountriesOrPartner(market);
		}
		
		return this.getVirtualCompetitionsByMarket(marketList, start_date, end_date);
	}
	
	/**
	 * return a list with all the virtual competitions a basic user can attend.
	 * 
	 * @param market
	 * @return
	 */
	public List<VirtualCompetition> getRestrictedVirtualCompetitionsBySubscriberInfo(Market market) {
		List<Market> marketList = new ArrayList<>();
		List<Market> resultedList = new ArrayList<>();
		Predicate<Market> predicate1, predicate2;
		
		marketList = this.marketDao.retrieveList(null);
		
		// open markets
		predicate1 = m -> Objects.nonNull(m.getCountryRestricted()) && Objects.nonNull(m.getPartnerRestricted());
		predicate2 = m -> m.getCountryRestricted() == false && m.getPartnerRestricted() == false;
		resultedList.addAll(this.marketDao.filterMarketList(marketList, predicate1, predicate2));
		
		// partner restricted only
		predicate1 = m -> Objects.nonNull(m.getPartner());
		predicate2 = m -> m.getPartner().equals(market.getPartner());
		resultedList.addAll(this.marketDao.filterMarketList(marketList, predicate1, predicate2));
		
		// country restricted only
		predicate1 = m -> Objects.nonNull(m.getCountryRestricted());
		predicate2 = m -> m.getCountries().equals(market.getCountries());
		resultedList.addAll(this.marketDao.filterMarketList(marketList, predicate1, predicate2));
		
		// partner and country restricted
		predicate1 = m -> Objects.nonNull(m.getPartner()) && Objects.nonNull(m.getCountries());
		predicate2 = m -> m.getCountries().equals(market.getCountries()) && m.getPartner().equals(market.getPartner());
		resultedList.addAll(this.marketDao.filterMarketList(marketList, predicate1, predicate2));
		
		return this.getVirtualCompetitionsByMarket(resultedList, null, null);
	}
	
	private List<VirtualCompetition> getVirtualCompetitionsByMarket(List<Market> marketList, Date start_date,
			Date end_date) {
		List<VirtualCompetition> competitionsList = new ArrayList<>();
		
		Query searchQuery = new Query();
		if (CollectionUtils.isEmpty(marketList)) {
			logger.warn("No markets were found in the searching process.");
		}
		
		searchQuery.addCriteria(Criteria.where("market").in(marketList));
		
		if (start_date != null && end_date != null) {
			searchQuery.addCriteria(Criteria.where("launchDateTime").lt(end_date)
					.andOperator(Criteria.where("launchDateTime").gte(start_date)));
		}
		
		competitionsList = this.mongoOperation.find(searchQuery, VirtualCompetition.class);
		
		if (CollectionUtils.isEmpty(competitionsList)) {
			logger.warn("No virtual competitions were found in the searching process.");
		}
		return competitionsList;
	}
	
}
