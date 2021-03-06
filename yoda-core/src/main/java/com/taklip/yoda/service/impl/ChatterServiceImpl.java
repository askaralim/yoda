package com.taklip.yoda.service.impl;

import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.properties.ChatterProperties;
import com.taklip.yoda.service.ChatterBotApi;
import com.taklip.yoda.service.ChatterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
public class ChatterServiceImpl implements ChatterService {
	private static Logger logger = LoggerFactory.getLogger(ChatterServiceImpl.class);

	protected final ChatterProperties properties;

	private ChatterBotApi api;

	@Autowired
	public ChatterServiceImpl(ChatterProperties properties) {
		this.properties = properties;

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(properties.getBaseUrl())
				.addConverterFactory(JacksonConverterFactory.create())
				.build();

		api = retrofit.create(ChatterBotApi.class);
	}

	@Override
	public ChatResponse getAnswer(String text) {
		ChatResponse chatResponse;

		try {
			Call<ChatResponse> chatResponseCall = api.getAnswer(text);

			Response<ChatResponse> response = chatResponseCall.execute();

			if (response.isSuccessful()) {
				chatResponse = response.body();

				return chatResponse;
			} else {
				logger.error(response.message());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return null;
	}

	@Override
	public boolean train() {
		try {
			Response<com.taklip.yoda.model.Response> response = api.train().execute();

			if (response.isSuccessful()) {
				com.taklip.yoda.model.Response trainResponse = response.body();

				if (trainResponse.getMessage().equalsIgnoreCase("success")) {
					return true;
				}
			} else {
				logger.error(response.message());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return false;
	}
}
