package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Event;

/**
 * 
 * Implements CRUD operations on Event document.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 12:37:29
 *
 */
@Repository
public class EventDAO extends AbstractDao<Event> {

	@Override
	public Event retrieve(Event event) {
		return super.retrieve(event, Event.class);
	}

	@Override
	public Event retrieveById(String id) {
		return super.retrieveById(id, Event.class);
	}

	@Override
	public List<Event> retrieveList(Event event) {
		return super.retrieveList(event, Event.class);
	}

	@Override
	public boolean update(Event event) {
		return super.update(event, Criteria.where("id").is(event.getId()), Event.class);
	}

	@Override
	public boolean delete(Event event) {
		Query query = new Query(Criteria.where("id").is(event.getId()));
		boolean deleted = super.delete(query, Event.class);
		if (!deleted) {
			logger.warn("Event " + event.getId() + "was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Event event) {
		Query searchQuery = new Query();

		if (event.getStartDateTime() != null) {
			searchQuery.addCriteria(Criteria.where("startDateTime").is(event.getStartDateTime()));
		}

		if (!StringUtils.isEmpty(event.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(event.getName()));
		}

		if (event.getRound() != null) {
			searchQuery.addCriteria(Criteria.where("round").is(event.getRound()));
		}

		if (event.getStadium() != null) {
			searchQuery.addCriteria(Criteria.where("stadium").is(event.getStadium()));
		}

		if (event.getStatus() != null) {
			searchQuery.addCriteria(Criteria.where("status").is(event.getStatus()));
		}

		if (event.getEventStatus() != null) {
			searchQuery.addCriteria(Criteria.where("eventStatus").is(event.getEventStatus()));
		}

		if (event.getVenue() != null) {
			searchQuery.addCriteria(Criteria.where("venue").is(event.getVenue()));
		}

		if (event.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(event.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Event event) {
		Update update = new Update();

		if (event.getStartDateTime() != null) {
			update.set("dateTime", event.getStartDateTime());
		}

		if (!StringUtils.isEmpty(event.getName())) {
			update.set("name", event.getName());
		}

		if (event.getRound() != null) {
			update.set("round", event.getRound());
		}

		if (event.getStadium() != null) {
			update.set("stadium", event.getStadium());
		}

		if (event.getStatus() != null) {
			update.set("status", event.getStatus());
		}

		if (event.getDescription() != null) {
			update.set("description", event.getDescription());
		}

		if (event.getUpdateDate() != null) {
			update.set("updateDate", event.getUpdateDate());
		}

		if (event.getEventStatus() != null) {
			update.set("eventStatus", event.getEventStatus());
		}

		if (event.getVenue() != null) {
			update.set("venue", event.getVenue());
		}

		// if (!CollectionUtils.isEmpty(event.getEventCompetitors())) {
		// update.set("eventCompetitors", event.getEventCompetitors());
		// }

		if (!CollectionUtils.isEmpty(event.getBroadcasters())) {
			update.set("broadcasters", event.getBroadcasters());
		}

		return update;
	}
}