package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Product;

@Repository
public class ProductDAO extends AbstractDao<Product> {

	@Override
	public Product retrieve(Product Product) {
		return super.retrieve(Product, Product.class);
	}

	@Override
	public Product retrieveById(String id) {
		return super.retrieveById(id, Product.class);
	}

	@Override
	public List<Product> retrieveList(Product Product) {
		return super.retrieveList(Product, Product.class);
	}

	@Override
	public boolean update(Product Product) {
		return super.update(Product, Criteria.where("id").is(Product.getId()), Product.class);
	}

	@Override
	public boolean delete(Product Product) {
		Query query = new Query(Criteria.where("id").is(Product.getId()));
		boolean deleted = super.delete(query, Product.class);
		if (!deleted) {
			logger.warn("Product " + Product.getId() + "was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Product t) {
		Query query = new Query();
		if(t.getName() != null){
			query.addCriteria(Criteria.where("name").is(t.getName()));
		}
		if(t.getDescription() != null){
			query.addCriteria(Criteria.where("description").is(t.getDescription()));
		}
		if(t.getImageURL() != null){
			query.addCriteria(Criteria.where("imageURL").is(t.getImageURL()));
		}
		if(t.getBidEndDate() != null){
			query.addCriteria(Criteria.where("bidEndDate").is(t.getBidEndDate()));
		}
		if(!CollectionUtils.isEmpty(t.getComments())){
			query.addCriteria(Criteria.where("comments").is(t.getComments()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(Product t) {
		Update update = new Update();
		
		if(t.getName() != null){
			update.set("name",t.getName());
		}
		if(t.getDescription() != null){
			update.set("description", t.getDescription());
		}
		if(t.getImageURL() != null){
			update.set("imageURL", t.getImageURL());
		}
		if(t.getBidEndDate() != null){
			update.set("bidEndDate", t.getBidEndDate());
		}
		if(!CollectionUtils.isEmpty(t.getComments())){
			update.addToSet("comments", t.getComments().get(0));
		}
		if(t.getStartingPrice() != 0){
			update.set("startingPrice",t.getStartingPrice());
		}
		if(t.getHighestPrice() != 0){
			update.set("highestPrice", t.getHighestPrice());
		}
		if(t.getQuantity() != 0){
			update.set("quantity", t.getQuantity());
		}
		if(t.getUpdateDate() != null){
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}

}
