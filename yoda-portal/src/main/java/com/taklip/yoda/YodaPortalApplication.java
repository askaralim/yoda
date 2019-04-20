package com.taklip.yoda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class YodaPortalApplication extends SpringBootServletInitializer {
	private final Logger logger = LoggerFactory.getLogger(YodaPortalApplication.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YodaPortalApplication.class).bannerMode(Banner.Mode.OFF);
	}

	public static void main(String[] args) {
		SpringApplication.run(YodaPortalApplication.class, args);
	}
}