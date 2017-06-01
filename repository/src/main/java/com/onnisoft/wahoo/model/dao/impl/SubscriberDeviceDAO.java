package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.SubscriberDevice;

/**
 * Implements CRUD operations for Competition document.
 *
 * @author alexandru.mos
 * @date Jun 9, 2016 - 5:09:47 PM
 *
 */
@Repository
public final class SubscriberDeviceDAO extends AbstractDao<SubscriberDevice> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.dao.Dao#retrieveById(java.lang.String)
	 */
	@Override
	public SubscriberDevice retrieveById(String id) {
		return super.retrieveById(id, SubscriberDevice.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#retrieve(java.lang.Object)
	 */
	@Override
	public SubscriberDevice retrieve(SubscriberDevice subscriberDevice) {
		return super.retrieve(subscriberDevice, SubscriberDevice.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#retrieveList(java.lang.Object)
	 */
	@Override
	public List<SubscriberDevice> retrieveList(SubscriberDevice subscriberDevice) {
		return super.retrieveList(subscriberDevice, SubscriberDevice.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public boolean update(SubscriberDevice subscriberDevice) {
		return super.update(subscriberDevice, Criteria.where("renewTokenId").is(subscriberDevice.getRenewTokenId()), SubscriberDevice.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(SubscriberDevice subscriberDevice) {
		Query query = new Query(Criteria.where("renewTokenId").is(subscriberDevice.getRenewTokenId()));
		return super.delete(query, SubscriberDevice.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createSearchQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Query createSearchQuery(SubscriberDevice subscriberDevice) {
		Query searchUserQuery = new Query();

		if (!StringUtils.isEmpty(subscriberDevice.getRenewTokenId())) {
			searchUserQuery.addCriteria(Criteria.where("renewTokenId").is(subscriberDevice.getRenewTokenId()));
		}
		if (subscriberDevice.getSubscriber() != null) {
			searchUserQuery.addCriteria(Criteria.where("subscriber").is(subscriberDevice.getSubscriber()));
		}
		if (!StringUtils.isEmpty(subscriberDevice.getUserAgent())) {
			searchUserQuery.addCriteria(Criteria.where("userAgent").is(subscriberDevice.getUserAgent()));
		}
		if (!StringUtils.isEmpty(subscriberDevice.getDeviceToken())) {
			searchUserQuery.addCriteria(Criteria.where("deviceToken").is(subscriberDevice.getDeviceToken()));
		}

		return searchUserQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createUpdateQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Update createUpdateQuery(SubscriberDevice subscriberDevice) {
		Update update = new Update();

		if (subscriberDevice.getSubscriber() != null) {
			update.set("subscriber", subscriberDevice.getSubscriber());
		}
		if (!StringUtils.isEmpty(subscriberDevice.getUserAgent())) {
			update.set("userAgent", subscriberDevice.getUserAgent());
		}
		if (!StringUtils.isEmpty(subscriberDevice.getDeviceToken())) {
			update.set("deviceToken", subscriberDevice.getDeviceToken());
		} else if (subscriberDevice.getDeviceToken() != null && subscriberDevice.getDeviceToken().length() < 1) {
			update.set("deviceToken", null);
		}

		return update;
	}
}
