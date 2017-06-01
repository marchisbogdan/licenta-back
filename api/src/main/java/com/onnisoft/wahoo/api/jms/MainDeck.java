package com.onnisoft.wahoo.api.jms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.onnisoft.wahoo.api.jms.consumers.KafkaConsumerImpl;
import com.onnisoft.wahoo.api.mappers.WahooObjectMapper;
import com.onnisoft.wahoo.model.dao.Dao;
import com.onnisoft.wahoo.model.document.Event;
import com.onnisoft.wahoo.model.document.EventCompetitor;
import com.onnisoft.wahoo.model.document.Player;
import com.onnisoft.wahoo.model.document.PlayerEventStatistics;
import com.onnisoft.wahoo.model.document.PlayerEventTimestampStatistic;
import com.onnisoft.wahoo.model.document.RealCompetition;
import com.onnisoft.wahoo.model.document.RealCompetitor;
import com.onnisoft.wahoo.model.document.Round;
import com.onnisoft.wahoo.model.document.Season;

@Component
public class MainDeck {

	@Autowired
	private WahooObjectMapper<RealCompetition> realCompetitionMapper;

	@Autowired
	private WahooObjectMapper<Season> seasonMapper;

	@Autowired
	private WahooObjectMapper<Round> roundMapper;

	@Autowired
	private WahooObjectMapper<Event> eventMapper;

	@Autowired
	private WahooObjectMapper<EventCompetitor> eventCompetitorMapper;

	@Autowired
	private WahooObjectMapper<RealCompetitor> realCompetitorMapper;

	@Autowired
	private WahooObjectMapper<PlayerEventStatistics> playerEventStatisticsMapper;

	@Autowired
	private WahooObjectMapper<PlayerEventTimestampStatistic> playrEventTimestampStatisticMapper;

	@Autowired
	private WahooObjectMapper<Player> playerMapper;

	@Autowired
	private Dao<RealCompetition> realCompetitionDao;

	@Autowired
	private Dao<Season> seasonDao;

	@Autowired
	private Dao<Round> roundDao;

	@Autowired
	private Dao<Event> eventDao;

	@Autowired
	private Dao<EventCompetitor> eventCompetitorDao;

	@Autowired
	private Dao<RealCompetitor> realCompetitorDao;

	@Autowired
	private Dao<PlayerEventStatistics> playerEventStatisticsDao;

	@Autowired
	private Dao<PlayerEventTimestampStatistic> playerEventTimestampStatisticDao;

	@Autowired
	private Dao<Player> playerDao;

	private final String TEST_TOPIC = "test";
	private final String REAL_COMPETITION_TOPIC = "real-competition";
	private final String SEASON_TOPIC = "season";
	private final String ROUND_TOPIC = "round";
	private final String EVENT_TOPIC = "event";
	private final String EVENT_COMPETITOR_TOPIC = "event-competitor";
	private final String REAL_COMPETITOR_TOPIC = "real-competitor";
	private final String PLAYER_EVENT_STATISTICS_TOPIC = "player-event-statistic";
	private final String PLAYER_EVENT_TIMESTAMP_STATISTICS_TOPIC = "player-event-statistic";
	private final String PLAYER_TOPIC = "player";

	private final String GROUP_ONE = "group-1";

	private List<KafkaConsumerImpl<?>> consumersList;

	@Autowired
	private TaskExecutor taskExecutor;

