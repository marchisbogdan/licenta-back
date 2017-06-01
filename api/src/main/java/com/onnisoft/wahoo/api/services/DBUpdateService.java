package com.onnisoft.wahoo.api.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;
import com.onnisoft.wahoo.model.document.PlayerStats;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.RealCompetitorSeason;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Sport;

@Component
public class DBUpdateService extends AbstractService<RealCompetition> {

	private final Logger logger = LoggerFactory.getLogger(DBUpdateService.class);

	private static final String SPORT = "football";

	private static final int TEAM_BUDGET = 50000;

	@Autowired
	private ImportService importServices;

	@Autowired
	private CompetitorService competitorService;

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<PlayerStats> playerStatsDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private Dao<RealCompetitorSeason> realCompetitorSeasonDao;

	public void populateDB() {
		processSports();

		GenericResponseDTO<List<Sport>> sportsList = importServices.getSportList();
		Optional<Sport> sport = sportsList.getData().stream().filter((s) -> s.getName().toLowerCase().compareTo(SPORT) == 0).findFirst();
		if (sport.isPresent()) {
			List<String> competitionsIds = updateCompetitionDBEntities(sport.get().getId());
			logger.debug("Finished processing the competitions, count:" + competitionsIds.size());
			List<String> seasonsIds = updateSeasonDBEntities(competitionsIds);
			logger.debug("Finished processing the seasons, count:" + seasonsIds.size());
			List<String> roundsIds = updateRoundDBEntities(seasonsIds);
			logger.debug("Finished processing the rounds, count:" + roundsIds.size());
			List<String> eventsIds = updateEventDBEntities(roundsIds);
			logger.debug("Finished processing the events, count:" + eventsIds.size());
			List<String> eventCompetitorsIds = updateEventCompetitorDBEntitiesByEventIds(eventsIds);
			logger.debug("Finished processing the event competitors, count:" + eventCompetitorsIds.size());
		} else {
			logger.warn("No sport with the specified name has been returned.");
		}

		updateAllSeasonsStats();

		// Process competitors and their dependencies
		processCompetitors();
		updateAllCompetitorsStats();

		// Update players dependencies
		// updatePlayersDependencies();
	}

	public void updatePlayersDependencies() {

		// // get only the players binded to a competitor
		// List<String> competitorsId =
		// realCompetitorDao.retrieveList(null).stream().map(rc ->
		// rc.getId()).collect(Collectors.toList());
		// List<String> playersIds = competitorsId.stream().map(rc_id ->
		// processPlayersByCompetitorId(rc_id)).flatMap(List::stream).collect(Collectors.toList());

		// get the players in chunks using pagination
		// int max_results = 100;
		// List<String> playersIds = new LinkedList<>();
		// for (int i = 0; i <= 1800; i++) {
		// playersIds.addAll(this.processPlayersByRange(i, max_results));
		// }

		// get all player event statistics for players binded to a
		// competitor
		// List<String> playersIds =
		// this.playerDao.retrieveList(null).stream().filter(p ->
		// Objects.nonNull(p.getCompetitor())).map(p -> p.getId())
		// .collect(Collectors.toList());
		// playersIds.parallelStream().map(id ->
		// processPlayerEventStatisticsByPlayerid(id)).flatMap(List::stream).collect(Collectors.toList());
		// playersIds.parallelStream().map(id ->
		// processPlayerEventTimestampStatisticByPlayerId(id)).flatMap(List::stream).collect(Collectors.toList());

		// update the points for each event
		updatePointsForAllPlayersEvents();

		createPlayersStats();
		updateAllPlayersStats();

		// updates the players values based on their last season points
		// updatePlayersValues();
	}

	public List<String> updateCompetitionDBEntities(String sportId) {
		List<String> competitionsIds = new LinkedList<>();
		competitionsIds = processCompetitionsBySportId(sportId);
		return competitionsIds;
	}

