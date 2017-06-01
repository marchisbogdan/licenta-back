package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Round;

/**
 * 
 * Implements CRUD operations on Round document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:55:04
 *
 */
@Repository
public class RoundDAO extends AbstractDao<Round> {

	@Override
	public Round retrieve(Round round) {
		return super.retrieve(round, Round.class);
	}

	@Override
	public Round retrieveById(String id) {
		return super.retrieveById(id, Round.class);
	}

	@Override
	public List<Round> retrieveList(Round round) {
		return super.retrieveList(round, Round.class);
	}

	@Override
	public boolean update(Round round) {
		return super.update(round, Criteria.where("id").is(round.getId()), Round.class);
	}

	@Override
	public boolean delete(Round round) {
		Query query = new Query(Criteria.where("id").is(round.getId()));
		boolean deleted = super.delete(query, Round.class);
		if (!deleted) {
			logger.warn("Round " + round.getName() + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Round round) {
		Query searchQuery = new Query();

		if (round.getSeason() != null) {
			searchQuery.addCriteria(Criteria.where("season").is(round.getSeason()));
		}
		if (!StringUtils.isEmpty(round.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(round.getName()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Round round) {
		Update update = new Update();

		if (!StringUtils.isEmpty(round.getName())) {
			update.set("name", round.getName());
		}
		return update;
	}

}
