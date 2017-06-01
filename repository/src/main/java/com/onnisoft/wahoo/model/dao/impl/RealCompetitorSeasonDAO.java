package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.RealCompetitorSeason;

@Repository
public class RealCompetitorSeasonDAO extends AbstractDao<RealCompetitorSeason> {

	@Override
	public RealCompetitorSeason retrieve(RealCompetitorSeason t) {
		return super.retrieve(t, RealCompetitorSeason.class);
	}

	@Override
	public RealCompetitorSeason retrieveById(String id) {
		return super.retrieveById(id, RealCompetitorSeason.class);
	}

	@Override
	public List<RealCompetitorSeason> retrieveList(RealCompetitorSeason t) {
		return super.retrieveList(t, RealCompetitorSeason.class);
	}

	@Override
	public boolean update(RealCompetitorSeason t) {
		return super.update(t, Criteria.where("id").is(t.getId()), RealCompetitorSeason.class);
	}

	@Override
	public boolean delete(RealCompetitorSeason t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean deleted = super.delete(query, RealCompetitorSeason.class);
		if (!deleted) {
			logger.warn("RealCompetitorSeason " + t.getId() + "was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(RealCompetitorSeason t) {
		Query query = new Query();
		if (t.getRealCompetitor() != null) {
			query.addCriteria(Criteria.where("realCompetitor").is(t.getRealCompetitor()));
		}
		if (t.getSeason() != null) {
			query.addCriteria(Criteria.where("season").is(t.getSeason()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(RealCompetitorSeason t) {
		Update update = new Update();

		if (t.getRealCompetitor() != null) {
			update.set("realCompetitor", t.getRealCompetitor());
		}

		if (t.getSeason() != null) {
			update.set("season", t.getSeason());
		}

		if (t.getNumOfAwayDraws() > 0) {
			update.set("numOfAwayDraws", t.getNumOfAwayDraws());
		}

		if (t.getNumOfAwayLooses() > 0) {
			update.set("numOfAwayLooses", t.getNumOfAwayLooses());
		}

		if (t.getNumOfAwayWins() > 0) {
			update.set("numOfAwayWins", t.getNumOfAwayWins());
		}

		if (t.getNumOfHomeDraws() > 0) {
			update.set("numOfHomeDraws", t.getNumOfHomeDraws());
		}

		if (t.getNumOfHomeLooses() > 0) {
			update.set("numOfHomeLooses", t.getNumOfHomeLooses());
		}

		if (t.getNumOfHomeWins() > 0) {
			update.set("numOfHomeWins", t.getNumOfHomeWins());
		}

		if (t.getNumOfGoalsScored() > 0l) {
			update.set("numOfGoalsScored", t.getNumOfGoalsScored());
		}

		if (t.getNumOfGoalsConceded() > 0) {
			update.set("numOfGoalsConceded", t.getNumOfGoalsConceded());
		}

		if (t.getNumOfMatches() > 0) {
			update.set("numOfMatches", t.getNumOfMatches());
		}

		if (t.getNumOfPoints() > 0) {
			update.set("numOfPoints", t.getNumOfPoints());
		}

		if (t.getRank() > 0) {
			update.set("rank", t.getRank());
		}

		if (t.getCleanSheets() > 0) {
			update.set("cleanSheets", t.getCleanSheets());
		}

		if (t.getNoGoalsScoredMatches() > 0) {
			update.set("noGoalsScoredMatches", t.getNoGoalsScoredMatches());
		}

		if (t.getValue() > 0) {
			update.set("value", t.getValue());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}

}
