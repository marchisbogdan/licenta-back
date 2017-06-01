package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Friend;

/**
 * 
 * Implements CRUD operations on Friend document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:17:38
 *
 */
@Repository
public class FriendDAO extends AbstractDao<Friend> {

	@Override
	public Friend retrieve(Friend friend) {
		return super.retrieve(friend, Friend.class);
	}

	@Override
	public Friend retrieveById(String id) {
		return super.retrieveById(id, Friend.class);
	}

	@Override
	public List<Friend> retrieveList(Friend friend) {
		return super.retrieveList(friend, Friend.class);
	}

	@Override
	public boolean update(Friend friend) {
		return super.update(friend, Criteria.where("id").is(friend.getId()), Friend.class);
	}

	@Override
	public boolean delete(Friend friend) {
		Query query = new Query(Criteria.where("id").is(friend.getId()));
		return super.delete(query, Friend.class);
	}

	@Override
	protected Query createSearchQuery(Friend friend) {
		Query searchQuery = new Query();
		if (friend.getFriend() != null) {
			searchQuery.addCriteria(Criteria.where("friend").is(friend.getFriend()));
		}
		if (friend.getSubscriber() != null) {
			searchQuery.addCriteria(Criteria.where("subscriber").is(friend.getSubscriber()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Friend friend) {
		Update update = new Update();
		if (friend.getFriend() != null) {
			update.set("friend", friend.getFriend());
		}
		if (friend.getSubscriber() != null) {
			update.set("subscriber", friend.getSubscriber());
		}
		return update;
	}
}
