package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Favorite;

/**
 * 
 * Implements CRUD operations on Favorite document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:22:53
 *
 */
@Repository
public class FavoriteDAO extends AbstractDao<Favorite> {
	@Override
	public Favorite retrieve(Favorite favorite) {
		return super.retrieve(favorite, Favorite.class);
	}

	@Override
	public Favorite retrieveById(String id) {
		return super.retrieveById(id, Favorite.class);
	}

	@Override
	public List<Favorite> retrieveList(Favorite favorite) {
		return super.retrieveList(favorite, Favorite.class);
	}

	@Override
	public boolean update(Favorite favorite) {
		return super.update(favorite, Criteria.where("id").is(favorite.getId()), Favorite.class);
	}

	@Override
	public boolean delete(Favorite favorite) {
		Query query = new Query(Criteria.where("id").is(favorite.getId()));
		return super.delete(query, Favorite.class);
	}

	@Override
	protected Query createSearchQuery(Favorite favorite) {
		Query searchQuery = new Query();
		if (favorite.getSubscriber() != null) {
			searchQuery.addCriteria(Criteria.where("subscriber").is(favorite.getSubscriber()));
		}
		if (favorite.getFavoriteCompetitor() != null) {
			searchQuery.addCriteria(Criteria.where("club").is(favorite.getFavoriteCompetitor()));
		}
		if (favorite.getFavoriteCompetition() != null) {
			searchQuery.addCriteria(Criteria.where("league").is(favorite.getFavoriteCompetition()));
		}
		if (favorite.getFavoritePlayer() != null) {
			searchQuery.addCriteria(Criteria.where("player").is(favorite.getFavoritePlayer()));
		}
		if (favorite.getFavoriteSport() != null) {
			searchQuery.addCriteria(Criteria.where("sport").is(favorite.getFavoriteSport()));
		}
		return null;
	}

	@Override
	protected Update createUpdateQuery(Favorite favorite) {
		return null;
	}
}
