package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Venue;

@Repository
public class VenueDAO extends AbstractDao<Venue> {

	@Override
	public Venue retrieve(Venue t) {
		return super.retrieve(t, Venue.class);
	}

	@Override
	public Venue retrieveById(String id) {
		return super.retrieveById(id, Venue.class);
	}

	@Override
	public List<Venue> retrieveList(Venue t) {
		return super.retrieveList(t, Venue.class);
	}

	@Override
	public boolean update(Venue t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Venue.class);
	}

	@Override
	public boolean delete(Venue t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, Venue.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Venue t) {
		Query query = new Query();
		if (!StringUtils.isEmpty(t.getName())) {
			query.addCriteria(Criteria.where("name").is(t.getName()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(Venue t) {
		Update update = new Update();

		if (!StringUtils.isEmpty(t.getName())) {
			update.set("name", t.getName());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}

		return update;
	}

}
