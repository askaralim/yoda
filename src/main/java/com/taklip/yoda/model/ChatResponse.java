package com.taklip.yoda.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatResponse {
    private String id;
    private String text;
    @JsonProperty(value = "search_text")
    private String searchText;
    @JsonProperty(value = "created_at")
    private Date createdAt;
    private String conversation;
    @JsonProperty(value = "in_response_to")
    private String inResponseTo;
    @JsonProperty(value = "search_in_response_to")
    private String searchInResponseTo;
    private String persona;
    private List<String> tags;
}