package com.taklip.yoda.service;

import jakarta.servlet.http.HttpServletRequest;

public interface WXMessageService {
	boolean checkSignature(String signature, String timestamp, String nonce);
	String process(HttpServletRequest request);
}
