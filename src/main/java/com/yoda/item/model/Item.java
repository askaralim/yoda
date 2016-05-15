package com.yoda.item.model;

import com.yoda.BaseEntity;
import com.yoda.brand.model.Brand;

public class Item extends BaseEntity {
	private Integer id;

	private Integer categoryId;

	private Long contentId;

	private Brand brand;

//	private int hitCounter;

	private int rating;

	private int siteId;

	private String description;

	private String imagePath;

	private String level;

	private String name;

	private int price;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public int getSiteId() {
		return this.siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

//	public int getItemHitCounter() {
//		return this.hitCounter;
//	}
//
//	public void setItemHitCounter(Integer itemHitCounter) {
//		this.hitCounter = itemHitCounter;
//	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

//	public Integer getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(Integer updateBy) {
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
//	public Integer getCreateBy() {
//		return this.createBy;
//	}
//
//	public void setCreateBy(Integer recCreateBy) {
//		this.createBy = recCreateBy;
//	}
//
//	public Date getCreateDate() {
//		return this.createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}

	public boolean isNew() {
		return (this.id == null);
	}
}