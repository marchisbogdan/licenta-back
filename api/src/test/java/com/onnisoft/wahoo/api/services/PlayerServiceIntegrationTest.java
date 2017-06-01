package com.onnisoft.wahoo.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventStatus;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerStats;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.enums.EventStatusEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerPositionEnum;
import com.onnisoft.wahoo.model.document.enums.StatusEnum;

@ContextConfiguration(locations = { "classpath:wahoo-test-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerServiceIntegrationTest {

	private static final String COMPETITOR_NAME = "test-competitor";

	// Season
	private static final String SEASON_NAME = "test-season";

	// Round
	private static final String ROUND_NAME = "test-round";

	// Event
	private static final String EVENT_NAME_1 = "Test-Competitor v Another-Competitor";
	private static final int MINUTE = 90;
	private static final int HOMESCORE = 2;
	private static final int AWAYSCORE = 1;

	// Player
	private static final PlayerPositionEnum PLAYER_POSITION = PlayerPositionEnum.CENTRE_MIDFIELD;

	// Player Event Statistics
	private static final boolean GAME_STARTED = true;
	private static final boolean TOTAL_SUB_ON = false;
	private static final boolean TOTAL_SUB_OFF = false;
	private static final int MINS_PLAYED = 70;
	private static final int GOALS = 3;
	private static final int GOAL_ASSIST = 1;
	private static final int OWN_GOALS = 1;
	private static final boolean CLEAN_SHEET = true;
	private static final boolean YELLOW_CARD = true;
	private static final boolean RED_CARD = true;;
	private static final boolean SECOUND_YELLOW = true;
	private static final int PEN_GOALS = 1;

	private static final String SUBSCRIBER_NAME = "bob";

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private PlayerService playerService;

	private RealCompetitor realCompetitor;
	private EventStatus eventStatus1;
	private Season season;
	private Round round;
	private Event event1;
	private Player player1;
	private PlayerEventStatistics playerEventStatistics1;

	@Before
	public void setUp() {

		// Real Competitor
		RealCompetitor realCompetitorToCreate = new RealCompetitor.Builder().name(COMPETITOR_NAME).status(StatusEnum.ACTIVE).toCreate().build();
		this.realCompetitor = this.realCompetitorDao.create(realCompetitorToCreate);

		// season
		Season seasonToCreate = new Season.Builder().name(SEASON_NAME).toCreate().builder();
		this.season = this.seasonDao.create(seasonToCreate);

		// round
		Round roundToCreate = new Round.Builder().name(ROUND_NAME).season(this.season).toCreate().build();
		this.round = this.roundDao.create(roundToCreate);

		// Event
		this.eventStatus1 = new EventStatus.Builder().minute(MINUTE).homeScore(HOMESCORE).awayScore(AWAYSCORE).toCreate().build();
		Event eventToCreate1 = new Event.Builder().name(EVENT_NAME_1).status(EventStatusEnum.ENDED).round(this.round).eventStatus(this.eventStatus1).toCreate()
				.build();
		this.event1 = this.eventDao.create(eventToCreate1);

		// Player 1
		Player playerToCreate = new Player.Builder().firstName(SUBSCRIBER_NAME).lastName(SUBSCRIBER_NAME).position(PLAYER_POSITION)
				.competitor(this.realCompetitor).toCreate().build();
		this.player1 = this.playerDao.create(playerToCreate);

		// Player Event Statistics
		PlayerEventStatistics playerEventStatisticsToCreate = new PlayerEventStatistics.Builder().event(event1).competitor(realCompetitor).player(player1)
				.gameStarted(GAME_STARTED).totalSubOn(TOTAL_SUB_ON).totalSubOff(TOTAL_SUB_OFF).minsPlayed(MINS_PLAYED).goals(GOALS).goalAssist(GOAL_ASSIST)
				.ownGoals(OWN_GOALS).yellowCard(YELLOW_CARD).redCard(RED_CARD).secondYellow(SECOUND_YELLOW).cleanSheet(CLEAN_SHEET).penGoals(PEN_GOALS)
				.toCreate().build();
		this.playerEventStatistics1 = this.playerEventStatisticsDao.create(playerEventStatisticsToCreate);

		assertNotNull(this.realCompetitor);
		assertNotNull(this.season);
		assertNotNull(this.round);
		assertNotNull(this.event1);
		assertNotNull(this.player1);
		assertNotNull(this.playerEventStatistics1);
	}

	@After
	public void tearDown() {
		assertTrue(this.realCompetitorDao.delete(this.realCompetitor));
		assertTrue(this.eventDao.delete(this.event1));
		assertTrue(this.roundDao.delete(this.round));
		assertTrue(this.seasonDao.delete(this.season));
		assertTrue(this.playerDao.delete(this.player1));
		assertTrue(this.playerEventStatisticsDao.delete(this.playerEventStatistics1));
	}

	@Test
	public void testUpdatePlayerStatsByEventStats() {
		PlayerStats playerStats1 = playerService.updatePlayerStatsByEventStats(this.player1, this.playerEventStatistics1);
		assertNotNull(playerStats1);
		assertTrue(playerStats1.getGoals() == GOALS);
		assertTrue(playerStats1.getAssists() == GOAL_ASSIST);
		assertTrue(playerStats1.getMinutesPlayed() == MINS_PLAYED);

		PlayerStats playerStats2 = playerService.updatePlayerStatsByEventStats(this.player1, this.playerEventStatistics1);
		assertNotNull(playerStats2);
		assertTrue(playerStats2.getGoals() == GOALS * 2);
		assertTrue(playerStats2.getAssists() == GOAL_ASSIST * 2);
		assertTrue(playerStats2.getMinutesPlayed() == MINS_PLAYED * 2);
	}

	@Test
	public void testUpdatePlayerPointsForAnEvent() {
		PlayerEventStatistics pes = playerService.updatePlayerPointsForAnEvent(this.player1, this.event1);
		assertNotNull(pes);
		assertTrue(pes.getPoints() > 0);
	}
}
