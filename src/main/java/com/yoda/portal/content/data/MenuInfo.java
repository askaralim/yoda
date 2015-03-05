package com.yoda.portal.content.data;

import java.util.List;

public class MenuInfo {
	String menuTitle;
	String menuName;
	int seqNo;
	String menuAnchor;
	String menuWindowTarget;
	String menuWindowMode;
	String menuUrl;
	List<MenuInfo> menus;

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<MenuInfo> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuInfo> menus) {
		this.menus = menus;
	}

	public String getMenuAnchor() {
		return menuAnchor;
	}

	public void setMenuAnchor(String menuAnchor) {
		this.menuAnchor = menuAnchor;
	}

	public String getMenuWindowMode() {
		return menuWindowMode;
	}

	public void setMenuWindowMode(String menuWindowMode) {
		this.menuWindowMode = menuWindowMode;
	}

	public String getMenuWindowTarget() {
		return menuWindowTarget;
	}

	public void setMenuWindowTarget(String menuWindowTarget) {
		this.menuWindowTarget = menuWindowTarget;
	}

	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

}
