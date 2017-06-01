package com.onnisoft.wahoo.model.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.dao.SubscriberCustomDao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberStatusEnum;

/**
 * Subscriber integration test.
 *
 * @author alexandru.mos
 * @date Jun 9, 2016 - 5:47:29 PM
 *
 */
// @Transactional
@ContextConfiguration(locations = { "classpath:wahoo-data-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SubscriberDAOIntegrationTest {

	private static final String EMAIL = "test@test.ro";
	private static final String UNAME = "jdoe";
	private static final String PASWD = "testPaswd";
	private static final String FNAME = "john";
	private static final String LNAME = "doe";
	private static final Date BDATE = new Date();
	private static final Country COUNTRY = new Country.Builder().id("2gvkh32h").name("Romania").abbreviation("RO").toCreate().build();
	private static final long ONEMINUTE = 60000;
	private static final Date TOKENEXPIRATIONDATE = Date.from(new Timestamp(System.currentTimeMillis() + ONEMINUTE).toInstant());
	private Subscriber subscriber;
	private Country country;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private SubscriberCustomDao subscriberCustomDao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Subscriber subscriberToCreate = new Subscriber.SubscriberBuilder().email(EMAIL).userName(UNAME).firstName(FNAME).lastName(LNAME).country(COUNTRY)
				.birthDate(BDATE).password(PASWD).role(SubscriberRoleEnum.USER).status(SubscriberStatusEnum.INIT).tokenExpirationDate(TOKENEXPIRATIONDATE)
				.toCreate().build();
		this.country = this.countryDao.create(COUNTRY);
		this.subscriber = this.subscriberDao.create(subscriberToCreate);
		assertNotNull(this.subscriber);
		assertNotNull(this.country);
		assertNotNull(this.country.getName().equals("Romania"));
		assertNotNull(this.subscriber.getCreationDate());
		assertTrue(this.subscriber.getEmail().equals(EMAIL));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		assertTrue(this.subscriberDao.delete(this.subscriber));
	}

	/**
	 * Test method for
	 * {@link com.onnisoft.ssp.model.dao.impl.SubscriberDAO#retrieveById(java.lang.String)}
	 * .
	 */
	@Test
	public void testRetrieveByIdString() {
		Subscriber subscriber = this.subscriberDao.retrieveById(this.subscriber.getId());
		Date now = Date.from(new Timestamp(System.currentTimeMillis()).toInstant());
		assertNotNull(subscriber);
		assertTrue(subscriber.getEmail().equals(EMAIL));
		assertTrue(subscriber.getTokenExpirationDate().after(now));
	}

	@Test
	public void testSearchUserSubscribersByRegExp() {
		List<Subscriber> subscribers = null;

		subscribers = this.subscriberCustomDao.searchUserSubscribersByRegExp(EMAIL);
		assertNotNull(subscribers);
		assertTrue(subscribers.get(0).getEmail().equals(EMAIL));

		subscribers = this.subscriberCustomDao.searchUserSubscribersByRegExp(FNAME);
		assertNotNull(subscribers);
		assertTrue(subscribers.get(0).getFirstName().equals(FNAME));

		subscribers = this.subscriberCustomDao.searchUserSubscribersByRegExp(LNAME);
		assertNotNull(subscribers);
		assertTrue(subscribers.get(0).getLastName().equals(LNAME));

		subscribers = this.subscriberCustomDao.searchUserSubscribersByRegExp(UNAME);
		assertNotNull(subscribers);
		assertTrue(subscribers.get(0).getUserName().equals(UNAME));

	}

	@Test
	public void testGetOnlineUsers() {
		List<Subscriber> subscribersOnline = null;

		subscribersOnline = this.subscriberCustomDao.getOnlineSubscribers(this.country.getId(), null);

		assertNotNull(subscribersOnline);
		assertTrue(!subscribersOnline.isEmpty());

		Optional<Subscriber> testSubscriber = subscribersOnline.stream().filter(e -> e.getEmail().equals(EMAIL)).findFirst();
		if (testSubscriber.isPresent()) {
			assertTrue(testSubscriber.get().getEmail().equals(EMAIL));
			assertTrue(testSubscriber.get().getCountry().equals(COUNTRY));
		}
	}

	/**
	 * Test method for
	 * {@link com.onnisoft.ssp.model.dao.AbstractDao#retrieve(java.lang.Object)}
	 * .
	 */
	@Test
	public void testRetrieve() {
		Subscriber subscriber = this.subscriberDao.retrieve(new Subscriber.SubscriberBuilder().email(EMAIL).build());
		assertNotNull(subscriber);
		assertTrue(subscriber.getEmail().equals(EMAIL));
	}

	/**
	 * Test method for
	 * {@link com.onnisoft.ssp.model.dao.AbstractDao#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		Subscriber subscriberToBeUpdated = new Subscriber.SubscriberBuilder().id(this.subscriber.getId()).email("test@test2.ro").userName("jdoe").build();
		assertTrue(this.subscriberDao.update(subscriberToBeUpdated));
		assertNotNull(subscriberToBeUpdated.getUpdateDate());
	}
}
