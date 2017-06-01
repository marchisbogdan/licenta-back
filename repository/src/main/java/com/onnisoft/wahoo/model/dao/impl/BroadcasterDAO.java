package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Broadcaster;

@Repository
public class BroadcasterDAO extends AbstractDao<Broadcaster> {

	@Override
	public Broadcaster retrieve(Broadcaster t) {
		return super.retrieve(t, Broadcaster.class);
	}

	@Override
	public Broadcaster retrieveById(String id) {
		return super.retrieveById(id, Broadcaster.class);
	}

	@Override
	public List<Broadcaster> retrieveList(Broadcaster t) {
		return super.retrieveList(t, Broadcaster.class);
	}

	@Override
	public boolean update(Broadcaster t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Broadcaster.class);
	}

	@Override
	public boolean delete(Broadcaster t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, Broadcaster.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Broadcaster broadcaster) {
		Query searchUserQuery = new Query();

		if (!StringUtils.isEmpty(broadcaster.getName())) {
			searchUserQuery.addCriteria(Criteria.where("name").is(broadcaster.getName()));
		}
		if (broadcaster.getCountry() != null) {
			searchUserQuery.addCriteria(Criteria.where("country").is(broadcaster.getCountry()));
		}

		return searchUserQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createUpdateQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Update createUpdateQuery(Broadcaster broadcaster) {
		Update update = new Update();

		if (!StringUtils.isEmpty(broadcaster.getName())) {
			update.set("name", broadcaster.getName());
		}
		if (broadcaster.getCountry() != null) {
			update.set("country", broadcaster.getCountry());
		}
		if (broadcaster.getUpdateDate() != null) {
			update.set("updateDate", broadcaster.getUpdateDate());
		}

		return update;
	}
}
