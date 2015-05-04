package com.yoda.poll.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yoda.BaseEntity;

@Entity
@Table(name = "poll_detail")
public class PollDetail extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "poll_detail_id")
	private Long pollDetailId;

	@Column(name = "site_id")
	private String siteId;

	@Column(name = "poll_option")
	private String pollOption;

	@Column(name = "seq_num")
	private Integer seqNum;

	@Column(name = "poll_vote_count")
	private Integer pollVoteCount;

	@Column(name = "rec_update_by")
	private String recUpdateBy;

	@Column(name = "rec_update_datetime")
	private Date recUpdateDatetime;

	@Column(name = "rec_create_by")
	private String recCreateBy;

	@Column(name = "rec_create_datetime")
	private Date recCreateDatetime;

	public Long getPollDetailId() {
		return this.pollDetailId;
	}

	public void setPollDetailId(Long pollDetailId) {
		this.pollDetailId = pollDetailId;
	}

	public String getSiteId() {
		return this.siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getPollOption() {
		return this.pollOption;
	}

	public void setPollOption(String pollOption) {
		this.pollOption = pollOption;
	}

	public Integer getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Integer getPollVoteCount() {
		return this.pollVoteCount;
	}

	public void setPollVoteCount(Integer pollVoteCount) {
		this.pollVoteCount = pollVoteCount;
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

}
