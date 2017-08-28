package com.onnisoft.wahoo.model.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberStatusEnum;

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
	@Qualifier("subscriberDAO")
	private Dao<Subscriber> subscriberDao;

	@Autowired
	@Qualifier("countryDAO")
	private Dao<Country> countryDao;

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
