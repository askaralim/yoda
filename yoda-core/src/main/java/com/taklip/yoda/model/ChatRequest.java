package com.taklip.yoda.model;

public class ChatRequest {
	private String text;

	public ChatRequest(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}