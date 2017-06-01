package com.onnisoft.wahoo.model.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:footballPointSystem.properties")
public class FootballPointSystem {
	// individual performance
	@Value("${football.goal_by_stryker}")
	public int GOAL_BY_STRYKER;
	@Value("${football.goal_by_midfielder}")
	public int GOAL_BY_MIDFIELDER;
	@Value("${football.goal_by_defender}")
	public int GOAL_BY_DEFENDER;
	@Value("${football.goal_by_goalkeeper}")
	public int GOAL_BY_GOALKEEPER;
	@Value("${football.own_goal}")
	public int OWN_GOAL;
	@Value("${football.assist}")
	public int ASSIST;

	// special performance
	@Value("${football.hattrick}")
	public int HATTRICK;
	@Value("${football.saved_penalty}")
	public int SAVED_PENALTY;
	@Value("${football.missed_penalty}")
	public int MISSED_PENALTY;

	// clean sheet points given to players with a minimum of 60 minute play time
	@Value("${football.clean_sheet_goalkeeper}")
	public int CLEAN_SHEET_GOALKEEPER;
	@Value("${football.clean_sheet_defender}")
	public int CLEAN_SHEET_DEFENDER;
	@Value("${football.clean_sheet_midfielder}")
	public int CLEAN_SHEET_MIDFIELDER;

	// fair play
	@Value("${football.yellow_card}")
	public int YELLOW_CARD;
	@Value("${football.second_yellow_card}")
	public int SECOND_YELLOW_CARD;
	@Value("${football.red_card}")
	public int RED_CARD;

	// Team Performance
	@Value("${football.own_team_goal}")
	public int OWN_TEAM_GOAL;
	@Value("${football.goal_conceded}")
	public int GOAL_CONCEDED;
	@Value("${football.home_match_won}")
	public int HOME_MATCH_WON;
	@Value("${football.home_match_drawn}")
	public int HOME_MATCH_DRAWN;
	@Value("${football.home_match_lost}")
	public int HOME_MATCH_LOST;
	@Value("${football.away_match_won}")
	public int AWAY_MATCH_WON;
	@Value("${football.away_match_drawn}")
	public int AWAY_MATCH_DRAWN;
	@Value("${football.away_match_lost}")
	public int AWAY_MATCH_LOST;

	// Tactics
	@Value("${football.in_start_formation}")
	public int IN_START_FORMATION;
	@Value("${football.in_as_substitute}")
	public int IN_AS_SUBSTITUTE;
	@Value("${football.out_as_substitute}")
	public int OUT_AS_SUBSTITUTE;
	@Value("${football.not_used_in_match}")
	public int NOT_USED_IN_MATCH;

	// Playing time
	@Value("${football.over_60}")
	public int OVER_60;
	@Value("${football.under_60}")
	public int UNDER_60;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
