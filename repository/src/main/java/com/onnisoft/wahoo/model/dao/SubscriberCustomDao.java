package com.onnisoft.wahoo.model.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;

/***
 * 
 * Custom Subscriber Dao operations
 *
 * @author bogdan.marchis
 * @date 29 Dec 2016 - 10:16:25
 *
 */
public abstract class SubscriberCustomDao extends AbstractDao<Subscriber> {

	@Autowired
	private Dao<Country> countryDao;

	/**
	 * Retrieve a list of User subscribers with the username, first name, last
	 * name, or email matching the pattern
	 * 
	 * @param regexp
	 * @return
	 */
	public List<Subscriber> searchUserSubscribersByRegExp(String expression) {
		List<Subscriber> documentsList = null;
		if (!StringUtils.isEmpty(expression)) {
			Query searchQuery = new Query();

			String regex = ".*" + expression + ".*";

			searchQuery.addCriteria(Criteria.where("role").is(SubscriberRoleEnum.USER).orOperator(Criteria.where("firstName").regex(regex),
					Criteria.where("lastName").regex(regex), Criteria.where("userName").regex(regex), Criteria.where("email").regex(expression)));

			documentsList = this.mongoOperation.find(searchQuery, Subscriber.class);
		}

		if (documentsList == null || documentsList.isEmpty()) {
			logger.warn("No subscribers were found in the searching process.");
		}

		return documentsList;
	}

	/**
	 * Retrieves a list with all the users which have an unexpired token
	 * 
	 * @return
	 */
	public List<Subscriber> getOnlineSubscribers(String countryId, String partnerId) {

		List<Subscriber> list = null;

		Query searchQuery = new Query();

		searchQuery.addCriteria(Criteria.where("tokenExpirationDate").gte(Date.from(new Timestamp(System.currentTimeMillis()).toInstant())));

		Country country = null;
		Subscriber partner = null;

		if (!StringUtils.isEmpty(countryId) && !StringUtils.isEmpty(partnerId)) {
			country = this.countryDao.retrieveById(countryId);
			partner = this.retrieveById(partnerId);
			searchQuery.addCriteria(Criteria.where("id").ne(null).andOperator(Criteria.where("country").is(country), Criteria.where("partner").is(partner)));

		} else {
			if (!StringUtils.isEmpty(countryId)) {
				country = this.countryDao.retrieveById(countryId);
				searchQuery.addCriteria(Criteria.where("country").is(country));
			}
			if (!StringUtils.isEmpty(partnerId)) {
				partner = this.retrieveById(partnerId);
				searchQuery.addCriteria(Criteria.where("partner").is(partner));
			}
		}

		list = this.mongoOperation.find(searchQuery, Subscriber.class);

		if (list == null || list.isEmpty()) {
			logger.warn("No subscribers were found in the searching process.");
		}

		return list;
	}

	public List<Subscriber> getNewlySignedUpSubscribers(Date start_date, Date end_date, Subscriber subscriber) {
		List<Subscriber> list = null;
		if (start_date != null && end_date != null) {
			list = this.retrieveByDates(start_date, end_date, null, null, subscriber, Subscriber.class);
		} else {
			list = this.retrieveByDates(null, null, null, null, subscriber, Subscriber.class);
		}

		if (list == null || list.isEmpty()) {
			logger.warn("No subscribers were found in the searching process.");
		}

		return list;
	}

}
