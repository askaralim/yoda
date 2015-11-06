package com.yoda.item.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yoda.BaseEntity;
import com.yoda.brand.model.Brand;

@Entity
@Table(name = "item")
public class Item extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "category_id")
	private Integer categoryId;

	//	@ManyToOne
//	@JoinColumn(name = "content_id")
	@Column(name = "content_id")
	private Long contentId;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

//	@Column(name = "update_date")
//	private Date updateDate;
//
//	@Column(name = "create_date")
//	private Date createDate;

//	private int hitCounter;

	@Column(name = "rating")
	private int rating;

//	@Column(name = "create_by")
//	private Integer createBy;

	@Column(name = "site_id")
	private Integer siteId;

//	@Column(name = "update_by")
//	private Integer updateBy;

//	@Column(name = "brand")
//	private String brand;

	@Column(name = "description")
	private String description;

	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "level")
	private String level;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
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

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
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