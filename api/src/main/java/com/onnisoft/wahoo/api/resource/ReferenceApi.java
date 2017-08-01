package com.onnisoft.wahoo.api.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Timed;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Service
@Path("/references")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Reference API")
public class ReferenceApi extends AbstractApi {

	@Autowired
	private Dao<Country> countryDao;

	@GET
	@Path("countries")
	@Timed
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiImplicitParams({ @ApiImplicitParam(name = HEADER_SECURITY_TOKEN, value = "Json Web Token", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_USERNAME, value = "Trusted username", dataType = "string", paramType = "header", required = false),
			@ApiImplicitParam(name = HEADER_SECURITY_TRUSTED_SECRET, value = "Trusted secret", dataType = "string", paramType = "header", required = false) })
	public GenericResponseDTO<List<Country>> getCountries() {

		List<Country> countries = this.countryDao.retrieveList(null);

		return GenericResponseDTO.createSuccess(countries);
	}
}