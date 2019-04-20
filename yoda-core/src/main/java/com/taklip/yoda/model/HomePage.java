package com.taklip.yoda.model;

public class HomePage extends BaseEntity {
	private Integer homePageId;

	private int siteId;

	private int seqNum;

	private boolean featureData;

	private Content content;

	public Integer getHomePageId() {
		return this.homePageId;
	}

	public void setHomePageId(Integer homePageId) {
		this.homePageId = homePageId;
	}

	public int getSiteId() {
		return this.siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public boolean getFeatureData() {
		return this.featureData;
	}

	public void setFeatureData(boolean featureData) {
		this.featureData = featureData;
	}

//	public Long getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(Long updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public Date getUpdateDate() {
//		return this.updateDate;
//	}
//
//	public void setUpdateDate(Date updateDate) {
//		this.updateDate = updateDate;
//	}
//
//	public Long getCreateBy() {
//		return this.createBy;
//	}
//
//	public void setCreateBy(Long createBy) {
//		this.createBy = createBy;
//	}
//
//	public Date getCreateDate() {
//		return this.createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}

	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public boolean isNew() {
		return (this.homePageId == null);
	}
}