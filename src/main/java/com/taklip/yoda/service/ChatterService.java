package com.taklip.yoda.service;

import com.taklip.yoda.model.ChatResponse;

public interface ChatterService {
	ChatResponse getAnswer(String text);

	boolean train();
}
