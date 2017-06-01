package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.RealCompetition;

/**
 * 
 * Implements CRUD operations on RealCompetition document.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 12:33:23
 *
 */
@Repository
public class RealCompetitionDAO extends AbstractDao<RealCompetition> {

	@Override
	public RealCompetition retrieve(RealCompetition realCompetition) {
		return super.retrieve(realCompetition, RealCompetition.class);
	}

	@Override
	public RealCompetition retrieveById(String id) {
		return super.retrieveById(id, RealCompetition.class);
	}

	@Override
	public List<RealCompetition> retrieveList(RealCompetition realCompetition) {
		return super.retrieveList(realCompetition, RealCompetition.class);
	}

	@Override
	public boolean update(RealCompetition realCompetition) {
		return super.update(realCompetition, Criteria.where("id").is(realCompetition.getId()), RealCompetition.class);
	}

	@Override
	public boolean delete(RealCompetition realCompetition) {
		Query query = new Query(Criteria.where("id").is(realCompetition.getId()));
		boolean delete = super.delete(query, RealCompetition.class);
		if (!delete) {
			logger.warn("RealCompetition " + realCompetition.getName() + "was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(RealCompetition realCompetition) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(realCompetition.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(realCompetition.getName()));
		}

		if (realCompetition.getCountry() != null) {
			searchQuery.addCriteria(Criteria.where("country").is(realCompetition.getCountry()));
		}

		if (realCompetition.getSport() != null) {
			searchQuery.addCriteria(Criteria.where("sport").is(realCompetition.getSport()));
		}
		if (realCompetition.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(realCompetition.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(RealCompetition realCompetition) {
		Update update = new Update();

		if (!StringUtils.isEmpty(realCompetition.getName())) {
			update.set("name", realCompetition.getName());
		}

		if (!StringUtils.isEmpty(realCompetition.getLogoLink())) {
			update.set("logoLink", realCompetition.getLogoLink());
		}

		if (realCompetition.getCountry() != null) {
			update.set("country", realCompetition.getCountry());
		}

		if (realCompetition.getSport() != null) {
			update.set("sport", realCompetition.getSport());
		}

		if (realCompetition.getUpdateDate() != null) {
			update.set("updateDate", realCompetition.getUpdateDate());
		}

		return update;
	}
}
