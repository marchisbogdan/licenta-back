package com.onnisoft.wahoo.api.jms.consumers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.onnisoft.api.utils.security.dto.GenericResponseDTO;
import com.onnisoft.wahoo.api.jms.Listener;
import com.onnisoft.wahoo.api.mappers.WahooObjectMapper;
import com.onnisoft.wahoo.model.dao.Dao;

public class KafkaConsumerImpl<C> extends AbstractConsumer<KafkaConsumer<String, String>, C> implements Runnable {

	private final Logger logger = LoggerFactory.getLogger(KafkaConsumerImpl.class);

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private String bootstrapServers = "localhost:9092";

	private String groupId = "test";

	private String autoCommit = "false";

	private String keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

	private String valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";

	private String autoOffsetReset = "earliest"; // duplication of data may
												 // appear when reading from the
												 // beginning of the topics
												 // partition

	private long consumerTimeout = 5000;

	private KafkaConsumer<String, String> consumer;

	public KafkaConsumerImpl() {
	}

	public KafkaConsumerImpl(WahooObjectMapper<C> mapper, Dao<C> dao) {
		this.mapper = mapper;
		this.dao = dao;
	}

	private Properties getProperties(String groupsId) {
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("group.id", groupsId);
		props.put("enable.auto.commit", autoCommit);
		props.put("key.deserializer", keyDeserializer);
		props.put("value.deserializer", valueDeserializer);
		props.put("auto.offset.reset", autoOffsetReset);
		return props;
	}

	@Override
	public synchronized KafkaConsumer<String, String> createConsumer(List<String> destinations, String groupsId) {
		Properties props = this.getProperties(groupsId);
		this.groupId = groupsId;
		this.consumer = new KafkaConsumer<>(props);
		this.consumer.subscribe(destinations);

		System.out.println("Created consumer for topic:" + destinations.get(0).toString());
		return this.consumer;
	}

	@Override
	public void setMessageListener(Listener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> receive() {
		consumer.commitSync();
		List<Object> values = new ArrayList<>();
		ConsumerRecords<String, String> records = consumer.poll(consumerTimeout);
		for (ConsumerRecord<String, String> record : records) {
			String key = record.key();
			values.add(record.value());
			logger.info("Got message with id:" + key + ", offset:" + record.offset() + ", topic:" + record.topic() + ", partition:" + record.partition()
					+ ", date:" + new Date(record.timestamp()));
			logger.info(this.dao.getClass().getName());
			logger.info(this.mapper.getClass().getName());
		}
		return values;
	}

	@Override
	public void process(List<Object> values) {
		List<C> result = mapper.processList(GenericResponseDTO.createSuccess(values));
		logger.info("Processed " + result.size() + " objects.");
		if (!CollectionUtils.isEmpty(result)) {
			result = mapper.saveMappedObjects(result, dao);
			logger.info("Saved " + result.size() + " mapped objects.");
		} else {
			logger.warn("There weren't any processed elements!");
		}

	}

	@Override
	public void run() {
		try {
			List<Object> values = new ArrayList<>();
			while (!closed.get()) {
				values = this.receive();
				if (!CollectionUtils.isEmpty(values)) {
					// TESTING
					for (Object obj : values) {
						System.out.println("thread:" + Thread.currentThread().getName() + ", group:" + this.groupId + ", Value:" + obj.toString());
					}
					this.process(values);
					values.clear();
				}
			}
		} catch (WakeupException e) {
			if (!closed.get()) {
				logger.error(e.getMessage());
			}
		} finally {
			consumer.close();
		}

	}

	@Override
	public void close() {
		closed.set(true);
		consumer.wakeup();
	}
}
