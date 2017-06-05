package com.onnisoft.wahoo.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoHibernate<T> implements DaoHibernate<T> {
	private Class< T > clazz;
	 
	   @Autowired
	   SessionFactory sessionFactory;
	 
	   public final void setClazz( Class< T > clazzToSet ){
	      this.clazz = clazzToSet;
	   }
	 
	   @SuppressWarnings("unchecked")
	   public T findOne( long id ){
	      return (T) getCurrentSession().get( clazz, id );
	   }
	   
	   @SuppressWarnings("unchecked")
	   public List<T> findByProps(T entity){
		   Criteria cr =getSearchQuery(entity);
		   return cr.list();
	   }
	   
	   @SuppressWarnings("unchecked")
	   public List< T > findAll(){
	      return getCurrentSession().createQuery( "from " + clazz.getName() ).list();
	   }
	 
	   public void create( T entity ){
	      getCurrentSession().persist( entity );
	   }
	 
	   @SuppressWarnings("unchecked")
	   public T update( T entity ){
		   return (T) getCurrentSession().merge( entity );
	   }
	 
	   public void delete( T entity ){
	      getCurrentSession().delete( entity );
	   }
	   public void deleteById( long entityId ) {
	      T entity = findOne( entityId );
	      delete( entity );
	   }
	 
	   protected final Session getCurrentSession() {
	      return sessionFactory.getCurrentSession();
	   }
	   
	   protected abstract Criteria getSearchQuery(T t);
}
