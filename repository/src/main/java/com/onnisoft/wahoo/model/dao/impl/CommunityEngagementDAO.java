package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.CommunityEngagement;

@Repository
public class CommunityEngagementDAO extends AbstractDao<CommunityEngagement> {

	@Override
	public CommunityEngagement retrieve(CommunityEngagement t) {
		return super.retrieve(t, CommunityEngagement.class);
	}

	@Override
	public CommunityEngagement retrieveById(String id) {
		return super.retrieveById(id, CommunityEngagement.class);
	}

	@Override
	public List<CommunityEngagement> retrieveList(CommunityEngagement t) {
		return super.retrieveList(t, CommunityEngagement.class);
	}

	@Override
	public boolean update(CommunityEngagement t) {
		return super.update(t, Criteria.where("id").is(t.getId()), CommunityEngagement.class);
	}

	@Override
	public boolean delete(CommunityEngagement t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, CommunityEngagement.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createSearchQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Query createSearchQuery(CommunityEngagement communityEngagement) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(communityEngagement.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(communityEngagement.getId()));
		}
		if (!StringUtils.isEmpty(communityEngagement.getUrl())) {
			searchQuery.addCriteria(Criteria.where("url").is(communityEngagement.getUrl()));
		}
		if (communityEngagement.getNumberOfSubscribers() > 0) {
			searchQuery.addCriteria(Criteria.where("numberOfSubscribers").is(communityEngagement.getNumberOfSubscribers()));
		}
		if (!StringUtils.isEmpty(communityEngagement.getLanguage())) {
			searchQuery.addCriteria(Criteria.where("language").is(communityEngagement.getLanguage()));
		}
		if (communityEngagement.getType() != null) {
			searchQuery.addCriteria(Criteria.where("type").is(communityEngagement.getType()));
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
	protected Update createUpdateQuery(CommunityEngagement communityEngagement) {
		Update update = new Update();

		if (!StringUtils.isEmpty(communityEngagement.getUrl())) {
			update.set("url", communityEngagement.getUrl());
		}
		if (communityEngagement.getNumberOfSubscribers() > 0) {
			update.set("numberOfSubscribers", communityEngagement.getNumberOfSubscribers());
		}
		if (!StringUtils.isEmpty(communityEngagement.getLanguage())) {
			update.set("language", communityEngagement.getLanguage());
		}
		if (communityEngagement.getType() != null) {
			update.set("type", communityEngagement.getType());
		}
		if (communityEngagement.getUpdateDate() != null) {
			update.set("updateDate", communityEngagement.getUpdateDate());
		}

		return update;
	}

}
