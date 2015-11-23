package com.yoda.poll.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.yoda.BaseEntity;

public class PollHeader extends BaseEntity {
	private Long pollHeaderId;

	private String siteId;

	private String pollTopic;

	private Date pollPublishOn;

	private Date pollExpireOn;

	private char published;

	private String recUpdateBy;

	private Date recUpdateDatetime;

	private String recCreateBy;

	private Date recCreateDatetime;

//	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//	@JoinColumn(name = "poll_header_id")
	private Set<PollDetail> pollDetails = new HashSet<PollDetail>(0);

	// Property accessors
	public Long getPollHeaderId() {
		return this.pollHeaderId;
	}

	public void setPollHeaderId(Long pollHeaderId) {
		this.pollHeaderId = pollHeaderId;
	}

	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getPollTopic() {
		return this.pollTopic;
	}

	public void setPollTopic(String pollTopic) {
		this.pollTopic = pollTopic;
	}

	public Date getPollPublishOn() {
		return this.pollPublishOn;
	}

	public void setPollPublishOn(Date pollPublishOn) {
		this.pollPublishOn = pollPublishOn;
	}

	public Date getPollExpireOn() {
		return this.pollExpireOn;
	}

	public void setPollExpireOn(Date pollExpireOn) {
		this.pollExpireOn = pollExpireOn;
	}

	public char getPublished() {
		return this.published;
	}

	public void setPublished(char published) {
		this.published = published;
	}

	public String getRecUpdateBy() {
		return this.recUpdateBy;
	}

	public void setRecUpdateBy(String recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public Date getRecUpdateDatetime() {
		return this.recUpdateDatetime;
	}

	public void setRecUpdateDatetime(Date recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public String getRecCreateBy() {
		return this.recCreateBy;
	}

	public void setRecCreateBy(String recCreateBy) {
		this.recCreateBy = recCreateBy;
	}

	public Date getRecCreateDatetime() {
		return this.recCreateDatetime;
	}

	public void setRecCreateDatetime(Date recCreateDatetime) {
		this.recCreateDatetime = recCreateDatetime;
	}

	public Set<PollDetail> getPollDetails() {
		return this.pollDetails;
	}

	public void setPollDetails(Set<PollDetail> pollDetails) {
		this.pollDetails = pollDetails;
	}

}
