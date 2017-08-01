package com.onnisoft.wahoo.api.services;

import java.util.List;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.document.Country;

/**
 * 
 * Defines methods for importing data from Mako.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 11:26:24
 *
 */
public interface ImportService {

	GenericResponseDTO<List<Country>> getCountryList();
}