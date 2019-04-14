package com.yoda.kernal.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.yoda.brand.service.BrandService;
import com.yoda.content.service.ContentService;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.redis.RedisService;
import com.yoda.util.Constants;

@Configuration
public class RedisKeyValueListener {
	private static Logger logger = LoggerFactory.getLogger(RedisKeyValueListener.class);

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

		long hitCounter = redisService.incr(record.key() + ":" + record.value());

		if (record.key().equals(Constants.REDIS_ITEM_HIT_COUNTER)) {
			itemService.updateItemHitCounter(Integer.valueOf(record.value()), (int)hitCounter);
		}
		else if (record.key().equals(Constants.REDIS_BRAND_HIT_COUNTER)) {
			brandService.updateBrandHitCounter(Integer.valueOf(record.value()), (int)hitCounter);
		}
		else if (record.key().equals(Constants.REDIS_CONTENT_HIT_COUNTER)) {
			contentService.updateContentHitCounter(Integer.valueOf(record.value()), (int)hitCounter);
		}
	}
}
