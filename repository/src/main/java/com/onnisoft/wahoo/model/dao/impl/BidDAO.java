package com.onnisoft.wahoo.model.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onnisoft.wahoo.model.dao.AbstractDaoHibernate;
import com.onnisoft.wahoo.model.document.Bid;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class BidDAO extends AbstractDaoHibernate<Bid> {
	public BidDAO(){
		super.setClazz(Bid.class);
	}

	@Override
	protected Criteria getSearchQuery(Bid t) {
		Criteria cr = getCurrentSession().createCriteria(Bid.class);
		
		if(t.getIdSubscriber() != null){
			cr.add(Restrictions.eq("idSubscriber", t.getIdSubscriber()));
		}
		if(t.getIdProduct() != null){
			cr.add(Restrictions.eq("idProduct",t.getIdProduct()));
		}
		if(t.getBidValue() > 0){
			cr.add(Restrictions.eq("bidValue", t.getBidValue()));
		}
		return cr;
	}
}
