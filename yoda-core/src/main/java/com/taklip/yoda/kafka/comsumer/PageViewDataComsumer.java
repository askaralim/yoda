package com.taklip.yoda.kafka.comsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.PageViewService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.util.DateUtil;

@Configuration
public class PageViewDataComsumer {
	@Autowired
	protected BrandService brandService;

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@Autowired
	PageViewService pageViewService;

	private static Logger logger = LoggerFactory.getLogger(PageViewDataComsumer.class);

	@KafkaListener(id = Constants.KAFKA_GROUP_ID_PAGE_VIEW, topics = {Constants.KAFKA_TOPIC_PAGE_VIEW})
	public void listen(ConsumerRecord<?, ?> record) {
		if (logger.isInfoEnabled()) {
			logger.info("[PageView] topic = " + record.topic() 
					+ " partition = " + record.partition() 
					+ " offset = " + record.offset() 
					+ " key = " + record.key() 
					+ " value = " + record.value());
		}

		String data = record.value().toString().substring(record.value().toString().indexOf("{"));

		try {
			JSONObject obj = JSON.parseObject(data);

			PageViewData pageView = new PageViewData();

			Integer pageTypeCode = obj.getInteger("pageType");
			Long pageId = obj.getLong("pageId");

			pageView.setCreateDate(DateUtil.getFullDatetime(obj.getString("createTime")));
			pageView.setPageId(pageId);
			pageView.setPageName(obj.getString("pageName"));
			pageView.setPageType(pageTypeCode);
			pageView.setPageUrl(obj.getString("pageUrl"));
			pageView.setUserIPAddress(obj.getString("userIPAddress"));
			pageView.setUserId(obj.getLong("userId"));
			pageView.setUsername(obj.getString("username"));

			pageViewService.addPageViewData(pageView);

			if (pageTypeCode == ContentTypeEnum.ITEM.getCode()) {
				itemService.increaseItemHitCounter(pageId);
			}
			else if (pageTypeCode == ContentTypeEnum.BRAND.getCode()) {
				brandService.increaseBrandHitCounter(pageId);
			}
			else if (pageTypeCode == ContentTypeEnum.CONTENT.getCode()) {
				contentService.increaseContentHitCounter(pageId);
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}
	}
}
