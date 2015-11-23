package com.yoda.brand.model;

import com.yoda.BaseEntity;

public class Brand extends BaseEntity {
	private Integer brandId;

	private String country;

	private String description;

	private String kind;

	private String imagePath;

	private String name;

	private int score;

//	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
//	@JoinTable(name = "brand_category", joinColumns = {@JoinColumn(name ="brand_id" )}, inverseJoinColumns = {@JoinColumn(name = "category_id") })
//	private Set<Category> categories = new HashSet<Category>();

	public Integer getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

//	public Set<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}

	public boolean isNew() {
		return (this.brandId == null);
	}
}