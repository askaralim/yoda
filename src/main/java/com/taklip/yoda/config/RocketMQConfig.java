package com.taklip.yoda.config;

// import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
@ConditionalOnProperty(name = "yoda.mq-enabled", havingValue = "true", matchIfMissing = false)
public class RocketMQConfig {
    @Bean
    @Role(org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE)
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }

    // @Bean
    // @Role(org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE)
    // public RocketMQTemplate rocketMQTemplate() {
    //     return new RocketMQTemplate();
    // }
}