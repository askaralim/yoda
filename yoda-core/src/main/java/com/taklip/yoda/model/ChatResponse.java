package com.taklip.yoda.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

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
	private List tags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getConversation() {
		return conversation;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}

	public String getInResponseTo() {
		return inResponseTo;
	}

	public void setInResponseTo(String inResponseTo) {
		this.inResponseTo = inResponseTo;
	}

	public String getSearchInResponseTo() {
		return searchInResponseTo;
	}

	public void setSearchInResponseTo(String searchInResponseTo) {
		this.searchInResponseTo = searchInResponseTo;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public List getTags() {
		return tags;
	}

	public void setTags(List tags) {
		this.tags = tags;
	}
}