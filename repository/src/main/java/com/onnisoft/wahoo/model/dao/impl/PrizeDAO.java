package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Prize;

@Repository
public class PrizeDAO extends AbstractDao<Prize> {

	@Override
	public Prize retrieve(Prize prize) {
		return super.retrieve(prize, Prize.class);
	}

	@Override
	public Prize retrieveById(String id) {
		return super.retrieveById(id, Prize.class);
	}

	@Override
	public List<Prize> retrieveList(Prize prize) {
		return super.retrieveList(prize, Prize.class);
	}

	@Override
	public boolean update(Prize prize) {
		return super.update(prize, Criteria.where("id").is(prize.getId()), Prize.class);
	}

	@Override
	public boolean delete(Prize prize) {
		Query query = new Query(Criteria.where("id").is(prize.getId()));

		boolean deleted = super.delete(query, Prize.class);

		if (!deleted) {
			logger.warn("Prize " + prize.getName() + " wasn't deleted");
		}

		return deleted;
	}

	@Override
	protected Query createSearchQuery(Prize prize) {
		Query searchQuery = new Query();
		if (prize.getType() != null) {
			searchQuery.addCriteria(Criteria.where("type").is(prize.getType()));
		}
		if (!StringUtils.isEmpty((prize.getName()))) {
			searchQuery.addCriteria(Criteria.where("name").is(prize.getName()));
		}
		if (!StringUtils.isEmpty(prize.getSponsorName())) {
			searchQuery.addCriteria(Criteria.where("sponsorName").is(prize.getSponsorName()));
		}
		if (prize.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(prize.getCreationDate()));
		}

		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Prize prize) {
		Update update = new Update();
		if (prize.getType() != null) {
			update.set("type", prize.getType());
		}
		if (!StringUtils.isEmpty(prize.getName())) {
			update.set("name", prize.getName());
		}
		if (!StringUtils.isEmpty(prize.getSponsorLogoLink())) {
			update.set("sponsorLogoLink", prize.getSponsorLogoLink());
		}
		if (!StringUtils.isEmpty(prize.getPrizeImageLink())) {
			update.set("prizeImageLink", prize.getPrizeImageLink());
		}
		if (!StringUtils.isEmpty(prize.getInfo())) {
			update.set("info", prize.getInfo());
		}
		if (prize.getValue() != null) {
			update.set("value", prize.getValue());
		}
		if (prize.getNumberOfPrizes() != null) {
			update.set("numberOfPrizes", prize.getNumberOfPrizes());
		}
		if (prize.getGuaranteedPool() != null) {
			update.set("guaranteedPool", prize.getGuaranteedPool());
		}
		if (prize.getNormalPool() != null) {
			update.set("normalPool", prize.getNormalPool());
		}
		if (prize.getSatellitePool() != null) {
			update.set("satellitePool", prize.getSatellitePool());
		}
		if (prize.getSponsorPool() != null) {
			update.set("sponsorPool", prize.getSponsorPool());
		}
		if (prize.getUpdateDate() != null) {
			update.set("updateDate", prize.getUpdateDate());
		}
		return update;
	}

}
