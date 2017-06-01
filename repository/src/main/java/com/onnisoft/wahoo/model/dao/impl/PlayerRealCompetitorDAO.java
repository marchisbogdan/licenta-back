package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.PlayerRealCompetitor;

@Repository
public class PlayerRealCompetitorDAO extends AbstractDao<PlayerRealCompetitor> {

	@Override
	public PlayerRealCompetitor retrieve(PlayerRealCompetitor t) {
		return super.retrieve(t, PlayerRealCompetitor.class);
	}

	@Override
	public PlayerRealCompetitor retrieveById(String id) {
		return super.retrieveById(id, PlayerRealCompetitor.class);
	}

	@Override
	public List<PlayerRealCompetitor> retrieveList(PlayerRealCompetitor t) {
		return super.retrieveList(t, PlayerRealCompetitor.class);
	}

	@Override
	public boolean update(PlayerRealCompetitor t) {
		return super.update(t, Criteria.where("id").is(t.getId()), PlayerRealCompetitor.class);
	}

	@Override
	public boolean delete(PlayerRealCompetitor t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean deleted = super.delete(query, PlayerRealCompetitor.class);
		if (!deleted) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(PlayerRealCompetitor t) {
		Query query = new Query();
		if (t.getCompetitor() != null) {
			query.addCriteria(Criteria.where("competitor").is(t.getCompetitor()));
		}
		if (t.getPlayer() != null) {
			query.addCriteria(Criteria.where("player").is(t.getPlayer()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(PlayerRealCompetitor t) {
		Update update = new Update();
		if (t.getCompetitor() != null) {
			update.set("competitor", t.getCompetitor());
		}
		if (t.getPlayer() != null) {
			update.set("player", t.getPlayer());
		}
		if (t.isCaptain() != null) {
			update.set("captain", t.isCaptain());
		}
		if (t.getRound() != null) {
			update.set("round", t.getRound());
		}
		if (t.getPosition() != null) {
			update.set("position", t.getPosition());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}

}
