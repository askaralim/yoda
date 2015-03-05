package com.yoda.content.model;

import java.util.Date;

import com.yoda.BaseEntity;

public class ContentImage extends BaseEntity {

	private Long imageId;

	private Long siteId;

	private int seqNum;

	private String contentType;

	private String imageName;

	private byte[] imageValue;

	private int height;

	private int width;

	private Long updateBy;

	private Date updateDate;

	private Long createBy;

	private Date createDate;

//	private Content content;

	// Constructors

	/** default constructor */
	public ContentImage() {
	}

//	/** minimal constructor */
//	public ContentImage(
//			long siteId, int seqNum, String contentType, String imageName,
//			byte[] imageValue, int imageHeight, int imageWidth,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.seqNum = seqNum;
//		this.contentType = contentType;
//		this.imageName = imageName;
//		this.imageValue = imageValue;
//		this.imageHeight = imageHeight;
//		this.imageWidth = imageWidth;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public ContentImage(
//			long siteId, int seqNum, String contentType, String imageName,
//			byte[] imageValue, int imageHeight, int imageWidth,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime, Content content) {
//		this.siteId = siteId;
//		this.seqNum = seqNum;
//		this.contentType = contentType;
//		this.imageName = imageName;
//		this.imageValue = imageValue;
//		this.imageHeight = imageHeight;
//		this.imageWidth = imageWidth;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.content = content;
//	}

	// Property accessors
	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
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

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getImageName() {
		return this.imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public byte[] getImageValue() {
		return this.imageValue;
	}

	public void setImageValue(byte[] imageValue) {
		this.imageValue = imageValue;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
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

	public void setCreateDate(Date createBy) {
		this.createDate = createBy;
	}

//	public Content getContent() {
//		return this.content;
//	}
//
//	public void setContent(Content content) {
//		this.content = content;
//	}
}