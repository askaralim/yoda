package com.yoda.poll.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PollHeader implements java.io.Serializable {
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

	private Set<PollDetail> pollDetails = new HashSet<PollDetail>(0);

	public PollHeader() {
	}

//	/** minimal constructor */
//	public PollHeader(String siteId, String pollTopic, Date pollPublishOn,
//			Date pollExpireOn, char published, String recUpdateBy,
//			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.pollTopic = pollTopic;
//		this.pollPublishOn = pollPublishOn;
//		this.pollExpireOn = pollExpireOn;
//		this.published = published;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}

	/** full constructor */
	public PollHeader(String siteId, String pollTopic, Date pollPublishOn,
			Date pollExpireOn, char published, String recUpdateBy,
			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime,
			Set<PollDetail> pollDetails) {
		this.siteId = siteId;
		this.pollTopic = pollTopic;
		this.pollPublishOn = pollPublishOn;
		this.pollExpireOn = pollExpireOn;
		this.published = published;
		this.recUpdateBy = recUpdateBy;
		this.recUpdateDatetime = recUpdateDatetime;
		this.recCreateBy = recCreateBy;
		this.recCreateDatetime = recCreateDatetime;
		this.pollDetails = pollDetails;
	}

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
