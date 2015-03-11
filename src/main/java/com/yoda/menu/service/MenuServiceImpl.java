package com.yoda.menu.service;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.dao.ContentDAO;
import com.yoda.content.model.Content;
import com.yoda.item.dao.ItemDAO;
import com.yoda.item.model.Item;
import com.yoda.menu.dao.MenuDAO;
import com.yoda.menu.model.Menu;
import com.yoda.section.dao.SectionDAO;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

@Transactional
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private ContentDAO contentDAO;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private MenuDAO menuDAO;

	@Autowired
	private SectionDAO sectionDAO;

	public Menu addMenu(
		long userId, long siteId, String menuSetName, String menuName) {
		Menu menuSet = new Menu();

		menuSet.setSiteId(siteId);
		menuSet.setMenuSetName(menuSetName);
		menuSet.setMenuName(menuName);
		menuSet.setSeqNum(0);
		menuSet.setPublished('Y');
		menuSet.setMenuType("");
		menuSet.setMenuUrl("");
		menuSet.setMenuWindowTarget("");
		menuSet.setMenuWindowMode("");
		menuSet.setRecUpdateBy(userId);
		menuSet.setRecCreateBy(userId);
		menuSet.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
		menuSet.setRecCreateDatetime(new Date(System.currentTimeMillis()));

		menuDAO.save(menuSet);

		return menuSet;
	}

	public Menu addMenu(
			long siteId, long menuParentId, int seqNum, String menuSetName,
			String menuTitle, String menuName, String menuType, String menuUrl,
			String menuWindowTarget, String menuWindowMode, char published,
			long recUpdateBy, long recCreateBy) {
		Menu menu = new Menu();

		menu.setSiteId(siteId);
		menu.setMenuParentId(menuParentId);
		menu.setSeqNum(seqNum);
		menu.setMenuSetName(menuSetName);
		menu.setMenuTitle(menuTitle);
		menu.setMenuName(menuName);
		menu.setMenuType(menuType);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setPublished(published);
		menu.setRecUpdateBy(recUpdateBy);
		menu.setRecCreateBy(recCreateBy);
		menu.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
		menu.setRecCreateDatetime(new Date(System.currentTimeMillis()));

		menuDAO.save(menu);

		return menu;
	}

	public void addMenu(
			long userId, long siteId, long menuId, String menuTitle, String menuName,
			String menuUrl, String menuWindowTarget, String menuWindowMode,
			char published, String menuType, long contentId, int itemId,
			long sectionId)
		throws SecurityException, Exception {
		Menu menu = new Menu();

		if (menuId != 0) {
			menu = menuDAO.getByMenuId_SiteId(siteId, menuId);
		}

		menu.setMenuTitle(menuTitle);
		menu.setMenuName(menuName);
//		menu.setMenuSetName(menuName);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setPublished(published == Constants.PUBLISHED_YES ? Constants.PUBLISHED_YES : Constants.PUBLISHED_NO);
		menu.setRecUpdateBy(userId);
		menu.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
		menu.setContent(null);
		menu.setItem(null);
		menu.setSection(null);
		menu.setSeqNum(0);
		menu.setSiteId(siteId);
		menu.setMenuType(menuType);
		menu.setRecUpdateBy(userId);
		menu.setRecCreateBy(userId);
		menu.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
		menu.setRecCreateDatetime(new Date(System.currentTimeMillis()));

		if (menuType.equals(Constants.MENU_CONTENT)) {
			if (Validator.isNotNull(contentId)) {
				Content content = contentDAO.getContentById(siteId, contentId);
				menu.setContent(content);
			}
		}

		if (menuType.equals(Constants.MENU_ITEM)) {
			if (Validator.isNotNull(itemId)) {
				Item item = itemDAO.getItem(siteId, itemId);
				menu.setItem(item);
			}
		}

		if (menuType.equals(Constants.MENU_SECTION)) {
			if (Validator.isNotNull(sectionId)) {
				Section section = sectionDAO.getSectionBySiteId_SectionId(siteId, sectionId);
				menu.setSection(section);
			}
		}

		menuDAO.update(menu);
	}

	public void deleteMenu(long siteId, long menuId) {
		menuDAO.delete(siteId, menuId);
	}

	@Transactional(readOnly = true)
	public Menu getMenu(long menuId) {
		return menuDAO.getByMenuId(menuId);
	}

	@Transactional(readOnly = true)
	public Menu getMenuBySiteIdMenuName(long siteId, String menuName) {
		return menuDAO.getMenuBySiteIdMenuName(siteId, menuName);
	}

	@Transactional(readOnly = true)
	public Menu getMenu(long siteId, String menuSetName) {
		return menuDAO.getMenu(siteId, menuSetName);
	}

	@Transactional(readOnly = true)
	public Menu getMenu(long siteId, long menuId) throws SecurityException, Exception {
		return menuDAO.getByMenuId_SiteId(siteId, menuId);
	}

	@Transactional(readOnly = true)
	public List<Menu> getMenu(long siteId, String menuSetName, long menuParentId) {
		return menuDAO.getMenu(siteId, menuSetName, menuParentId);
	}

	@Transactional(readOnly = true)
	public List<Menu> getByMenuParentId(long parentMenuId) {
		return menuDAO.getByParentMenuId(parentMenuId);
	}

	@Transactional(readOnly = true)
	public List<Menu> getBySiteIdMenuParentId(long siteId, long menuParentId) {
		return menuDAO.getBySiteId_MenuParentId(siteId, menuParentId);
	}

	@Transactional(readOnly = true)
	public int selectMaxSeqNumByMenuId_SiteId(long siteId, long parentMenuId) {
		return menuDAO.selectMaxSeqNumBySiteIdParentMenuId(siteId, parentMenuId);
	}

	public Menu updateMenu(
			long siteId, long menuId, Content content, Item item,
			Section section, String menuUrl, String menuWindowMode,
			String menuWindowTarget, String menuType)
		throws SecurityException, Exception {
		Menu menu = getMenu(siteId, menuId);

		menu.setContent(content);
		menu.setItem(item);
		menu.setSection(section);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuType(menuType);

		updateMenu(menu);

		return menu;
	}

	public void updateMenu(Menu menu) {
		menuDAO.update(menu);
	}

	public void updateSeqNum(long siteId, long menuParentId, int seqNum) {
		menuDAO.updateSeqNum(siteId, menuParentId, seqNum);
	}

	public String formatMenuName(long siteId, long menuId)
		throws Exception {
		String menuString = "";

		while (true) {
			Menu menu = new Menu();

			menu = getMenu(menuId);

			if (menu.getSiteId() != siteId) {
				throw new SecurityException();
			}

			if (menuString.length() > 0) {
				menuString = " - " + menuString;
			}

			if (menu.getMenuParentId() == 0) {
				menuString = menu.getMenuSetName() + menuString;
				break;
			}

			menuString = menu.getMenuName() + menuString;
			menuId = menu.getMenuParentId();
		}

		return menuString;
	}


	@Transactional(readOnly = true)
	public DropDownMenu[] makeMenuTreeList(long siteId)
			throws Exception {
		Vector<DropDownMenu> menuVector = new Vector<DropDownMenu>();

		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_MAIN));
