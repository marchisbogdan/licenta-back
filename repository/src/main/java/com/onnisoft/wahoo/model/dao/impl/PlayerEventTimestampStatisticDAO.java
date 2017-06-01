package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;

@Repository
public class PlayerEventTimestampStatisticDAO extends AbstractDao<PlayerEventTimestampStatistic> {

	@Override
	public PlayerEventTimestampStatistic retrieve(PlayerEventTimestampStatistic t) {
		return super.retrieve(t, PlayerEventTimestampStatistic.class);
	}

	@Override
	public PlayerEventTimestampStatistic retrieveById(String id) {
		return super.retrieveById(id, PlayerEventTimestampStatistic.class);
	}

	@Override
	public List<PlayerEventTimestampStatistic> retrieveList(PlayerEventTimestampStatistic t) {
		return super.retrieveList(t, PlayerEventTimestampStatistic.class);
	}

	@Override
	public boolean update(PlayerEventTimestampStatistic t) {
		return super.update(t, Criteria.where("id").is(t.getId()), PlayerEventTimestampStatistic.class);
	}

	@Override
	public boolean delete(PlayerEventTimestampStatistic t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, PlayerEventTimestampStatistic.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(PlayerEventTimestampStatistic t) {
		Query query = new Query();
		if (t.getCompetitor() != null) {
			query.addCriteria(Criteria.where("competitor").is(t.getCompetitor()));
		}
		if (t.getPlayer() != null) {
			query.addCriteria(Criteria.where("player").is(t.getPlayer()));
		}
		if (t.getEvent() != null) {
			query.addCriteria(Criteria.where("event").is(t.getEvent()));
		}
		if (t.getAction() != null) {
			query.addCriteria(Criteria.where("action").is(t.getAction()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(PlayerEventTimestampStatistic t) {
		Update update = new Update();
		if (t.getActionMinute() > -1) {
			update.set("actionMinute", t.getActionMinute());
		}
		if (t.getActionSecond() > -1) {
			update.set("actionSecond", t.getActionSecond());
		}
		if (t.getAction() != null) {
			update.set("action", t.getAction());
		}
		if (t.getLastUpdated() != null) {
			update.set("lastUpdated", t.getLastUpdated());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}

}
