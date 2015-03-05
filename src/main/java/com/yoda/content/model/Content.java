package com.yoda.content.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.yoda.BaseEntity;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;
import com.yoda.section.model.Section;

public class Content extends BaseEntity {
	private Long contentId;

	private boolean published;

	private ContentImage image;

	private Date publishDate;

	private Date expireDate;

	private Date updateDate;

	private Date createDate;

	private Long hitCounter;

	private Long score;

	private Long siteId;

	private Long updateBy;

	private Long createBy;

	private Long sectionId;

	private Section section;

	private Set<Item> items = new HashSet<Item>();

	private Set<Menu> menus = new HashSet<Menu>(0);

	private String naturalKey;

	private String title;

	private String shortDescription;

	private String description;

	private String pageTitle;

	private String featuredImage;

	public Long getContentId() {
		return this.contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
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

	public Long getHitCounter() {
		return this.hitCounter;
	}

	public void setHitCounter(Long hitCounter) {
		this.hitCounter = hitCounter;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
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

	public long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public ContentImage getImage() {
		return this.image;
	}

	public void setImage(ContentImage image) {
		this.image = image;
	}

	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public void addItem(Item item) {
		getItems().add(item);

		item.setContent(this);
	}
}