	private void generateConsumerInstances() {

		consumersList = new ArrayList<>();
		// TEST
		// TODO: delete
		KafkaConsumerImpl<RealCompetitor> kafkaConsumer = new KafkaConsumerImpl<>(realCompetitorMapper, realCompetitorDao);
		kafkaConsumer.createConsumer(Arrays.asList(TEST_TOPIC), GROUP_ONE);
		this.taskExecutor.execute(kafkaConsumer);
		consumersList.add(kafkaConsumer);

		// REAL-COMPETITION
		KafkaConsumerImpl<RealCompetition> realCompetitionKafkaConsumer = new KafkaConsumerImpl<>(realCompetitionMapper, realCompetitionDao);
		realCompetitionKafkaConsumer.createConsumer(Arrays.asList(REAL_COMPETITION_TOPIC), GROUP_ONE);
		consumersList.add(realCompetitionKafkaConsumer);

		// SEASON
		KafkaConsumerImpl<Season> seasonKafkaConsumer = new KafkaConsumerImpl<>(seasonMapper, seasonDao);
		seasonKafkaConsumer.createConsumer(Arrays.asList(SEASON_TOPIC), GROUP_ONE);
		consumersList.add(seasonKafkaConsumer);

		// ROUND
		KafkaConsumerImpl<Round> roundKafkaConsumer = new KafkaConsumerImpl<>(roundMapper, roundDao);
		roundKafkaConsumer.createConsumer(Arrays.asList(ROUND_TOPIC), GROUP_ONE);
		consumersList.add(roundKafkaConsumer);

		// EVENT
		KafkaConsumerImpl<Event> eventKafkaConsumer = new KafkaConsumerImpl<>(eventMapper, eventDao);
		eventKafkaConsumer.createConsumer(Arrays.asList(EVENT_TOPIC), GROUP_ONE);
		consumersList.add(eventKafkaConsumer);

		// EVENT-COMPETITOR
		KafkaConsumerImpl<EventCompetitor> eventCompetitorKafkaConsumer = new KafkaConsumerImpl<>(eventCompetitorMapper, eventCompetitorDao);
		eventCompetitorKafkaConsumer.createConsumer(Arrays.asList(EVENT_COMPETITOR_TOPIC), GROUP_ONE);
		consumersList.add(eventCompetitorKafkaConsumer);

		// REAL-COMPETITOR
		KafkaConsumerImpl<RealCompetitor> realCompetitorKafkaConsumer = new KafkaConsumerImpl<>(realCompetitorMapper, realCompetitorDao);
		realCompetitorKafkaConsumer.createConsumer(Arrays.asList(REAL_COMPETITOR_TOPIC), GROUP_ONE);
		consumersList.add(realCompetitorKafkaConsumer);

		// PLAYER_TOPIC
		KafkaConsumerImpl<Player> playerKafkaConsumer = new KafkaConsumerImpl<>(playerMapper, playerDao);
		playerKafkaConsumer.createConsumer(Arrays.asList(PLAYER_TOPIC), GROUP_ONE);
		consumersList.add(playerKafkaConsumer);

		// PLAYER_EVENT_STATISTICS_
		KafkaConsumerImpl<PlayerEventStatistics> playerEventStatisticsKafkaConsumer = new KafkaConsumerImpl<>(playerEventStatisticsMapper,
				playerEventStatisticsDao);
		playerEventStatisticsKafkaConsumer.createConsumer(Arrays.asList(PLAYER_EVENT_STATISTICS_TOPIC), GROUP_ONE);
		consumersList.add(playerEventStatisticsKafkaConsumer);

		// PLAYER_EVENT_TIMESTAMP_STATISTICS
		KafkaConsumerImpl<PlayerEventTimestampStatistic> playerEventTimestampStatisticKafkaConsumer = new KafkaConsumerImpl<>(
				playrEventTimestampStatisticMapper, playerEventTimestampStatisticDao);
		playerEventTimestampStatisticKafkaConsumer.createConsumer(Arrays.asList(PLAYER_EVENT_TIMESTAMP_STATISTICS_TOPIC), GROUP_ONE);
		consumersList.add(playerEventTimestampStatisticKafkaConsumer);

		// try {
		// // TODO: Delete the next section
		// Thread.sleep(12000);
		// kafkaConsumer.close();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	public void start() {
		generateConsumerInstances();
		consumersList.stream().forEach(c -> {
			System.out.println(c.getClass().getName());
			this.taskExecutor.execute(c);
		});
	}
}
