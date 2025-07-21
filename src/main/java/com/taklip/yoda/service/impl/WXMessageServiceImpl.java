package com.taklip.yoda.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.tools.SHA1;
import com.taklip.yoda.common.util.WXMessageUtil;
import com.taklip.yoda.model.ChatQuestion;
import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.properties.WXProperties;
import com.taklip.yoda.service.ChatQuestionService;
import com.taklip.yoda.service.ChatterService;
import com.taklip.yoda.service.WXMessageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class WXMessageServiceImpl implements WXMessageService {
    private final WXProperties properties;

    private ChatterService chatterService;

    private ChatQuestionService chatQuestionService;

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
            log.error(e.getMessage());
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

        LocalDateTime createTime = LocalDateTime.now();
        String text = map.get("Content");
        String openId = map.get("FromUserName");

        replyMap.put("ToUserName", openId);
        replyMap.put("FromUserName", map.get("ToUserName"));
        replyMap.put("CreateTime", String.valueOf(createTime.toEpochSecond(ZoneOffset.UTC)));
        replyMap.put("MsgType", WXMessageUtil.MESSAGE_TYPE_TEXT);

        ChatResponse chatResponse = chatterService.getAnswer(text);

        String answer = chatResponse.getText();

        answer = answer.replaceAll(Constants.NEWLINE, "\n");

        ChatQuestion chatQuestion = new ChatQuestion();

        chatQuestion.setCreateTime(createTime);
        chatQuestion.setAnswer(answer);
        chatQuestion.setOpenId(openId);
        chatQuestion.setPersona(chatResponse.getPersona());
        chatQuestion.setQuestion(chatResponse.getInResponseTo());
        chatQuestion.setUnionId(StringUtils.EMPTY);

        chatQuestionService.addChatQuestion(chatQuestion);

        replyMap.put("Content", answer);

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
            replyMap.put("Content",
                    "hello，欢迎关注「taklip太离谱」！\n如果有想了解的问题，可以直接在输入框发送信息，如果小助手无法回答就会去联系管事儿的。\n\n「taklip太离谱」还有个交流群，用于分享交流，有意加入可以添加微信：asikar\n Cheers!");

            response = WXMessageUtil.transferMapToXML(replyMap);
        } else if (eventType.equals(WXMessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

        } else if (eventType.equals(WXMessageUtil.EVENT_TYPE_SCAN)) {

        }

        return response;
    }
}
