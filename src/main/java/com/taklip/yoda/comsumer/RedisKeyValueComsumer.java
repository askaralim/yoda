package com.taklip.yoda.comsumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.common.contant.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnProperty(name = "yoda.mq-enabled", havingValue = "true", matchIfMissing = false)
@RocketMQMessageListener(topic = Constants.KAFKA_TOPIC_REDIS_INCR, consumerGroup = Constants.KAFKA_GROUP_ID_REDIS_INCR)
@Slf4j
public class RedisKeyValueComsumer implements RocketMQListener<String> {
    @Autowired
    protected BrandService brandService;

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ItemService itemService;

    @Autowired
    RedisService redisService;

    @Override
    public void onMessage(String message) {
        log.info("[RedisKeyValue] message = " + message);
    }
}