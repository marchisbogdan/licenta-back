package com.onnisoft.wahoo.api.resource;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.onnisoft.api.utils.security.JsonWebToken;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Subscriber;

import io.jsonwebtoken.Claims;

public abstract class AbstractApi {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractApi.class);

	protected static final String HEADER_SECURITY_TOKEN = "X-CustomToken";
	protected static final String HEADER_SECURITY_TRUSTED_USERNAME = "trusted-username";
	protected static final String HEADER_SECURITY_TRUSTED_SECRET = "trusted-secret";

	@Autowired
	@Qualifier("subscriberDAO")
	private Dao<Subscriber> subscriberDao;

	@Autowired
	private JsonWebToken jwt;

	/**
	 * Retrieve subscriber from session token.
	 *
	 * @param request
	 * @return
	 */
	protected Subscriber retrieveUserFromToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_SECURITY_TOKEN);
		if (!StringUtils.isEmpty(token)) {
			Claims claims = this.jwt.parseJWT(token);
			if (claims != null) {
				return this.subscriberDao.retrieveById(claims.getId());
			} else {
				logger.warn("Couldn't extract information from session token=" + token);

			}
		} else {
			logger.warn("Session token was not mentioned");
		}

		return null;
	}
}
