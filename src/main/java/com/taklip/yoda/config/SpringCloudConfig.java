package com.taklip.yoda.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

import java.time.Duration;

@Configuration
@EnableFeignClients(basePackages = "com.taklip.yoda.client")
public class SpringCloudConfig {
    
    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .failureRateThreshold(50) // 50% failure rate threshold
                .waitDurationInOpenState(Duration.ofSeconds(60)) // Wait 60 seconds in open state
                .slidingWindowSize(10) // Count failures in last 10 calls
                .minimumNumberOfCalls(5) // Minimum calls before circuit breaker can open
                .permittedNumberOfCallsInHalfOpenState(3) // Allow 3 calls in half-open state
                .build();
    }
    
    @Bean
    public TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5)) // 5 seconds timeout
                .build();
    }
}
