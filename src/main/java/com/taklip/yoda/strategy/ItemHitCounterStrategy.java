package com.taklip.yoda.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;

@Component("itemHitCounterStrategy")
public class ItemHitCounterStrategy implements HitCounterStrategy {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ItemService itemService;

    @Override
    public void increase(Long id) {
        itemService.increaseItemHitCounter(id);
        redisService.incr(Constants.REDIS_ITEM_HIT_COUNTER + ":" + id);
    }
}
