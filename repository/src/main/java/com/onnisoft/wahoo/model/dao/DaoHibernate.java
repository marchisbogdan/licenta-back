package com.onnisoft.wahoo.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;

public interface DaoHibernate<T> {
	 T findOne(final long id);
	 
	 List<T> findByProps(final T entity);
	 
	 List<T> findAll();
	 
	 void create(final T entity);
	 
	 T update(final T entity);
	 
	 void delete(final T entity);
	 
	 void deleteById(final long entityId);
	 
	 SessionFactory getSessionFactory();
}
