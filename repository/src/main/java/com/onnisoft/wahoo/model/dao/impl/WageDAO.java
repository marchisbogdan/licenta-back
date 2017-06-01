package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Wage;

@Repository
public class WageDAO extends AbstractDao<Wage> {

	@Override
	public Wage retrieve(Wage t) {
		return super.retrieve(t, Wage.class);
	}

	@Override
	public Wage retrieveById(String id) {
		return super.retrieveById(id, Wage.class);
	}

	@Override
	public List<Wage> retrieveList(Wage t) {
		return super.retrieveList(t, Wage.class);
	}

	@Override
	public boolean update(Wage t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Wage.class);
	}

	@Override
	public boolean delete(Wage t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, Wage.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Wage wage) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(wage.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(wage.getId()));
		}
		if (wage.getNetSalary() != null && wage.getNetSalary() > 0) {
			searchQuery.addCriteria(Criteria.where("netSalary").is(wage.getNetSalary()));
		}
		if (wage.getSponsorSalary() != null && wage.getSponsorSalary() > 0) {
			searchQuery.addCriteria(Criteria.where("sponsorSalary").is(wage.getSponsorSalary()));
		}
		if (wage.getTotalBonus() != null && wage.getTotalBonus() > 0) {
			searchQuery.addCriteria(Criteria.where("totalBonus").is(wage.getTotalBonus()));
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
	protected Update createUpdateQuery(Wage wage) {
		Update update = new Update();

		if (wage.getNetSalary() != null && wage.getNetSalary() > 0) {
			update.set("netSalary", wage.getNetSalary());
		}
		if (wage.getSponsorSalary() != null && wage.getSponsorSalary() > 0) {
			update.set("sponsorSalary", wage.getSponsorSalary());
		}
		if (wage.getTotalBonus() != null && wage.getTotalBonus() > 0) {
			update.set("totalBonus", wage.getTotalBonus());
		}
		if (wage.getUpdateDate() != null) {
			update.set("updateDate", wage.getUpdateDate());
		}

		return update;
	}

}
