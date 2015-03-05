package com.yoda.homepage.model;

import java.util.Date;

import com.yoda.BaseEntity;
import com.yoda.content.model.Content;

public class HomePage extends BaseEntity {

	private Long homePageId;

	private Long siteId;

	private int seqNum;

	private Character featureData;

	private Long updateBy;

	private Date updateDate;

	private Long createBy;

	private Date createDate;

//	private Item item;

	private Content content;

	public HomePage() {
	}

//	public HomePage(long siteId, int seqNum, char featureData,
//			long recUpdateBy, Date recUpdateDatetime, long recCreateBy,
//			Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.seqNum = seqNum;
//		this.featureData = featureData;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public HomePage(long siteId, int seqNum, char featureData,
//			long recUpdateBy, Date recUpdateDatetime, long recCreateBy,
//			Date recCreateDatetime, Item item, Content content) {
//		this.siteId = siteId;
//		this.seqNum = seqNum;
//		this.featureData = featureData;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.item = item;
//		this.content = content;
//	}

	public Long getHomePageId() {
		return this.homePageId;
	}

	public void setHomePageId(Long homePageId) {
		this.homePageId = homePageId;
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public char getFeatureData() {
		return this.featureData;
	}

	public void setFeatureData(char featureData) {
		this.featureData = featureData;
	}

	public Long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

//	public Item getItem() {
//		return this.item;
//	}
//
//	public void setItem(Item item) {
//		this.item = item;
//	}

	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}