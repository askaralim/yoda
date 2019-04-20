package com.taklip.yoda.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.DropDownMenu;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Menu;

public interface MenuService {
	Menu getMenu(int menuId);

	Menu getMenu(String menuSetName);

	Menu getMenuBySiteIdMenuName(int siteId, String menuName);

//	Menu getMenu(int siteId, int menuId) throws SecurityException, Exception;

	List<Menu> getMenus(String menuSetName, int menuParentId);

//	List<Menu> getBySiteIdMenuParentId(int siteId, int menuParentId);

	String formatMenuName(int siteId, int menuId) throws Exception;

	DropDownMenu[] makeMenuTreeList(int siteId) throws Exception;

	DropDownMenu makeMenuTree(int siteId, String menuSetName) throws Exception;

	DropDownMenu[] makeMenu(int siteId, String menuSetName, int menuParentId) throws Exception;

	DropDownMenu[] makeDdmMenu(int siteId, String menuSetName, int menuParentId) throws Exception;

	Menu addMenu(int siteId, String menuSetName, String menuName);

	void addMenu(int siteId, int menuId, String menuTitle, String menuName,
				String menuUrl, String menuWindowTarget, String menuWindowMode,
				boolean published, String menuType, long contentId, int itemId, int sectionId) throws SecurityException, Exception;

	void deleteMenu(int menuId);

	List<Menu> getMenus(int siteId, int parentMenuId);

	int selectMaxSeqNumByMenuId_SiteId(int siteId, int parentMenuId);

	void updateSeqNum(int siteId, int menuParentId, int seqNum);

	Menu addMenu(int siteId, int menuParentId, int seqNum,
				 String menuSetName, String menuTitle, String menuName, String menuType, String menuUrl,
				 String menuWindowTarget, String menuWindowMode, boolean published);

	void updateMenu(Menu menu);

	public JSONObject makeJSONMenuTree(int siteId) throws Exception;

	public JSONObject makeJSONMenuTreeNode(int siteId, int menuId) throws Exception;

	Menu updateMenu(int lastVisitSiteId, int i, Content content, Item item,
			String string, String menuWindowMode,
			String menuWindowTarget, String menuContent) throws SecurityException, Exception;
}