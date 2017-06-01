package com.onnisoft.wahoo.api.services;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.model.config.FootballPointSystem;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventStatus;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerStats;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;
import com.onnisoft.wahoo.model.document.enums.StatusEnum;

@Component
public class PlayerService extends AbstractService<Player> {

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<PlayerStats> playerStatsDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatsDao;

	@Autowired
	private FootballPointSystem footballPointSystem;

	@Override
	void inactivateById(String id) {
		this.playerDao.update(new Player.Builder().id(id).value(0.0).height(0.0).weight(0.0).status(StatusEnum.INACTIVE).build());
	}

	/**
	 * Calculates the statistics for a given player by a specific event
	 * statistic.
	 * 
	 * @param player
	 * @param playerEventStats
	 * @return A PlayerStats entity if it has been updated and null otherwise
	 */
	public PlayerStats updatePlayerStatsByEventStats(Player player, PlayerEventStatistics playerEventStats) {
		Calendar gameFinishes = Calendar.getInstance();
		gameFinishes.setTime(playerEventStats.getEvent().getStartDateTime());
		gameFinishes.add(Calendar.MINUTE, 150);
		Calendar now = Calendar.getInstance();

		if (gameFinishes.before(now)) {
			int matchesPlayed = 0, minutesPlayed = 0, goals = 0, assists = 0, yellowCards = 0, redCards = 0, lineupStarts = 0, substitutedIn = 0,
					substitutedOut = 0;
			PlayerStats playerStats = this.playerStatsDao
					.retrieve(new PlayerStats.Builder().player(player).season(playerEventStats.getEvent().getRound().getSeason()).build());

			if (playerStats != null) {
				matchesPlayed = playerStats.getMatchesPlayed();
				minutesPlayed = playerStats.getMinutesPlayed();
				goals = playerStats.getGoals();
				assists = playerStats.getAssists();
				yellowCards = playerStats.getYellowCards();
				redCards = playerStats.getRedCards();
				lineupStarts = playerStats.getLineupStarts();
				substitutedIn = playerStats.getSubstitutedIn();
				substitutedOut = playerStats.getSubstitutedOut();
			}

			if (playerEventStats.isGameStarted() == true) {
				if (playerEventStats.getMinsPlayed() > 0) {
					// check if player was in the game and how long
					matchesPlayed++;
					minutesPlayed += playerEventStats.getMinsPlayed();
					// lineupStarts
					if (playerEventStats.isTotalSubOn() == false) {
						lineupStarts++;
					} else {
						// substituted in
						substitutedIn++;
					}
					// substituted out
					if (playerEventStats.isTotalSubOff() == true) {
						substitutedOut++;
					}
				}
				// goals
				goals += playerEventStats.getGoals();

				// assists
				assists += playerEventStats.getGoalAssist();
				// cards
				if (playerEventStats.isRedCard() == true) {
					redCards++;
				}
				if (playerEventStats.isYellowCard() == true) {
					yellowCards++;
				}
				if (playerEventStats.isSecondYellow() == true) {
					yellowCards++;
				}
			}

			if (playerStats != null) {
				// update
				boolean updated = playerStatsDao.update(new PlayerStats.Builder().id(playerStats.getId()).matchesPlayed(matchesPlayed)
						.minutesPlayed(minutesPlayed).goals(goals).assists(assists).yellowCards(yellowCards).redCards(redCards).lineupStarts(lineupStarts)
						.substitutedIn(substitutedIn).substitutedOut(substitutedOut).build());
				if (!updated) {
					logger.warn("There was a problem updating the player event statistics with id:" + playerStats.getId());
				} else {
					return null;
				}
			} else {
				logger.warn("Their is no PlayerStats entity for player:" + player.getId() + " and season:"
						+ playerEventStats.getEvent().getRound().getSeason().getName());
				return null;
			}
		}
		return playerStatsDao.retrieve(new PlayerStats.Builder().player(player).build());
	}

