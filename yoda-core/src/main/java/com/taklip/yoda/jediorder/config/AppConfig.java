package com.taklip.yoda.jediorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.taklip.yoda.jediorder.bean.IdMeta;

@Configuration
public class AppConfig {
	@Bean
	public IdMeta idMetaFactory() {
		IdMeta idMeta = new IdMeta((byte) 1, (byte) 41, (byte) 10, (byte) 12);

		return idMeta;
	}
}