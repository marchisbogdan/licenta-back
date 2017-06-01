package com.onnisoft.wahoo.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.RealCompetitor;

public abstract class PlayerCustomDao extends AbstractDao<Player> {

	public List<Player> getPlayersByNameRegexAndCompetitor(String nameExpr, RealCompetitor competitor) {
		List<Player> result = new ArrayList<>();

		if (!StringUtils.isEmpty(nameExpr)) {
			Query query = new Query();

			String regex = ".*" + nameExpr + ".*";
			Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

			query.addCriteria(Criteria.where("competitor").is(competitor).orOperator(Criteria.where("firstName").regex(pattern),
					Criteria.where("lastName").regex(pattern)));

			result = this.mongoOperation.find(query, Player.class);
		}

		return result;
	}
}
