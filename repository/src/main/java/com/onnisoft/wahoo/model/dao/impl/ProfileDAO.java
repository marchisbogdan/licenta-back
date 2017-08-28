package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Profile;

@Repository
public class ProfileDAO extends AbstractDao<Profile> {

	@Override
	public Profile retrieve(Profile profile) {
		return super.retrieve(profile, Profile.class);
	}

	@Override
	public Profile retrieveById(String id) {
		return super.retrieveById(id, Profile.class);
	}

	@Override
	public List<Profile> retrieveList(Profile profile) {
		return super.retrieveList(profile, Profile.class);
	}

	@Override
	public boolean update(Profile profile) {
		return super.update(profile, Criteria.where("id").is(profile.getId()), Profile.class);
	}

	@Override
	public boolean delete(Profile profile) {
		Query query = new Query(Criteria.where("id").is(profile.getId()));
		boolean deleted = super.delete(query, Profile.class);
		if (!deleted) {
			logger.warn("User profile " + profile + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Profile profile) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(profile.getCity())) {
			searchQuery.addCriteria(Criteria.where("city").is(profile.getCity()));
		}
		if (profile.getLanguage() != null) {
			searchQuery.addCriteria(Criteria.where("language").is(profile.getLanguage()));
		}
		if (profile.getGender() != null) {
			searchQuery.addCriteria(Criteria.where("gender").is(profile.getGender()));
		}
		if (profile.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(profile.getCreationDate()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Profile profile) {
		Update update = new Update();

		if (!StringUtils.isEmpty(profile.getAddress())) {
			update.set("address", profile.getAddress());
		}
		if (!StringUtils.isEmpty(profile.getCity())) {
			update.set("city", profile.getCity());
		}
		if (!StringUtils.isEmpty(profile.getStatement())) {
			update.set("statusMessage", profile.getStatement());
		}
		if (!StringUtils.isEmpty(profile.getZipCode())) {
			update.set("zipCode", profile.getZipCode());
		}
		if (!StringUtils.isEmpty(profile.getAvatarLink())) {
			update.set("avatarLink", profile.getAvatarLink());
		}
		if (profile.getLanguage() != null) {
			update.set("language", profile.getLanguage());
		}
		if (profile.getUpdateDate() != null) {
			update.set("updateDate", profile.getUpdateDate());
		}
		return update;
	}
}
