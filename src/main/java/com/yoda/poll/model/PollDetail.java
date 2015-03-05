package com.yoda.poll.model;

import java.util.Date;

public class PollDetail implements java.io.Serializable {
	private Long pollDetailId;

	private String siteId;

	private String pollOption;

	private Integer seqNum;

	private Integer pollVoteCount;

	private String recUpdateBy;

	private Date recUpdateDatetime;

	private String recCreateBy;

	private Date recCreateDatetime;

	public PollDetail() {
	}

//	/** full constructor */
//	public PollDetail(String siteId, String pollOption, Integer seqNum,
//			Integer pollVoteCount, String recUpdateBy, Date recUpdateDatetime,
//			String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.pollOption = pollOption;
//		this.seqNum = seqNum;
//		this.pollVoteCount = pollVoteCount;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}

	// Property accessors
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
