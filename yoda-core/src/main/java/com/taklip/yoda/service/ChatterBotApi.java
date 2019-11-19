package com.taklip.yoda.service;

import com.taklip.yoda.model.ChatRequest;
import com.taklip.yoda.model.ChatResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ChatterBotApi {
	@POST("api/chatterbot/")
	Call<ChatResponse> getAnswer(
		@Body ChatRequest chatRequest
	);
}