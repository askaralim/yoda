package com.taklip.yoda.support;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.properties.YodaProperties;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.PortalUtil;

@Service
public class PageViewHandler {
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected BrandService brandService;

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@Autowired
	PageViewService pageViewService;
	
	@Autowired
	RedisService redisService;

	@Autowired
	protected KafkaTemplate<String, String> kafkaTemplate;

	protected final YodaProperties properties;

	@Autowired
	public PageViewHandler(YodaProperties properties) {
		this.properties = properties;
	}

	public void add(
			HttpServletRequest request, String pageType, String pageName,
			Long pageId) {
		String ip = PortalUtil.getClientIP(request);

		PageViewData pageView = new PageViewData();

		pageView.setCreateTime(DateUtil.getFullDatetime(new Date()));
		pageView.setPageId(pageId);
		pageView.setPageName(pageName);
		pageView.setPageType(ContentTypeEnum.getCode(pageType));
		pageView.setPageUrl(request.getRequestURL().toString());
		pageView.setUserIPAddress(ip);

		User user = AuthenticatedUtil.getAuthenticatedUser();

		if (null != user) {
			pageView.setUserId(user.getId());
			pageView.setUsername(user.getUsername());
		}

		if (properties.getKafkaEnabled()) {
			kafkaTemplate.send(Constants.KAFKA_TOPIC_PAGE_VIEW, pageView.toString());
		}
		else {
			pageViewService.addPageViewData(pageView);

			if (pageType.equals(ContentTypeEnum.ITEM.getType())) {
				itemService.increaseItemHitCounter(pageId);
			}
			else if (pageType.equals(ContentTypeEnum.BRAND.getType())) {
				brandService.increaseBrandHitCounter(pageId);
			}
			else if (pageType.equals(ContentTypeEnum.CONTENT.getType())) {
				contentService.increaseContentHitCounter(pageId);
			}
		}
	}
}