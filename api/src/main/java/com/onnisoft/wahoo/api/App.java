package com.onnisoft.wahoo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onnisoft.api.utils.ConfigUtils;
import com.onnisoft.wahoo.api.configuration.ApplicationConfiguration;
import com.onnisoft.wahoo.api.configuration.ApplicationSpringConfiguration;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * 
 * ! Dropwizard startup class.
 *
 * @author mbozesan
 * @date 4 Oct 2016 - 11:09:27
 *
 */
public class App extends Application<ApplicationConfiguration> {

	private static final Logger logger = LoggerFactory.getLogger("apiLog");

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}

	@Override
	public String getName() {
		return "api-api";
	}

	@Override
	public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle("/swagger-ui", "/api-docs", "index.html"));
	}

	@Override
	public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
		logger.info("Running SSP Restful API application");

		ConfigUtils.configureSpring(configuration, environment, ApplicationSpringConfiguration.class);
		ConfigUtils.addCORSHeaders(environment);
		ConfigUtils.addSpringSecurity(environment);
	}

}
