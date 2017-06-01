package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.CommunityEngagement;
import com.onnisoft.wahoo.model.document.MarketingHistory;

@Repository
public class MarketingHistoryDAO extends AbstractDao<MarketingHistory> {

	@Autowired
	private Dao<CommunityEngagement> communityEngagementDAO;

	@Override
	public MarketingHistory retrieve(MarketingHistory t) {
		return super.retrieve(t, MarketingHistory.class);
	}

	@Override
	public MarketingHistory retrieveById(String id) {
		return super.retrieveById(id, MarketingHistory.class);
	}

	@Override
	public List<MarketingHistory> retrieveList(MarketingHistory t) {
		return super.retrieveList(t, MarketingHistory.class);
	}

	@Override
	public boolean update(MarketingHistory t) {
		return super.update(t, Criteria.where("id").is(t.getId()), MarketingHistory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(MarketingHistory marketingHistory) {
		Query query = new Query(Criteria.where("id").is(marketingHistory.getId()));
		boolean deleted = super.delete(query, MarketingHistory.class);
		if (!deleted) {
			logger.warn("Marketing history was not deleted " + marketingHistory);
		} else {
			if (marketingHistory.getCommunityEngagement() != null) {
				CommunityEngagement communityEngagement = this.communityEngagementDAO.retrieveById(marketingHistory.getCommunityEngagement().getId());
				boolean deletedCE = this.communityEngagementDAO.delete(communityEngagement);
				if (!deletedCE) {
					logger.warn("Community engagement was not deleted " + communityEngagement);
				}
				deleted = deleted && deletedCE;
			}
		}
		return deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createSearchQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Query createSearchQuery(MarketingHistory marketingHistory) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(marketingHistory.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(marketingHistory.getId()));
		}
		if (marketingHistory.getCommunityEngagement() != null) {
			searchQuery.addCriteria(Criteria.where("communityEngagement").is(marketingHistory.getCommunityEngagement()));
		}

		return searchQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createUpdateQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Update createUpdateQuery(MarketingHistory marketingHistory) {
		Update update = new Update();

		if (marketingHistory.getCommunityEngagement() != null) {
			update.set("communityEngagement", marketingHistory.getCommunityEngagement());
		}

		return update;
	}

}
