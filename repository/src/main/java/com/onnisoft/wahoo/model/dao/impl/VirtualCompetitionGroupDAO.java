package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.VirtualCompetitionGroup;

/**
 * 
 * Implements CRUD operations on VirtualCompetition document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 15:08:58
 *
 */
@Repository
public class VirtualCompetitionGroupDAO extends AbstractDao<VirtualCompetitionGroup> {

	@Override
	public VirtualCompetitionGroup retrieve(VirtualCompetitionGroup group) {
		return super.retrieve(group, VirtualCompetitionGroup.class);
	}

	@Override
	public VirtualCompetitionGroup retrieveById(String id) {
		return super.retrieveById(id, VirtualCompetitionGroup.class);
	}

	@Override
	public List<VirtualCompetitionGroup> retrieveList(VirtualCompetitionGroup group) {
		return super.retrieveList(group, VirtualCompetitionGroup.class);
	}

	@Override
	public boolean update(VirtualCompetitionGroup group) {
		return super.update(group, Criteria.where("id").is(group.getId()), VirtualCompetitionGroup.class);
	}

	@Override
	public boolean delete(VirtualCompetitionGroup group) {
		Query query = new Query(Criteria.where("id").is(group.getId()));
		boolean deleted = super.delete(query, VirtualCompetitionGroup.class);
		if (!deleted) {
			logger.warn("Group " + group.getName() + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(VirtualCompetitionGroup group) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(group.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(group.getName()));
		}
		if (group.getCreator() != null) {
			searchQuery.addCriteria(Criteria.where("creator").is(group.getCreator()));
		}
		if (group.getVirtualCompetition() != null) {
			searchQuery.addCriteria(Criteria.where("competition").is(group.getVirtualCompetition()));
		}
		if (group.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(group.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(VirtualCompetitionGroup group) {
		Update update = new Update();

		if (!StringUtils.isEmpty(group.getName())) {
			update.set("name", group.getName());
		}
		if (!StringUtils.isEmpty(group.getPassword())) {
			update.set("password", group.getPassword());
		}
		if (group.getUpdateDate() != null) {
			update.set("updateDate", group.getUpdateDate());
		}
		return update;
	}

}
