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
import com.onnisoft.wahoo.model.document.Sponsorship;
import com.onnisoft.wahoo.model.document.Wage;

@Repository
public class FinancialHistoryDAO extends AbstractDao<FinancialHistory> {

	@Autowired
	private Dao<Wage> wageDAO;

	@Autowired
	private Dao<Sponsorship> sponsorshipDAO;

	@Override
	public FinancialHistory retrieve(FinancialHistory t) {
		return super.retrieve(t, FinancialHistory.class);
	}

	@Override
	public FinancialHistory retrieveById(String id) {
		return super.retrieveById(id, FinancialHistory.class);
	}

	@Override
	public List<FinancialHistory> retrieveList(FinancialHistory t) {
		return super.retrieveList(t, FinancialHistory.class);
	}

	@Override
	public boolean update(FinancialHistory t) {
		return super.update(t, Criteria.where("id").is(t.getId()), FinancialHistory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.onnisoft.ssp.model.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(FinancialHistory financialHistory) {
		Query query = new Query(Criteria.where("id").is(financialHistory.getId()));
		boolean deleted = super.delete(query, FinancialHistory.class);
		if (!deleted) {
			logger.warn("Financial history was not deleted " + financialHistory);
		} else {
			Wage wage = financialHistory.getWage();
			if (wage != null) {
				Wage wageToDelete = this.wageDAO.retrieveById(wage.getId());
				boolean deletedWage = this.wageDAO.delete(wageToDelete);
				if (!deletedWage) {
					logger.warn("Wage was not deleted " + wageToDelete);
				}
				deleted = deleted && deletedWage;

			}

			Set<Sponsorship> sponsorships = financialHistory.getSponsorships();
			if (sponsorships != null && !sponsorships.isEmpty()) {
				for (Sponsorship sponsorship : sponsorships) {
					boolean deletedSponsorship = this.sponsorshipDAO.delete(sponsorship);
					if (!deletedSponsorship) {
						logger.warn("Sponsorship was not deleted " + sponsorship);
					}
					deleted = deleted && deletedSponsorship;
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
	protected Query createSearchQuery(FinancialHistory financialHistory) {
		Query searchuery = new Query();

		if (!StringUtils.isEmpty(financialHistory.getId())) {
			searchuery.addCriteria(Criteria.where("id").is(financialHistory.getId()));
		}
		if (financialHistory.getTotalGateReceipt() != null && financialHistory.getTotalGateReceipt() > 0) {
			searchuery.addCriteria(Criteria.where("totalGateReceipt").is(financialHistory.getTotalGateReceipt()));
		}
		if (financialHistory.getPrizeMoney() != null && financialHistory.getPrizeMoney() > 0) {
			searchuery.addCriteria(Criteria.where("prizeMoney").is(financialHistory.getPrizeMoney()));
		}
		if (financialHistory.getWage() != null) {
			searchuery.addCriteria(Criteria.where("wage").is(financialHistory.getWage()));
		}

		return searchuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.onnisoft.ssp.model.dao.AbstractDao#createUpdateQuery(java.lang.Object
	 * )
	 */
	@Override
	protected Update createUpdateQuery(FinancialHistory financialHistory) {
		Update update = new Update();
		if (financialHistory.getTotalGateReceipt() != null && financialHistory.getTotalGateReceipt() > 0) {
			update.set("totalGateReceipt", financialHistory.getTotalGateReceipt());
		}
		if (financialHistory.getPrizeMoney() != null && financialHistory.getPrizeMoney() > 0) {
			update.set("prizeMoney", financialHistory.getPrizeMoney());
		}
		if (financialHistory.getWage() != null) {
			update.set("wage", financialHistory.getWage());
		}
		if (financialHistory.getSponsorships() != null && !financialHistory.getSponsorships().isEmpty()) {
			update.set("sponsorships", financialHistory.getSponsorships());
		}
		if (financialHistory.getUpdateDate() != null) {
			update.set("updateDate", financialHistory.getUpdateDate());
		}

		return update;
	}

}
