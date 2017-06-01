package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.SubscriberCustomDao;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.SubscriberDevice;

/**
 * Implements CRUD operations for Subscriber document
 *
 * @author mbozesan
 * @date 15 Sep 2016 - 12:09:43
 */
@Repository
public class SubscriberDAO extends SubscriberCustomDao {

	@Autowired
	private Dao<SubscriberDevice> subscriberDeviceDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.wahoo.model.dao.Dao#retrieve(java.lang.Object)
	 */
	@Override
	public Subscriber retrieve(Subscriber subscriber) {
		return super.retrieve(subscriber, Subscriber.class);
	}

	@Override
	public Subscriber retrieveById(String id) {
		return super.retrieveById(id, Subscriber.class);
	}

	@Override
	public List<Subscriber> retrieveList(Subscriber subscriber) {
		return super.retrieveList(subscriber, Subscriber.class);
	}

	@Override
	public boolean update(Subscriber subscriber) {
		return super.update(subscriber, Criteria.where("id").is(subscriber.getId()), Subscriber.class);
	}

	@Override
	public boolean delete(Subscriber subscriber) {
		Query query = new Query(Criteria.where("id").is(subscriber.getId()));
		boolean deleted = super.delete(query, Subscriber.class);
		if (!deleted) {
			logger.warn("User " + subscriber + " was not deleted!");
		} else {
			List<SubscriberDevice> subscriberDevices = this.subscriberDeviceDao
					.retrieveList(new SubscriberDevice.SubscriberDeviceBuilder().subscriber(subscriber).build());
			for (SubscriberDevice subscriberDevice : subscriberDevices) {
				boolean deletedDevice = this.subscriberDeviceDao.delete(subscriberDevice);
				if (!deletedDevice) {
					logger.warn("Subscriber device was not deleted " + deletedDevice);
				}
				deleted = deleted && deletedDevice;
			}
		}
		return deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.wahoo.model.dao.AbstractDao#createSearchQuery(java.lang.
	 * Object)
	 */
	@Override
	protected Query createSearchQuery(Subscriber subscriber) {
		Query searchUserQuery = new Query();

		if (!StringUtils.isEmpty(subscriber.getEmail())) {
			searchUserQuery.addCriteria(Criteria.where("email").is(subscriber.getEmail()));
		}
		if (!StringUtils.isEmpty(subscriber.getUserName())) {
			searchUserQuery.addCriteria(Criteria.where("userName").is(subscriber.getUserName()));
		}
		if (subscriber.getCountry() != null) {
			searchUserQuery.addCriteria(Criteria.where("country").is(subscriber.getCountry()));
		}
		if (subscriber.getRole() != null) {
			searchUserQuery.addCriteria(Criteria.where("role").is(subscriber.getRole()));
		}
		if (subscriber.getStatus() != null) {
			searchUserQuery.addCriteria(Criteria.where("status").is(subscriber.getStatus()));
		}
		if (subscriber.getPartner() != null) {
			searchUserQuery.addCriteria(Criteria.where("partner").is(subscriber.getPartner()));
		}
		if (subscriber.getCreationDate() != null) {
			searchUserQuery.addCriteria(Criteria.where("creationDate").gte(subscriber.getCreationDate()));
		}
		return searchUserQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.wahoo.model.dao.AbstractDao#createUpdateQuery(java.lang.
	 * Object)
	 */
	@Override
	protected Update createUpdateQuery(Subscriber subscriber) {
		Update update = new Update();

		if (!StringUtils.isEmpty(subscriber.getEmail())) {
			update.set("email", subscriber.getEmail());
		}

		if (!StringUtils.isEmpty(subscriber.getUserName())) {
			update.set("userName", subscriber.getUserName());
		}

		if (!StringUtils.isEmpty(subscriber.getPassword())) {
			update.set("password", subscriber.getPassword());
		}

		if (!StringUtils.isEmpty(subscriber.getProfile())) {
			update.set("profile", subscriber.getProfile());
		}

		if (subscriber.getRole() != null) {
			update.set("role", subscriber.getRole());
		}

		if (subscriber.getStatus() != null) {
			update.set("status", subscriber.getStatus());
		}

		if (subscriber.getPartner() != null) {
			update.set("partner", subscriber.getPartner());
		}
		if (subscriber.getUpdateDate() != null) {
			update.set("updateDate", subscriber.getUpdateDate());
		}
		if (subscriber.getTokenExpirationDate() != null) {
			update.set("tokenExpirationDate", subscriber.getTokenExpirationDate());
		}

		return update;
	}
}
