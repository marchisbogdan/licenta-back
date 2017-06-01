package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Brand;

@Repository
public class BrandDAO extends AbstractDao<Brand> {

	@Override
	public Brand retrieve(Brand t) {
		return super.retrieve(t, Brand.class);
	}

	@Override
	public Brand retrieveById(String id) {
		return super.retrieveById(id, Brand.class);
	}

	@Override
	public List<Brand> retrieveList(Brand t) {
		return super.retrieveList(t, Brand.class);
	}

	@Override
	public boolean update(Brand t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Brand.class);
	}

	@Override
	public boolean delete(Brand t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, Brand.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	@Override
	protected Query createSearchQuery(Brand brand) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(brand.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(brand.getId()));
		}
		if (!StringUtils.isEmpty(brand.getName())) {
			searchQuery.addCriteria(Criteria.where("name").is(brand.getName()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Brand brand) {
		Update update = new Update();

		if (!StringUtils.isEmpty(brand.getName())) {
			update.set("name", brand.getName());
		}
		if (brand.getUpdateDate() != null) {
			update.set("updateDate", brand.getUpdateDate());
		}
		return update;
	}

}
