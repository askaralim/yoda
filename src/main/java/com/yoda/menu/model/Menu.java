package com.yoda.menu.model;

import com.yoda.BaseEntity;
import com.yoda.content.model.Content;
import com.yoda.section.model.Section;

public class Menu extends BaseEntity {
	private int menuId;

	private int siteId;

	private String setName;

	private String title;

	private String name;

	private int seqNum;

	private int parentId;

	private String menuType;

	private String menuUrl;

	private String menuWindowTarget;

	private String menuWindowMode;

	private boolean published;

	private Section section;

//	private Item item;

	private Content content;

	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getSiteId() {
		return this.siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSetName() {
		return this.setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuWindowTarget() {
		return this.menuWindowTarget;
	}

	public void setMenuWindowTarget(String menuWindowTarget) {
		this.menuWindowTarget = menuWindowTarget;
	}

	public String getMenuWindowMode() {
		return this.menuWindowMode;
	}

	public void setMenuWindowMode(String menuWindowMode) {
		this.menuWindowMode = menuWindowMode;
	}

	public boolean isPublished() {
		return this.published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

//	public Item getItem() {
//		return this.item;
//	}
//
//	public void setItem(Item item) {
//		this.item = item;
//	}

	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

}