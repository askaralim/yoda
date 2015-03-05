package com.yoda.menu;

public class MenuDisplayCommand {
	boolean remove;
	long menuId;
	String menuName;
	String seqNum;
	String menuUrlOrContent;
	String menuWindowTarget;
	String menuWindowMode;
	String published;

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getMenuUrlOrContent() {
		return menuUrlOrContent;
	}

	public void setMenuUrlOrContent(String menuUrlOrContent) {
		this.menuUrlOrContent = menuUrlOrContent;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
