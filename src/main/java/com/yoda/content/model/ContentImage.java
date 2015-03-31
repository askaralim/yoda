package com.yoda.content.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yoda.BaseEntity;

@Entity
@Table(name = "content_image")
public class ContentImage extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_d")
	private Long imageId;

	@Column(name = "site_id")
	private int siteId;

	@Column(name = "seq_num")
	private int seqNum;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "image_value")
	private byte[] imageValue;

	@Column(name = "height")
	private int height;

	@Column(name = "width")
	private int width;

	@Column(name = "update_by")
	private Long updateBy;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "create_by")
	private Long createBy;

	@Column(name = "create_date")
	private Date createDate;

//	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JoinColumn(name = "content_id")
//	private Content content;

	public Long getImageId() {
		return this.imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
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