	/**
	 * Calculates the players point for a specific event, by taking into
	 * consideration his performance and his team performance.
	 * 
	 * @param player
	 * @param event
	 * @return
	 */
	public PlayerEventStatistics updatePlayerPointsForAnEvent(Player player, Event event) {
		PlayerEventStatistics statistics = this.playerEventStatsDao.retrieve(new PlayerEventStatistics.Builder().player(player).event(event).build());

		Calendar gameFinishes = Calendar.getInstance();
		gameFinishes.setTime(event.getStartDateTime());
		gameFinishes.add(Calendar.MINUTE, 150);
		Calendar now = Calendar.getInstance();

		if (gameFinishes.before(now)) {
			int points = 0;
			// team performance
			points += this.calculatePointsForTeamPerformance(player.getCompetitor().getName(), event);

			// player performance

			// goals and clean sheet
			PlayerPositionEnum position = player.getPosition();
			if (PlayerPositionEnum.attackPositions().contains(position.name())) {
				points += statistics.getGoals() * footballPointSystem.GOAL_BY_STRYKER;
			} else if (PlayerPositionEnum.midfieldPositions().contains(position.name())) {
				points += statistics.getGoals() * footballPointSystem.GOAL_BY_MIDFIELDER;
				if (statistics.getCleanSheet()) {
					if (statistics.getMinsPlayed() > 60) {
						points += footballPointSystem.CLEAN_SHEET_MIDFIELDER;
					}
				}
			} else if (PlayerPositionEnum.defensePositions().contains(position.name())) {
				points += statistics.getGoals() * footballPointSystem.GOAL_BY_DEFENDER;
				if (statistics.getCleanSheet()) {
					if (statistics.getMinsPlayed() > 60) {
						points += footballPointSystem.CLEAN_SHEET_DEFENDER;
					}
				}
			} else {
				points += statistics.getGoals() * footballPointSystem.GOAL_BY_GOALKEEPER;
				if (statistics.getCleanSheet()) {
					if (statistics.getMinsPlayed() > 60) {
						points += footballPointSystem.CLEAN_SHEET_GOALKEEPER;
					}
				}
			}

			// assist
			points += statistics.getGoalAssist() * footballPointSystem.ASSIST;

			// hattrick
			if (statistics.getGoals() >= 3) {
				points += footballPointSystem.HATTRICK;
			}
			// penalty !!!!! Missing information about saved or missed penalties

			// cards
			if (statistics.isYellowCard()) {
				points += footballPointSystem.YELLOW_CARD;
			}
			if (statistics.isSecondYellow()) {
				points += footballPointSystem.SECOND_YELLOW_CARD;
			}
			if (statistics.isRedCard()) {
				points += footballPointSystem.RED_CARD;
			}

			// tactics
			if (!statistics.isTotalSubOn() && statistics.getMinsPlayed() > 0) {
				// in start formation
				points += footballPointSystem.IN_START_FORMATION;
			}
			if (statistics.isTotalSubOn()) {
				points += footballPointSystem.IN_AS_SUBSTITUTE;
			}
			if (statistics.isTotalSubOff()) {
				points += footballPointSystem.OUT_AS_SUBSTITUTE;
			}
			if (statistics.getMinsPlayed() == 0) {
				points += footballPointSystem.NOT_USED_IN_MATCH;
			}

			// playing time
			if (statistics.getMinsPlayed() >= 60) {
				points += footballPointSystem.OVER_60;
			}
			if (statistics.getMinsPlayed() >= 1 && statistics.getMinsPlayed() <= 59) {
				points += footballPointSystem.UNDER_60;
			}

			boolean updated = this.playerEventStatsDao.update(new PlayerEventStatistics.Builder().id(statistics.getId()).points(points).build());
			if (!updated) {
				logger.warn("There was a problem updating the player event statistics with id:" + statistics.getId());
			}
		}
		return this.playerEventStatsDao.retrieveById(statistics.getId());
	}

	/**
	 * The function calculates the total points scored by a player in the events
	 * averaging of the specified season, where he played with his current team.
	 * 
	 * @param player
	 * @return
	 */
	public PlayerStats updatePlayerTotalPoints(Player player, Season season) {

		// TODO: TEST the method

		List<PlayerEventStatistics> playerEventStatistics = this.playerEventStatsDao
				.retrieveList(new PlayerEventStatistics.Builder().player(player).competitor(player.getCompetitor()).build()).stream()
				.filter(pes -> pes.getEvent().getRound().getSeason().equals(season)).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(playerEventStatistics)) {
			logger.warn("The player:" + player.getId() + " hasn't played in season:" + season.getName());
			return null;
		}

		int totalPoints = playerEventStatistics.stream().map(pes -> pes.getPoints()).reduce(0, (sum, points) -> sum + points).intValue();

		PlayerStats playerStats = this.playerStatsDao
				.retrieve(new PlayerStats.Builder().player(player).season(season).realCompetitor(player.getCompetitor()).build());

		if (playerStats == null) {
			logger.warn("There is no PlayerStats entity for the player:" + player.getId() + " and season:" + season.getName());
			return null;
		} else {
			boolean updated = this.playerStatsDao.update(new PlayerStats.Builder().id(playerStats.getId()).totalPoints(totalPoints).build());
			if (!updated) {
				logger.warn("The PlayerStats entity wasn't UPDATED for player:" + player.getId() + " and season:" + season.getName());
				return null;
			} else {
				logger.info(
						"UPDATED the PlayerStats entity for player:" + player.getId() + " and season:" + season.getName() + " with totalPoints:" + totalPoints);
			}
		}
		return this.playerStatsDao.retrieve(new PlayerStats.Builder().player(player).season(season).realCompetitor(player.getCompetitor()).build());
	}

	private int calculatePointsForTeamPerformance(String competitor_name, Event event) {
		// get event status
		int points = 0;
		EventStatus eventStatus = event.getEventStatus();

		String[] teams = event.getName().split(" v ");
		if (teams[0].equalsIgnoreCase(competitor_name)) {
			// home team
			points += eventStatus.getHomeScore() * footballPointSystem.OWN_TEAM_GOAL;
			points += eventStatus.getAwayScore() * footballPointSystem.GOAL_CONCEDED;
			if (eventStatus.getHomeScore() > eventStatus.getAwayScore()) {
				// won
				points += footballPointSystem.HOME_MATCH_WON;
			} else if (eventStatus.getHomeScore() < eventStatus.getAwayScore()) {
				// lost
				points += footballPointSystem.HOME_MATCH_LOST;
			} else {
				// draw
				points += footballPointSystem.HOME_MATCH_DRAWN;
			}
		} else if (teams[1].equalsIgnoreCase(competitor_name)) {
			// away team
			points += eventStatus.getHomeScore() * footballPointSystem.GOAL_CONCEDED;
			points += eventStatus.getAwayScore() * footballPointSystem.OWN_TEAM_GOAL;
			if (eventStatus.getHomeScore() > eventStatus.getAwayScore()) {
				// lost
				points += footballPointSystem.AWAY_MATCH_LOST;
			} else if (eventStatus.getHomeScore() < eventStatus.getAwayScore()) {
				// won
				points += footballPointSystem.AWAY_MATCH_WON;
			} else {
				// draw
				points += footballPointSystem.AWAY_MATCH_DRAWN;
			}
		} else {
			logger.warn("Problem identifying on which position is the Team:{" + competitor_name + "} playing in Event:{" + event.getName() + "}.");
		}
		return points;
	}
}
