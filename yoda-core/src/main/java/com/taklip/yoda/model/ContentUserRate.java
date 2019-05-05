package com.taklip.yoda.model;

public class ContentUserRate extends BaseEntity {
	private Long id;

	private Long contentId;

	private Integer score;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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