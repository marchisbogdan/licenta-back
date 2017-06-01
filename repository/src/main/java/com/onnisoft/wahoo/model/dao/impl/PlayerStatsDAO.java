package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.PlayerStats;

@Repository
public class PlayerStatsDAO extends AbstractDao<PlayerStats> {

	@Override
	public PlayerStats retrieve(PlayerStats t) {
		return super.retrieve(t, PlayerStats.class);
	}

	@Override
	public PlayerStats retrieveById(String id) {
		return super.retrieveById(id, PlayerStats.class);
	}

	@Override
	public List<PlayerStats> retrieveList(PlayerStats t) {
		return super.retrieveList(t, PlayerStats.class);
	}

	@Override
	public boolean update(PlayerStats t) {
		return super.update(t, Criteria.where("id").is(t.getId()), PlayerStats.class);
	}

	@Override
	public boolean delete(PlayerStats t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, PlayerStats.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(PlayerStats t) {
		Query query = new Query();
		if (t.getPlayer() != null) {
			query.addCriteria(Criteria.where("player").is(t.getPlayer()));
		}
		if (t.getSeason() != null) {
			query.addCriteria(Criteria.where("season").is(t.getSeason()));
		}
		if (t.getRealCompetitor() != null) {
			query.addCriteria(Criteria.where("realCompetitor").is(t.getRealCompetitor()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(PlayerStats t) {
		Update update = new Update();

		if (t.getMatchesPlayed() > 0) {
			update.set("matchesPlayed", t.getMatchesPlayed());
		}
		if (t.getMinutesPlayed() > 0) {
			update.set("minutesPlayed", t.getMinutesPlayed());
		}
		if (t.getGoals() > 0) {
			update.set("goals", t.getGoals());
		}
		if (t.getAssists() > 0) {
			update.set("assists", t.getAssists());
		}
		if (t.getYellowCards() > 0) {
			update.set("yellowCards", t.getYellowCards());
		}
		if (t.getRedCards() > 0) {
			update.set("redCards", t.getRedCards());
		}
		if (t.getLineupStarts() > 0) {
			update.set("lineupStarts", t.getLineupStarts());
		}
		if (t.getSubstitutedIn() > 0) {
			update.set("substitutedIn", t.getSubstitutedIn());
		}
		if (t.getSubstitutedOut() > 0) {
			update.set("substitutedOut", t.getSubstitutedOut());
		}
		if (t.getValue() > 0) {
			update.set("value", t.getValue());
		}
		if (t.getTotalPoints() > 0) {
			update.set("totalPoints", t.getTotalPoints());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}
}
