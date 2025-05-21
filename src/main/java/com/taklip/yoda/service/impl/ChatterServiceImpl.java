package com.taklip.yoda.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.taklip.yoda.model.ChatResponse;
import com.taklip.yoda.properties.ChatterProperties;
import com.taklip.yoda.service.ChatterBotApi;
import com.taklip.yoda.service.ChatterService;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addConverterFactory(GsonConverterFactory.create())
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
            Response<ResponseEntity<String>> response = api.train().execute();

            if (response.isSuccessful()) {
                ResponseEntity<String> trainResponse = response.body();

                if (trainResponse.getBody() != null && trainResponse.getBody().equalsIgnoreCase("success")) {
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
