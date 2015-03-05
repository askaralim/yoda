package com.yoda.content;

public class ContentMenuDisplayCommand {
	Long menuId;

	String menuLongDesc;

	String menuWindowMode;

	String menuWindowTarget;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuLongDesc() {
		return menuLongDesc;
	}

	public void setMenuLongDesc(String menuLongDesc) {
		this.menuLongDesc = menuLongDesc;
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
}
