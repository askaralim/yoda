package com.taklip.yoda.comsumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@RocketMQMessageListener(topic = Constants.KAFKA_TOPIC_PAGE_VIEW, consumerGroup = Constants.KAFKA_GROUP_ID_PAGE_VIEW)
@Slf4j
public class PageViewDataComsumer implements RocketMQListener<String> {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PageViewService pageViewService;

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

            if (pageTypeCode == ContentTypeEnum.ITEM.getCode()) {
                itemService.increaseItemHitCounter(pageId);
            } else if (pageTypeCode == ContentTypeEnum.BRAND.getCode()) {
                brandService.increaseBrandHitCounter(pageId);
            } else if (pageTypeCode == ContentTypeEnum.CONTENT.getCode()) {
                contentService.increaseContentHitCounter(pageId);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
    }
}
