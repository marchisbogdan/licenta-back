package com.onnisoft.wahoo.model.dao.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.onnisoft.wahoo.model.dao.AbstractDao;
import com.onnisoft.wahoo.model.document.Comment;

@Repository
public class CommentDAO extends AbstractDao<Comment> {

	@Override
	public Comment retrieve(Comment Comment) {
		return super.retrieve(Comment, Comment.class);
	}

	@Override
	public Comment retrieveById(String id) {
		return super.retrieveById(id, Comment.class);
	}

	@Override
	public List<Comment> retrieveList(Comment Comment) {
		return super.retrieveList(Comment, Comment.class);
	}

	@Override
	public boolean update(Comment Comment) {
		return super.update(Comment, Criteria.where("id").is(Comment.getId()), Comment.class);
	}

	@Override
	public boolean delete(Comment Comment) {
		Query query = new Query(Criteria.where("id").is(Comment.getId()));
		boolean deleted = super.delete(query, Comment.class);
		if (!deleted) {
			logger.warn("Comment " + Comment.getId() + " was not deleted");
		}
		return deleted;
	}

	@Override
	protected Query createSearchQuery(Comment t) {
		Query query = new Query();
		if(t.getSubscriber() != null){
			query.addCriteria(Criteria.where("subscriber").is(t.getSubscriber()));
		}
		return query;
	}

	@Override
	protected Update createUpdateQuery(Comment t) {
		Update update = new Update();
		if(t.getContent() != null){
			update.set("content", t.getContent());
		}
		if(t.getUpdateDate() != null){
			update.set("updateDate", t.getUpdateDate());
		}
		return update;
	}

}
