package com.yoda.menu.service;

import java.util.List;

import org.json.JSONObject;

import com.yoda.content.model.Content;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;

public interface MenuService {
	Menu getMenu(int menuId);

	Menu getMenu(int siteId, String menuSetName);

	Menu getMenuBySiteIdMenuName(int siteId, String menuName);

//	Menu getMenu(int siteId, int menuId) throws SecurityException, Exception;

	List<Menu> getMenus(int siteId, String menuSetName, int menuParentId);

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

	Menu updateMenu(int siteId, int menuId, Content content, Item item, Section section, String menuUrl, String menuWindowMode, String menuWindowTarget, String menuType)  throws SecurityException, Exception;
}