package com.yoda.content.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.yoda.BaseEntity;
import com.yoda.category.model.Category;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;

public class Content extends BaseEntity {
	private Long contentId;

	private boolean published;

	private boolean homePage;

	private Category category;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date publishDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expireDate;

	private int hitCounter;

	private int score;

	private int siteId;

//	private int sectionId;

	private String naturalKey;

	private String title;

	private String shortDescription;

	private String description;

	private String pageTitle;

	private String featuredImage;

	private String contentUrl;

	private boolean featureData;

//	private Section section;

	private List<ContentContributor> contentContributors = new ArrayList<ContentContributor>();

	private List<ContentBrand> contentBrands = new ArrayList<ContentBrand>();

	private List<Item> items = new ArrayList<Item>();

	private List<Menu> menus = new ArrayList<Menu>();

	public Long getContentId() {
		return this.contentId;
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

	public int getHitCounter() {
		return this.hitCounter;
	}

	public void setHitCounter(int hitCounter) {
		this.hitCounter = hitCounter;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
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

	public boolean isPublished() {
		return this.published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

//	public long getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(long updateBy) {
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
//	public long getCreateBy() {
//		return this.createBy;
//	}
//
//	public void setCreateBy(long createBy) {
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

//	public int getSectionId() {
//		return this.sectionId;
//	}
//
//	public void setSectionId(int sectionId) {
//		this.sectionId = sectionId;
//	}

//	public Section getSection() {
//		return this.section;
//	}
//
//	public void setSection(Section section) {
//		this.section = section;
//	}

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

	public boolean isNew() {
		return (this.contentId == null);
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public boolean isFeatureData() {
		return featureData;
	}

	public void setFeatureData(boolean featureData) {
		this.featureData = featureData;
	}
}