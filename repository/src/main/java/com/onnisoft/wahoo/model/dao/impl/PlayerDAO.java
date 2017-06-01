package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.dao.PlayerCustomDao;
import com.onnisoft.wahoo.model.document.Player;

/**
 * 
 * Implements CRUD operations on Player document.
 *
 * @author mbozesan
 * @date 20 Oct 2016 - 14:18:15
 *
 */
@Repository
public class PlayerDAO extends PlayerCustomDao {

	@Override
	public Player retrieve(Player player) {
		return super.retrieve(player, Player.class);
	}

	@Override
	public Player retrieveById(String id) {
		return super.retrieveById(id, Player.class);
	}

	@Override
	public List<Player> retrieveList(Player player) {
		return super.retrieveList(player, Player.class);
	}

	@Override
	public boolean update(Player player) {
		return super.update(player, Criteria.where("id").is(player.getId()), Player.class);
	}

	@Override
	public boolean delete(Player player) {
		Query query = new Query(Criteria.where("id").is(player.getId()));
		boolean deleted = super.delete(query, Player.class);
		if (!deleted) {
			logger.warn("User " + player + " was not deleted!");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Player player) {
		Query searchQuery = new Query();
		if (!StringUtils.isEmpty(player.getFirstName())) {
			searchQuery.addCriteria(Criteria.where("firstName").is(player.getFirstName()));
		}
		if (!StringUtils.isEmpty(player.getLastName())) {
			searchQuery.addCriteria(Criteria.where("lastName").is(player.getLastName()));
		}
		if (!StringUtils.isEmpty(player.getNickName())) {
			searchQuery.addCriteria(Criteria.where("nickName").is(player.getNickName()));
		}
		if (player.getCountry() != null) {
			searchQuery.addCriteria(Criteria.where("country").is(player.getCountry()));
		}
		if (player.getPosition() != null) {
			searchQuery.addCriteria(Criteria.where("position").is(player.getPosition().name()));
		}
		if (player.getValue() != null) {
			searchQuery.addCriteria(Criteria.where("value").is(player.getValue()));
		}
		if (player.getBirthDate() != null) {
			searchQuery.addCriteria(Criteria.where("birthDate").is(player.getBirthDate()));
		}
		if (player.getSuspensions() != null) {
			searchQuery.addCriteria(Criteria.where("suspensions").is(player.getSuspensions()));
		}
		if (player.getCreationDate() != null) {
			searchQuery.addCriteria(Criteria.where("creationDate").is(player.getCreationDate()));
		}
		if (player.getState() != null) {
			searchQuery.addCriteria(Criteria.where("state").is(player.getState()));
		}
		if (player.getCompetitor() != null) {
			searchQuery.addCriteria(Criteria.where("competitor").is(player.getCompetitor()));
		}
		if (player.getWage() != null) {
			searchQuery.addCriteria(Criteria.where("wage").is(player.getWage()));
		}
		return searchQuery;
	}

	@Override
	protected Update createUpdateQuery(Player player) {
		Update update = new Update();

		if (!StringUtils.isEmpty(player.getFirstName())) {
			update.set("firstName", player.getFirstName());
		}
		if (!StringUtils.isEmpty(player.getLastName())) {
			update.set("lastName", player.getFirstName());
		}
		if (!StringUtils.isEmpty(player.getNickName())) {
			update.set("nickName", player.getNickName());
		}
		if (player.getCountry() != null) {
			update.set("country", player.getCountry());
		}
		if (!StringUtils.isEmpty(player.getStatement())) {
			update.set("statement", player.getStatement());
		}
		if (!StringUtils.isEmpty(player.getAvatarLink())) {
			update.set("avatarLink", player.getAvatarLink());
		}
		if (player.getPosition() != null) {
			update.set("position", player.getPosition().name());
		}
		if (player.getValue() != null) {
			update.set("value", player.getValue());
		}
		if (player.getBirthDate() != null) {
			update.set("birthDate", player.getBirthDate());
		}
		if (player.getHeight() != null) {
			update.set("height", player.getHeight());
		}
		if (player.getWeight() != null) {
			update.set("weight", player.getWeight());
		}
		if (player.getSuspensions() != null) {
			update.set("suspensions", player.getSuspensions());
		}
		if (player.getPosition() != null) {
			update.set("position", player.getPosition());
		}
		if (player.getStatus() != null) {
			update.set("status", player.getStatus());
		}
		if (player.getState() != null) {
			update.set("state", player.getState());
		}
		if (player.getCompetitor() != null) {
			update.set("competitor", player.getCompetitor());
		}
		if (player.getWage() != null) {
			update.set("wage", player.getWage());
		}
		if (!CollectionUtils.isEmpty(player.getFinancialHistories())) {
			update.set("financialHistories", player.getFinancialHistories());
		}
		if (player.getUpdateDate() != null) {
			update.set("updateDate", player.getUpdateDate());
		}
		return update;
	}
}
