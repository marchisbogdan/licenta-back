package com.onnisoft.wahoo.model.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

/**
 * Defines DAO operations.
 *
 * @author alexandru.mos
 * @date Jun 9, 2016 - 5:03:32 PM
 *
 */
public interface Dao<T> {

	/**
	 * Retrieve informations by T object.
	 * 
	 * @param t
	 * @return
	 */
	T retrieve(T t);

	/**
	 * Retrieve by primary key.
	 * 
	 * @param id
	 * @return
	 */
	T retrieveById(String id);

	/**
	 * Retrieve a list of informations by T object.
	 * 
	 * @param t
	 * @return
	 */
	List<T> retrieveList(T t);

	/**
	 * Retrieve a list of T objects ordered by the given sortDirection and
	 * sortParam, by skipping the first (index-1)*amount and limiting the
	 * results to the given amount.
	 * 
	 * 
	 * @param sortDirection
	 * @param sortParam
	 * @param index
	 * @param amount
	 * @param clasz
	 * @return
	 */
	List<T> retrieveSortedListBYInterval(Sort.Direction sortDirection, String sortParam, int index, int amount, Class<T> clasz);

	/**
	 * Create operation.
	 * 
	 * @param t
	 * @return
	 */
	T create(T t);

	/**
	 * Update informations.
	 * 
	 * @param t
	 * @return
	 */
	boolean update(T t);

	/**
	 * Delete found record.
	 * 
	 * @param t
	 * @return
	 */
	boolean delete(T t);

	/**
	 * retrieve a list of objects by the creation and update date and the
	 * properties found in the T object
	 * 
	 * @param creationStartDate
	 * @param creationEndDate
	 * @param updateStartDate
	 * @param updateEndDate
	 * @param t
	 * @param clasz
	 * @return
	 */
	List<T> retrieveByDates(Date creationStartDate, Date creationEndDate, Date updateStartDate, Date updateEndDate, T t, Class<T> clasz);

	/**
	 * Retrieve a list of T objects which correspond to the interval set for the
	 * specified field.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param field
	 * @param clasz
	 * @return
	 */
	List<T> processByDateInterval(Date startDate, Date endDate, String field, Class<T> clasz);

	/**
	 * Retrieve a list of T objects which match the provided regex for the
	 * specified field
	 * 
	 * @param expression
	 * @param field
	 * @return
	 */
	List<T> retireveByRegex(String expression, String field, Class<T> clasz);
}
