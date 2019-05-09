package com.taklip.yoda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.taklip.yoda", "com.taklip.jediorder"})
public class YodaCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(YodaCoreApplication.class, args);
	}
}