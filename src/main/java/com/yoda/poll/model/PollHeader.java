package com.yoda.poll.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yoda.BaseEntity;

@Entity
@Table(name = "poll_header")
public class PollHeader extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "poll_header_id")
	private Long pollHeaderId;

	@Column(name = "site_id")
	private String siteId;

	@Column(name = "poll_topic")
	private String pollTopic;

	@Column(name = "poll_publish_on")
	private Date pollPublishOn;

	@Column(name = "poll_expire_on")
	private Date pollExpireOn;

	@Column(name = "published")
	private char published;

	@Column(name = "rec_update_by")
	private String recUpdateBy;

	@Column(name = "rec_update_datetime")
	private Date recUpdateDatetime;

	@Column(name = "rec_create_by")
	private String recCreateBy;

	@Column(name = "rec_create_datetime")
	private Date recCreateDatetime;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "poll_header_id")
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
