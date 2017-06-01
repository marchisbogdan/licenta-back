package com.onnisoft.wahoo.api.services;

import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestOperations;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.api.common.CommonLogic;
import com.onnisoft.wahoo.api.mappers.WahooObjectMapper;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Country;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;
import com.onnisoft.wahoo.model.document.Sport;

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
	private WahooObjectMapper<EventCompetitor> eventCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Event> eventMapper;

	@Autowired
	private WahooObjectMapper<PlayerEventStatistics> playerEventStatisticsMapper;

	@Autowired
	private WahooObjectMapper<PlayerEventTimestampStatistic> playerEventTimestampStatisticMapper;

	@Autowired
	private WahooObjectMapper<Player> playerMapper;

	@Autowired
	private WahooObjectMapper<RealCompetition> realCompetitionMapper;

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	@Autowired
	private WahooObjectMapper<Round> roundMapper;

	@Autowired
	private WahooObjectMapper<Season> seasonMapper;

	@Autowired
	private WahooObjectMapper<Sport> sportMapper;

	@Autowired
	private Dao<Country> countryDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private Dao<PlayerEventTimestampStatistic> playerEventTimestampStatisticDao;

	@Autowired
	private Dao<Player> playerDao;

	@Autowired
	private Dao<RealCompetition> realCompetitionDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Sport> sportDao;

	@Override
	public GenericResponseDTO<List<Country>> getCountryList() {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + countryLink, GenericResponseDTO.class);

		return this.processAndSave(countryMapper, response, countryDao);
	}

	@Override
	public GenericResponseDTO<List<Player>> getPlayerList() {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + playerLink, GenericResponseDTO.class);

		return this.processAndSave(playerMapper, response, playerDao);
	}

	@Override
	public GenericResponseDTO<List<Player>> getPlayerListByCompetitor(String competitorId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + playerLink + playerSingleLink + competitorIdLink + competitorId, GenericResponseDTO.class);

		return this.processAndSave(playerMapper, response, playerDao);
	}

	@Override
	public GenericResponseDTO<List<Player>> getPlayerListByRange(int range, int max_results) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + playerLink + pageLink + range + maxResultsLink + max_results, GenericResponseDTO.class);

		return this.processAndSave(playerMapper, response, playerDao);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<RealCompetitor>>> getRealCompetitorList() {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + competitorLink, GenericResponseDTO.class);

		GenericResponseDTO<List<RealCompetitor>> result = this.processAndSave(realCompetitorMapper, response, realCompetitorDao);

		return new AsyncResult<GenericResponseDTO<List<RealCompetitor>>>(result);
	}

	@Override
	public GenericResponseDTO<List<Sport>> getSportList() {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + sportLink, GenericResponseDTO.class);

		return this.processAndSave(sportMapper, response, sportDao);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<RealCompetition>>> getRealCompetitionList(String sportId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + competitionLink + sportIdLink + sportId,
				GenericResponseDTO.class);
		GenericResponseDTO<List<RealCompetition>> result = this.processAndSave(realCompetitionMapper, response, realCompetitionDao);
		return new AsyncResult<GenericResponseDTO<List<RealCompetition>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<Season>>> getSeasonList(String competitionId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + seasonLink + competitionIdLink + competitionId,
				GenericResponseDTO.class);

		GenericResponseDTO<List<Season>> result = this.processAndSave(seasonMapper, response, seasonDao);

		return new AsyncResult<GenericResponseDTO<List<Season>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<Round>>> getRoundList(String seasonId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + roundLink + seasonIdLink + seasonId,
				GenericResponseDTO.class);

		GenericResponseDTO<List<Round>> result = this.processAndSave(roundMapper, response, roundDao);

		return new AsyncResult<GenericResponseDTO<List<Round>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<Event>>> getEventList(String roundId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate.getForObject(rootLink + rootPath + eventLink + roundIdLink + roundId,
				GenericResponseDTO.class);

		GenericResponseDTO<List<Event>> result = this.processAndSave(eventMapper, response, eventDao);

		return new AsyncResult<GenericResponseDTO<List<Event>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<EventCompetitor>>> getEventCompetitorListByEventId(String eventId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + competitorLink + eventCompetitorLink + eventIdLink + eventId, GenericResponseDTO.class);

		GenericResponseDTO<List<EventCompetitor>> result = this.processAndSave(eventCompetitorMapper, response, eventCompetitorDao);

		return new AsyncResult<GenericResponseDTO<List<EventCompetitor>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<EventCompetitor>>> getEventCompetitorListByCompetitorId(String competitorId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + competitorLink + eventCompetitorLink + competitorIdLink + competitorId, GenericResponseDTO.class);

		GenericResponseDTO<List<EventCompetitor>> result = this.processAndSave(eventCompetitorMapper, response, eventCompetitorDao);

		return new AsyncResult<GenericResponseDTO<List<EventCompetitor>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<PlayerEventStatistics>>> getPlayerEventStatisticsListByPlayerId(String playerId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + playerLink + statisticsLink + playerEventLink + playerIdLink + playerId, GenericResponseDTO.class);

		GenericResponseDTO<List<PlayerEventStatistics>> result = this.processAndSave(playerEventStatisticsMapper, response, playerEventStatisticsDao);

		return new AsyncResult<GenericResponseDTO<List<PlayerEventStatistics>>>(result);
	}

	@Override
	@Async
	public Future<GenericResponseDTO<List<PlayerEventTimestampStatistic>>> getPlayerEventTimestampStatisticByPlayerId(String playerId) {
		GenericResponseDTO<String> token = commonLogic.getTokenFromRemote();
		String tok = token.getData();

		commonLogic.setupRestTemplate(tok);

		@SuppressWarnings("unchecked")
		GenericResponseDTO<List<Object>> response = this.restTemplate
				.getForObject(rootLink + rootPath + playerLink + statisticsLink + playerEventTimestampLink + playerIdLink + playerId, GenericResponseDTO.class);

		GenericResponseDTO<List<PlayerEventTimestampStatistic>> result = this.processAndSave(playerEventTimestampStatisticMapper, response,
				playerEventTimestampStatisticDao);

		return new AsyncResult<GenericResponseDTO<List<PlayerEventTimestampStatistic>>>(result);
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
