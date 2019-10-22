package com.taklip.yoda.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.taklip.yoda.util.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;

public class Content extends BaseEntity {
	private Long id;

	private Boolean featureData = false;

	private Boolean published = true;

	private Boolean homePage = true;

	private Category category;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date publishDate = new Date();

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date expireDate = DateUtil.getHighDate();

	private Integer hitCounter = 0;

	private Integer score = 0;

	private Integer siteId;

	private String naturalKey;

	@NotEmpty(message = "content-title-required")
	private String title;

	private String shortDescription;

	private String description;

	private String pageTitle;

	private String featuredImage;

	private List<ContentContributor> contentContributors = new ArrayList<>();

	private List<ContentBrand> contentBrands = new ArrayList<>();

	private List<Item> items = new ArrayList<>();

	private List<Menu> menus = new ArrayList<>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSiteId() {
		return this.siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getNaturalKey() {
		return this.naturalKey;
	}

	public void setNaturalKey(String naturalKey) {
		this.naturalKey = naturalKey;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShortDescription() {
		return this.shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}

	public Integer getHitCounter() {
		return this.hitCounter;
	}

	public void setHitCounter(Integer hitCounter) {
		this.hitCounter = hitCounter;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Boolean isPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(Boolean homePage) {
		this.homePage = homePage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<ContentContributor> getContentContributors() {
		return contentContributors;
	}

	public void setContentContributors(List<ContentContributor> contentContributors) {
		this.contentContributors = contentContributors;
	}

	public List<ContentBrand> getContentBrands() {
		return contentBrands;
	}

	public void setContentBrands(List<ContentBrand> contentBrands) {
		this.contentBrands = contentBrands;
	}

	public void addContentBrand(ContentBrand contentBrand) {
		getContentBrands().add(contentBrand);

//		contentBrand.setContent(this);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		getItems().add(item);
	}

	public Boolean isNew() {
		return (this.id == null);
	}

	public Boolean isFeatureData() {
		return featureData;
	}

	public void setFeatureData(Boolean featureData) {
		this.featureData = featureData;
	}
}