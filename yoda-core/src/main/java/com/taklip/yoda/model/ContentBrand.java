package com.taklip.yoda.model;

public class ContentBrand extends BaseEntity {
	private Long id;

	private Long contentId;

	private Long brandId;

	private String description;

	private String brandName;

	private String brandLogo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Long getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Long brandId) {
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