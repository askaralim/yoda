package com.taklip.yoda.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	private final KafkaTemplate<Object, Object> kafkaTemplate;

	Producer(KafkaTemplate<Object, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(Object message) {
		this.kafkaTemplate.send("testTopic", message);
		System.out.println("Sent sample message [" + message + "]");
	}

}
