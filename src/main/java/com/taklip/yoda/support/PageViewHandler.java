package com.taklip.yoda.support;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.model.User;
import com.taklip.yoda.properties.YodaProperties;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PageViewHandler {
    @Autowired
    private BrandService brandService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PageViewService pageViewService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private YodaProperties properties;

    public void add(
            HttpServletRequest request, String pageType, String pageName,
            Long pageId) {
        String ip = PortalUtil.getClientIP(request);

        PageView pageView = new PageView();

        pageView.setPageId(pageId);
        pageView.setPageName(pageName);
        pageView.setPageType(ContentTypeEnum.getCode(pageType));
        pageView.setPageUrl(request.getRequestURL().toString());
        pageView.setUserIpAddress(ip);

        User user = AuthenticatedUtil.getAuthenticatedUser();

        if (null != user) {
            pageView.setUserId(user.getId());
            pageView.setUsername(user.getUsername());
        }

        if (properties.getMqEnabled()) {
            // Add debugging
            log.info("MQ enabled, rocketMQTemplate: {}", rocketMQTemplate);
            if (rocketMQTemplate != null) {
                try {
                    SendResult sendResult = rocketMQTemplate.syncSend(Constants.KAFKA_TOPIC_PAGE_VIEW, pageView);
                    log.info("sendResult: {}", sendResult);
                } catch (Exception e) {
                    log.error("Error sending message to RocketMQ: {}", e.getMessage(), e);
                }
            } else {
                log.error("RocketMQTemplate is null!");
            }
        }
        else {
            pageViewService.addPageView(pageView);

            if (pageType.equals(ContentTypeEnum.ITEM.getType())) {
                itemService.increaseItemHitCounter(pageId);
            }
            else if (pageType.equals(ContentTypeEnum.BRAND.getType())) {
                brandService.increaseBrandHitCounter(pageId);
            }
            else if (pageType.equals(ContentTypeEnum.CONTENT.getType())) {
                contentService.increaseHitCounter(pageId);
            }
        }
    }
}