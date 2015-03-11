package com.yoda.menu;

import java.util.Enumeration;

import com.yoda.section.model.DropDownMenu;

public class MenuEditCommand {
	DropDownMenu menuList[];
	long menuId;
	long menuParentId;
	String menuTitle;
	String menuName;
	String seqNum;
	String menuUrl;
	String menuType;
	String menuWindowTarget;
	String menuWindowMode;
	String menuPosition;
	String published;
	String mode;
	String createMode;
	MenuDisplayCommand childrenMenus[];
	String menuSetName;
	String createMenuSetName;
	long removeMenuId;
	long createMenuId;
	boolean isSequence;
	int itemId;
	String itemNum;
	String itemShortDesc;
	long contentId;
	String contentTitle;
	long sectionId;
	String sectionShortTitle;

	public String getSectionShortTitle() {
		return sectionShortTitle;
	}

	public void setSectionShortTitle(String sectionShortTitle) {
		this.sectionShortTitle = sectionShortTitle;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public long getContentId() {
		return contentId;
	}

	public void setContentId(long contentId) {
		this.contentId = contentId;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemShortDesc() {
		return itemShortDesc;
	}

	public void setItemShortDesc(String itemShortDesc) {
		this.itemShortDesc = itemShortDesc;
	}

	public MenuDisplayCommand getChildrenMenu(int index) {
		return childrenMenus[index];
	}

	public long getCreateMenuId() {
		return createMenuId;
	}

	public void setCreateMenuId(long createMenuId) {
		this.createMenuId = createMenuId;
	}

	public String getCreateMode() {
		return createMode;
	}

	public void setCreateMode(String createMode) {
		this.createMode = createMode;
	}

	public boolean isSequence() {
		return isSequence;
	}

	public void setSequence(boolean isSequence) {
		this.isSequence = isSequence;
	}

	public long getRemoveMenuId() {
		return removeMenuId;
	}

	public void setRemoveMenuId(long removeMenuId) {
		this.removeMenuId = removeMenuId;
	}

	public String getMenuSetName() {
		return menuSetName;
	}

	public void setMenuSetName(String menuSetName) {
		this.menuSetName = menuSetName;
	}

	public MenuDisplayCommand[] getChildrenMenus() {
		return childrenMenus;
	}

	public void setChildrenMenus(MenuDisplayCommand[] childrenMenus) {
		this.childrenMenus = childrenMenus;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}

	public DropDownMenu[] getMenuList() {
		return menuList;
	}

	public void setMenuList(DropDownMenu[] menuList) {
		this.menuList = menuList;
	}

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

	public String getMenuPosition() {
		return menuPosition;
	}

	public void setMenuPosition(String menuPosition) {
		this.menuPosition = menuPosition;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

//	public void reset(ActionMapping mapping, HttpServletRequest request) {
//		String CHILDMENUS = "childrenMenu.*menuId";
//		int count = 0;
//		Enumeration enumeration = request.getParameterNames();
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			if (name.matches(CHILDMENUS)) {
//				count++;
//			}
//		}
//		childrenMenus = new MenuDisplayCommand[count];
//		for (int i = 0; i < childrenMenus.length; i++) {
//			childrenMenus[i] = new MenuDisplayCommand();
//		}
//	}

	public long getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(long menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getCreateMenuSetName() {
		return createMenuSetName;
	}

	public void setCreateMenuSetName(String createMenuSetName) {
		this.createMenuSetName = createMenuSetName;
	}
}
