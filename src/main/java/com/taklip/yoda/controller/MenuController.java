package com.taklip.yoda.controller;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.Menu;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.MenuService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.contant.StringPool;
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.vo.MenuDisplayCommand;
import com.taklip.yoda.vo.MenuEditCommand;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class MenuController {
    @Autowired
    ContentService contentService;

    @Autowired
    MenuService menuService;

    @Autowired
    ItemService itemService;

    @PostMapping("/controlpanel/menu/create")
    public String create(
            @ModelAttribute MenuEditCommand menuEditCommand,
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        long siteId = user.getLastVisitSiteId();

        Menu referenceMenu = menuService.getMenu(menuEditCommand.getCreateMenuId());

        long menuParentId = 0;

        long seqNum = 0;

        if (menuEditCommand.getCreateMode().equals("C")) { // append child node
            menuParentId = referenceMenu.getId();

            long menuSeqNum = menuService.selectMaxSeqNumByMenuId_SiteId(siteId, referenceMenu.getId());

            if (menuSeqNum == 0) {
                seqNum = 0;
            } else {
                seqNum = menuSeqNum + 1;
            }
        } else if (menuEditCommand.getCreateMode().equals("B")) { // before current
            menuParentId = referenceMenu.getParentId();

            seqNum = referenceMenu.getSeqNum();

            menuService.updateSeqNum(siteId, referenceMenu.getParentId(), referenceMenu.getSeqNum());
        } else if (menuEditCommand.getCreateMode().equals("A")) { // after current
            menuParentId = referenceMenu.getParentId();

            seqNum = referenceMenu.getSeqNum() + 1;

            menuService.updateSeqNum(siteId, referenceMenu.getParentId(), referenceMenu.getSeqNum());
        }

        Menu menu = menuService.addMenu(
                siteId, menuParentId, seqNum, referenceMenu.getSetName(),
                "New Menu", "New Menu", Constants.MENU_HOME, StringPool.BLANK, StringPool.BLANK,
                StringPool.BLANK, true);

        menuEditCommand.setMenuId(menu.getId());
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
        // menuEditCommand.setPublished(String.valueOf(Constants.PUBLISHED_YES));
        menuEditCommand.setSequence(false);
        menuEditCommand.setMode(Constants.MODE_CREATE);

        initListInfo(menuEditCommand, siteId);

        return "controlpanel/menu/edit";
    }

    @PostMapping("/controlpanel/menu/showsequence")
    public String showSequence(
            @RequestParam int menuId,
            @ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
            SessionStatus status,
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

        Menu menu = menuService.getMenu(menuId);

        menuEditCommand.setMenuId(menuId);

        if (menu.getParentId() != 0) {
            menuEditCommand.setMenuParentId(menu.getParentId());
        }

        menuEditCommand.setMenuId(menu.getId());
        menuEditCommand.setMenuSetName(menu.getSetName());
        menuEditCommand.setMenuName(menu.getName());
        menuEditCommand.setMenuType(menu.getMenuType());
        menuEditCommand.setMenuUrl(menu.getMenuUrl());
        menuEditCommand.setMenuWindowTarget(menu.getMenuWindowTarget());
        menuEditCommand.setMenuWindowMode(menu.getMenuWindowMode());
        menuEditCommand.setPublished(menu.isPublished());
        menuEditCommand.setMode(Constants.MODE_UPDATE);
        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
        // menuEditCommand.setPublished(true);
        menuEditCommand.setMode(Constants.MODE_UPDATE);
        menuEditCommand.setSequence(true);

        initListInfo(menuEditCommand, user.getLastVisitSiteId());

        return "controlpanel/menu/edit";
    }

    @PostMapping("/controlpanel/menu/removeselected")
    public String removeSelected(
            @ModelAttribute MenuEditCommand menuEditCommand,
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        MenuDisplayCommand childrenMenus[] = menuEditCommand.getChildrenMenus();

        for (int i = 0; i < childrenMenus.length; i++) {
            if (childrenMenus[i].isRemove()) {
                long menuId = childrenMenus[i].getMenuId();

                cascadeRemoveMenu(menuId, user.getLastVisitSiteId());
            }
        }

        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

        initListInfo(menuEditCommand, user.getLastVisitSiteId());

        menuEditCommand.setSequence(true);

        return "controlpanel/menu/edit";
    }

    @PostMapping("/controlpanel/menu/resequence")
    public String resequence(
            @ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
            SessionStatus status,
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        if (result.hasErrors()) {
            menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
            menuEditCommand.setSequence(true);

            return "controlpanel/menu/edit";
        }

        return "controlpanel/menu/edit";
    }

    @GetMapping("/controlpanel/menu/edit")
    public ModelAndView setupForm(
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {

        MenuEditCommand command = new MenuEditCommand();

        User user = AuthenticatedUtil.getAuthenticatedUser();

        command.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
        command.setMode("");

        return new ModelAndView("controlpanel/menu/edit", "menuEditCommand", command);
    }

    @PostMapping("/controlpanel/menu/newmenuset")
    public String newMenuSet(
            @Valid @ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
            SessionStatus status,
            HttpServletRequest request, HttpServletResponse response)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        long siteId = user.getLastVisitSiteId();

        menuService.addMenu(
                siteId, menuEditCommand.getCreateMenuSetName(),
                menuEditCommand.getCreateMenuSetName());

        menuEditCommand.setCreateMenuSetName("");
        menuEditCommand.setMenuList(menuService.makeMenuTreeList(siteId));
        menuEditCommand.setMenuId(0);
        menuEditCommand.setMode("");

        return "controlpanel/menu/edit";
    }

    @GetMapping("/controlpanel/menu/update/{menuId}")
    public ModelAndView updateMenu(
            @PathVariable int menuId,
            @ModelAttribute MenuEditCommand menuEditCommand,
            // BindingResult result, SessionStatus status,
            HttpServletRequest request)
            throws Throwable {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));

        Menu menu = menuService.getMenu(menuId);

        menuEditCommand.setMenuId(menuEditCommand.getMenuId());

        if (menu.getParentId() != 0) {
            menuEditCommand.setMenuParentId(menu.getParentId());
        }

        menuEditCommand.setMenuId(menu.getId());
        menuEditCommand.setMenuSetName(menu.getSetName());
        menuEditCommand.setMenuTitle(menu.getTitle());
        menuEditCommand.setMenuName(menu.getName());
        menuEditCommand.setMenuUrl(menu.getMenuUrl());
        menuEditCommand.setMenuWindowTarget(menu.getMenuWindowTarget());
        menuEditCommand.setMenuWindowMode(menu.getMenuWindowMode());
        menuEditCommand.setPublished(menu.isPublished());
        menuEditCommand.setMode(Constants.MODE_UPDATE);
        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
        // menuEditCommand.setPublished(true);
        menuEditCommand.setSequence(false);
        menuEditCommand.setMode(Constants.MODE_UPDATE);

        menuEditCommand.setMenuType(menu.getMenuType());

        if (menu.getMenuType().equals(Constants.MENU_CONTENT)
                && menu.getContent() != null) {
            menuEditCommand.setContentId(menu.getContent().getId());
            menuEditCommand.setContentTitle(menu.getContent().getTitle());
        }

        initListInfo(menuEditCommand, user.getLastVisitSiteId());

        return new ModelAndView("controlpanel/menu/edit", "menuEditCommand", menuEditCommand);
    }

    @PostMapping("/controlpanel/menu/save")
    public String saveMenu(
            @Valid @ModelAttribute MenuEditCommand menuEditCommand, BindingResult result,
            SessionStatus status,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = AuthenticatedUtil.getAuthenticatedUser();

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

        return "controlpanel/menu/edit";
    }

    @PostMapping("/controlpanel/menu/removemenuset")
    public String removeMenuSet(
            @RequestParam int removeMenuId,
            @ModelAttribute MenuEditCommand menuEditCommand,
            HttpServletRequest request) throws Exception {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        // Menu menu = menuService.getMenu(user.getLastVisitSiteId(), removeMenuId);

        menuService.deleteMenu(removeMenuId);

        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
        menuEditCommand.setMenuId(0);
        menuEditCommand.setMode("");

        return "controlpanel/menu/edit";
    }

    @PostMapping("/controlpanel/menu/remove")
    public String remove(
            @RequestParam long removeMenuId,
            @ModelAttribute MenuEditCommand menuEditCommand,
            HttpServletRequest request) throws Exception {
        User user = AuthenticatedUtil.getAuthenticatedUser();

        cascadeRemoveMenu(menuEditCommand.getMenuId(), user.getLastVisitSiteId());

        menuEditCommand.setMenuList(menuService.makeMenuTreeList(user.getLastVisitSiteId()));
        menuEditCommand.setMode("");

        return "controlpanel/menu/edit";
    }

    public void cascadeRemoveMenu(long menuId, long siteId) throws Exception {
        List<Menu> menus = menuService.getMenus(siteId, menuId);

        for (Menu childMenu : menus) {
            cascadeRemoveMenu(childMenu.getId(), siteId);
        }

        menuService.deleteMenu(menuId);
    }

    protected void initListInfo(MenuEditCommand command, long siteId)
            throws Exception {
        List<Menu> menus = menuService.getMenus(siteId, command.getMenuId());

        Vector<MenuDisplayCommand> vector = new Vector<MenuDisplayCommand>();

        for (Menu childMenu : menus) {
            MenuDisplayCommand display = new MenuDisplayCommand();

            display.setMenuId(childMenu.getId());
            display.setMenuName(childMenu.getName());
            display.setSeqNum(String.valueOf(childMenu.getSeqNum()));
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