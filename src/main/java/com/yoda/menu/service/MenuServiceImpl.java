package com.yoda.menu.service;

import java.util.List;
import java.util.Vector;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.model.Content;
import com.yoda.content.persistence.ContentMapper;
import com.yoda.item.model.Item;
import com.yoda.menu.model.Menu;
import com.yoda.menu.persistence.MenuMapper;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

@Transactional
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
//	private ContentDAO contentDAO;
	private ContentMapper contentMapper;

//	@Autowired
//	private ItemDAO itemDAO;
//	private ItemDAO itemMapper;

	@Autowired
//	private MenuDAO menuDAO;
	private MenuMapper menuMapper;

//	@Autowired
//	private SectionDAO sectionDAO;

	public Menu addMenu(int siteId, String menuSetName, String menuName) {
		Menu menuSet = new Menu();

		menuSet.setSiteId(siteId);
		menuSet.setSetName(menuSetName);
		menuSet.setName(menuName);
		menuSet.setSeqNum(0);
		menuSet.setPublished(true);
		menuSet.setMenuType("");
		menuSet.setMenuUrl("");
		menuSet.setMenuWindowTarget("");
		menuSet.setMenuWindowMode("");

		menuSet.preInsert();

		menuMapper.insert(menuSet);

		return menuSet;
	}

	public Menu addMenu(
			int siteId, int menuParentId, int seqNum, String menuSetName,
			String menuTitle, String menuName, String menuType, String menuUrl,
			String menuWindowTarget, String menuWindowMode, boolean published) {
		Menu menu = new Menu();

		menu.setSiteId(siteId);
		menu.setParentId(menuParentId);
		menu.setSeqNum(seqNum);
		menu.setSetName(menuSetName);
		menu.setTitle(menuTitle);
		menu.setName(menuName);
		menu.setMenuType(menuType);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setPublished(published);

		menu.preInsert();

		menuMapper.insert(menu);

		return menu;
	}

	public void addMenu(
			int siteId, int menuId, String menuTitle, String menuName,
			String menuUrl, String menuWindowTarget, String menuWindowMode,
			boolean published, String menuType, long contentId, int itemId,
			int sectionId)
		throws SecurityException, Exception {
		Menu menu = new Menu();

		if (menuId != 0) {
			menu = menuMapper.getById(menuId);
		}

		menu.setTitle(menuTitle);
		menu.setName(menuName);
//		menu.setSetName(menuName);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setPublished(published);
		menu.setContent(null);
//		menu.setItem(null);
		menu.setSection(null);
		menu.setSeqNum(0);
		menu.setSiteId(siteId);
		menu.setMenuType(menuType);

		if (menuType.equals(Constants.MENU_CONTENT)) {
			if (Validator.isNotNull(contentId)) {
				Content content = contentMapper.getById(contentId);
				menu.setContent(content);
			}
		}

//		if (menuType.equals(Constants.MENU_ITEM)) {
//			if (Validator.isNotNull(itemId)) {
//				Item item = itemDAO.getItem(siteId, itemId);
//				menu.setItem(item);
//			}
//		}

//		if (menuType.equals(Constants.MENU_SECTION)) {
//			if (Validator.isNotNull(sectionId)) {
//				Section section = sectionDAO.getSectionBySiteId_SectionId(siteId, sectionId);
//				menu.setSection(section);
//			}
//		}

		menu.preInsert();

		menuMapper.update(menu);
	}

	public void deleteMenu(int menuId) {
		menuMapper.delete(menuId);
	}

	@Transactional(readOnly = true)
	public Menu getMenu(int menuId) {
		return menuMapper.getById(menuId);
	}

	@Transactional(readOnly = true)
	public Menu getMenuBySiteIdMenuName(int siteId, String menuName) {
		return menuMapper.getMenuBySiteIdAndName(siteId, menuName);
	}

	@Transactional(readOnly = true)
	public Menu getMenu(int siteId, String menuSetName) {
		return menuMapper.getMenuRootBySiteIdAndSetNameOrderBySetName(siteId, menuSetName);
	}

//	@Transactional(readOnly = true)
//	public Menu getMenu(int siteId, int menuId) throws SecurityException, Exception {
//		return menuMapper.getById(menuId);
//	}

	@Transactional(readOnly = true)
	public List<Menu> getMenus(int siteId, String menuSetName, int menuParentId) {
		return menuMapper.getMenusBySiteIdAndSetNameAndParentId(siteId, menuSetName, menuParentId);
	}

	@Transactional(readOnly = true)
	public List<Menu> getMenus(int site, int parentMenuId) {
		return menuMapper.getMenusBySiteIdAndParentId(site, parentMenuId);
	}

