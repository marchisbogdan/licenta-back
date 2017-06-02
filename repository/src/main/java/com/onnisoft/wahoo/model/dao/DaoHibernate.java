package com.onnisoft.wahoo.model.dao;

import java.util.List;

public interface DaoHibernate<T> {
	 T findOne(final long id);
	 
	 List<T> findAll();
	 
	 void create(final T entity);
	 
	 T update(final T entity);
	 
	 void delete(final T entity);
	 
	 void deleteById(final long entityId);
}