	public List<String> updateSeasonDBEntities(List<String> competitionsIds) {
		List<String> result = new LinkedList<>();
		Function<String, List<String>> function = id -> processSeasonsByCompetitionId(id);
		result = processedIdsCollector(competitionsIds, function);
		logger.debug("Finished processing the Seasons, count:" + result.size());
		return result;
	}

	public List<String> updateRoundDBEntities(List<String> seasonsIds) {
		List<String> result = new LinkedList<>();
		Function<String, List<String>> function = id -> processRoundsBySeasonId(id);
		result = processedIdsCollector(seasonsIds, function);
		logger.debug("Finished processing the Rounds, count:" + result.size());
		return result;
	}

	public List<String> updateEventDBEntities(List<String> roundsIds) {
		List<String> result = new LinkedList<>();
		Function<String, List<String>> function = id -> processEventsByRoundId(id);
		result = processedIdsCollector(roundsIds, function);
		logger.debug("Finished processing the Events, count:" + result.size());
		return result;
	}

	public List<String> updateEventCompetitorDBEntitiesByEventIds(List<String> eventIds) {
		List<String> result = new LinkedList<>();
		Function<String, List<String>> function = id -> processEventCompetitorsByEventId(id);
		result = processedIdsCollector(eventIds, function);
		logger.debug("Finished processing the EventCompetitors, count:" + result.size());
		return result;
	}

	public List<String> updateEventCompetitorDBEntitiesByCompetitorsIds(List<String> competitorsIds) {
		List<String> result = new LinkedList<>();
		Function<String, List<String>> function = id -> processEventCompetitorsByCompetitorsId(id);
		result = processedIdsCollector(competitorsIds, function);
		logger.debug("Finished processing the EventCompetitors, count:" + result.size());
		return result;
	}

	/**
	 * the method processes a given collection of objects id's as Strings, and
	 * using the provided function,as intended, it maps those id's as parameters
	 * to a method that is supposed to return a list of id's or an id ,as
	 * String, for each method call.
	 * 
	 * @param collection
	 * @param function
	 * @return
	 */
	private List<String> processedIdsCollector(List<String> collection, Function<String, List<String>> function) {
		return collection.parallelStream().map(id -> function.apply(id)).flatMap(List::stream).collect(Collectors.toList());
	}

	public void updateAllSeasonsStats() {
		List<Season> seasonList = seasonDao.retrieveList(null);
		seasonList.stream().forEach(s -> seasonService.updateSeasonStats(s));
	}

	// TODO: test the function
	public void updatePlayersValues() {
		List<Season> seasonList = this.seasonDao.retrieveList(null);
		logger.info("Started calculating values for the players by seasons...");

		for (Season season : seasonList) {

			String last_season_name = this.getLastSeasonName(season);
			if (last_season_name == null) {
				continue;
			}

			logger.info("This season name:" + season.getName());
			logger.info("Looking for the last season with the name:" + last_season_name);
			Optional<Season> candidate = seasonList.stream()
					.filter(s -> s.getName().contains(last_season_name) && s.getCompetition().equals(season.getCompetition())).findFirst();

			// find the last year season from the candidates
			Season last_season = null;
			if (candidate.isPresent()) {
				last_season = candidate.get();
				logger.info("Found last season:" + last_season);
			} else {
				last_season = season;
				logger.warn("NO candidates found, replaced with current season:" + last_season);
			}
			this.updatePlayerStatsWithNewPlayersValuesForCompetitionSeason(last_season, season);
		}
		logger.info("Finished updating the values of the players.");
	}

