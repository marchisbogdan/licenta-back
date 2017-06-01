package com.onnisoft.wahoo.model.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.SubscriberCustomDao;
import com.onnisoft.wahoo.model.dao.VirtualCompetitonCustomDao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.VirtualCompetition;
import com.onnisoft.wahoo.model.document.enums.CompetitionTypeEnum;
import com.onnisoft.wahoo.model.document.enums.GenderEnum;
import com.onnisoft.wahoo.model.document.enums.RulesAndPointSystemEnum;

/**
 * 
 * Virtual Competition integration test.
 *
 * @author bogdan.marchis
 * @date 4 Jan 2017 - 15:43:53
 *
 */
@ContextConfiguration(locations = { "classpath:wahoo-data-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VirtualCompetitionDAOIntegrationTest {
	private static final String NAME = "Liga 1";
	private static final String SECONDARYNAME = "Romanian League";
	private static final CompetitionTypeEnum TYPE = CompetitionTypeEnum.SEASON_COMPETITION;

	private static final int MAXNUMPARTICIPANTS = 1000;
	private static final int MAXENTRIES = 1;
	private static final String INFO = "The first romanian league.";
	private static final RulesAndPointSystemEnum RULESSYSTEM = RulesAndPointSystemEnum.FOOTBALL;
	private static final long ONEDAY = 86400000;
	private static final Date STARTDATETIME = Date.from(new Timestamp(System.currentTimeMillis() + ONEDAY).toInstant());
	private static final Date ENDDATETIME = Date.from(new Timestamp(System.currentTimeMillis() + ONEDAY * 2).toInstant());
	private static final Date LAUNCHDATETIME = Date.from(new Timestamp(System.currentTimeMillis()).toInstant());

	private static final String OPEN_MARKET_NAME = "test-open-market";
	private static final String RESTRICTED_MARKET_NAME = "test-restricted-market";
	private static final GenderEnum TARGETGENDER = GenderEnum.MALE;
	private static final int TARGETAGE = 99;

	private VirtualCompetition virtualCompetition;
	private VirtualCompetition restrictedVirtualCompetition;
	private Market openMarket;
	private Market restrictedMarket;
	private Country country;
	private Subscriber partner;

	@Autowired
	private VirtualCompetitonCustomDao virtualCompetitionDao;

	@Autowired
	private Dao<Market> marketDao;

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private SubscriberCustomDao subscriberDao;

	@Before
	public void setUp() throws Exception {
		Subscriber partner = new Subscriber.SubscriberBuilder().userName("ab").email("ab@yahoo.com").toCreate().build();

		this.partner = this.subscriberDao.create(partner);

		Country countryToCreate = new Country.Builder().id("2gvkh32h").name("Romania").abbreviation("RO").toCreate().build();

		this.country = this.countryDao.create(countryToCreate);

		Market openMarketToCreate = new Market.Builder().name(OPEN_MARKET_NAME).targetGender(TARGETGENDER).targetAge(TARGETAGE).toCreate().build();

		this.openMarket = this.marketDao.create(openMarketToCreate);

		Market restrictedMarketToCreate = new Market.Builder().name(RESTRICTED_MARKET_NAME).countries(Collections.singletonList(this.country))
				.partner(this.partner).targetGender(TARGETGENDER).targetAge(TARGETAGE).toCreate().build();

		this.restrictedMarket = this.marketDao.create(restrictedMarketToCreate);

		VirtualCompetition virtualCompetitionToCreate = new VirtualCompetition.VirtualCompetitionBuilder().name(NAME).secondaryName(SECONDARYNAME).type(TYPE)
				.maxNumParticipants(MAXNUMPARTICIPANTS).maxEntries(MAXENTRIES).info(INFO).rulesAndPointSystem(RULESSYSTEM).startDateTime(STARTDATETIME)
				.endDateTime(ENDDATETIME).launchDateTime(LAUNCHDATETIME).market(this.openMarket).toCreate().build();

		VirtualCompetition restrictedVirtualCompetitionToCreate = new VirtualCompetition.VirtualCompetitionBuilder().name(NAME).secondaryName(SECONDARYNAME)
				.type(TYPE).maxNumParticipants(MAXNUMPARTICIPANTS).maxEntries(MAXENTRIES).info(INFO).rulesAndPointSystem(RULESSYSTEM)
				.startDateTime(STARTDATETIME).endDateTime(ENDDATETIME).launchDateTime(LAUNCHDATETIME).market(this.restrictedMarket).toCreate().build();

		this.virtualCompetition = this.virtualCompetitionDao.create(virtualCompetitionToCreate);
		this.restrictedVirtualCompetition = this.virtualCompetitionDao.create(restrictedVirtualCompetitionToCreate);
		assertNotNull(this.country);
		assertNotNull(this.partner);
		assertNotNull(this.restrictedVirtualCompetition);
		assertNotNull(this.openMarket);
		assertNotNull(this.virtualCompetition);
		assertNotNull(this.virtualCompetition.getCreationDate());
		assertTrue(this.virtualCompetition.getName().equals(NAME));
	}

	@After
	public void tearDown() throws Exception {
		assertTrue(this.virtualCompetitionDao.delete(this.virtualCompetition));
		assertTrue(this.virtualCompetitionDao.delete(this.restrictedVirtualCompetition));
		assertTrue(this.marketDao.delete(this.openMarket));
		assertTrue(this.marketDao.delete(this.restrictedMarket));
		assertTrue(this.subscriberDao.delete(partner));
		assertTrue(this.countryDao.delete(this.country));
	}

	@Test
	public void testRetriveById() {
		VirtualCompetition virtualCompetitionToRetrive = this.virtualCompetitionDao.retrieveById(this.virtualCompetition.getId());
		assertNotNull(virtualCompetitionToRetrive);
		assertTrue(virtualCompetitionToRetrive.getName().equals(NAME));
	}

	@Test
	public void testUpdate() {
		VirtualCompetition virtualCompetitionToBeUpdated = new VirtualCompetition.VirtualCompetitionBuilder().id(this.virtualCompetition.getId())
				.secondaryName("Romanian Firts League").build();
		assertTrue(this.virtualCompetitionDao.update(virtualCompetitionToBeUpdated));
		assertNotNull(virtualCompetitionToBeUpdated.getUpdateDate());
	}

	@Test
	public void testGetOpenVirtualCompetitions() {
		List<VirtualCompetition> list = null;
		ArrayList<Date> dates = new ArrayList<>();
		dates.add(0, Date.from(new Timestamp(System.currentTimeMillis() - ONEDAY).toInstant()));
		dates.add(1, Date.from(new Timestamp(System.currentTimeMillis() + ONEDAY).toInstant()));

		Date start_date = dates.get(0);
		Date end_date = dates.get(1);

		list = this.virtualCompetitionDao.getOpenVirtualCompetitions(start_date, end_date);
		assertNotNull(list);
		assertTrue(!list.isEmpty());
	}

	@Test
	public void testGetRestrictedVirtualCompetitions() {
		List<VirtualCompetition> list = null;
		ArrayList<Date> dates = new ArrayList<>();
		dates.add(0, Date.from(new Timestamp(System.currentTimeMillis() - ONEDAY).toInstant()));
		dates.add(1, Date.from(new Timestamp(System.currentTimeMillis() + ONEDAY).toInstant()));

		Date start_date = dates.get(0);
		Date end_date = dates.get(1);

		list = this.virtualCompetitionDao.getRestrictedVirtualCompetitions(start_date, end_date, this.restrictedMarket);

		assertNotNull(list);
		assertTrue(!list.isEmpty());
	}

}
