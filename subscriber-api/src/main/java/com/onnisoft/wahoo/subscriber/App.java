package com.onnisoft.wahoo.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onnisoft.api.utils.ConfigUtils;
import com.onnisoft.wahoo.subscriber.configuration.ApplicationConfiguration;
import com.onnisoft.wahoo.subscriber.configuration.ApplicationSpringConfiguration;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * ! Dropwizard startup class.
 *
 * @author mbozesan
 * @date 12 Sep 2016 - 15:24:58
 */
public class App extends Application<ApplicationConfiguration> {

	private static final Logger logger = LoggerFactory.getLogger("subscriberApiLog");

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}

	@Override
	public String getName() {
		return "subscriber-api";
	}

	@Override
	public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle("/swagger-ui/", "/api-docs", "index.html"));
	}

	@Override
	public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
		logger.info("Running WAHOO Subscriber API application");

		ConfigUtils.configureSpring(configuration, environment, ApplicationSpringConfiguration.class);
		ConfigUtils.addCORSHeaders(environment);
		ConfigUtils.addSpringSecurity(environment);
	}
}
