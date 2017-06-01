package com.onnisoft.wahoo.api.services;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.EventStatus;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.RealCompetitorSeason;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;
import com.onnisoft.wahoo.model.document.enums.StatusEnum;

@Component
public class CompetitorService extends AbstractService<RealCompetitor> {

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<RealCompetitorSeason> realCompetitorSeasonDao;

	@Autowired
	private Dao<VirtualCompetitor> virtualCompetitorDao;

	@Autowired
	private Dao<VirtualCompetitorTeam> virtualCompetitorTeamDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Override
	void inactivateById(String id) {
		this.realCompetitorDao.update(new RealCompetitor.Builder().id(id).name(null).country(null).logoUrl(null).shirtUrl(null).clubUrl(null).statement(null)
				.status(StatusEnum.INACTIVE).build());
	}

	public RealCompetitorSeason updateCompetitorStatsByEvent(RealCompetitor realCompetitor, Event event) {
		RealCompetitorSeason realCompetitorSeason = realCompetitorSeasonDao
				.retrieve(new RealCompetitorSeason.Builder().realCompetitor(realCompetitor).season(event.getRound().getSeason()).build());
		if (realCompetitorSeason == null) {
			logger.warn("There is no instance of RealCompetitorSeason for the competitor:" + realCompetitor + " and season:" + event.getRound().getSeason());
			return null;
		}
		/// TODO: Change this with a proper verification to see if the game has
		/// ended
		Calendar gameFinishes = Calendar.getInstance();
		gameFinishes.setTime(event.getStartDateTime());
		gameFinishes.add(Calendar.MINUTE, 150);
		Calendar now = Calendar.getInstance();
		///
		if (gameFinishes.before(now)) {
			int numOfMatches = realCompetitorSeason.getNumOfMatches(), numOfGoalsScored = realCompetitorSeason.getNumOfGoalsScored(),
					numOfGoalsConceded = realCompetitorSeason.getNumOfGoalsConceded(), numOfPoints = realCompetitorSeason.getNumOfPoints(),
					value = realCompetitorSeason.getValue(), numOfHomeWins = realCompetitorSeason.getNumOfHomeWins(),
					numOfHomeLooses = realCompetitorSeason.getNumOfHomeLooses(), numOfHomeDraws = realCompetitorSeason.getNumOfHomeDraws(),
					numOfAwayWins = realCompetitorSeason.getNumOfAwayWins(), numOfAwayLooses = realCompetitorSeason.getNumOfAwayLooses(),
					numOfAwayDraws = realCompetitorSeason.getNumOfAwayDraws(), cleanSheets = realCompetitorSeason.getCleanSheets(),
					noGoalsScoredMatches = realCompetitorSeason.getNoGoalsScoredMatches(), rank = realCompetitorSeason.getRank();

			EventStatus eventStatus = event.getEventStatus();
			// check if the competitor was the away team or the home team
			String[] teams = event.getName().split(" v ");
			if (teams[0].equalsIgnoreCase(realCompetitor.getName())) {
				// numOfMatches
				numOfMatches++;
				// numOfGoals
				numOfGoalsScored += eventStatus.getHomeScore();
				numOfGoalsConceded += eventStatus.getAwayScore();
				if (eventStatus.getHomeScore() > eventStatus.getAwayScore()) {
					// win
					numOfHomeWins++;
					numOfPoints += 3;

				} else if (eventStatus.getHomeScore() < eventStatus.getAwayScore()) {
					// lose
					numOfHomeLooses++;
				} else {
					// draw
					numOfHomeDraws++;
					numOfPoints += 1;
				}
				// clean sheet
				if (eventStatus.getAwayScore() == 0) {
					cleanSheets++;
				}
				// no goals scored matches
				if (eventStatus.getHomeScore() == 0) {
					noGoalsScoredMatches++;
				}
			} else if (teams[1].equalsIgnoreCase(realCompetitor.getName())) {
				// numOfMatches
				numOfMatches++;
				// numOfGoals
				numOfGoalsScored += eventStatus.getAwayScore();
				numOfGoalsConceded += eventStatus.getHomeScore();
				if (eventStatus.getAwayScore() > eventStatus.getHomeScore()) {
					// win
					numOfAwayWins++;
					numOfPoints += 3;

				} else if (eventStatus.getAwayScore() < eventStatus.getHomeScore()) {
					// lose
					numOfAwayLooses++;
				} else {
					// draw
					numOfAwayDraws++;
					numOfPoints += 1;
				}
				// clean sheet
				if (eventStatus.getHomeScore() == 0) {
					cleanSheets++;
				}
				// no goals scored matches
				if (eventStatus.getAwayScore() == 0) {
					noGoalsScoredMatches++;
				}
			} else {
				logger.warn("Problem identifying on which position is the Team:{" + realCompetitor.getName() + "} playing in Event:{" + event.getName() + "}.");
			}
			boolean update = this.realCompetitorSeasonDao
					.update(new RealCompetitorSeason.Builder().id(realCompetitorSeason.getId()).numOfMatches(numOfMatches).numOfGoalsScored(numOfGoalsScored)
							.numOfGoalsConceded(numOfGoalsConceded).numOfPoints(numOfPoints).value(value).numOfHomeWins(numOfHomeWins)
							.numOfHomeDraws(numOfHomeDraws).numOfHomeLooses(numOfHomeLooses).numOfAwayWins(numOfAwayWins).numOfAwayDraws(numOfAwayDraws)
							.numOfAwayLooses(numOfAwayLooses).cleanSheets(cleanSheets).noGoalsScoredMatches(noGoalsScoredMatches).rank(rank).build());
			if (!update) {
				logger.warn("There was a problem updating the competitor with id:" + realCompetitor.getId() + " and name:" + realCompetitor.getName());
			}
		}
		return this.realCompetitorSeasonDao.retrieveById(realCompetitorSeason.getId());
	}

