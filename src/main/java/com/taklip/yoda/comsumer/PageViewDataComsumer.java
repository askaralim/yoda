package com.taklip.yoda.comsumer;

import java.util.List;
import java.util.Map;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.DateUtil;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.strategy.BrandHitCounterStrategy;
import com.taklip.yoda.strategy.ContentHitCounterStrategy;
import com.taklip.yoda.strategy.HitCounterStrategy;
import com.taklip.yoda.strategy.ItemHitCounterStrategy;

import lombok.extern.slf4j.Slf4j;

@Component
@RocketMQMessageListener(topic = Constants.KAFKA_TOPIC_PAGE_VIEW, consumerGroup = Constants.KAFKA_GROUP_ID_PAGE_VIEW)
@Slf4j
public class PageViewDataComsumer implements RocketMQListener<String> {
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
    public void onMessage(String message) {
        log.info("[PageView] message = " + message);

        try {
            JSONObject obj = JSON.parseObject(message);

            PageView pageView = new PageView();

            Integer pageTypeCode = obj.getInteger("pageType");
            Long pageId = obj.getLong("pageId");

            pageView.setCreateTime(DateUtil.getLocalDateTime(obj.getString("createTime")));
            pageView.setPageId(pageId);
            pageView.setPageName(obj.getString("pageName"));
            pageView.setPageType(pageTypeCode);
            pageView.setPageUrl(obj.getString("pageUrl"));
            pageView.setUserIpAddress(obj.getString("userIpAddress"));
            pageView.setUserId(obj.getLong("userId"));
            pageView.setUsername(obj.getString("username"));

            pageViewService.addPageView(pageView);

            HitCounterStrategy hitCounterStrategy = strategyMap.get(ContentTypeEnum.getType(pageTypeCode).getType());

            if (hitCounterStrategy == null)
                throw new IllegalArgumentException("Unsupported strategy");
            hitCounterStrategy.increase(pageId);
        } catch (JSONException e) {
            log.error("", e);
        }
    }
}
