package com.yoda.section.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.yoda.BaseEntity;
import com.yoda.content.model.Content;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;

public class Section extends BaseEntity {

	private long sectionId;

	private long siteId;

	private String sectionNaturalKey;

	private String sectionTitle;

	private int seqNum;

	private long sectionParentId;

	private String sectionShortTitle;

	private String sectionDesc;

	private char published;

	private long recUpdateBy;

	private Date recUpdateDatetime;

	private long recCreateBy;

	private Date recCreateDatetime;

	private Set<Menu> menus = new HashSet<Menu>(0);

	private Set<Item> items = new HashSet<Item>(0);

	private Set<Content> contents = new HashSet<Content>(0);

//	public Section() {
//	}
//
//	public Section(
//			long siteId, String sectionNaturalKey,
//			String sectionTitle, int seqNum, String sectionShortTitle,
//			String sectionDesc, char published, String recUpdateBy,
//			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.sectionNaturalKey = sectionNaturalKey;
//		this.sectionTitle = sectionTitle;
//		this.seqNum = seqNum;
//		this.sectionShortTitle = sectionShortTitle;
//		this.sectionDesc = sectionDesc;
//		this.published = published;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public Section(
//			long siteId, String sectionNaturalKey,
//			String sectionTitle, int seqNum, long sectionParentId,
//			String sectionShortTitle, String sectionDesc, char published,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime, Set<Menu> menus, Set<Item> items,
//			Set<Content> contents) {
//		this.siteId = siteId;
//		this.sectionNaturalKey = sectionNaturalKey;
//		this.sectionTitle = sectionTitle;
//		this.seqNum = seqNum;
//		this.sectionParentId = sectionParentId;
//		this.sectionShortTitle = sectionShortTitle;
//		this.sectionDesc = sectionDesc;
//		this.published = published;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.menus = menus;
//		this.items = items;
//		this.contents = contents;
//	}

	public long getSectionId() {
		return this.sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getSectionNaturalKey() {
		return this.sectionNaturalKey;
	}

	public void setSectionNaturalKey(String sectionNaturalKey) {
		this.sectionNaturalKey = sectionNaturalKey;
	}

	public String getSectionTitle() {
		return this.sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public long getSectionParentId() {
		return this.sectionParentId;
	}

	public void setSectionParentId(long sectionParentId) {
		this.sectionParentId = sectionParentId;
	}

	public String getSectionShortTitle() {
		return this.sectionShortTitle;
	}

	public void setSectionShortTitle(String sectionShortTitle) {
		this.sectionShortTitle = sectionShortTitle;
	}

	public String getSectionDesc() {
		return this.sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public char getPublished() {
		return this.published;
	}

	public void setPublished(char published) {
		this.published = published;
	}

	public long getRecUpdateBy() {
		return this.recUpdateBy;
	}

	public void setRecUpdateBy(long recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public Date getRecUpdateDatetime() {
		return this.recUpdateDatetime;
	}

	public void setRecUpdateDatetime(Date recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public long getRecCreateBy() {
		return this.recCreateBy;
	}

	public void setRecCreateBy(long recCreateBy) {
		this.recCreateBy = recCreateBy;
	}

	public Date getRecCreateDatetime() {
		return this.recCreateDatetime;
	}

	public void setRecCreateDatetime(Date recCreateDatetime) {
		this.recCreateDatetime = recCreateDatetime;
	}

	public Set<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Set<Content> getContents() {
		return this.contents;
	}

	public void setContents(Set<Content> contents) {
		this.contents = contents;
	}
}