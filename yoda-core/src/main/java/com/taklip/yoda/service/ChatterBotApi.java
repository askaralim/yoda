package com.taklip.yoda.service;

import com.taklip.yoda.model.ChatRequest;
import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.model.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatterBotApi {
	@POST("api/chatterbot/")
	Call<ChatResponse> getAnswer(
			@Body ChatRequest chatRequest
	);

	@GET("chat")
	Call<ChatResponse> getAnswer(
			@Query(value = "msg") String msg
	);

	@GET("train")
	Call<Response> train();
}