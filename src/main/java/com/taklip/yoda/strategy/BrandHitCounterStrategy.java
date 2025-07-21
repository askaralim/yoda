package com.taklip.yoda.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.RedisService;

@Component("brandHitCounterStrategy")
public class BrandHitCounterStrategy implements HitCounterStrategy {
    @Autowired
    private RedisService redisService;

    @Autowired
    private BrandService brandService;

    @Override
    public void increase(Long id) {
        brandService.increaseBrandHitCounter(id);
        redisService.incr(Constants.REDIS_BRAND_HIT_COUNTER + ":" + id);
    }
}
