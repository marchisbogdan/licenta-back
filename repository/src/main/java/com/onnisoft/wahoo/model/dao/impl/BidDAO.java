package com.onnisoft.wahoo.model.dao.impl;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDaoHibernate;
import com.onnisoft.wahoo.model.document.Bid;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BidDAO extends AbstractDaoHibernate<Bid> {
	public BidDAO(){
		super.setClazz(Bid.class);
	}
}
