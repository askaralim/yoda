package com.taklip.yoda.model;

public class ContentBrand extends BaseEntity {
	private Long contentBrandId;

	private Long contentId;

	private Integer brandId;

	private String description;

	private String brandName;

	private String brandLogo;

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

	public String getBrandLogo() {
		return brandLogo;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
}