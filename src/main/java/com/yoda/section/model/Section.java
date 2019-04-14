package com.yoda.section.model;

import java.util.HashSet;
import java.util.Set;

import com.yoda.BaseEntity;
import com.yoda.content.model.Content;
import com.yoda.menu.model.Menu;

public class Section extends BaseEntity {
	private Integer sectionId;

	private int siteId;

	private String naturalKey;

	private String title;

	private int seqNum;

	private int parentId;

	private String shortTitle;

	private String description;

	private boolean published;

//	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//	@JoinColumn(name = "section_id")
	private Set<Menu> menus = new HashSet<Menu>();

//	private Set<Item> items = new HashSet<Item>(0);

//	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//	@JoinColumn(name = "section_id")
	private Set<Content> contents = new HashSet<Content>();

	public Integer getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public String getNaturalKey() {
		return naturalKey;
	}

	public void setNaturalKey(String naturalKey) {
		this.naturalKey = naturalKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

//	public Set<Item> getItems() {
//		return this.items;
//	}
//
//	public void setItems(Set<Item> items) {
//		this.items = items;
//	}

	public Set<Content> getContents() {
		return this.contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}

	public boolean isNew() {
		return (this.sectionId == null);
	}
}