package com.onnisoft.wahoo.model.dao.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Subscriber;
import com.onnisoft.wahoo.model.document.SubscriberDevice;
import com.onnisoft.wahoo.model.document.enums.SubscriberRoleEnum;
import com.onnisoft.wahoo.model.document.enums.SubscriberStatusEnum;

@ContextConfiguration(locations = { "classpath:wahoo-data-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SubscriberDeviceDAOIntegrationTest {

	private static final String EMAIL = "test@test.ro";
	private static final String UNAME = "jdoe";
	private static final String PASWD = "testPaswd";
	private static final String FNAME = "john";
	private static final String LNAME = "doe";
	private static final Date BDATE = new Date();
	private static final String USER_AGENT = "Eclipse-UA";
	private static final Country COUNTRY = new Country.Builder().id("2gvkh32h").name("Romania").abbreviation("RO").toCreate().build();

	private Subscriber subscriber;
	private SubscriberDevice subscriberDevice;

	@Autowired
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private Dao<SubscriberDevice> subscriberDeviceDao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Subscriber subscriberToCreate = new Subscriber.SubscriberBuilder().email(EMAIL).userName(UNAME).firstName(FNAME).lastName(LNAME).country(COUNTRY)
				.birthDate(BDATE).password(PASWD).role(SubscriberRoleEnum.USER).status(SubscriberStatusEnum.INIT).toCreate().build();
		this.subscriber = this.subscriberDao.create(subscriberToCreate);
		assertNotNull(this.subscriber);
		assertNotNull(this.subscriber.getCreationDate());
		assertTrue(this.subscriber.getEmail().equals(EMAIL));

		SubscriberDevice subscriberDeviceToCreate = new SubscriberDevice.SubscriberDeviceBuilder().userAgent(USER_AGENT).subscriber(this.subscriber).build();
		this.subscriberDevice = this.subscriberDeviceDao.create(subscriberDeviceToCreate);
		assertNotNull(this.subscriberDevice);
		assertNotNull(this.subscriberDevice.getRenewTokenId());
		assertTrue(this.subscriberDevice.getUserAgent().equalsIgnoreCase(USER_AGENT));
		assertTrue(this.subscriberDevice.getSubscriber().getEmail().equals(EMAIL));
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
	 * {@link com.onnisoft.wahoo.model.dao.impl.SubscriberDAO#retrieveById(java.lang.String)}
	 * .
	 */
	@Test
	public void testRetrieveByIdString() {
		SubscriberDevice subscriberDevice = this.subscriberDeviceDao.retrieveById(this.subscriberDevice.getRenewTokenId());
		assertNotNull(subscriberDevice);
		assertTrue(subscriberDevice.getUserAgent().equals(USER_AGENT));
	}

	/**
	 * Test method for
	 * {@link com.onnisoft.wahoo.model.dao.AbstractDao#retrieve(java.lang.Object)}
	 * .
	 */
	@Test
	public void testRetrieve() {
		SubscriberDevice subscriberDevice = this.subscriberDeviceDao
				.retrieve(new SubscriberDevice.SubscriberDeviceBuilder().userAgent(USER_AGENT).subscriber(this.subscriber).build());
		assertNotNull(subscriberDevice);
		assertTrue(subscriberDevice.getUserAgent().equals(USER_AGENT));
	}

	/**
	 * Test method for
	 * {@link com.onnisoft.wahoo.model.dao.AbstractDao#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		SubscriberDevice sdToBeUpdated = new SubscriberDevice.SubscriberDeviceBuilder().renewTokenId(this.subscriberDevice.getRenewTokenId())
				.userAgent(this.subscriberDevice.getUserAgent())
				.subscriber(new Subscriber.SubscriberBuilder().id(this.subscriber.getId()).email(this.subscriber.getEmail() + "o")
						.userName(this.subscriber.getUserName() + "a").firstName(this.subscriber.getFirstName()).lastName(this.subscriber.getLastName())
						.country(this.subscriber.getCountry()).birthDate(this.subscriber.getBirthDate()).password(this.subscriber.getPassword())
						.role(this.subscriber.getRole()).status(this.subscriber.getStatus()).build())
				.build();
		assertTrue(this.subscriberDeviceDao.update(sdToBeUpdated));
	}
}
