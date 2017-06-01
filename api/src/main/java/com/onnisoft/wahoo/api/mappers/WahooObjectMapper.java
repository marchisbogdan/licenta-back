package com.onnisoft.wahoo.api.mappers;

import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;

public interface WahooObjectMapper<T> {

	/**
	 * Insert a list of objects which will be mapped to a specific T class
	 * 
	 * @param list
	 * @return
	 */
	List<T> processList(GenericResponseDTO<List<Object>> list);

	/**
	 * Save or update the instances to the database.
	 * 
	 * @param list
	 * @param dao
	 * @return
	 */
	List<T> saveMappedObjects(List<T> list, Dao<T> dao);

	/**
	 * Save or update the instance in the database.
	 * 
	 * @param list
	 * @param dao
	 * @return
	 */
	T saveMappedObject(T t, Dao<T> dao);

	/**
	 * The function is used to map the fields according to the object of type T
	 * 
	 * @param t
	 * @param jp
	 * @return
	 */
	T objectBuilder(String t, JsonParser jp);

}
