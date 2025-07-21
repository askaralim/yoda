package com.taklip.yoda.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.RedisService;

@Component("contentHitCounterStrategy")
public class ContentHitCounterStrategy implements HitCounterStrategy {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ContentService contentService;

    @Override
    public void increase(Long id) {
        contentService.increaseContentHitCounter(id);
        redisService.incr(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + id);
    }
}
