package com.taklip.yoda.service.impl;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ContentMapper;
import com.taklip.yoda.mapper.MenuMapper;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.DropDownMenu;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Menu;
import com.taklip.yoda.service.MenuService;
import com.taklip.yoda.common.contant.Constants;

@Transactional
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private ContentMapper contentMapper;

    public Menu addMenu(long siteId, String menuSetName, String menuName) {
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

        this.save(menuSet);

        return menuSet;
    }

    public Menu addMenu(
            long siteId, long menuParentId, long seqNum, String menuSetName,
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

        this.save(menu);

        return menu;
    }

    public void addMenu(
            long siteId, long menuId, String menuTitle, String menuName,
            String menuUrl, String menuWindowTarget, String menuWindowMode,
            boolean published, String menuType, long contentId, long itemId,
            long sectionId)
            throws SecurityException, Exception {
        Menu menu = new Menu();

        if (menuId != 0) {
            menu = this.getById(menuId);
        }

        menu.setTitle(menuTitle);
        menu.setName(menuName);
        // menu.setSetName(menuName);
        menu.setMenuUrl(menuUrl);
        menu.setMenuWindowTarget(menuWindowTarget);
        menu.setMenuWindowMode(menuWindowMode);
        menu.setPublished(published);
        menu.setContent(null);
        // menu.setItem(null);
        menu.setSeqNum(0);
        menu.setSiteId(siteId);
        menu.setMenuType(menuType);

        if (menuType.equals(Constants.MENU_CONTENT)) {
            Content content = contentMapper.selectById(contentId);
            menu.setContent(content);
        }

        // if (menuType.equals(Constants.MENU_ITEM)) {
        // if (Validator.isNotNull(itemId)) {
        // Item item = itemDAO.getItem(siteId, itemId);
        // menu.setItem(item);
        // }
        // }

        // if (menuType.equals(Constants.MENU_SECTION)) {
        // if (Validator.isNotNull(sectionId)) {
        // Section section = sectionDAO.getSectionBySiteId_SectionId(siteId, sectionId);
        // menu.setSection(section);
        // }
        // }

        this.updateById(menu);
    }

    public void deleteMenu(long menuId) {
        this.removeById(menuId);
    }

    @Transactional(readOnly = true)
    public Menu getMenu(long menuId) {
        return this.getById(menuId);
    }

    @Transactional(readOnly = true)
    public Menu getMenuBySiteIdMenuName(long siteId, String menuName) {
        return this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId).eq(Menu::getName, menuName));
    }

    @Transactional(readOnly = true)
    public Menu getMenu(String menuSetName) {
        return this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getSetName, menuSetName).eq(Menu::getParentId, 0)
                .orderByAsc(Menu::getSeqNum));
    }

    // @Transactional(readOnly = true)
    // public Menu getMenu(int siteId, int menuId) throws SecurityException,
    // Exception {
    // return menuMapper.getById(menuId);
    // }

    @Transactional(readOnly = true)
    public List<Menu> getMenus(String menuSetName, long menuParentId) {
        return this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSetName, menuSetName)
                .eq(Menu::getParentId, menuParentId).orderByAsc(Menu::getSeqNum));
    }

    @Transactional(readOnly = true)
    public List<Menu> getMenus(long site, long parentMenuId) {
        return this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, site).eq(Menu::getParentId, parentMenuId)
                .orderByAsc(Menu::getSeqNum));
    }

    // @Transactional(readOnly = true)
    // public List<Menu> getMenus(int siteId, int menuParentId) {
    // return menuMapper.getBySiteId_MenuParentId(siteId, menuParentId);
    // }

    @Transactional(readOnly = true)
    public long selectMaxSeqNumByMenuId_SiteId(long siteId, long parentMenuId) {
        return this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId).eq(Menu::getParentId, parentMenuId))
                .stream().mapToLong(Menu::getSeqNum).max().orElse(0);
    }

    public Menu updateMenu(
            long siteId, long menuId, Content content, Item item,
            String menuUrl, String menuWindowMode,
            String menuWindowTarget, String menuType)
            throws SecurityException, Exception {
        Menu menu = getMenu(menuId);

        menu.setContent(content);
        // menu.setItem(item);
        menu.setMenuUrl(menuUrl);
        menu.setMenuWindowMode(menuWindowMode);
        menu.setMenuWindowTarget(menuWindowTarget);
        menu.setMenuType(menuType);

        updateMenu(menu);

        return menu;
    }

    public void updateMenu(Menu menu) {
        this.updateById(menu);
    }

    public void updateSeqNum(long siteId, long menuParentId, long seqNum) {
        this.update(new LambdaUpdateWrapper<Menu>()
                .eq(Menu::getSiteId, siteId)
                .eq(Menu::getParentId, menuParentId)
                .ge(Menu::getSeqNum, seqNum)
                .setSql("seq_num = seq_num + 1"));
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
    public DropDownMenu[] makeMenuTreeList(long siteId)
            throws Exception {
        Vector<DropDownMenu> menuVector = new Vector<DropDownMenu>();

        menuVector.add(makeMenuTree(siteId, Constants.MENUSET_MAIN));
        // menuVector.add(makeMenuTree(siteId, Constants.MENUSET_SECONDARY));

        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId));

        for (Menu menu : menus) {
            if (menu.getSetName().equals(Constants.MENUSET_MAIN)) {
                continue;
            }

            // if (menu.getSetName().equals(Constants.MENUSET_SECONDARY)) {
            // continue;
            // }
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
    public DropDownMenu makeMenuTree(long siteId, String menuSetName)
            throws Exception {
        Menu menu = this.getOne(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId)
                .eq(Menu::getSetName, menuSetName).eq(Menu::getParentId, 0).orderByAsc(Menu::getSeqNum));

        DropDownMenu menus[] = makeMenu(siteId, menuSetName, menu.getId());
        DropDownMenu ddm = new DropDownMenu();

        ddm.setMenuName(menu.getSetName());
        ddm.setMenuKey(menu.getId());
        ddm.setMenuItems(menus);

        return ddm;
    }

    @Transactional(readOnly = true)
    public DropDownMenu[] makeMenu(
            long siteId, String menuSetName, long menuParentId)
            throws Exception {
        DropDownMenu menuArr[] = null;
        Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId)
                .eq(Menu::getSetName, menuSetName).eq(Menu::getParentId, menuParentId).orderByAsc(Menu::getSeqNum));

        for (Menu menu : menus) {
            DropDownMenu ddm = new DropDownMenu();

            ddm.setMenuKey(menu.getId());
            ddm.setMenuName(menu.getName());

            if (menu.getId() != 0) {
                DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName, menu.getId());
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
            long siteId, String menuSetName, long menuParentId)
            throws Exception {
        DropDownMenu menuArr[] = null;
        Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();

        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId)
                .eq(Menu::getSetName, menuSetName).eq(Menu::getParentId, menuParentId).orderByAsc(Menu::getSeqNum));

        for (Menu menu : menus) {
            DropDownMenu ddm = new DropDownMenu();

            ddm.setMenuKey(menu.getId());
            ddm.setMenuName(menu.getName());

            if (menu.getId() == 0) {
                DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName, menu.getId());

                ddm.setMenuItems(childMenus);
            }

            menuList.add(ddm);
        }

        menuArr = new DropDownMenu[menuList.size()];
        menuList.copyInto(menuArr);

        return menuArr;
    }

    @Transactional(readOnly = true)
    @Override
    public JSONObject makeJSONMenuTree(long siteId) throws Exception {
        JSONObject object = new JSONObject();

        List<Menu> menus = this.list(new LambdaQueryWrapper<Menu>().eq(Menu::getSiteId, siteId).eq(Menu::getParentId, 0)
                .orderByAsc(Menu::getSeqNum));

        Vector<JSONObject> menuSetVector = new Vector<JSONObject>();

        for (Menu menu : menus) {
            JSONObject menuSetObject = makeJSONMenuTreeNode(siteId, menu.getId());

            menuSetVector.add(menuSetObject);
        }

        object.put("menuSets", menuSetVector);

        return object;
    }

    @Transactional(readOnly = true)
    public JSONObject makeJSONMenuTreeNode(long siteId, long menuId)
            throws Exception {
        JSONObject jsonObject = new JSONObject();

        Menu menu = baseMapper.selectById(menuId);

        if (menu.getParentId() == 0) {
            jsonObject.put("menuSetName", menu.getSetName());
        } else {
            jsonObject.put("menuId", menu.getId());
            jsonObject.put("menuName", menu.getName());
        }

        List<Menu> menus = baseMapper.selectList(new LambdaQueryWrapper<Menu>()
                .eq(Menu::getSiteId, siteId)
                .eq(Menu::getParentId, menuId)
                .orderByAsc(Menu::getSeqNum));

        Vector<JSONObject> vector = new Vector<JSONObject>();

        for (Menu child : menus) {
            JSONObject object = makeJSONMenuTreeNode(siteId, child.getId());

            vector.add(object);
        }

        jsonObject.put("menus", vector);

        return jsonObject;
    }
}