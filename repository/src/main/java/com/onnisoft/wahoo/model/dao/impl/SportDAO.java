package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Sport;

/**
 * 
 * Implements CRUD operations on Sport document.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 12:36:49
 *
 */
@Repository
public class SportDAO extends AbstractDao<Sport> {

	@Override
	public Sport retrieve(Sport sport) {
		return super.retrieve(sport, Sport.class);
	}

	@Override
	public Sport retrieveById(String id) {
		return super.retrieveById(id, Sport.class);
	}

	@Override
	public List<Sport> retrieveList(Sport sport) {
		return super.retrieveList(sport, Sport.class);
	}

	@Override
	public boolean update(Sport sport) {
		return super.update(sport, Criteria.where("id").is(sport.getId()), Sport.class);
	}

	@Override
	public boolean delete(Sport sport) {
		Query query = new Query(Criteria.where("id").is(sport.getId()));
		boolean delete = super.delete(query, Sport.class);
		if (!delete) {
			logger.warn("Sport " + sport.getName() + "was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Sport sport) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(sport.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(sport.getName()));
		}
		if (!StringUtils.isEmpty(sport.getDescription())) {
			searchQuery.addCriteria(Criteria.where("description").is(sport.getDescription()));
		}
		if (sport.getType() != null) {
			searchQuery.addCriteria(Criteria.where("type").is(sport.getType()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Sport sport) {
		Update update = new Update();

		if (!StringUtils.isEmpty(sport.getName())) {
			update.set("name", sport.getName());
		}
		if (!StringUtils.isEmpty(sport.getDescription())) {
			update.set("description", sport.getDescription());
		}
		if (sport.getType() != null) {
			update.set("type", sport.getType());
		}
		if (sport.getUpdateDate() != null) {
			update.set("updateDate", sport.getUpdateDate());
		}
		return update;
	}
}