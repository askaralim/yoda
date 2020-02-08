package com.taklip.yoda.service.impl;

import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.properties.WXProperties;
import com.taklip.yoda.service.ChatterService;
import com.taklip.yoda.service.WXMessageService;
import com.taklip.yoda.tool.SHA1;
import com.taklip.yoda.util.WXMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class WXMessageServiceImpl implements WXMessageService {
	private static Logger logger = LoggerFactory.getLogger(WXMessageServiceImpl.class);

	protected final WXProperties properties;

	@Autowired
	ChatterService chatterService;

	@Autowired
	public WXMessageServiceImpl(WXProperties properties) {
		this.properties = properties;
	}

	@Override
	public String process(HttpServletRequest request) {
		try {
			Map<String, String> map = WXMessageUtil.xmlToMap(request);

			String msgType = map.get("MsgType");

			if (msgType.equals(WXMessageUtil.MESSAGE_TYPE_TEXT)) {
				return processTextMessage(map);
			} else if (msgType.equals(WXMessageUtil.MESSAGE_TYPE_EVENT)) {
				return processEventMessage(map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	public boolean checkSignature(String signature, String timestamp, String nonce) {
		List<String> params = new ArrayList<>();
		params.add(properties.getToken());
		params.add(timestamp);
		params.add(nonce);

		Collections.sort(params, String::compareTo);

		String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));

		return temp.equals(signature);
	}

	private String processTextMessage(Map<String, String> map) {
		Map<String, String> replyMap = new HashMap<>();

		replyMap.put("ToUserName", map.get("FromUserName"));
		replyMap.put("FromUserName", map.get("ToUserName"));
		replyMap.put("CreateTime", String.valueOf(new Date().getTime()));
		replyMap.put("MsgType", WXMessageUtil.MESSAGE_TYPE_TEXT);

		ChatResponse chatResponse = chatterService.getAnswer(map.get("Content"));

		replyMap.put("Content", chatResponse.getText());

		return WXMessageUtil.transferMapToXML(replyMap);
	}

	private String processEventMessage(Map<String, String> map) {
		String response = null;
		Map<String, String> replyMap = new HashMap<>();

		String eventType = map.get("Event");

		replyMap.put("ToUserName", map.get("FromUserName"));
		replyMap.put("FromUserName", map.get("ToUserName"));
		replyMap.put("CreateTime", String.valueOf(new Date().getTime()));
		replyMap.put("MsgType", WXMessageUtil.MESSAGE_TYPE_TEXT);

		if (eventType.equals(WXMessageUtil.EVENT_TYPE_SUBSCRIBE)) {
			replyMap.put("MsgType", WXMessageUtil.MESSAGE_TYPE_TEXT);
			replyMap.put("Content", "欢迎关注 taklip，任何问题都可以直接回复信息，会尽量回答。");

			response = WXMessageUtil.transferMapToXML(replyMap);
		} else if (eventType.equals(WXMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

		} else if (eventType.equals(WXMessageUtil.EVENT_TYPE_SCAN)) {

		}

		return response;
	}
}
