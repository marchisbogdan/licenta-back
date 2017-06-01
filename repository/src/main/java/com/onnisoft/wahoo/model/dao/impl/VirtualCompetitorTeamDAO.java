package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;

@Repository
public class VirtualCompetitorTeamDAO extends AbstractDao<VirtualCompetitorTeam> {

	@Override
	public VirtualCompetitorTeam retrieve(VirtualCompetitorTeam VirtualCompetitorTeam) {
		return super.retrieve(VirtualCompetitorTeam, VirtualCompetitorTeam.class);
	}

	@Override
	public VirtualCompetitorTeam retrieveById(String id) {
		return super.retrieveById(id, VirtualCompetitorTeam.class);
	}

	@Override
	public List<VirtualCompetitorTeam> retrieveList(VirtualCompetitorTeam VirtualCompetitorTeam) {
		return super.retrieveList(VirtualCompetitorTeam, VirtualCompetitorTeam.class);
	}

	@Override
	public boolean update(VirtualCompetitorTeam VirtualCompetitorTeam) {
		return super.update(VirtualCompetitorTeam, Criteria.where("id").is(VirtualCompetitorTeam.getId()), VirtualCompetitorTeam.class);
	}

	@Override
	public boolean delete(VirtualCompetitorTeam VirtualCompetitorTeam) {
		Query query = new Query(Criteria.where("id").is(VirtualCompetitorTeam.getId()));
		boolean deleted = super.delete(query, VirtualCompetitorTeam.class);
		if (!deleted) {
			logger.warn("VirtualCompetitorTeam " + VirtualCompetitorTeam.getId() + "was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(VirtualCompetitorTeam t) {
		Query query = new Query();
		if (t.getVirtualCompetitor() != null) {
			query.addCriteria(Criteria.where("virtualCompetitor").is(t.getVirtualCompetitor()));
		}
		if (t.getRound() != null) {
			query.addCriteria(Criteria.where("round").is(t.getRound()));
		}
		if (!CollectionUtils.isEmpty(t.getTeam())) {
			query.addCriteria(Criteria.where("team").all(t.getTeam()));
		}
		if (!CollectionUtils.isEmpty(t.getPositions())) {
			query.addCriteria(Criteria.where("positions").all(t.getPositions()));
		}
		if (t.getCaptain() != null) {
			query.addCriteria(Criteria.where("captain").is(t.getCaptain()));
		}
		if (t.getFormation() != null) {
			query.addCriteria(Criteria.where("formation").is(t.getFormation()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(VirtualCompetitorTeam t) {
		Update update = new Update();
		if (!CollectionUtils.isEmpty(t.getTeam())) {
			update.set("team", t.getTeam());
		}
		if (!CollectionUtils.isEmpty(t.getPositions())) {
			update.set("positions", t.getPositions());
		}
		if (t.getCaptain() != null) {
			update.set("captain", t.getCaptain());
		}
		if (t.getFormation() != null) {
			update.set("formation", t.getFormation());
		}
		if (t.getPoints() > 0) {
			update.set("points", t.getPoints());
		}
		if (t.getUpdateDate() != null) {
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}
}
