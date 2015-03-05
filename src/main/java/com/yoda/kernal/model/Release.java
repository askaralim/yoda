package com.yoda.kernal.model;

import java.util.Date;

public class Release {
	private Long releaseId;

	private Date createDate;

	private Date modifiedDate;

	private Integer buildNumber;

	private Boolean verified;

	public Long getReleaseId() {
		return releaseId;
	}

	public void setReleaseId(Long releaseId) {
		this.releaseId = releaseId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(Integer buildNumber) {
		this.buildNumber = buildNumber;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
}