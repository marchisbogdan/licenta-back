package com.onnisoft.wahoo.model.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.MarketCustomDao;
import com.onnisoft.wahoo.model.dao.SubscriberCustomDao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Market;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.GenderEnum;

@ContextConfiguration(locations = { "classpath:wahoo-data-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MarketDAOIntegrationTest {
	private static final GenderEnum TARGETGENDER = GenderEnum.MALE;
	private static final String OPEN_MARKET_NAME = "test-open-market";
	private static final String RESTRICTED_MARKET_NAME = "test-restricted-market";

	private Market openMarket;
	private Market restrictedMarket;
	private Subscriber partner;
	private Country country1;
	private Country country2;

	@Autowired
	private MarketCustomDao marketDao;

	@Autowired
	private SubscriberCustomDao subscriberDao;

	@Autowired
	private Dao<Country> countryDao;

	@Before
	public void setUp() throws Exception {

		Country romania = new Country.Builder().id("2gvkh32h").name("Romania").abbreviation("RO").toCreate().build();
		Country bulgaria = new Country.Builder().id("3gvkh32h").name("Bulgaria").abbreviation("BG").toCreate().build();

		this.country1 = this.countryDao.create(romania);
		this.country2 = this.countryDao.create(bulgaria);

		List<Country> countries = Arrays.asList(romania, bulgaria);

		Subscriber partnerToCreate = new Subscriber.SubscriberBuilder().id("4gvkh32h").userName("ab").email("ab@yahoo.com").toCreate().build();
		this.partner = this.subscriberDao.create(partnerToCreate);

		Market restrictedMarketToCreate = new Market.Builder().name(RESTRICTED_MARKET_NAME).countries(countries).partner(this.partner)
				.targetGender(TARGETGENDER).targetAge(11).toCreate().build();
		Market openMarketToCreate = new Market.Builder().name(OPEN_MARKET_NAME).targetGender(TARGETGENDER).targetAge(99).toCreate().build();

		this.restrictedMarket = this.marketDao.create(restrictedMarketToCreate);
		this.openMarket = this.marketDao.create(openMarketToCreate);
		assertNotNull(this.restrictedMarket);
		assertNotNull(this.openMarket);
		assertTrue(this.restrictedMarket.getCountryRestricted() == true);
		assertTrue(this.openMarket.getCountryRestricted() == null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		assertTrue(this.marketDao.delete(this.restrictedMarket));
		assertTrue(this.marketDao.delete(this.openMarket));
		assertTrue(this.subscriberDao.delete(this.partner));
		assertTrue(this.countryDao.delete(this.country1));
		assertTrue(this.countryDao.delete(this.country2));
	}

	@Test
	public void testRetrieveByIdString() {
		Market market = this.marketDao.retrieveById(this.openMarket.getId());
		assertNotNull(market);
		assertTrue(market.getTargetGender().equals(TARGETGENDER));
	}

	@Test
	public void testGetMarketsRestrictedBy() {
		List<Market> list = null;
		list = this.marketDao.getMarketsRestrictedByCountriesOrPartner(restrictedMarket);

		assertNotNull(list);
		assertTrue(!list.isEmpty());

		Market marketToTest = list.get(0);
		assertTrue(marketToTest.getPartner().equals(this.partner));
		assertTrue(!marketToTest.getCountries().isEmpty());
	}
}