	private String getLastSeasonName(Season currentSeason) {
		String season_name = currentSeason.getName();
		Integer current_season_year = null;
		String last_season_name = null;

		String pattern_case_1 = "(^\\d{4})(\\/)(\\d{4})";
		String pattern_case_2 = "^(.*)(\\s)(\\d{4}$)";
		String pattern_case_3 = "(^\\d{4})(\\s)(.*)";

		Pattern pattern1 = Pattern.compile(pattern_case_1);
		Pattern pattern2 = Pattern.compile(pattern_case_2);
		Pattern pattern3 = Pattern.compile(pattern_case_3);
		StringBuilder sb = new StringBuilder();

		if (pattern1.matcher(season_name).matches()) {
			// case 1
			// the start year will be in group 1
			current_season_year = Integer.parseInt(pattern1.matcher(season_name).group(1));
			sb.append(current_season_year - 1).append("/").append(current_season_year);
		} else if (pattern2.matcher(season_name).matches()) {
			// case 2
			// the start year will be in group 3
			current_season_year = Integer.parseInt(pattern2.matcher(season_name).group(3));
			sb.append(pattern2.matcher(season_name).group(1)).append(pattern2.matcher(season_name).group(2)).append(current_season_year - 1);
		} else if (pattern3.matcher(season_name).matches()) {
			// case 3
			// the start year will be in group 1
			current_season_year = Integer.parseInt(pattern3.matcher(season_name).group(1));
			sb.append(current_season_year - 1).append(pattern3.matcher(season_name).group(2)).append(pattern3.matcher(season_name).group(3));
		} else {
			// exception
			logger.error("The season with name:" + season_name + " doesn't match any case!");
			return null;
		}
		last_season_name = sb.toString();

		return last_season_name;
	}

	/**
	 * The function calculates the value of a player based on an algorithm
	 * containing the sum of the total points from last season competition and
	 * the average value of those points. player_value = (player_points *
	 * average_value)/average_points
	 * 
	 * Observation: The value is set to default (average_value) for players who
	 * transfered in for the currentSeason, or for players from teams who joined
	 * in to the current season
	 * 
	 * @param lastSeason
	 * @param currentSeason
	 */
	// TODO: function isn't used yet. To be tested when importing data.
	public void updatePlayerStatsWithNewPlayersValuesForCompetitionSeason(Season lastSeason, Season currentSeason) {
		// get all the real competitors from the current season
		List<RealCompetitor> realCompetitorsList = this.competitorService.getRealCompetitorsFromSeason(currentSeason);

		// get the players statistics from the last season after updating their
		// total points.
		List<Player> playersList = new LinkedList<>();
		List<PlayerStats> playerStatsList = new LinkedList<>();
		for (RealCompetitor rc : realCompetitorsList) {
			List<Player> currentTeamPlayers = this.playerDao.retrieveList(new Player.Builder().competitor(rc).build());
			playersList.addAll(currentTeamPlayers);
			// the update will return null for players how have transferred to a
			// team in this season or how are part of a newly joined team
			List<PlayerStats> currentTeamPlayerStats = currentTeamPlayers.stream().map(p -> this.playerService.updatePlayerTotalPoints(p, lastSeason))
					.filter(Objects::nonNull).collect(Collectors.toList());
			playerStatsList.addAll(currentTeamPlayerStats);
		}

		if (!CollectionUtils.isEmpty(playersList) && !CollectionUtils.isEmpty(playerStatsList)) {
			// make an average of all players totalPoints from last season
			int totalPointsSum = playerStatsList.stream().map(ps -> ps.getTotalPoints()).reduce(0, (sum, value) -> sum + value).intValue();
			int average_points = totalPointsSum / playerStatsList.size();
			int average_value = TEAM_BUDGET / 11;

			logger.info("Total points sum for the last Season equals to:" + totalPointsSum);
			logger.info("Average points for the last Season equals to:" + average_points);

			// calculate the player Value using the following algorithm
			// player_value = (player_points * average_value)/average_points
			// save the player value in the current season PlayerStats

			int player_value;
			for (Player player : playersList) {
				Optional<PlayerStats> lastSeasonStats = playerStatsList.stream().filter(ps -> ps.getPlayer().equals(player)).findFirst();
				if (!lastSeasonStats.isPresent()) {
					logger.info("Player:" + player.getId() + " WAS NOT in the last season!");
					// player was not in the last season competition
					player_value = 0;
				} else {
					player_value = (lastSeasonStats.get().getTotalPoints() * average_value) / average_points;
				}

				boolean updated = this.playerStatsDao.update(
						new PlayerStats.Builder().player(player).realCompetitor(player.getCompetitor()).season(currentSeason).value(player_value).build());
				if (!updated) {
					logger.warn("PlayerStats wasn't UPDATED for player:" + player.getId() + " , current season:" + currentSeason.getName()
							+ " and player value:" + player_value);
				} else {
					logger.info("UPDATED PlayerStats for player:" + player.getId() + " , current season:" + currentSeason.getName() + " with player value:"
							+ player_value);
				}
			}
		}
	}

