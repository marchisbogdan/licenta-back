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
import com.onnisoft.wahoo.model.document.FinancialHistory;
import com.onnisoft.wahoo.model.document.Logbook;
import com.onnisoft.wahoo.model.document.MarketingHistory;
import com.onnisoft.wahoo.model.document.StatisticalHistory;

@SuppressWarnings("deprecation")
@Repository
public class LogbookDAO extends AbstractDao<Logbook> {

	@Autowired
	private Dao<FinancialHistory> financialHistoryDAO;

	@Autowired
	private Dao<StatisticalHistory> statisticalHistoryDAO;

	@Autowired
	private Dao<MarketingHistory> marketingHistoryDAO;

	@Override
	public Logbook retrieve(Logbook t) {
		return super.retrieve(t, Logbook.class);
	}

	@Override
	public Logbook retrieveById(String id) {
		return super.retrieveById(id, Logbook.class);
	}

	@Override
	public List<Logbook> retrieveList(Logbook t) {
		return super.retrieveList(t, Logbook.class);
	}

	@Override
	public boolean update(Logbook t) {
		return super.update(t, Criteria.where("id").is(t.getId()), Logbook.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(Logbook logbook) {
		Query query = new Query(Criteria.where("id").is(logbook.getId()));
		boolean deleted = super.delete(query, Logbook.class);
		if (!deleted) {
			logger.warn("Logbook was not deleted " + logbook);
		} else {
			Set<FinancialHistory> financialHistories = logbook.getFinancialHistories();
			if (financialHistories != null && !financialHistories.isEmpty()) {
				for (FinancialHistory financialHistory : financialHistories) {
					boolean deletedFinancialHistory = this.financialHistoryDAO.delete(financialHistory);
					if (!deletedFinancialHistory) {
						logger.warn("Financial history was not deleted " + financialHistory);
					}
					deleted = deleted && deletedFinancialHistory;
				}
			}

			Set<StatisticalHistory> statisticalHistories = logbook.getStatisticalHistories();
			if (statisticalHistories != null && !statisticalHistories.isEmpty()) {
				for (StatisticalHistory statisticalHistory : statisticalHistories) {
					boolean deletedStatisticalHistory = this.statisticalHistoryDAO.delete(statisticalHistory);
					if (!deletedStatisticalHistory) {
						logger.warn("Statistical history was not deleted " + statisticalHistory);
					}
					deleted = deleted && deletedStatisticalHistory;
				}
			}

			Set<MarketingHistory> marketingHistories = logbook.getMarketingHistories();
			if (marketingHistories != null && !marketingHistories.isEmpty()) {
				for (MarketingHistory marketingHistory : marketingHistories) {
					boolean deletedMarketingHistory = this.marketingHistoryDAO.delete(marketingHistory);
					if (!deletedMarketingHistory) {
						logger.warn("Marketing history was not deleted " + marketingHistory);
					}
					deleted = deleted && deletedMarketingHistory;
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
	protected Query createSearchQuery(Logbook logbook) {
		Query searchQuery = new Query();

		if (!StringUtils.isEmpty(logbook.getId())) {
			searchQuery.addCriteria(Criteria.where("id").is(logbook.getId()));
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
	protected Update createUpdateQuery(Logbook logbook) {
		Update update = new Update();

		if (logbook.getFinancialHistories() != null && !logbook.getFinancialHistories().isEmpty()) {
			update.set("financialHistories", logbook.getFinancialHistories());
		}
		if (logbook.getStatisticalHistories() != null && !logbook.getStatisticalHistories().isEmpty()) {
			update.set("statisticalHistories", logbook.getStatisticalHistories());
		}
		if (logbook.getMarketingHistories() != null && !logbook.getMarketingHistories().isEmpty()) {
			update.set("marketingHistories", logbook.getMarketingHistories());
		}

		return update;
	}

}
