package com.onnisoft.wahoo.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.RealCompetitorSeason;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.VirtualCompetitor;
import com.onnisoft.wahoo.model.document.VirtualCompetitorTeam;
import com.onnisoft.wahoo.model.document.enums.EventStatusEnum;
import com.onnisoft.wahoo.model.document.enums.PlayerGamePositionEnum;
import com.onnisoft.wahoo.model.document.enums.StatusEnum;

@ContextConfiguration(locations = { "classpath:wahoo-test-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CompetitorServiceIntegrationTest {

	private static final String COMPETITOR_NAME = "test-competitor";

	private static final String EVENT_NAME_1 = "Test-Competitor v Another-Competitor";

	private static final String EVENT_NAME_2 = "A-Team v Test-Competitor";

	private static final String EVENT_NAME_3 = "Test-team v Test-Competitor";

	// Season
	private static final String SEASON_NAME = "test-season";

	// Round
	private static final String ROUND_NAME = "test-round";

	// Event
	private static final int MINUTE = 90;
	private static final int HOMESCORE = 2;
	private static final int AWAYSCORE = 1;

	private static final int NUM_OF_MATCHES = 2;
	private static final int WINS = 1;
	private static final int NUM_OF_DRAWS = 1;
	private static final int POINTS = 4;
	private static final int GOALS_CONCEDED = 2;
	private static final int EXPECTED_HOMESCORE = 3;

	// Subscriber
	private static final String SUBSCRIBER_NAME = "bob";
	private static final String SUBSCRIBER_EMAIL = SUBSCRIBER_NAME + "@gmail.com";

	// Virtual Competition
	private static final String VIRTUAL_COMPETITION_NAME = "test-virtual-competition";

	// Virtual Competitor
	private static final String VIRTUAL_COMPETITOR_NAME = "test-virtual-competitor";

	// Event Virtual Competitor
	private static final int EVENT_POINTS_1 = 12;
	private static final int EVENT_POINTS_2 = 35;

	// Player Event Statistics
	private static final int PLAYER_EVENT_POINTS = 39;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<RealCompetitorSeason> realCompetitorSeasonDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private Dao<VirtualCompetition> virtualCompetitionDao;

	@Autowired
	private Dao<VirtualCompetitor> VirtualCompetitorDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<VirtualCompetitorTeam> virtualCompetitorTeamDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private CompetitorService competitorService;

	private RealCompetitor realCompetitor;
	private RealCompetitorSeason realCompetitorSeason;
	private Season season;
	private Round round;
	private Event event1;
	private Event event2;
	private Event event3;
	private EventStatus eventStatus1;
	private EventStatus eventStatus2;
	private Subscriber subscriber;
	private VirtualCompetition virtualCompetition;
	private VirtualCompetitor virtualCompetitor;
	private Player player;
	private VirtualCompetitorTeam virtualCompetitorTeam;
	private PlayerEventStatistics playerEventStatistics;

	@Before
	public void setUp() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2000);
		Date startDateTime = calendar.getTime();

		// real competitor
		RealCompetitor realCompetitorToCreate = new RealCompetitor.Builder().name(COMPETITOR_NAME).status(StatusEnum.ACTIVE).toCreate().build();
		this.realCompetitor = this.realCompetitorDao.create(realCompetitorToCreate);

		// season
		Season seasonToCreate = new Season.Builder().name(SEASON_NAME).toCreate().builder();
		this.season = this.seasonDao.create(seasonToCreate);

		// round
		Round roundToCreate = new Round.Builder().name(ROUND_NAME).season(this.season).toCreate().build();
		this.round = this.roundDao.create(roundToCreate);

		// events
		this.eventStatus1 = new EventStatus.Builder().minute(MINUTE).homeScore(HOMESCORE).awayScore(AWAYSCORE).toCreate().build();
		Event eventToCreate1 = new Event.Builder().name(EVENT_NAME_1).startDateTime(startDateTime).status(EventStatusEnum.ENDED).round(this.round)
				.eventStatus(this.eventStatus1).toCreate().build();
		this.event1 = this.eventDao.create(eventToCreate1);

		this.eventStatus2 = new EventStatus.Builder().minute(MINUTE).homeScore(AWAYSCORE).awayScore(AWAYSCORE).toCreate().build();
		Event eventToCreate2 = new Event.Builder().name(EVENT_NAME_2).startDateTime(startDateTime).status(EventStatusEnum.ENDED).round(this.round)
				.eventStatus(this.eventStatus2).toCreate().build();
		this.event2 = this.eventDao.create(eventToCreate2);

		Event eventToCreate3 = new Event.Builder().name(EVENT_NAME_3).startDateTime(startDateTime).status(EventStatusEnum.ENDED).round(this.round)
				.eventStatus(this.eventStatus2).toCreate().build();
		this.event3 = this.eventDao.create(eventToCreate3);

		// real competitor season
		RealCompetitorSeason realCompetitorSeasonToCreate = new RealCompetitorSeason.Builder().realCompetitor(this.realCompetitor).season(this.season)
				.toCreate().build();
		this.realCompetitorSeason = this.realCompetitorSeasonDao.create(realCompetitorSeasonToCreate);

		// subscriber
		Subscriber subscriberToCreate = new Subscriber.SubscriberBuilder().userName(SUBSCRIBER_NAME).email(SUBSCRIBER_EMAIL).toCreate().build();
		this.subscriber = this.subscriberDao.create(subscriberToCreate);
		// virtual competition
		VirtualCompetition virtualCompetitionToCreate = new VirtualCompetition.VirtualCompetitionBuilder().name(VIRTUAL_COMPETITION_NAME).toCreate().build();
		this.virtualCompetition = this.virtualCompetitionDao.create(virtualCompetitionToCreate);
		// virtual competitor
		VirtualCompetitor virtualCompetitorToCreate = new VirtualCompetitor.Builder().name(VIRTUAL_COMPETITOR_NAME).subscriber(this.subscriber)
				.virtualCompetition(this.virtualCompetition).toCreate().build();
		this.virtualCompetitor = this.VirtualCompetitorDao.create(virtualCompetitorToCreate);

		// player
		Player playerToCreate = new Player.Builder().firstName(SUBSCRIBER_NAME).lastName(SUBSCRIBER_NAME).competitor(this.realCompetitor).toCreate().build();
		this.player = this.playerDao.create(playerToCreate);

		// virtual competitor team
		Map<String, PlayerGamePositionEnum> positions = new HashMap<>();
		List<Player> team = new LinkedList<>();
		team.add(this.player);
		positions.put(this.player.getId(), PlayerGamePositionEnum.ATTACKING_MIDFIELD);
		VirtualCompetitorTeam virtualCompetitorTeamToCreate = new VirtualCompetitorTeam.Builder().virtualCompetitor(this.virtualCompetitor).round(this.round)
				.team(team).positions(positions).points(EVENT_POINTS_1 + EVENT_POINTS_2).toCreate().build();
		this.virtualCompetitorTeam = this.virtualCompetitorTeamDao.create(virtualCompetitorTeamToCreate);

		// player event statistics
		PlayerEventStatistics playerEventStatisticsToCreate = new PlayerEventStatistics.Builder().competitor(this.realCompetitor).player(this.player)
				.event(this.event3).points(PLAYER_EVENT_POINTS).toCreate().build();
		this.playerEventStatistics = this.playerEventStatisticsDao.create(playerEventStatisticsToCreate);

		assertNotNull(this.realCompetitor);
		assertNotNull(this.realCompetitorSeason);
		assertNotNull(this.season);
		assertNotNull(this.round);
		assertNotNull(this.event1);
		assertNotNull(this.event2);
		assertNotNull(this.event3);
		assertNotNull(this.subscriber);
		assertNotNull(this.virtualCompetition);
		assertNotNull(this.virtualCompetitor);
		assertNotNull(this.player);
		assertNotNull(this.virtualCompetitorTeam);
		assertNotNull(this.playerEventStatistics);
	}

	@After
	public void tearDown() {
		assertTrue(this.eventDao.delete(this.event1));
		assertTrue(this.eventDao.delete(this.event2));
		assertTrue(this.eventDao.delete(this.event3));
		assertTrue(this.roundDao.delete(this.round));
		assertTrue(this.seasonDao.delete(this.season));
		assertTrue(this.realCompetitorDao.delete(this.realCompetitor));
		assertTrue(this.realCompetitorSeasonDao.delete(this.realCompetitorSeason));
		assertTrue(this.subscriberDao.delete(this.subscriber));
		assertTrue(this.virtualCompetitionDao.delete(this.virtualCompetition));
		assertTrue(this.VirtualCompetitorDao.delete(this.virtualCompetitor));
		assertTrue(this.playerDao.delete(this.player));
		assertTrue(this.virtualCompetitorTeamDao.delete(this.virtualCompetitorTeam));
		assertTrue(this.playerEventStatisticsDao.delete(this.playerEventStatistics));
	}

	@Test
	public void testUpdateCompetitorStatsByEvent() {
		RealCompetitorSeason processedRealCompetitorSeason = competitorService.updateCompetitorStatsByEvent(realCompetitor, event1);
		processedRealCompetitorSeason = competitorService.updateCompetitorStatsByEvent(realCompetitor, event2);
		assertTrue(processedRealCompetitorSeason.getNumOfMatches() == NUM_OF_MATCHES);
		assertTrue(processedRealCompetitorSeason.getNumOfGoalsScored() == EXPECTED_HOMESCORE);
		assertTrue(processedRealCompetitorSeason.getNumOfPoints() == POINTS);
		assertTrue(processedRealCompetitorSeason.getNumOfGoalsConceded() == GOALS_CONCEDED);
		assertTrue(processedRealCompetitorSeason.getNumOfHomeWins() == WINS);
		assertTrue(processedRealCompetitorSeason.getNumOfAwayDraws() == NUM_OF_DRAWS);
	}

	@Test
	public void testUpdateVirtualCompetitorTeamsPoints() {
		List<VirtualCompetitorTeam> VCteams = competitorService.updateVirtualCompetitorTeamsPoints(this.virtualCompetitor, this.event3);
		assertTrue(VCteams.get(0).getPoints() == PLAYER_EVENT_POINTS);
	}

	@Test
	public void testUpdateVirtualCompetitorTotalPoints() {
		VirtualCompetitor virtualCompetitor = competitorService.updateVirtualCompetitorTotalPoints(this.subscriber, this.virtualCompetition);
		assertTrue(virtualCompetitor.getTotalPoints() == EVENT_POINTS_1 + EVENT_POINTS_2);
	}
}
