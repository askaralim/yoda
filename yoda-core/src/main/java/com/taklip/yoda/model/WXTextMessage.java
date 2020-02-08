package com.taklip.yoda.model;

import com.taklip.yoda.util.WXMessageUtil;

public class WXTextMessage extends WXBaseMessage {
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String getMsgType() {
		return WXMessageUtil.MESSAGE_TYPE_TEXT;
	}
}
