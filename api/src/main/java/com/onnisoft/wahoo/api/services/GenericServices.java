package com.onnisoft.wahoo.api.services;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.document.Node;

/**
 * 
 * Contains generic methods that are used by the API and CommonLogic,
 * respectively.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 10:54:38
 *
 * @param <T>
 */

public class GenericServices<T> {

	protected final Logger logger = LoggerFactory.getLogger(GenericServices.class);

	/**
	 * returns a list with the id's of the processed data.
	 * 
	 * @param processedDataList
	 * @param clasz
	 * @return
	 */
	public List<String> processedDataIds(GenericResponseDTO<List<T>> processedDataList, Class<T> clasz) {
		List<String> ids = new LinkedList<>();
		if (processedDataList.isSuccess()) {
			ids = processedDataList.getData().stream().map(p -> ((Node) p).getId()).collect(Collectors.toList());
		} else {
			logger.warn("Error message:" + processedDataList.getErrorMessage());
		}
		return ids;
	}
}
