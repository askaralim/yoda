package com.taklip.yoda.model;

import com.taklip.yoda.common.util.WXMessageUtil;

public class WXEventMessage extends WXBaseMessage {
    private String Event;

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    @Override
    public String getMsgType() {
        return WXMessageUtil.MESSAGE_TYPE_EVENT;
    }
}
