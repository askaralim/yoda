package com.yoda.content.model;

import com.yoda.BaseEntity;

public class ContentContributor extends BaseEntity {
	private Long id;

	private Long contentId;

	private Long userId;

	private String username;

	private String profilePhotoSmall;

	private String version;

	private boolean approved;

	public Long getId() {
		return id;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePhotoSmall() {
		return profilePhotoSmall;
	}

	public void setProfilePhotoSmall(String profilePhotoSmall) {
		this.profilePhotoSmall = profilePhotoSmall;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
}