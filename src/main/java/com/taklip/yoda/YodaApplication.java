package com.taklip.yoda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.taklip.yoda" })
@ConfigurationPropertiesScan
public class YodaApplication {
    public static void main(String[] args) {
        SpringApplication.run(YodaApplication.class, args);
    }
}