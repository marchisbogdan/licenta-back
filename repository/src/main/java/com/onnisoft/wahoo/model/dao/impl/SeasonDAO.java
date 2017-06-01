package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Season;

/**
 * 
 * Implements CRUD operations on Season document.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 12:36:23
 *
 */
@Repository
public class SeasonDAO extends AbstractDao<Season> {

	@Override
	public Season retrieve(Season season) {
		return super.retrieve(season, Season.class);
	}

	@Override
	public Season retrieveById(String id) {
		return super.retrieveById(id, Season.class);
	}

	@Override
	public List<Season> retrieveList(Season season) {
		return super.retrieveList(season, Season.class);
	}

	@Override
	public boolean update(Season season) {
		return super.update(season, Criteria.where("id").is(season.getId()), Season.class);
	}

	@Override
	public boolean delete(Season season) {
		Query query = new Query(Criteria.where("id").is(season.getId()));
		boolean deleted = super.delete(query, Season.class);
		if (!deleted) {
			logger.warn("The season " + season.getName() + " wasn't deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Season season) {
		Query searchQuery = new Query();

		if (StringUtils.isEmpty(season.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(season.getName()));
		}

		if (season.getCompetition() != null) {
			searchQuery.addCriteria(Criteria.where("competition").is(season.getCompetition()));
		}

		if (season.getRounds() != null) {
			searchQuery.addCriteria(Criteria.where("rounds").is(season.getRounds()));
		}

		if (season.getNumTeams() != null) {
			searchQuery.addCriteria(Criteria.where("numTeams").is(season.getNumTeams()));
		}

		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Season season) {
		Update update = new Update();

		if (!StringUtils.isEmpty(season.getName())) {
			update.set("name", season.getName());
		}

		if (season.getRounds() != null) {
			update.set("rounds", season.getRounds());
		}

		if (season.getNumTeams() != null) {
			update.set("numTeams", season.getNumTeams());
		}

		return update;
	}
}
