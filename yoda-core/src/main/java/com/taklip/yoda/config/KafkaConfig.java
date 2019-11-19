package com.taklip.yoda.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import com.taklip.yoda.properties.YodaProperties;

@Configuration
@EnableKafka
public class KafkaConfig {
	private final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

	protected final YodaProperties properties;

	@Autowired
	public KafkaConfig(YodaProperties properties) {
		this.properties = properties;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory,
			KafkaTemplate<Object, Object> template) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();

		configurer.configure(factory, kafkaConsumerFactory);

		factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), 3));

		logger.info("[Kafka] Init, default topic: " + template.getDefaultTopic());

		if (!properties.getKafkaEnabled()) {
			factory.setAutoStartup(false);
		}

		return factory;
	}
}