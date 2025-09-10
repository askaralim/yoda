package com.taklip.yoda.service;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.DropDownMenu;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Menu;
import com.taklip.yoda.dto.ContentDTO;

public interface MenuService extends IService<Menu> {
        Menu getMenu(long menuId);

        Menu getMenu(String menuSetName);

        Menu getMenuBySiteIdMenuName(long siteId, String menuName);

        // Menu getMenu(int siteId, int menuId) throws SecurityException, Exception;

        List<Menu> getMenus(String menuSetName, long menuParentId);

        // List<Menu> getBySiteIdMenuParentId(int siteId, int menuParentId);

        String formatMenuName(long siteId, long menuId) throws Exception;

        DropDownMenu[] makeMenuTreeList(long siteId) throws Exception;

        DropDownMenu makeMenuTree(long siteId, String menuSetName) throws Exception;

        DropDownMenu[] makeMenu(long siteId, String menuSetName, long menuParentId)
                        throws Exception;

        DropDownMenu[] makeDdmMenu(long siteId, String menuSetName, long menuParentId)
                        throws Exception;

        Menu addMenu(long siteId, String menuSetName, String menuName);

        void addMenu(long siteId, long menuId, String menuTitle, String menuName, String menuUrl,
                        String menuWindowTarget, String menuWindowMode, boolean published,
                        String menuType, long contentId, long itemId, long sectionId)
                        throws SecurityException, Exception;

        void deleteMenu(long menuId);

        List<Menu> getMenus(long siteId, long parentMenuId);

        long selectMaxSeqNumByMenuId_SiteId(long siteId, long parentMenuId);

        void updateSeqNum(long siteId, long menuParentId, long seqNum);

        Menu addMenu(long siteId, long menuParentId, long seqNum, String menuSetName,
                        String menuTitle, String menuName, String menuType, String menuUrl,
                        String menuWindowTarget, String menuWindowMode, boolean published);

        void updateMenu(Menu menu);

        public JSONObject makeJSONMenuTree(long siteId) throws Exception;

        public JSONObject makeJSONMenuTreeNode(long siteId, long menuId) throws Exception;

        Menu updateMenu(long lastVisitSiteId, long i, ContentDTO content, Item item, String string,
                        String menuWindowMode, String menuWindowTarget, String menuContent)
                        throws SecurityException, Exception;
}
