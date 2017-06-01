package com.onnisoft.wahoo.api.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onnisoft.api.utils.security.JsonWebToken;
import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.api.request.MakoRemoteAuthenticationRequestDTO;

/**
 * 
 * Contains the logic that is common across APIs.
 *
 * @author mbozesan
 * @date 26 Oct 2016 - 15:41:18
 *
 */

@Component
public class CommonLogic implements Common {

	private final Logger logger = LoggerFactory.getLogger(CommonLogic.class);

	@Autowired
	private RestOperations restTemplate;

	@Value("${subscriber.root.link}")
	private String rootLink;

	@Value("${subscriber.root.path}")
	private String subscriberRootPath;

	@Value("${subscriber.login}")
	private String subscriberLogin;

	@Value("${username.mako}")
	private String userName;

	@Value("${password}")
	private String password;

	private static String token;

	@Autowired
	private JsonWebToken jwt;

	@PostConstruct
	private void init() {
		ObjectMapper objectMapper = new ObjectMapper();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		jsonMessageConverter.setObjectMapper(objectMapper);
		messageConverters.add(jsonMessageConverter);
		((RestTemplate) this.restTemplate).setMessageConverters(messageConverters);
	}

	@Override
	public GenericResponseDTO<String> getTokenFromRemote() {
		if (!StringUtils.isEmpty(token) && jwt.parseJWT(token) != null) {
			return GenericResponseDTO.createSuccess(token);
		}

		MakoRemoteAuthenticationRequestDTO request = new MakoRemoteAuthenticationRequestDTO(userName, password);

		GenericResponseDTO<String> response = restTemplate.postForObject(rootLink + subscriberRootPath + subscriberLogin, request, GenericResponseDTO.class);

		if (!response.isSuccess()) {
			logger.warn(response.getErrorMessage());
			return GenericResponseDTO.createFailed("Invalid request");
		}

		Object data = response.getData();
		LinkedHashMap<String, String> valueMap = (LinkedHashMap<String, String>) data;
		token = valueMap.get("jwtToken");

		return GenericResponseDTO.createSuccess(token);
	}

	@Override
	public void setupRestTemplate(final String token) {
		ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
				org.springframework.http.HttpHeaders headers = request.getHeaders();
				headers.add("User-Agent", "wahoo-project");
				if (token != null) {
					headers.set("X-CustomToken", token);
				}

				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				headers.setAccept(new ArrayList<org.springframework.http.MediaType>() {
					private static final long serialVersionUID = -7874092369836338359L;

					{
						add(org.springframework.http.MediaType.APPLICATION_JSON);
					}
				});
				return execution.execute(request, body);
			}
		};

		((InterceptingHttpAccessor) this.restTemplate).setInterceptors(Collections.singletonList(interceptor));
	}
}
