package com.taklip.yoda.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;

@Configuration
public class RedisKeyValueComsumer {
	private static Logger logger = LoggerFactory.getLogger(RedisKeyValueComsumer.class);

	@Autowired
	protected BrandService brandService;

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@Autowired
	RedisService redisService;

	@KafkaListener(id = Constants.KAFKA_CONTAINER_REDIS_INCR, topics = {Constants.KAFKA_TOPIC_REDIS_INCR})
	public void listen(ConsumerRecord<String, String> record) {
		if (logger.isInfoEnabled()) {
			logger.info("[Kafka: Redis key-value] topic = " + record.topic() 
					+ " partition = " + record.partition() 
					+ " offset = " + record.offset() 
					+ " key = " + record.key() 
					+ " value = " + record.value());
		}
	}
}