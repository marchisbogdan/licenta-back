package com.onnisoft.wahoo.model.dao.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.onnisoft.wahoo.model.dao.DaoHibernate;
import com.onnisoft.wahoo.model.document.Bid;

@ContextConfiguration(locations = { "classpath:wahoo-data-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BidDAOIntegrationTest {
	
	@Autowired
	private DaoHibernate<Bid> bidDao;
	
	private long id;
	private String idSubscriber="10j8d92hf912hf";
	private String idProduct="10fj9nc9uhf10jf012fj";
	private long bidValue= 255000;
	private Date date= new Date(Calendar.getInstance().getTime().getTime());
	
	private Bid bid1;
	
	@Before
	public void setup(){
		bid1 = new Bid.Builder().idSubscriber(idSubscriber).idProduct(idProduct).bidValue(bidValue).date(date).build();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testCreateBid(){
		this.bidDao.create(bid1);
		List<Bid> list = this.bidDao.findAll();
		Bid bid = list.get(0);
		Assert.assertTrue(bid.getIdSubscriber().equals(this.idSubscriber));
	}
}
