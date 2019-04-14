package com.yoda.kernal.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import com.yoda.util.Constants;

@Configuration
@EnableKafka
public class TopicConfig {
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.KAFKA_BOOTSTRAP_SERVERS);
		return new KafkaAdmin(configs);
	}

	@Bean(name = Constants.KAFKA_TOPIC_PAGE_VIEW)
	public NewTopic pageViewTopic() {
		return new NewTopic(Constants.KAFKA_TOPIC_PAGE_VIEW, 10, (short) 2);
	}

	@Bean(name = Constants.KAFKA_TOPIC_REDIS_INCR)
	public NewTopic redisIncrTopic() {
		return new NewTopic(Constants.KAFKA_TOPIC_REDIS_INCR, 10, (short) 2);
	}

//	@Bean(name = Constants.TOPIC_CLICK)
//	public NewTopic clickTopic() {
//		return new NewTopic(Constants.TOPIC_CLICK, 10, (short) 2);
//	}
}