	private void updatePointsForAllPlayersEvents() {
		List<PlayerEventStatistics> playerEventStats = this.playerEventStatisticsDao.retrieveList(null).stream()
				.filter(pes -> Objects.nonNull(pes.getPlayer()) && Objects.nonNull(pes.getEvent())).collect(Collectors.toList());
		playerEventStats.stream().map(pes -> this.playerService.updatePlayerPointsForAnEvent(pes.getPlayer(), pes.getEvent())).collect(Collectors.toList());
	}

	private void createPlayersStats() {
		// get all the players with competitors
		List<Player> playersList = this.playerDao.retrieveList(null).stream().filter(p -> Objects.nonNull(p.getCompetitor())).collect(Collectors.toList());
		logger.info("Players With competitors:" + playersList.size());
		// get all the event-competitors entities for each distinct competitor
		// List<RealCompetitor> competitorsList = playersList.stream().map(p ->
		// p.getCompetitor()).distinct().collect(Collectors.toList());
		// logger.info("Competitors found:" + competitorsList.size());
		// List<EventCompetitor> eventCompetitorsList = competitorsList.stream()
		// .map(c -> this.eventCompetitorDao.retrieveList(new
		// EventCompetitor.Builder().competitior(c).build())).flatMap(List::stream)
		// .collect(Collectors.toList());

		List<PlayerEventStatistics> eventStatsList = this.playerEventStatisticsDao.retrieveList(null);

		// create PlayerStats for each player who played for a specific team in
		// a season
		for (Player p : playersList) {
			List<PlayerEventStatistics> eventStatsForPlayer = eventStatsList.stream()
					.filter(es -> Objects.nonNull(es.getPlayer()) && Objects.nonNull(es.getCompetitor()) && Objects.nonNull(es.getEvent()))
					.filter(es -> es.getPlayer().equals(p)).collect(Collectors.toList());

			// List<EventCompetitor> eventCompetitorPlayed =
			// eventCompetitorsList.stream().filter(ec ->
			// ec.getCompetitor().equals(p.getCompetitor()))
			// .collect(Collectors.toList());
			// List<Season> seasonList = eventCompetitorPlayed.stream().map(ec
			// ->
			// ec.getEvent().getRound().getSeason()).distinct().collect(Collectors.toList());

			for (PlayerEventStatistics pes : eventStatsForPlayer) {
				PlayerStats ps = this.playerStatsDao.retrieve(
						new PlayerStats.Builder().player(p).realCompetitor(pes.getCompetitor()).season(pes.getEvent().getRound().getSeason()).build());
				if (ps == null) {
					PlayerStats created = this.playerStatsDao.create(new PlayerStats.Builder().player(p).realCompetitor(pes.getCompetitor())
							.season(pes.getEvent().getRound().getSeason()).toCreate().build());
					if (created != null) {
						logger.info("CREATED PlayerStats for Player:" + p.getId() + ", Competitor:" + pes.getCompetitor().getId() + ", Season:"
								+ pes.getEvent().getRound().getSeason().getName());
					}
				}
			}
		}

	}