//	@Transactional(readOnly = true)
//	public List<Menu> getMenus(int siteId, int menuParentId) {
//		return menuMapper.getBySiteId_MenuParentId(siteId, menuParentId);
//	}

	@Transactional(readOnly = true)
	public int selectMaxSeqNumByMenuId_SiteId(int siteId, int parentMenuId) {
		return menuMapper.selectMaxSeqNumBySiteIdParentMenuId(siteId, parentMenuId);
	}

	public Menu updateMenu(
			int siteId, int menuId, Content content, Item item,
			Section section, String menuUrl, String menuWindowMode,
			String menuWindowTarget, String menuType)
		throws SecurityException, Exception {
		Menu menu = getMenu(menuId);

		menu.setContent(content);
//		menu.setItem(item);
		menu.setSection(section);
		menu.setMenuUrl(menuUrl);
		menu.setMenuWindowMode(menuWindowMode);
		menu.setMenuWindowTarget(menuWindowTarget);
		menu.setMenuType(menuType);

		updateMenu(menu);

		return menu;
	}

	public void updateMenu(Menu menu) {
		menu.preUpdate();

		menuMapper.update(menu);
	}

	public void updateSeqNum(int siteId, int menuParentId, int seqNum) {
		menuMapper.updateSeqNum(siteId, menuParentId, seqNum);
	}

	public String formatMenuName(int siteId, int menuId)
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

			if (menu.getParentId() == 0) {
				menuString = menu.getSetName() + menuString;
				break;
			}

			menuString = menu.getName() + menuString;
			menuId = menu.getParentId();
		}

		return menuString;
	}


	@Transactional(readOnly = true)
	public DropDownMenu[] makeMenuTreeList(int siteId)
			throws Exception {
		Vector<DropDownMenu> menuVector = new Vector<DropDownMenu>();

		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_MAIN));
//		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_SECONDARY));

		List<Menu> menus = menuMapper.getMenusBySiteId(siteId);

		for (Menu menu : menus) {
			if (menu.getSetName().equals(Constants.MENUSET_MAIN)) {
				continue;
			}

//			if (menu.getSetName().equals(Constants.MENUSET_SECONDARY)) {
//				continue;
//			}
			/*
			 * DropDownMenu list[] = new DropDownMenu[1]; list[0] =
			 * makeMenuTree(siteId, menu.getSetName()); DropDownMenu ddm =
			 * new DropDownMenu(); ddm.setName(menu.getSetName());
			 * ddm.setMenuItems(list); menuVector.add(ddm);
			 */
			menuVector.add(makeMenuTree(siteId, menu.getSetName()));
		}

		DropDownMenu ddmList[] = new DropDownMenu[menuVector.size()];

		menuVector.copyInto(ddmList);

		return ddmList;
	}

	@Transactional(readOnly = true)
	public DropDownMenu makeMenuTree(int siteId, String menuSetName)
			throws Exception {
		Menu menu = menuMapper.getMenuRootBySiteIdAndSetNameOrderBySetName(siteId, menuSetName);

		DropDownMenu menus[] = makeMenu(siteId, menuSetName, menu.getMenuId());
		DropDownMenu ddm = new DropDownMenu();

		ddm.setMenuName(menu.getSetName());
		ddm.setMenuKey(menu.getMenuId());
		ddm.setMenuItems(menus);

		return ddm;
	}

	@Transactional(readOnly = true)
	public DropDownMenu[] makeMenu(
			int siteId, String menuSetName, int menuParentId)
		throws Exception {
		DropDownMenu menuArr[] = null;
		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

		List<Menu> menus = menuMapper.getMenusBySiteIdAndSetNameAndParentId(siteId, menuSetName, menuParentId);

		for (Menu menu : menus) {
			DropDownMenu ddm = new DropDownMenu();

			ddm.setMenuKey(menu.getMenuId());
			ddm.setMenuName(menu.getName());

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
			int siteId, String menuSetName, int menuParentId)
		throws Exception {
		DropDownMenu menuArr[] = null;
		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

		List<Menu> menus = menuMapper.getMenusBySiteIdAndSetNameAndParentId(siteId, menuSetName, menuParentId);

		for (Menu menu : menus) {
			DropDownMenu ddm = new DropDownMenu();

			ddm.setMenuKey(menu.getMenuId());
			ddm.setMenuName(menu.getName());

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
	public JSONObject makeJSONMenuTree(int siteId) throws Exception {
		JSONObject object = new JSONObject();

		List<Menu> menus = menuMapper.getMenusRootBySiteIdOrderBySetName(siteId);

		Vector<JSONObject> menuSetVector = new Vector<JSONObject>();

		for (Menu menu : menus) {
			JSONObject menuSetObject = makeJSONMenuTreeNode(siteId, menu.getMenuId());

			menuSetVector.add(menuSetObject);
		}

		object.put("menuSets", menuSetVector);

		return object;
	}

	@Transactional(readOnly = true)
	public JSONObject makeJSONMenuTreeNode(int siteId, int menuId)
			throws Exception {
		JSONObject jsonObject = new JSONObject();

		Menu menu = menuMapper.getById(menuId);

		if (menu.getParentId() == 0) {
			jsonObject.put("menuSetName", menu.getSetName());
		}
		else {
			jsonObject.put("menuId", menu.getMenuId());
			jsonObject.put("menuName", menu.getName());
		}

		List<Menu> menus = menuMapper.getMenusBySiteIdAndParentId(siteId, menuId);

		Vector<JSONObject> vector = new Vector<JSONObject>();

		for (Menu child : menus) {
			JSONObject object = makeJSONMenuTreeNode(siteId, child.getMenuId());

			vector.add(object);
		}

		jsonObject.put("menus", vector);

		return jsonObject;
	}
}