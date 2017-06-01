package com.onnisoft.wahoo.api.services;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.api.jms.MainDeck;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Sport;

@ContextConfiguration(locations = { "classpath:wahoo-test-api.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class DBUpdateServiceIntegrationTest {

	@Autowired
	private DBUpdateService dbUpdateService;

	@Autowired
	private Dao<Sport> sportDao;

	@Autowired
	private Dao<RealCompetition> competitionDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private MainDeck mainDeck;

	private static final String SPORT = "football";

	private List<Sport> sportsList;
	private List<RealCompetition> competitionsList;
	private List<Season> seasonsList;
	private List<Round> roundsList;
	private List<Event> eventsList;
	private List<RealCompetitor> competitorsList;

	@Before
	public void setUp() {

		// sportsList = this.sportDao.retrieveList(null);
		// competitionsList = this.competitionDao.retrieveList(null);
		// seasonsList = this.seasonDao.retrieveList(null);
		// roundsList = this.roundDao.retrieveList(null);
		// eventsList = this.eventDao.retrieveList(null);
		// competitorsList = this.realCompetitorDao.retrieveList(null);

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testPopulateDB() {
		// this.dbUpdateService.populateDB();
	}

	@Test
	public void testProcessSports() {
		// List<String> list = new LinkedList<>();
		// list = this.dbUpdateService.processSports();
		// System.out.println("Done ... entities updated:" + list.size());
		// assertNotNull(list);
		// assertTrue(list.isEmpty() == false);

	}

	@Test
	public void testProcessCountries() {
		// List<String> countriesIds = new LinkedList<>();
		// countriesIds = this.dbUpdateService.processCountries();
		// System.out.println("Done ... entities updated:" +
		// countriesIds.size());
		// assertNotNull(countriesIds);
		// assertTrue(countriesIds.isEmpty() == false);
	}

	@Test
	public void testCompetitionsDBEntities() {

		// List<String> competitionsIds = new LinkedList<>();
		// Optional<Sport> sport = sportsList.stream().filter((s) ->
		// s.getName().toLowerCase().compareTo(SPORT) == 0).findFirst();
		// if (sport.isPresent()) {
		// competitionsIds =
		// dbUpdateService.updateCompetitionDBEntities(sport.get().getId());
		// }
		// System.out.println("Done ... entities updated:" +
		// competitionsIds.size());
		// assertNotNull(competitionsIds);
		// assertTrue(competitionsIds.isEmpty() == false);

	}

	@Test
	public void testUpdateCompetitorsDBEntities() {
		// List<String> ids = dbUpdateService.processCompetitors();
		// System.out.println("Done ... entities updated:" + ids.size());
		// assertNotNull(ids);
		// assertTrue(ids.isEmpty() == false);
	}

	@Test
	public void testSeasonDBEntities() {
		// List<String> competitionsIdsList =
		// this.competitionsList.stream().map(c ->
		// c.getId()).collect(Collectors.toList());
		// List<String> result =
		// dbUpdateService.updateSeasonDBEntities(competitionsIdsList);
		// System.out.println("Done... entities updated:" + result.size());
		// assertTrue(result.isEmpty() == false);
	}

	@Test
	public void testRoundDBEntities() {

		// List<String> seasonsIdsList = this.seasonsList.stream().map(s ->
		// s.getId()).collect(Collectors.toList());
		// List<String> result =
		// dbUpdateService.updateRoundDBEntities(seasonsIdsList);
		// System.out.println("Done... entities updated:" + result.size());
		// assertTrue(result.isEmpty() == false);

	}

	@Test
	public void testEventDBEntities() {

		// List<String> roundsIdsList = this.roundsList.stream().map(r ->
		// r.getId()).collect(Collectors.toList());
		// List<String> result =
		// dbUpdateService.updateEventDBEntities(roundsIdsList);
		// System.out.println("Done... entities updated:" + result.size());
		// assertTrue(result.isEmpty() == false);

	}

	@Test
	public void testEventCompetitorDBEntities() {
		// List<String> competitorsIdsList = this.competitorsList.stream().map(c
		// -> c.getId()).collect(Collectors.toList());
		// List<String> result =
		// dbUpdateService.updateEventCompetitorDBEntitiesByCompetitorsIds(competitorsIdsList);

		// List<String> eventsIdsList = this.eventsList.stream().map(e ->
		// e.getId()).collect(Collectors.toList());
		// List<String> result =
		// dbUpdateService.updateEventCompetitorDBEntitiesByEventIds(eventsIdsList);
		// System.out.println("Done... entities updated:" + result.size());
		// assertTrue(result.isEmpty() == false);
	}

	@Test
	public void testUpdateAllCompetitorsStats() {
		// dbUpdateService.updateAllCompetitorsStats();
	}

	@Test
	public void testUpdateAllSeasonsStats() {
		// this.dbUpdateService.updateAllSeasonsStats();
	}

	@Test
	public void testUpdatePlayersDependencies() {
		this.dbUpdateService.updatePlayersDependencies();
	}

	@Test
	public void testMainDeck() {
		// this.mainDeck.start();
		// try {
		// Thread.sleep(20000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
