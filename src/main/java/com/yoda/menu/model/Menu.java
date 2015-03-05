package com.yoda.menu.model;

import java.util.Date;

import com.yoda.content.model.Content;
import com.yoda.item.model.Item;
import com.yoda.section.model.Section;

public class Menu implements java.io.Serializable {

	// Fields

	private long menuId;

	private long siteId;

	private String menuSetName;

	private String menuTitle;

	private String menuName;

	private int seqNum;

	private Long menuParentId;

	private String menuType;

	private String menuUrl;

	private String menuWindowTarget;

	private String menuWindowMode;

	private char published;

	private long recUpdateBy;

	private Date recUpdateDatetime;

	private long recCreateBy;

	private Date recCreateDatetime;

	private Section section;

	private Item item;

	private Content content;

//	// Constructors
//
//	/** default constructor */
//	public Menu() {
//	}
//
//	/** minimal constructor */
//	public Menu(
//			long siteId, String menuSetName, String menuName, int seqNum,
//			String menuType, String menuUrl, String menuWindowTarget,
//			String menuWindowMode, char published, long recUpdateBy,
//			Date recUpdateDatetime, long recCreateBy,
//			Date recCreateDatetime) {
//
//		this.siteId = siteId;
//		this.menuSetName = menuSetName;
//		this.menuName = menuName;
//		this.seqNum = seqNum;
//		this.menuType = menuType;
//		this.menuUrl = menuUrl;
//		this.menuWindowTarget = menuWindowTarget;
//		this.menuWindowMode = menuWindowMode;
//		this.published = published;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public Menu(
//			long siteId, String menuSetName, String menuName, int seqNum,
//			Long menuParentId, String menuType, String menuUrl,
//			String menuWindowTarget, String menuWindowMode, char published,
//			long recUpdateBy, Date recUpdateDatetime, long recCreateBy,
//			Date recCreateDatetime, Section section, Item item,
//			Content content) {
//
//		this.siteId = siteId;
//		this.menuSetName = menuSetName;
//		this.menuName = menuName;
//		this.seqNum = seqNum;
//		this.menuParentId = menuParentId;
//		this.menuType = menuType;
//		this.menuUrl = menuUrl;
//		this.menuWindowTarget = menuWindowTarget;
//		this.menuWindowMode = menuWindowMode;
//		this.published = published;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.section = section;
//		this.item = item;
//		this.content = content;
//	}

	// Property accessors
	public long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getMenuSetName() {
		return this.menuSetName;
	}

	public void setMenuSetName(String menuSetName) {
		this.menuSetName = menuSetName;
	}

	public String getMenuTitle() {
		return this.menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public Long getMenuParentId() {
		return this.menuParentId;
	}

	public void setMenuParentId(Long menuParentId) {
		this.menuParentId = menuParentId;
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

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Content getContent() {
		return this.content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

}
