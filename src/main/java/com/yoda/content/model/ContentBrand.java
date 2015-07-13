package com.yoda.content.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yoda.BaseEntity;

@Entity
@Table(name = "content_brand")
public class ContentBrand extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_brand_id")
	private Long contentBrandId;

	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "brand_id")
	private Integer brandId;

	@Column(name = "description")
	private String description;

	@Column(name = "brand_name")
	private String brandName;

	public Long getContentBrandId() {
		return contentBrandId;
	}

	public void setContentBrandId(Long contentBrandId) {
		this.contentBrandId = contentBrandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public boolean isNew() {
		return (this.brandId == null);
	}
}