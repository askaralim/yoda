package com.yoda.menu.service;

import java.util.List;

import org.json.JSONObject;

import com.yoda.content.model.Content;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;

public interface MenuService {
	Menu getMenu(long menuId);

	Menu getMenu(long siteId, String menuSetName);

	Menu getMenuBySiteIdMenuName(long siteId, String menuName);

	Menu getMenu(long siteId, long menuId) throws SecurityException, Exception;

	List<Menu> getMenu(long siteId, String menuSetName, long menuParentId);

	List<Menu> getBySiteIdMenuParentId(long siteId, long menuParentId);

	String formatMenuName(long siteId, long menuId) throws Exception;

	DropDownMenu[] makeMenuTreeList(long siteId) throws Exception;

	DropDownMenu makeMenuTree(long siteId, String menuSetName) throws Exception;

	DropDownMenu[] makeMenu(long siteId, String menuSetName, long menuParentId) throws Exception;

	DropDownMenu[] makeDdmMenu(long siteId, String menuSetName, Long menuParentId) throws Exception;

	Menu addMenu(long userId, long siteId, String menuSetName, String menuName);

	void addMenu(long userId, long siteId, long menuId, String menuTitle, String menuName,
				 String menuUrl, String menuWindowTarget, String menuWindowMode,
				 char published, String menuType, long contentId, long itemId, long sectionId) throws SecurityException, Exception;

	void deleteMenu(long siteId, long menuId);

	List<Menu> getByMenuParentId(long parentMenuId);

	int selectMaxSeqNumByMenuId_SiteId(long siteId, long parentMenuId);

	void updateSeqNum(long siteId, long menuParentId, int seqNum);

	Menu addMenu(long siteId, long menuParentId, int seqNum,
				 String menuSetName, String menuTitle, String menuName, String menuType, String menuUrl,
				 String menuWindowTarget, String menuWindowMode, char published, long recUpdateBy,
				 long recCreateBy);

	void updateMenu(Menu menu);

	public JSONObject makeJSONMenuTree(long siteId) throws Exception;

	public JSONObject makeJSONMenuTreeNode(long siteId, long menuId) throws Exception;

	Menu updateMenu(long siteId, long menuId, Content content, Item item, Section section, String menuUrl, String menuWindowMode, String menuWindowTarget, String menuType)  throws SecurityException, Exception;
}
