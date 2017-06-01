package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.EventCompetitor;

@Repository
public class EventCompetitorDAO extends AbstractDao<EventCompetitor> {

	@Override
	public EventCompetitor retrieve(EventCompetitor eventCompetitor) {
		return super.retrieve(eventCompetitor, EventCompetitor.class);
	}

	@Override
	public EventCompetitor retrieveById(String id) {
		return super.retrieveById(id, EventCompetitor.class);
	}

	@Override
	public List<EventCompetitor> retrieveList(EventCompetitor eventCompetitor) {
		return super.retrieveList(eventCompetitor, EventCompetitor.class);
	}

	@Override
	public boolean update(EventCompetitor eventCompetitor) {
		return super.update(eventCompetitor, Criteria.where("id").is(eventCompetitor.getId()), EventCompetitor.class);
	}

	@Override
	public boolean delete(EventCompetitor eventCompetitor) {
		Query query = new Query(Criteria.where("id").is(eventCompetitor.getId()));
		boolean deleted = super.delete(query, EventCompetitor.class);
		if (!deleted) {
			logger.warn("EventCompetitor " + eventCompetitor.getId() + "was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(EventCompetitor eventCompetitor) {
		Query searchQuery = new Query();

		if (eventCompetitor.getCompetitor() != null) {
			searchQuery.addCriteria(Criteria.where("competitor").is(eventCompetitor.getCompetitor()));
		}

		if (eventCompetitor.getEvent() != null) {
			searchQuery.addCriteria(Criteria.where("event").is(eventCompetitor.getEvent()));
		}

		if (eventCompetitor.getPosition() != null) {
			searchQuery.addCriteria(Criteria.where("position").is(eventCompetitor.getPosition()));
		}

		if (eventCompetitor.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(eventCompetitor.getCreationDate()));
		}

		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(EventCompetitor eventCompetitor) {
		Update update = new Update();

		if (eventCompetitor.getCompetitor() != null) {
			update.set("competitor", eventCompetitor.getCompetitor());
		}

		if (eventCompetitor.getEvent() != null) {
			update.set("event", eventCompetitor.getEvent());
		}

		if (eventCompetitor.getPosition() != null) {
			update.set("position", eventCompetitor.getPosition());
		}

		if (eventCompetitor.getUpdateDate() != null) {
			update.set("creationDate", eventCompetitor.getUpdateDate());
		}

		return update;
	}

}