	public void updateAllCompetitorsStats() {
		List<RealCompetitor> realCompetitorsList = realCompetitorDao.retrieveList(null);
		List<EventCompetitor> eventCompetitorList = eventCompetitorDao.retrieveList(null);

		for (RealCompetitor realCompetitor : realCompetitorsList) {
			logger.info(
					"Started updating the dependencies for the real competitor with id:" + realCompetitor.getId() + " and name:" + realCompetitor.getName());

			// update competitor statistics for a specific season by a given
			// event
			List<Event> eventsParticipatedIn = eventCompetitorList.stream().filter(e -> Objects.nonNull(e.getCompetitor()) && Objects.nonNull(e.getEvent()))
					.filter(e -> e.getCompetitor().equals(realCompetitor)).map(e -> e.getEvent()).collect(Collectors.toList());

			// create the entries in the DB for every (real-competitor,season)
			// tuple in the realCompetitorSeason collection
			// this has to be done before updating the competitors statistics
			List<Season> seasonsParticipatingIn = eventsParticipatedIn.stream().map(e -> e.getRound().getSeason()).distinct().collect(Collectors.toList());
			List<RealCompetitorSeason> createdEntities = seasonsParticipatingIn.stream().map(
					s -> this.realCompetitorSeasonDao.create(new RealCompetitorSeason.Builder().realCompetitor(realCompetitor).season(s).toCreate().build()))
					.collect(Collectors.toList());
			logger.info(realCompetitor.getName() + " participates in " + createdEntities.size() + " seasons.");

			eventsParticipatedIn.stream().forEach(e -> competitorService.updateCompetitorStatsByEvent(realCompetitor, e));
			logger.info(
					"Finished updatingthe dependencies for the real competitor with id:" + realCompetitor.getId() + " and name:" + realCompetitor.getName());
		}
	}

	public void updateAllPlayersStats() {
		List<Player> playerList = playerDao.retrieveList(null).stream().filter(p -> Objects.nonNull(p.getCompetitor())).collect(Collectors.toList());

		for (Player player : playerList) {
			List<PlayerEventStatistics> playerEventStatisticsList = playerEventStatisticsDao
					.retrieveList(new PlayerEventStatistics.Builder().player(player).build()).stream().filter(pes -> Objects.nonNull(pes.getEvent()))
					.collect(Collectors.toList());
			playerEventStatisticsList.stream().forEach(e -> playerService.updatePlayerStatsByEventStats(player, e));
		}
	}

	/**
	 * Saves the imported Sports in the DB and returns a list with their id's.
	 * 
	 * @return
	 */
	public List<String> processSports() {
		GenericServices<Sport> sportService = new GenericServices<>();
		GenericResponseDTO<List<Sport>> sports = importServices.getSportList();
		return sportService.processedDataIds(sports, Sport.class);
	}

	/**
	 * Saves the imported Countries in the DB and returns a list with their
	 * id's.
	 * 
	 * @return
	 */
	public List<String> processCountries() {
		GenericServices<Country> countryService = new GenericServices<>();
		GenericResponseDTO<List<Country>> countries = importServices.getCountryList();
		return countryService.processedDataIds(countries, Country.class);
	}

	/**
	 * Saves the Competitors in the DB and returns a list with their id's.
	 * 
	 * @return
	 */
	public List<String> processCompetitors() {
		GenericServices<RealCompetitor> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<RealCompetitor>>> future = importServices.getRealCompetitorList();
		GenericResponseDTO<List<RealCompetitor>> competitors = new GenericResponseDTO<>();
		try {
			competitors = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}
		return genericService.processedDataIds(competitors, RealCompetitor.class);
	}

	/**
	 * Saves the Players from the the Real Competitor with the given id in the
	 * DB and returns a list with their id's.
	 * 
	 * @return
	 */
	private List<String> processPlayersByCompetitorId(String competitorId) {
		GenericServices<Player> genericService = new GenericServices<>();
		GenericResponseDTO<List<Player>> players = importServices.getPlayerListByCompetitor(competitorId);
		if (CollectionUtils.isEmpty(players.getData())) {
			players = GenericResponseDTO.createFailed("The Player list is empty for competitorId:" + competitorId);
		}
		return genericService.processedDataIds(players, Player.class);
	}