//		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_SECONDARY));

		List<Menu> menus = menuDAO.getBySiteId(siteId);

		for (Menu menu : menus) {
			if (menu.getMenuSetName().equals(Constants.MENUSET_MAIN)) {
				continue;
			}

//			if (menu.getMenuSetName().equals(Constants.MENUSET_SECONDARY)) {
//				continue;
//			}
			/*
			 * DropDownMenu list[] = new DropDownMenu[1]; list[0] =
			 * makeMenuTree(siteId, menu.getMenuSetName()); DropDownMenu ddm =
			 * new DropDownMenu(); ddm.setMenuName(menu.getMenuSetName());
			 * ddm.setMenuItems(list); menuVector.add(ddm);
			 */
			menuVector.add(makeMenuTree(siteId, menu.getMenuSetName()));
		}

		DropDownMenu ddmList[] = new DropDownMenu[menuVector.size()];

		menuVector.copyInto(ddmList);

		return ddmList;
	}

	@Transactional(readOnly = true)
	public DropDownMenu makeMenuTree(long siteId, String menuSetName)
			throws Exception {
		Menu menu = menuDAO.getMenu(siteId, menuSetName);

		DropDownMenu menus[] = makeMenu(siteId, menuSetName, menu.getMenuId());
		DropDownMenu ddm = new DropDownMenu();

		ddm.setMenuName(menu.getMenuSetName());
		ddm.setMenuKey(menu.getMenuId());
		ddm.setMenuItems(menus);

		return ddm;
	}

	@Transactional(readOnly = true)
	public DropDownMenu[] makeMenu(
			long siteId, String menuSetName, long menuParentId)
		throws Exception {
		DropDownMenu menuArr[] = null;
		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

		List<Menu> menus = menuDAO.getMenu(siteId, menuSetName, menuParentId);

		for (Menu menu : menus) {
			DropDownMenu ddm = new DropDownMenu();

			ddm.setMenuKey(menu.getMenuId());
			ddm.setMenuName(menu.getMenuName());

			if (menu.getMenuId() != 0) {
				DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName, menu.getMenuId());
				ddm.setMenuItems(childMenus);
			}

			menuList.add(ddm);
		}

		menuArr = new DropDownMenu[menuList.size()];
		menuList.copyInto(menuArr);
		return menuArr;
	}

	@Transactional(readOnly = true)
	public DropDownMenu[] makeDdmMenu(
			long siteId, String menuSetName, Long menuParentId)
		throws Exception {
		DropDownMenu menuArr[] = null;
		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

		List<Menu> menus = menuDAO.getMenu(siteId, menuSetName, menuParentId);

		for (Menu menu : menus) {
			DropDownMenu ddm = new DropDownMenu();

			ddm.setMenuKey(menu.getMenuId());
			ddm.setMenuName(menu.getMenuName());

			if (menu.getMenuId() == 0) {
				DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName, menu.getMenuId());

				ddm.setMenuItems(childMenus);
			}

			menuList.add(ddm);
		}

		menuArr = new DropDownMenu[menuList.size()];
		menuList.copyInto(menuArr);

		return menuArr;
	}

	@Transactional(readOnly = true)
	public JSONObject makeJSONMenuTree(long siteId) throws Exception {
		JSONObject object = new JSONObject();

		List<Menu> menus = menuDAO.getBySiteId_MenuParentId_orderBy_MenuSetName(siteId);

		Vector<JSONObject> menuSetVector = new Vector<JSONObject>();

		for (Menu menu : menus) {
			JSONObject menuSetObject = makeJSONMenuTreeNode(siteId, menu.getMenuId());

			menuSetVector.add(menuSetObject);
		}

		object.put("menuSets", menuSetVector);

		return object;
	}

	@Transactional(readOnly = true)
	public JSONObject makeJSONMenuTreeNode(long siteId, long menuId)
			throws Exception {
		JSONObject jsonObject = new JSONObject();

		Menu menu = menuDAO.getByMenuId_SiteId(siteId, menuId);

		if (menu.getMenuParentId() == 0) {
			jsonObject.put("menuSetName", menu.getMenuSetName());
		}
		else {
			jsonObject.put("menuId", menu.getMenuId());
			jsonObject.put("menuName", menu.getMenuName());
		}

		List<Menu> menus = menuDAO.getBySiteId_MenuParentId(siteId, menuId);

		Vector<JSONObject> vector = new Vector<JSONObject>();

		for (Menu child : menus) {
			JSONObject object = makeJSONMenuTreeNode(siteId, child.getMenuId());

			vector.add(object);
		}

		jsonObject.put("menus", vector);

		return jsonObject;
	}
}