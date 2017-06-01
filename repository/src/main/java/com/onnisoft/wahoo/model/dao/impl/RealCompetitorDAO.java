package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.RealCompetitor;

/**
 * 
 * Implements CRUD operations on RealCompetitor document.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 12:34:07
 *
 */
@Repository
public class RealCompetitorDAO extends AbstractDao<RealCompetitor> {

	@Override
	public RealCompetitor retrieve(RealCompetitor realCompetitor) {
		return super.retrieve(realCompetitor, RealCompetitor.class);
	}

	@Override
	public RealCompetitor retrieveById(String id) {
		return super.retrieveById(id, RealCompetitor.class);
	}

	@Override
	public List<RealCompetitor> retrieveList(RealCompetitor realCompetitor) {
		return super.retrieveList(realCompetitor, RealCompetitor.class);
	}

	@Override
	public boolean update(RealCompetitor realCompetitor) {
		return super.update(realCompetitor, Criteria.where("id").is(realCompetitor.getId()), RealCompetitor.class);
	}

	@Override
	public boolean delete(RealCompetitor realCompetitor) {
		Query query = new Query(Criteria.where("id").is(realCompetitor.getId()));
		boolean delete = super.delete(query, RealCompetitor.class);
		if (!delete) {
			logger.warn("RealCompetitor " + realCompetitor.getName() + "was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(RealCompetitor realCompetitor) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(realCompetitor.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(realCompetitor.getName()));
		}
		if (realCompetitor.getCountry() != null) {
			searchQuery.addCriteria(Criteria.where("country").is(realCompetitor.getCountry()));
		}
		if (realCompetitor.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(realCompetitor.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(RealCompetitor realCompetitor) {
		Update update = new Update();

		if (!StringUtils.isEmpty(realCompetitor.getName())) {
			update.set("name", realCompetitor.getName());
		}

		if (!StringUtils.isEmpty(realCompetitor.getLogoUrl())) {
			update.set("logoUrl", realCompetitor.getLogoUrl());
		}

		if (!StringUtils.isEmpty(realCompetitor.getShirtUrl())) {
			update.set("shirtUrl", realCompetitor.getShirtUrl());
		}

		if (!StringUtils.isEmpty(realCompetitor.getClubUrl())) {
			update.set("clubUrl", realCompetitor.getClubUrl());
		}

		if (!StringUtils.isEmpty(realCompetitor.getStatement())) {
			update.set("statement", realCompetitor.getStatement());
		}

		if (realCompetitor.getCountry() != null) {
			update.set("country", realCompetitor.getCountry());
		}

		if (realCompetitor.getUpdateDate() != null) {
			update.set("updateDate", realCompetitor.getUpdateDate());
		}

		if (!CollectionUtils.isEmpty(realCompetitor.getLogbooks())) {
			update.set("logbooks", realCompetitor.getLogbooks());
		}

		return update;
	}
}
