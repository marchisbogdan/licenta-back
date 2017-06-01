package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Sponsorship;

@Repository
public class SponsorshipDAO extends AbstractDao<Sponsorship> {
	@Override
	public Sponsorship retrieve(Sponsorship t) {
		return super.retrieve(t, Sponsorship.class);
	}

	@Override
	public Sponsorship retrieveById(String id) {
		return super.retrieveById(id, Sponsorship.class);
	}

	@Override
	public List<Sponsorship> retrieveList(Sponsorship t) {
		return super.retrieveList(t, Sponsorship.class);
	}

	@Override
	public boolean update(Sponsorship t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Sponsorship.class);
	}

	@Override
	public boolean delete(Sponsorship t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, Sponsorship.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Sponsorship sponsorship) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(sponsorship.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(sponsorship.getId()));
		}
		if (sponsorship.getCost() != null && sponsorship.getCost() > 0) {
			searchQuery.addCriteria(Criteria.where("cost").is(sponsorship.getCost()));
		}
		if (sponsorship.getType() != null) {
			searchQuery.addCriteria(Criteria.where("type").is(sponsorship.getType()));
		}

		return searchQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createUpdateQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Update createUpdateQuery(Sponsorship sponsorship) {
		Update update = new Update();

		if (sponsorship.getCost() != null && sponsorship.getCost() > 0) {
			update.set("cost", sponsorship.getCost());
		}
		if (sponsorship.getType() != null) {
			update.set("type", sponsorship.getType());
		}
		if (sponsorship.getUpdateDate() != null) {
			update.set("updateDate", sponsorship.getUpdateDate());
		}

		return update;
	}
}
