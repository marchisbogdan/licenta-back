package com.onnisoft.wahoo.api.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.api.common.CommonLogic;
import com.onnisoft.wahoo.api.mappers.WahooObjectMapper;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;

/**
 * 
 * Implements operations for importing data from Mako.
 *
 * @author mbozesan
 * @date 31 Oct 2016 - 11:27:56
 *
 */
@Component
public class MakoImportServices implements ImportService {

	private final Logger logger = LoggerFactory.getLogger(MakoImportServices.class);

	@Autowired
	private CommonLogic commonLogic;

	@Autowired
	private RestOperations restTemplate;

	@Value("${root.link}")
	private String rootLink;

	@Value("${sports.football.id}")
	private String sportsFootballId;

	@Value("${country.link}")
	private String countryLink;

	@Value("${player.link}")
	private String playerLink;

	@Value("${competitor.link}")
	private String competitorLink;

	@Value("${sport.link}")
	private String sportLink;

	@Value("${competition.link}")
	private String competitionLink;

	@Value("${season.link}")
	private String seasonLink;

	@Value("${round.link}")
	private String roundLink;

	@Value("${event.link}")
	private String eventLink;

	@Value("${root.path}")
	private String rootPath;

	@Value("${seasonId.link}")
	private String seasonIdLink;

	@Value("${competitionId.link}")
	private String competitionIdLink;

	@Value("${eventCompetitor.link}")
	private String eventCompetitorLink;

	@Value("${statistics.link}")
	private String statisticsLink;

	@Value("${playerEvent.link}")
	private String playerEventLink;

	@Value("${playerEventTimestamp.link}")
	private String playerEventTimestampLink;

	@Value("${player.single.link}")
	private String playerSingleLink;

	@Value("${roundId.link}")
	private String roundIdLink;

	@Value("${sportId.link}")
	private String sportIdLink;

	@Value("${eventId.link}")
	private String eventIdLink;

	@Value("${competitorId.link}")
	private String competitorIdLink;

	@Value("${playerId.link}")
	private String playerIdLink;

	@Value("${page.link}")
	private String pageLink;

	@Value("${max.results.link}")
	private String maxResultsLink;

	@Autowired
	private WahooObjectMapper<Country> countryMapper;

	@Autowired
	@Qualifier("countryDAO")
	private Dao<Country> countryDao;

	@Override
	public GenericResponseDTO<List<Country>> getCountryList() {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + countryLink, GenericResponseDTO.class);

		return this.processAndSave(countryMapper, response, countryDao);
	}
	/**
	 * The function uses a Json Mapper, for the objects inside response, to save
	 * or update the DBEntities.
	 * 
	 * @param mapper
	 * @param response
	 * @param dao
	 * @return
	 */
	private <T> GenericResponseDTO<List<T>> processAndSave(WahooObjectMapper<T> mapper, GenericResponseDTO<List<Object>> response, Dao<T> dao) {
		List<T> result = mapper.processList(response);

		if (CollectionUtils.isEmpty(result)) {
			logger.warn("There is no data to be displayed");
			return GenericResponseDTO.createFailed("There is no data to be displayed");
		}
		logger.info("SAVING OR UPDATING, nr of elements:" + result.size());
		result = mapper.saveMappedObjects(result, dao);

		return GenericResponseDTO.createSuccess(result);
	}
}
