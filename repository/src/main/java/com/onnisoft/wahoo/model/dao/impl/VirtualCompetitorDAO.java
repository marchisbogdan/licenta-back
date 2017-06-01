package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;

/**
 * 
 * Implements CRUD operations on VirtualCompetitor document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 15:09:36
 *
 */
@Repository
public class VirtualCompetitorDAO extends AbstractDao<VirtualCompetitor> {

	@Autowired
	private Dao<VirtualCompetitorTeam> virtualCompetitorTeamDao;

	@Override
	public VirtualCompetitor retrieve(VirtualCompetitor competitor) {
		return super.retrieve(competitor, VirtualCompetitor.class);
	}

	@Override
	public VirtualCompetitor retrieveById(String id) {
		return super.retrieveById(id, VirtualCompetitor.class);
	}

	@Override
	public List<VirtualCompetitor> retrieveList(VirtualCompetitor competitor) {
		return super.retrieveList(competitor, VirtualCompetitor.class);
	}

	@Override
	public boolean update(VirtualCompetitor competitor) {
		return super.update(competitor, Criteria.where("id").is(competitor.getId()), VirtualCompetitor.class);
	}

	@Override
	public boolean delete(VirtualCompetitor competitor) {
		Query query = new Query(Criteria.where("id").is(competitor.getId()));
		boolean deleted = super.delete(query, VirtualCompetitor.class);
		if (!deleted) {
			logger.warn("Competitor " + competitor + " was not deleted!");
		} else {
			List<VirtualCompetitorTeam> teams = this.virtualCompetitorTeamDao
					.retrieveList(new VirtualCompetitorTeam.Builder().virtualCompetitor(competitor).build());
			for (VirtualCompetitorTeam team : teams) {
				boolean del = this.virtualCompetitorTeamDao.delete(team);
				if (!del) {
					logger.warn("Record " + team + " was not deleted!");
				}
				deleted = deleted && del;
			}
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(VirtualCompetitor competitor) {
		Query searchQuery = new Query();
		if (competitor.getSubscriber() != null) {
			searchQuery.addCriteria(Criteria.where("subscriber").is(competitor.getSubscriber()));
		}
		if (competitor.getCompetition() != null) {
			searchQuery.addCriteria(Criteria.where("competition").is(competitor.getCompetition()));
		}
		if (competitor.getGroup() != null) {
			searchQuery.addCriteria(Criteria.where("group").is(competitor.getGroup()));
		}
		if (!StringUtils.isEmpty(competitor.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(competitor.getName()));
		}
		if (competitor.getTotalPoints() != null) {
			searchQuery.addCriteria(Criteria.where("totalPoints").is(competitor.getTotalPoints()));
		}
		if (competitor.getRank() != null) {
			searchQuery.addCriteria(Criteria.where("rank").is(competitor.getTotalPoints()));
		}
		if (competitor.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(competitor.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(VirtualCompetitor competitor) {
		Update update = new Update();
		if (StringUtils.isEmpty(competitor.getName())) {
			update.set("name", competitor.getName());
		}
		if (competitor.getTotalPoints() != null) {
			update.set("totalPoints", competitor.getTotalPoints());
		}
		if (competitor.getRank() != null) {
			update.set("rank", competitor.getValue());
		}
		if (competitor.getGroup() != null) {
			update.set("group", competitor.getGroup());
		}
		if (competitor.getValue() != null) {
			update.set("value", competitor.getValue());
		}
		if (competitor.getUpdateDate() != null) {
			update.set("updateDate", competitor.getUpdateDate());
		}
		return update;
	}

}
