package com.yoda.kernal.kafka.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.yoda.pageview.model.PageViewData;
import com.yoda.pageview.service.PageViewService;
import com.yoda.util.Constants;

@Configuration
public class PageViewDataListener {
	@Autowired
	PageViewService pageViewService;

	private static Logger logger = LoggerFactory.getLogger(PageViewDataListener.class);

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
			JSONObject obj = new JSONObject(data);

			PageViewData pageView = new PageViewData();

			SimpleDateFormat fullDatetimeformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			pageView.setCreateDate(fullDatetimeformat.parse(obj.getString("createTime")));
			pageView.setPageId(obj.getInt("pageId"));
			pageView.setPageName(obj.getString("pageName"));
			pageView.setPageType(obj.getInt("pageType"));
			pageView.setPageUrl(obj.getString("pageUrl"));
			pageView.setUserIPAddress(obj.getString("userIPAddress"));
			pageView.setUserId(obj.optLong("userId"));
			pageView.setUsername(obj.optString("username"));

			pageViewService.addPageViewData(pageView);
		}
		catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
