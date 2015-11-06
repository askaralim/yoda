package com.yoda.homepage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yoda.BaseEntity;
import com.yoda.content.model.Content;

@Entity
@Table(name = "home_page")
public class HomePage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "home_page_id")
	private Integer homePageId;

//	@Column(name = "update_date")
//	private Date updateDate;
//
//	@Column(name = "create_date")
//	private Date createDate;
//
//	@Column(name = "create_by")
//	private long createBy;

	@Column(name = "site_id")
	private int siteId;

//	@Column(name = "update_by")
//	private long updateBy;

	@Column(name = "seq_num")
	private int seqNum;

	@Column(name = "feature_data")
	private boolean featureData;

//	private Item item;

//	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinColumn(name = "content_id")
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