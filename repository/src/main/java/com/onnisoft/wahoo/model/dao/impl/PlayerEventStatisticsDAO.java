package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;

@Repository
public class PlayerEventStatisticsDAO extends AbstractDao<PlayerEventStatistics> {

	@Override
	public PlayerEventStatistics retrieve(PlayerEventStatistics t) {
		return super.retrieve(t, PlayerEventStatistics.class);
	}

	@Override
	public PlayerEventStatistics retrieveById(String id) {
		return super.retrieveById(id, PlayerEventStatistics.class);
	}

	@Override
	public List<PlayerEventStatistics> retrieveList(PlayerEventStatistics t) {
		return super.retrieveList(t, PlayerEventStatistics.class);
	}

	@Override
	public boolean update(PlayerEventStatistics t) {
		return super.update(t, Criteria.where("id").is(t.getId()), PlayerEventStatistics.class);
	}

	@Override
	public boolean delete(PlayerEventStatistics t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, PlayerEventStatistics.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(PlayerEventStatistics t) {
		Query query = new Query();
		if (t.getEvent() != null) {
			query.addCriteria(Criteria.where("event").is(t.getEvent()));
		}
		if (t.getCompetitor() != null) {
			query.addCriteria(Criteria.where("competitor").is(t.getCompetitor()));
		}
		if (t.getPlayer() != null) {
			query.addCriteria(Criteria.where("player").is(t.getPlayer()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(PlayerEventStatistics t) {
		Update update = new Update();

		if (t.getMinsPlayed() > 0) {
			update.set("minsPlayed", t.getMinsPlayed());
		}
		if (t.getGoals() > 0) {
			update.set("goals", t.getGoals());
		}
		if (t.getGoalAssist() > 0) {
			update.set("goalAssist", t.getGoalAssist());
		}
		if (t.getOwnGoals() > 0) {
			update.set("ownGoals", t.getOwnGoals());
		}
		if (t.getGoalPlusminus() > 0) {
			update.set("goalPlusminus", t.getGoalPlusminus());
		}
		if (t.getGoalPlusminusFor() > 0) {
			update.set("goalPlusminusFor", t.getGoalPlusminusFor());
		}
		if (t.getGoalPlusminusAgainst() > 0) {
			update.set("goalPlusminusAgainst", t.getGoalPlusminusAgainst());
		}
		if (t.getPenGoals() > 0) {
			update.set("penGoals", t.getPenGoals());
		}
		if (t.getPoints() > 0) {
			update.set("points", t.getPoints());
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