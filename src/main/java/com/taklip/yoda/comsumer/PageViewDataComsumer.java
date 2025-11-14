package com.taklip.yoda.comsumer;

import java.util.List;
import java.util.Map;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.strategy.BrandHitCounterStrategy;
import com.taklip.yoda.strategy.ContentHitCounterStrategy;
import com.taklip.yoda.strategy.HitCounterStrategy;
import com.taklip.yoda.strategy.ItemHitCounterStrategy;

import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnProperty(name = "yoda.mq-enabled", havingValue = "true", matchIfMissing = false)
@RocketMQMessageListener(topic = Constants.KAFKA_TOPIC_PAGE_VIEW, consumerGroup = Constants.KAFKA_GROUP_ID_PAGE_VIEW)
@Slf4j
public class PageViewDataComsumer implements RocketMQListener<PageView> {
    @Autowired
    private PageViewService pageViewService;

    private final Map<String, HitCounterStrategy> strategyMap;

    public PageViewDataComsumer(List<HitCounterStrategy> strategies) {
        this.strategyMap = Map.of(
                ContentTypeEnum.ITEM.getType(),
                strategies.stream().filter(s -> s instanceof ItemHitCounterStrategy).findFirst().orElseThrow(),
                ContentTypeEnum.BRAND.getType(),
                strategies.stream().filter(s -> s instanceof BrandHitCounterStrategy).findFirst().orElseThrow(),
                ContentTypeEnum.CONTENT.getType(),
                strategies.stream().filter(s -> s instanceof ContentHitCounterStrategy).findFirst().orElseThrow());
    }

    @Override
    public void onMessage(PageView pageView) {
        log.info("[PageView] message = {}", pageView);

        try {
            pageViewService.addPageView(pageView);

            HitCounterStrategy hitCounterStrategy = strategyMap.get(ContentTypeEnum.getType(pageView.getPageType()).getType());

            if (hitCounterStrategy == null)
                throw new IllegalArgumentException("Unsupported strategy");
            hitCounterStrategy.increase(pageView.getPageId());
        } catch (Exception e) {
            log.error("Error processing page view message", e);
        }
    }
}