	public List<VirtualCompetitorTeam> updateVirtualCompetitorTeamsPoints(VirtualCompetitor competitor, Event event) {
		List<VirtualCompetitorTeam> list = new LinkedList<>();
		List<VirtualCompetitorTeam> updatedEntities = new LinkedList<>();

		if (competitor != null) {
			list = this.virtualCompetitorTeamDao
					.retrieveList(new VirtualCompetitorTeam.Builder().virtualCompetitor(competitor).round(event.getRound()).build());
		}
		// for each team created by an user for a specific round event,
		// calculate the points scored by the chosen players in the specified
		// event

		for (VirtualCompetitorTeam team : list) {
			// get players from users team
			List<Player> players = team.getTeam();

			int roundPoints = players.stream()
					.map(p -> this.playerEventStatisticsDao.retrieve(new PlayerEventStatistics.Builder().player(p).event(event).build()))
					.map(pes -> pes.getPoints()).reduce(0, (sum, points) -> sum + points).intValue();

			boolean updated = this.virtualCompetitorTeamDao.update(new VirtualCompetitorTeam.Builder().id(team.getId()).points(roundPoints).build());

			if (!updated) {
				logger.warn("The VirtualCompetitorTeam entity couldn't be updated, id:" + team.getId());
			} else {
				logger.info("UPDATED the VirtualCompetitorTeam entity with the id:" + team.getId());
				updatedEntities.add(this.virtualCompetitorTeamDao.retrieveById(team.getId()));
			}
		}

		return updatedEntities;

	}

	public VirtualCompetitor updateVirtualCompetitorTotalPoints(Subscriber subscriber, VirtualCompetition virtualCompetition) {
		List<VirtualCompetitorTeam> list = new LinkedList<>();

		VirtualCompetitor virtualCompetitor = this.virtualCompetitorDao
				.retrieve(new VirtualCompetitor.Builder().subscriber(subscriber).virtualCompetition(virtualCompetition).build());

		if (virtualCompetitor != null) {
			// the virtual competitor might have multiple TEAMS for the same
			// ROUND
			list = this.virtualCompetitorTeamDao.retrieveList(new VirtualCompetitorTeam.Builder().virtualCompetitor(virtualCompetitor).build());

			int totalPoints = list.stream().map(vct -> vct.getPoints()).reduce(0, (s, p) -> s + p).intValue();

			boolean update = this.virtualCompetitorDao.update(new VirtualCompetitor.Builder().id(virtualCompetitor.getId()).totalPoints(totalPoints).build());

			if (!update) {
				logger.warn("There was a problem updating the virtual competitor with the id:" + virtualCompetitor.getId());
			} else {
				logger.info("UPDATED the Virtual Competitor:" + virtualCompetitor.getId() + " total points:" + totalPoints);
			}
		} else {
			logger.warn("There is no virtual competitor for subscriber:" + subscriber.getId() + ", virtualCompetition:" + virtualCompetition.getId());
		}

		return this.virtualCompetitorDao.retrieveById(virtualCompetitor.getId());
	}

	public List<RealCompetitor> getRealCompetitorsFromSeason(Season season) {
		List<Round> roundList = this.roundDao.retrieveList(new Round.Builder().season(season).build());
		List<Event> eventList = roundList.stream().map(r -> this.eventDao.retrieveList(new Event.Builder().round(r).build())).flatMap(List::stream)
				.collect(Collectors.toList());
		List<RealCompetitor> realCompetitorsList = eventList.stream()
				.map(e -> this.eventCompetitorDao.retrieveList(new EventCompetitor.Builder().event(e).build())).flatMap(List::stream)
				.map(ec -> ec.getCompetitor()).distinct().collect(Collectors.toList());
		return realCompetitorsList;
	}
}