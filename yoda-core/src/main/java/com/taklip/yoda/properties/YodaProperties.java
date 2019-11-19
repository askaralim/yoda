package com.taklip.yoda.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="yoda")
public class YodaProperties {
	private String fileLocation;
	private Boolean kafkaEnabled;

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Boolean getKafkaEnabled() {
		return kafkaEnabled;
	}

	public void setKafkaEnabled(Boolean kafkaEnabled) {
		this.kafkaEnabled = kafkaEnabled;
	}
}