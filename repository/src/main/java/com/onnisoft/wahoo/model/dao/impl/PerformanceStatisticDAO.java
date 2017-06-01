package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.PerformanceStatistic;

@Deprecated
@Repository
public class PerformanceStatisticDAO extends AbstractDao<PerformanceStatistic> {

	@Override
	public PerformanceStatistic retrieve(PerformanceStatistic t) {
		return super.retrieve(t, PerformanceStatistic.class);
	}

	@Override
	public PerformanceStatistic retrieveById(String id) {
		return super.retrieveById(id, PerformanceStatistic.class);
	}

	@Override
	public List<PerformanceStatistic> retrieveList(PerformanceStatistic t) {
		return super.retrieveList(t, PerformanceStatistic.class);
	}

	@Override
	public boolean update(PerformanceStatistic t) {
		return super.update(t, Criteria.where("id").is(t.getId()), PerformanceStatistic.class);
	}

	@Override
	public boolean delete(PerformanceStatistic t) {
		Query query = new Query(Criteria.where("id").is(t.getId()));
		boolean delete = super.delete(query, PerformanceStatistic.class);
		if (!delete) {
			logger.warn("Record :" + t + " was not deleted!");
		}
		return delete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createSearchQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Query createSearchQuery(PerformanceStatistic performanceStatistic) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(performanceStatistic.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(performanceStatistic.getId()));
		}
		if (performanceStatistic.getPointPerGame() > 0) {
			searchQuery.addCriteria(Criteria.where("pointPerGame").is(performanceStatistic.getPointPerGame()));
		}
		if (performanceStatistic.getProjectedPointPerGame() > 0) {
			searchQuery.addCriteria(Criteria.where("projectedPointPerGame").is(performanceStatistic.getProjectedPointPerGame()));
		}
		if (performanceStatistic.getCurrentPosition() > 0) {
			searchQuery.addCriteria(Criteria.where("currentPosition").is(performanceStatistic.getCurrentPosition()));
		}
		if (performanceStatistic.getProjectedFinishingPosition() > 0) {
			searchQuery.addCriteria(Criteria.where("projectedFinishingPosition").is(performanceStatistic.getProjectedFinishingPosition()));
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
	protected Update createUpdateQuery(PerformanceStatistic performanceStatistic) {
		Update update = new Update();

		if (performanceStatistic.getPointPerGame() > 0) {
			update.set("pointPerGame", performanceStatistic.getPointPerGame());
		}
		if (performanceStatistic.getProjectedPointPerGame() > 0) {
			update.set("projectedPointPerGame", performanceStatistic.getProjectedPointPerGame());
		}
		if (performanceStatistic.getCurrentPosition() > 0) {
			update.set("currentPosition", performanceStatistic.getCurrentPosition());
		}
		if (performanceStatistic.getProjectedFinishingPosition() > 0) {
			update.set("projectedFinishingPosition", performanceStatistic.getProjectedFinishingPosition());
		}

		return update;
	}

}
