package com.yoda.content.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yoda.BaseEntity;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;
import com.yoda.section.model.Section;

@Entity
@Table(name = "content")
public class Content extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "published")
	private boolean published;

	@Column(name = "publish_date")
	private Date publishDate;

	@Column(name = "expire_date")
	private Date expireDate;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "hit_counter")
	private int hitCounter;

	@Column(name = "score")
	private int score;

	@Column(name = "site_id")
	private int siteId;

	@Column(name = "update_by")
	private long updateBy;

	@Column(name = "create_by")
	private long createBy;

//	@Column(name = "section_id")
//	private int sectionId;

	@Column(name = "natural_key")
	private String naturalKey;

	@Column(name = "title")
	private String title;

	@Column(name = "short_description")
	private String shortDescription;

	@Column(name = "description")
	private String description;

	@Column(name = "page_title")
	private String pageTitle;

	@Column(name = "featured_image")
	private String featuredImage;

	@ManyToOne
	@JoinColumn(name = "section_id")
	private Section section;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "content_id")
	private Set<Item> items = new HashSet<Item>();

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "content_id")
	private Set<Menu> menus = new HashSet<Menu>();

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

//	public int getSectionId() {
//		return this.sectionId;
//	}
//
//	public void setSectionId(int sectionId) {
//		this.sectionId = sectionId;
//	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
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