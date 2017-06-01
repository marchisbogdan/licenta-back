package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.PerformanceStatistic;
import com.onnisoft.wahoo.model.document.StatisticalHistory;

@Deprecated
@Repository
public class StatisticalHistoryDAO extends AbstractDao<StatisticalHistory> {

	@Autowired
	private Dao<PerformanceStatistic> performanceStatisticDAO;

	@Override
	public StatisticalHistory retrieve(StatisticalHistory t) {
		return super.retrieve(t, StatisticalHistory.class);
	}

	@Override
	public StatisticalHistory retrieveById(String id) {
		return super.retrieveById(id, StatisticalHistory.class);
	}

	@Override
	public List<StatisticalHistory> retrieveList(StatisticalHistory t) {
		return super.retrieveList(t, StatisticalHistory.class);
	}

	@Override
	public boolean update(StatisticalHistory t) {
		return super.update(t, Criteria.where("id").is(t.getId()), StatisticalHistory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(StatisticalHistory statisticalHistory) {
		Query query = new Query(Criteria.where("id").is(statisticalHistory.getId()));
		boolean deleted = super.delete(query, StatisticalHistory.class);
		if (!deleted) {
			logger.warn("Statistical history was not deleted " + statisticalHistory);
		} else {
			Set<PerformanceStatistic> performanceStatistics = statisticalHistory.getPerformanceStatistics();
			if (performanceStatistics != null && !performanceStatistics.isEmpty()) {
				for (PerformanceStatistic performanceStatistic : performanceStatistics) {
					boolean deletedPS = this.performanceStatisticDAO.delete(performanceStatistic);
					if (!deletedPS) {
						logger.warn("Performance statistic was not deleted " + performanceStatistic);
					}
					deleted = deleted && deletedPS;
				}
			}
		}
		return deleted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createSearchQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Query createSearchQuery(StatisticalHistory statisticalHistory) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(statisticalHistory.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(statisticalHistory.getId()));
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
	protected Update createUpdateQuery(StatisticalHistory statisticalHistory) {
		Update update = new Update();

		if (statisticalHistory.getPerformanceStatistics() != null && !statisticalHistory.getPerformanceStatistics().isEmpty()) {
			update.set("performanceStatistics", statisticalHistory.getPerformanceStatistics());
		}

		return update;
	}

}
