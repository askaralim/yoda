package com.yoda.content.model;

import com.yoda.BaseEntity;

public class ContentUserRate extends BaseEntity {
	private Integer id;

	private Long contentId;

	private Integer score;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public boolean isNew() {
		return (this.id == null);
	}
}