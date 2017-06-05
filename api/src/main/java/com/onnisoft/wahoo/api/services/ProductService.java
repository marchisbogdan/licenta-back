package com.onnisoft.wahoo.api.services;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.api.request.BidCreationRequestDTO;
import com.onnisoft.wahoo.model.dao.DaoHibernate;
import com.onnisoft.wahoo.model.document.Bid;
import com.onnisoft.wahoo.model.document.Product;
import com.onnisoft.wahoo.model.document.Subscriber;

@Component
public class ProductService extends AbstractService<Product> {

	@Autowired
	private DaoHibernate<Bid> bidDao;

	@Autowired
	SessionFactory sessionFactory;

	@Override
	void inactivateById(String id) {

	}

	public void createBidTransaction(BidCreationRequestDTO bidDTO, Subscriber subscriber) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			// get all the bids for the specified product
			List<Bid> bids = this.getSordedBidsForProduct(bidDTO.getIdProduct());

			if (!CollectionUtils.isEmpty(bids)) {
				bids.stream().sorted((b1, b2) -> Long.compare(b2.getBidValue(), b1.getBidValue()));
				// check if the current bid is higher then any other bid and do
				// the proper changes
				if (bids.get(0).getBidValue() >= bidDTO.getBidValue()) {
					throw new Exception("Your bid is lower then the highest bid");
				}
			}
			// in case there is no higher bidder or there are no bids the new
			// bid is added to the DB
			this.bidDao.create(new Bid.Builder().bidValue(bidDTO.getBidValue()).idProduct(bidDTO.getIdProduct())
					.idSubscriber(subscriber.getId()).date(new Date(Calendar.getInstance().getTimeInMillis())).build());
			
			transaction.commit();
		} catch (RuntimeException e) {
			try {
				transaction.rollback();
			}catch(RuntimeException rbe){
				logger.error("Couldn't roll back transaction:"+rbe.getMessage());
			}
		} catch (Exception e1) {
			throw new Exception(e1.getMessage());
		}
	}
	
	public List<Bid> getSordedBidsForProduct(String productId){
		List<Bid> bids = this.bidDao.findByProps(new Bid.Builder().idProduct(productId).build());
		if(!CollectionUtils.isEmpty(bids)){
			bids = bids.stream().sorted((b1, b2) -> Long.compare(b2.getBidValue(), b1.getBidValue())).collect(Collectors.toList());			
		}
		return bids;
	}
}
