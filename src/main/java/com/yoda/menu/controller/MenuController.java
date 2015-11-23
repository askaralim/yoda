package com.yoda.menu.controller;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.service.ContentService;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.MenuDisplayCommand;
import com.yoda.menu.MenuEditCommand;
import com.yoda.menu.MenuEditValidator;
import com.yoda.menu.MenuResequenceValidator;
import com.yoda.menu.MenuSaveValidator;
import com.yoda.menu.model.Menu;
import com.yoda.menu.service.MenuService;
import com.yoda.portal.content.frontend.MenuFactory;
import com.yoda.section.service.SectionService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;

@Controller
public class MenuController {
	@Autowired
	ContentService contentService;

	@Autowired
	MenuService menuService;

	@Autowired
	ItemService itemService;

//	@Autowired
//	SectionService sectionService;

	@RequestMapping(value = "/controlpanel/menu/create", method = RequestMethod.POST)
	public String create(
			@ModelAttribute MenuEditCommand menuEditCommand,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		Menu referenceMenu = menuService.getMenu(menuEditCommand.getCreateMenuId());

		int menuParentId = 0;

		int seqNum = 0;

		if (menuEditCommand.getCreateMode().equals("C")) { // append child node
			menuParentId = referenceMenu.getMenuId();

			int menuSeqNum = menuService.selectMaxSeqNumByMenuId_SiteId(siteId, referenceMenu.getMenuId());

			if (menuSeqNum == 0) {
				seqNum = 0;
			}
			else {
				seqNum = menuSeqNum + 1;
			}
		}
		else if (menuEditCommand.getCreateMode().equals("B")) { // before current
			menuParentId = referenceMenu.getParentId();

			seqNum = referenceMenu.getSeqNum();

			menuService.updateSeqNum(siteId, referenceMenu.getParentId(), referenceMenu.getSeqNum());
		}
		else if (menuEditCommand.getCreateMode().equals("A")) { // after current
			menuParentId = referenceMenu.getParentId();

			seqNum = referenceMenu.getSeqNum() + 1;

			menuService.updateSeqNum(siteId, referenceMenu.getParentId(), referenceMenu.getSeqNum());
		}

		Menu menu = menuService.addMenu(
			siteId, menuParentId, seqNum, referenceMenu.getSetName(),
			"New Menu", "New Menu", Constants.MENU_HOME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, true);

		menuEditCommand.setMenuId(menu.getMenuId());
		menuEditCommand.setMenuParentId(menuParentId);
		menuEditCommand.setMenuSetName(menu.getSetName());
		menuEditCommand.setMenuTitle(menu.getTitle());
		menuEditCommand.setMenuName(menu.getName());
		menuEditCommand.setMenuType(Constants.MENU_HOME);
		menuEditCommand.setMenuUrl("");
		menuEditCommand.setMenuWindowTarget("");
		menuEditCommand.setMenuWindowMode("");
		menuEditCommand.setPublished(menu.isPublished());
		menuEditCommand.setMode(Constants.MODE_UPDATE);
		menuEditCommand.setMenuList(menuService.makeMenuTreeList(siteId));
//		menuEditCommand.setPublished(String.valueOf(Constants.PUBLISHED_YES));
		menuEditCommand.setSequence(false);
		menuEditCommand.setMode(Constants.MODE_CREATE);

		initListInfo(menuEditCommand, siteId);

		MenuFactory.clear();

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/showsequence", method = RequestMethod.POST)
	public String showSequence(
			@RequestParam(value = "menuId") int menuId,
			@ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
			SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

		Menu menu  = menuService.getMenu(menuId);

		menuEditCommand.setMenuId(menuId);

		if (menu.getParentId() != 0) {
			menuEditCommand.setMenuParentId(menu.getParentId());
		}

		menuEditCommand.setMenuId(menu.getMenuId());
		menuEditCommand.setMenuSetName(menu.getSetName());
		menuEditCommand.setMenuName(menu.getName());
		menuEditCommand.setMenuType(menu.getMenuType());
		menuEditCommand.setMenuUrl(menu.getMenuUrl());
		menuEditCommand.setMenuWindowTarget(menu.getMenuWindowTarget());
		menuEditCommand.setMenuWindowMode(menu.getMenuWindowMode());
		menuEditCommand.setPublished(menu.isPublished());
		menuEditCommand.setMode(Constants.MODE_UPDATE);
		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
//		menuEditCommand.setPublished(true);
		menuEditCommand.setMode(Constants.MODE_UPDATE);
		menuEditCommand.setSequence(true);

		initListInfo(menuEditCommand, user.getLastVisitSiteId());

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/removeselected", method = RequestMethod.POST)
	public String removeSelected(
			@ModelAttribute MenuEditCommand menuEditCommand,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		MenuDisplayCommand childrenMenus[] = menuEditCommand.getChildrenMenus();

		for (int i = 0; i < childrenMenus.length; i++) {
			if (childrenMenus[i].isRemove()) {
				int menuId = childrenMenus[i].getMenuId();

				cascadeRemoveMenu(menuId, user.getLastVisitSiteId());
			}
		}

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

		initListInfo(menuEditCommand, user.getLastVisitSiteId());

		menuEditCommand.setSequence(true);

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/resequence", method = RequestMethod.POST)
	public String resequence(
			@ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
			SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		new MenuResequenceValidator().validate(menuEditCommand, result);

		if(result.hasErrors()) {
			menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
			menuEditCommand.setSequence(true);

			return "controlpanel/menu/edit";
		}

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value="/controlpanel/menu/edit", method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
//		String loginMessage = AdminLookup.lookUpAdmin(request, response);
//
//		if (Validator.isNotNull(loginMessage)) {
//			ModelMap modelMap = new ModelMap();
//
//			modelMap.put("loginMessage", loginMessage);
//
//			return new ModelAndView(
//				"redirect:" + Constants.LOGIN_PAGE_URL, modelMap);
//		}

		MenuEditCommand command = new MenuEditCommand();

		User user = PortalUtil.getAuthenticatedUser();

		command.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
		command.setMode("");

		return new ModelAndView("controlpanel/menu/edit", "menuEditCommand", command);
	}

	@RequestMapping(value = "/controlpanel/menu/newmenuset", method = RequestMethod.POST)
	public String newMenuSet(
			@ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
			SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		new MenuEditValidator().validate(menuEditCommand, result);

		if(result.hasErrors()) {
			menuEditCommand.setMenuList(menuService.makeMenuTreeList(siteId));

			return "controlpanel/menu/edit";
		}

		menuService.addMenu(
			siteId, menuEditCommand.getCreateMenuSetName(),
			menuEditCommand.getCreateMenuSetName());

		menuEditCommand.setCreateMenuSetName("");
		menuEditCommand.setMenuList(menuService.makeMenuTreeList(siteId));
		menuEditCommand.setMenuId(0);
		menuEditCommand.setMode("");

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/update/{menuId}", method = RequestMethod.GET)
	public ModelAndView updateMenu(
			@PathVariable("menuId") int menuId,
			@ModelAttribute MenuEditCommand menuEditCommand,
//			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

		Menu menu = menuService.getMenu(menuId);

		menuEditCommand.setMenuId(menuEditCommand.getMenuId());

		if (menu.getParentId() != 0) {
			menuEditCommand.setMenuParentId(menu.getParentId());
		}

		menuEditCommand.setMenuId(menu.getMenuId());
		menuEditCommand.setMenuSetName(menu.getSetName());
		menuEditCommand.setMenuTitle(menu.getTitle());
		menuEditCommand.setMenuName(menu.getName());
		menuEditCommand.setMenuUrl(menu.getMenuUrl());
		menuEditCommand.setMenuWindowTarget(menu.getMenuWindowTarget());
		menuEditCommand.setMenuWindowMode(menu.getMenuWindowMode());
		menuEditCommand.setPublished(menu.isPublished());
		menuEditCommand.setMode(Constants.MODE_UPDATE);
		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
//		menuEditCommand.setPublished(true);
		menuEditCommand.setSequence(false);
		menuEditCommand.setMode(Constants.MODE_UPDATE);

		menuEditCommand.setMenuType(menu.getMenuType());

		if (menu.getMenuType().equals(Constants.MENU_CONTENT)
				&& menu.getContent() != null) {
			menuEditCommand.setContentId(menu.getContent().getContentId());
			menuEditCommand.setContentTitle(menu.getContent().getTitle());
		}
//		else if (menu.getMenuType().equals(Constants.MENU_ITEM)
//				&& menu.getItem() != null) {
//			menuEditCommand.setItemId(menu.getItem().getItemId());
//			menuEditCommand.setItemNum(menu.getItem().getItemNum());
//			menuEditCommand.setItemShortDesc(menu.getItem().getItemShortDesc());
//		}
		else if (menu.getMenuType().equals(Constants.MENU_SECTION)
				&& menu.getSection() != null) {
			menuEditCommand.setSectionId(menu.getSection().getSectionId());
			menuEditCommand.setSectionShortTitle(menu.getSection().getShortTitle());
		}

		initListInfo(menuEditCommand, user.getLastVisitSiteId());

		MenuFactory.clear();

		return new ModelAndView("controlpanel/menu/edit", "menuEditCommand", menuEditCommand);
	}

	@RequestMapping(value = "/controlpanel/menu/save", method = RequestMethod.POST)
	public String saveMenu(
			@ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
			SessionStatus status, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = PortalUtil.getAuthenticatedUser();

		new MenuSaveValidator().validate(menuEditCommand, result);

		if(result.hasErrors()) {
			return "controlpanel/menu/edit";
		}

		menuService.addMenu(
			user.getLastVisitSiteId(), menuEditCommand.getMenuId(),
			menuEditCommand.getMenuTitle(), menuEditCommand.getMenuName(), menuEditCommand.getMenuUrl(),
			menuEditCommand.getMenuWindowTarget(),
			menuEditCommand.getMenuWindowMode(),
			menuEditCommand.isPublished(),
			menuEditCommand.getMenuType(),
			menuEditCommand.getContentId(), menuEditCommand.getItemId(),
			menuEditCommand.getSectionId());

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

		initListInfo(menuEditCommand, user.getLastVisitSiteId());

		MenuFactory.clear();

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/removemenuset", method = RequestMethod.POST)
	public String removeMenuSet(
			@RequestParam(value = "removeMenuId") int removeMenuId,
			@ModelAttribute MenuEditCommand menuEditCommand,
			HttpServletRequest request) throws Exception {
		User user = PortalUtil.getAuthenticatedUser();

//		Menu menu = menuService.getMenu(user.getLastVisitSiteId(), removeMenuId);

		menuService.deleteMenu(removeMenuId);

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
		menuEditCommand.setMenuId(0);
		menuEditCommand.setMode("");

		return "controlpanel/menu/edit";
	}

	@RequestMapping(value = "/controlpanel/menu/remove", method = RequestMethod.POST)
	public String remove(
			@RequestParam(value = "removeMenuId") long removeMenuId,
			@ModelAttribute MenuEditCommand menuEditCommand,
			HttpServletRequest request) throws Exception {
		User user = PortalUtil.getAuthenticatedUser();

		cascadeRemoveMenu(menuEditCommand.getMenuId(), user.getLastVisitSiteId());

		menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
		menuEditCommand.setMode("");

		return "controlpanel/menu/edit";
	}

	public void cascadeRemoveMenu(int menuId, int siteId) throws Exception {
		List<Menu> menus = menuService.getMenus(siteId, menuId);

		for (Menu childMenu : menus) {
			cascadeRemoveMenu(childMenu.getMenuId(), siteId);
		}

		menuService.deleteMenu(menuId);
	}

	protected void initListInfo(MenuEditCommand command, int siteId)
			throws Exception {
		List<Menu> menus = menuService.getMenus(siteId, command.getMenuId());

		Vector<MenuDisplayCommand> vector = new Vector<MenuDisplayCommand>();

		for (Menu childMenu : menus) {
			MenuDisplayCommand display = new MenuDisplayCommand();

			display.setMenuId(childMenu.getMenuId());
			display.setMenuName(childMenu.getName());
			display.setSeqNum(Format.getInt(childMenu.getSeqNum()));
			display.setMenuWindowTarget(childMenu.getMenuWindowTarget());
			display.setMenuWindowMode(childMenu.getMenuWindowMode());
			display.setPublished(String.valueOf(childMenu.isPublished()));

			vector.add(display);
		}
		MenuDisplayCommand childrenMenus[] = new MenuDisplayCommand[vector.size()];

		vector.copyInto(childrenMenus);

		command.setChildrenMenus(childrenMenus);
	}
}