package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.VirtualCompetitonCustomDao;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitionGroup;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;

/**
 * 
 * Implements CRUD operation on VirtualCompetition document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:13:49
 *
 */
@Repository
public class VirtualCompetitionDAO extends VirtualCompetitonCustomDao {

	@Autowired
	private Dao<VirtualCompetitor> virtualCompetitorDao;

	@Autowired
	private Dao<VirtualCompetitionGroup> virtualCompetitionGroupDao;

	@Override
	public VirtualCompetition retrieve(VirtualCompetition competition) {
		return super.retrieve(competition, VirtualCompetition.class);
	}

	@Override
	public VirtualCompetition retrieveById(String id) {
		return super.retrieveById(id, VirtualCompetition.class);
	}

	@Override
	public List<VirtualCompetition> retrieveList(VirtualCompetition competition) {
		return super.retrieveList(competition, VirtualCompetition.class);
	}

	@Override
	public boolean update(VirtualCompetition competition) {
		return super.update(competition, Criteria.where("id").is(competition.getId()), VirtualCompetition.class);
	}

	@Override
	public boolean delete(VirtualCompetition competition) {
		Query query = new Query(Criteria.where("id").is(competition.getId()));
		boolean deleted = super.delete(query, VirtualCompetition.class);
		if (!deleted) {
			logger.warn("Competition " + competition.getName() + " was not deleted!");
		} else {
			List<VirtualCompetitor> competitors = this.virtualCompetitorDao
					.retrieveList(new VirtualCompetitor.Builder().rank(0).value(0).totalPoints(0).virtualCompetition(competition).build());
			for (VirtualCompetitor competitor : competitors) {
				boolean deletedCompetitor = virtualCompetitorDao.delete(competitor);
				if (!deletedCompetitor) {
					logger.warn("Competitor was not deleted " + competitor.getName());
				}
				deleted = deleted && deletedCompetitor;
			}

			List<VirtualCompetitionGroup> groups = this.virtualCompetitionGroupDao
					.retrieveList(new VirtualCompetitionGroup.Builder().virtualCompetition(competition).build());
			for (VirtualCompetitionGroup group : groups) {
				boolean deletedGroup = virtualCompetitionGroupDao.delete(group);
				if (!deletedGroup) {
					logger.warn("Group was not deleted " + group.getName());
				}
				deleted = deleted && deletedGroup;
			}
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(VirtualCompetition competition) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(competition.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(competition.getName()));
		}
		if (competition.getMarket() != null) {
			searchQuery.addCriteria(Criteria.where("market").is(competition.getMarket()));
		}
		if (competition.getSport() != null) {
			searchQuery.addCriteria(Criteria.where("sport").is(competition.getSport()));
		}
		if (competition.getRealCompetition() != null) {
			searchQuery.addCriteria(Criteria.where("realCompetition").is(competition.getRealCompetition()));
		}
		if (competition.getSeason() != null) {
			searchQuery.addCriteria(Criteria.where("season").is(competition.getSeason()));
		}
		if (competition.getType() != null) {
			searchQuery.addCriteria(Criteria.where("type").is(competition.getType()));
		}
		if (competition.getPrize() != null) {
			searchQuery.addCriteria(Criteria.where("prize").is(competition.getPrize()));
		}
		if (competition.getRulesAndPointSystem() != null) {
			searchQuery.addCriteria(Criteria.where("rulesAndPointSystem").is(competition.getRulesAndPointSystem()));
		}
		if (competition.getStartDateTime() != null) {
			searchQuery.addCriteria(Criteria.where("startDateTime").is(competition.getStartDateTime()));
		}
		if (competition.getEndDateTime() != null) {
			searchQuery.addCriteria(Criteria.where("endDateTime").is(competition.getEndDateTime()));
		}
		if (competition.getLaunchDateTime() != null) {
			searchQuery.addCriteria(Criteria.where("launchDateTime").is(competition.getLaunchDateTime()));
		}
		if (competition.getUpdateDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(competition.getUpdateDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(VirtualCompetition competition) {
		Update update = new Update();
		if (!StringUtils.isEmpty(competition.getName())) {
			update.set("name", competition.getName());
		}
		if (!StringUtils.isEmpty(competition.getSecondaryName())) {
			update.set("secondaryName", competition.getSecondaryName());
		}
		if (!StringUtils.isEmpty(competition.getAvatarLink())) {
			update.set("avatarLink", competition.getAvatarLink());
		}
		if (competition.getMarket() != null) {
			update.set("market", competition.getMarket());
		}
		if (competition.getMaxNumParticipants() != null) {
			update.set("maxNumParticipants", competition.getMaxNumParticipants());
		}
		if (competition.getMaxEntries() != null) {
			update.set("maxEntries", competition.getMaxEntries());
		}
		if (competition.getRounds() != null) {
			if (!competition.getRounds().isEmpty()) {
				update.set("rounds", competition.getRounds());
			}
		}
		if (competition.getEvents() != null) {
			if (!competition.getEvents().isEmpty()) {
				update.set("events", competition.getEvents());
			}
		}
		if (competition.getPrize() != null) {
			update.set("prize", competition.getPrize());
		}
		if (competition.isTerritoryLimitedAccess() != null) {
			update.set("territoryLimitedAccess", competition.isTerritoryLimitedAccess());
		}
		if (competition.isLinkLimitedAccess() != null) {
			update.set("linkLimitedAccess", competition.isLinkLimitedAccess());
		}
		if (competition.isPromotion() != null) {
			update.set("promotion", competition.isPromotion());
		}
		if (competition.getNumberOfSeats() != null) {
			update.set("numberOfSeats", competition.getNumberOfSeats());
		}
		if (competition.getSeatValue() != null) {
			update.set("seatValue", competition.getSeatValue());
		}
		if (competition.getStartDateTime() != null) {
			update.set("startDateTime", competition.getStartDateTime());
		}
		if (competition.getEndDateTime() != null) {
			update.set("endDateTime", competition.getEndDateTime());
		}
		if (competition.getLaunchDateTime() != null) {
			update.set("launchDateTime", competition.getLaunchDateTime());
		}
		if (competition.getUpdateDate() != null) {
			update.set("updateDate", competition.getUpdateDate());
		}
		return update;
	}

}