	/**
	 * Saves the Players for the given range and max results in the DB and
	 * returns a list with their id's.
	 * 
	 * @return
	 */
	private List<String> processPlayersByRange(int range, int max_results) {
		GenericServices<Player> genericService = new GenericServices<>();
		GenericResponseDTO<List<Player>> players = importServices.getPlayerListByRange(range, max_results);
		if (CollectionUtils.isEmpty(players.getData())) {
			players = GenericResponseDTO.createFailed("The Player list is empty for range:" + range + ", and max results:" + max_results);
		} else {
			// TODO: delete if you want return all the ids not just the ones
			// binded to a competitor.
			List<Player> result = players.getData().stream().filter(p -> Objects.nonNull(p.getCompetitor())).collect(Collectors.toList());
			players = GenericResponseDTO.createSuccess(result);
		}

		return genericService.processedDataIds(players, Player.class);
	}

	/**
	 * Saves the Competitions with the given SportId in the DB and returns a
	 * list with their id's.
	 * 
	 * @param sportId
	 * @return
	 */
	private List<String> processCompetitionsBySportId(String sportId) {
		GenericServices<RealCompetition> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<RealCompetition>>> future = importServices.getRealCompetitionList(sportId);
		GenericResponseDTO<List<RealCompetition>> competitions = new GenericResponseDTO<>();

		try {
			competitions = future.get();
			if (CollectionUtils.isEmpty(competitions.getData())) {
				competitions = GenericResponseDTO.createFailed("The Season list is empty for competitionId:" + sportId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}

		return genericService.processedDataIds(competitions, RealCompetition.class);
	}

	/**
	 * Saves the Seasons with the given CompetitionId in the DB and returns a
	 * list with their id's.
	 * 
	 * @param competitionId
	 * @return
	 */
	private List<String> processSeasonsByCompetitionId(String competitionId) {
		GenericServices<Season> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<Season>>> future = importServices.getSeasonList(competitionId);
		GenericResponseDTO<List<Season>> seasons = new GenericResponseDTO<>();

		try {
			seasons = future.get();
			if (CollectionUtils.isEmpty(seasons.getData())) {
				seasons = GenericResponseDTO.createFailed("The Season list is empty for competitionId:" + competitionId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}
		return genericService.processedDataIds(seasons, Season.class);
	}

	/**
	 * Saves the Rounds with the given SeasonId in the DB and returns a list
	 * with their id's.
	 * 
	 * @param seasonId
	 * @return
	 */
	private List<String> processRoundsBySeasonId(String seasonId) {
		GenericServices<Round> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<Round>>> future = importServices.getRoundList(seasonId);
		GenericResponseDTO<List<Round>> rounds = new GenericResponseDTO<>();

		try {
			rounds = future.get();
			if (CollectionUtils.isEmpty(rounds.getData())) {
				rounds = GenericResponseDTO.createFailed("The Round list is empty for seasonId:" + seasonId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}
		return genericService.processedDataIds(rounds, Round.class);
	}

	/**
	 * Saves the Events with the given RoundId in the DB and returns a list with
	 * their id's.
	 * 
	 * @param roundsId
	 * @return
	 */
	private List<String> processEventsByRoundId(String roundId) {
		GenericServices<Event> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<Event>>> future = importServices.getEventList(roundId);
		GenericResponseDTO<List<Event>> events = new GenericResponseDTO<>();

		try {
			events = future.get();
			if (CollectionUtils.isEmpty(events.getData())) {
				events = GenericResponseDTO.createFailed("The Event list is empty for roundId:" + roundId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}
		return genericService.processedDataIds(events, Event.class);
	}

	/**
	 * Saves the EventCompetitors with the given EventId in the DB and returns a
	 * list with their id's.
	 * 
	 * @param eventId
	 * @return
	 */
	private List<String> processEventCompetitorsByEventId(String eventId) {
		GenericServices<EventCompetitor> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<EventCompetitor>>> future = importServices.getEventCompetitorListByEventId(eventId);
		GenericResponseDTO<List<EventCompetitor>> eventCompetitors = new GenericResponseDTO<>();

		try {
			eventCompetitors = future.get();
			if (CollectionUtils.isEmpty(eventCompetitors.getData())) {
				eventCompetitors = GenericResponseDTO.createFailed("The EventCompetitor list is empty for eventId:" + eventId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}
		return genericService.processedDataIds(eventCompetitors, EventCompetitor.class);
	}

	/**
	 * Saves the EventCompetitors with the given CompetitorId in the DB and
	 * returns a list with their id's.
	 * 
	 * @param eventId
	 * @return
	 */
	private List<String> processEventCompetitorsByCompetitorsId(String competitorId) {
		GenericServices<EventCompetitor> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<EventCompetitor>>> future = importServices.getEventCompetitorListByCompetitorId(competitorId);
		GenericResponseDTO<List<EventCompetitor>> eventCompetitors = new GenericResponseDTO<>();

		try {
			eventCompetitors = future.get();
			if (CollectionUtils.isEmpty(eventCompetitors.getData())) {
				eventCompetitors = GenericResponseDTO.createFailed("The EventCompetitor list is empty for eventId:" + competitorId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}

		return genericService.processedDataIds(eventCompetitors, EventCompetitor.class);
	}

	/**
	 * Saves the PlayerEventStatistics with the given PlayerId in the DB and
	 * returns a list with their id's.
	 * 
	 * @param playerId
	 * @return
	 */
	private List<String> processPlayerEventStatisticsByPlayerid(String playerId) {
		GenericServices<PlayerEventStatistics> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<PlayerEventStatistics>>> future = importServices.getPlayerEventStatisticsListByPlayerId(playerId);
		GenericResponseDTO<List<PlayerEventStatistics>> playerEventStatistics = new GenericResponseDTO<>();

		try {
			playerEventStatistics = future.get();
			if (CollectionUtils.isEmpty(playerEventStatistics.getData())) {
				playerEventStatistics = GenericResponseDTO.createFailed("The PlayerEventStatistics list is empty for playerId:" + playerId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}

		return genericService.processedDataIds(playerEventStatistics, PlayerEventStatistics.class);
	}

	/**
	 * Saves the PlayerEventTimestampStatistics with the given PlayerId in the
	 * DB and returns a list with their id's.
	 * 
	 * @param playerId
	 * @return
	 */
	private List<String> processPlayerEventTimestampStatisticByPlayerId(String playerId) {
		GenericServices<PlayerEventTimestampStatistic> genericService = new GenericServices<>();
		Future<GenericResponseDTO<List<PlayerEventTimestampStatistic>>> future = importServices.getPlayerEventTimestampStatisticByPlayerId(playerId);
		GenericResponseDTO<List<PlayerEventTimestampStatistic>> playerEventTimestampStatistic = new GenericResponseDTO<>();

		try {
			playerEventTimestampStatistic = future.get();
			if (CollectionUtils.isEmpty(playerEventTimestampStatistic.getData())) {
				playerEventTimestampStatistic = GenericResponseDTO.createFailed("The PlayerEventTimestampStatistics list is empty for playerId:" + playerId);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " was interrupted:" + e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
			logger.error("Thread:" + Thread.currentThread().getName() + " execution exception:" + e.getMessage());
		}

		return genericService.processedDataIds(playerEventTimestampStatistic, PlayerEventTimestampStatistic.class);
	}

	@Override
	void inactivateById(String id) {
		// TODO Auto-generated method stub

	}
}
