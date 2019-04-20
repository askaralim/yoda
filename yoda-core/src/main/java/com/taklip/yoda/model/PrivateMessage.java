package com.taklip.yoda.model;

public class PrivateMessage extends BaseEntity {
	private Integer id;

	private Integer rootId;

	private User messageFrom;

	private User messageTo;

	private String description;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRootId() {
		return rootId;
	}

	public void setRootId(Integer rootId) {
		this.rootId = rootId;
	}

	public User getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(User messageFrom) {
		this.messageFrom = messageFrom;
	}

	public User getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(User messageTo) {
		this.messageTo = messageTo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNew() {
		return (this.id == null);
	}
}