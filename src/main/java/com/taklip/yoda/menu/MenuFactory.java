package com.taklip.yoda.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.Menu;
import com.taklip.yoda.model.MenuInfo;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.MenuService;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.contant.StringPool;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Deprecated
@Component
public class MenuFactory {
    @Autowired
    protected MenuService menuService;

    @Autowired
    protected SiteService siteService;

    // private static MenuFactory _instance = new MenuFactory();

    private static String horizontalMenu;

    public static String getHorizontalMenu(
            HttpServletRequest request, HttpServletResponse response) {
        return getHorizontalMenu(request, response);
    }

    public static void setHorizontalMenu(String horizontalMenu) {
        setHorizontalMenu(horizontalMenu);
    }

    public static void clear() {
        horizontalMenu = null;
    }

    private String initHorizontalMenu(HttpServletRequest request, HttpServletResponse response) {
        String menuSetName = "MAIN";
        String styleClassSuffix = "";

        // Site site = PortalUtil.getSite(request);

        List<MenuInfo> menus = getMenu(menuSetName);

        String horizontalMenuCode = MenuGenerator.generateMenu(menus, styleClassSuffix, request.getLocale());

        return horizontalMenuCode;
    }

    // public String getTemplate(
    // HttpServletRequest request, HttpServletResponse response,
    // VelocityEngine engine, String templateName, Map<String, Object> model) {
    // ComponentInfo componentInfo = new ComponentInfo();
    //
    // componentInfo.setContextPath(ServletContextUtil.getContextPath());
    //
    // model.put("componentInfo", componentInfo);
    //
    // model.put(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE,
    // new RequestContext(request, response, ServletContextUtil.getServletContext(),
    // model));
    //
    // return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName,
    // "UTF-8", model);
    // }

    public List<MenuInfo> getMenu(String menuSetName) {
        Menu parent = menuService.getMenu(menuSetName);

        return getMenus(menuSetName, parent.getId());
    }

    private List<MenuInfo> getMenus(String menuSetName, long menuParentId) {
        List<MenuInfo> menuInfos = new ArrayList<MenuInfo>();

        List<Menu> menus = menuService.getMenus(menuSetName, menuParentId);

        for (Menu menu : menus) {
            MenuInfo menuInfo = new MenuInfo();

            menuInfo.setMenuTitle(menu.getTitle());
            menuInfo.setMenuName(menu.getName());
            menuInfo.setSeqNo(menu.getSeqNum());
            menuInfo.setMenuWindowMode(menu.getMenuWindowMode());
            menuInfo.setMenuWindowTarget(menu.getMenuWindowTarget());
            menuInfo.setMenus(getMenus(menuSetName, menu.getId()));

            // NOT GOOD!!!
            List<Site> sites = siteService.getSites();
            String publicURLPrefix = getPublicURLPrefix(sites.get(0));

            String frontEndUrlPrefix = Constants.FRONTEND_URL_PREFIX;

            if (StringUtils.isNoneBlank(frontEndUrlPrefix)) {
                frontEndUrlPrefix = StringPool.SLASH + frontEndUrlPrefix;
            }

            // String contextPath = ServletContextUtil.getContextPath();
            String contextPath = "";

            String menuAnchor = null;
            String url = null;
            String menuName = null;

            if (menu.getMenuType().equals(Constants.MENU_STATIC_URL) && menu.getMenuUrl() != null) {
                url = menu.getMenuUrl();
            } else if (menu.getMenuType().equals(Constants.MENU_HOME)) {
                url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getName();
            } else if (menu.getMenuType().equals(Constants.MENU_CONTENT) && menu.getContent() != null) {
                Content content = menu.getContent();

                url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getName()
                        + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getId();
            } else if (menu.getMenuType().equals(Constants.MENU_CONTACTUS)) {
                url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getName();
            } else if (menu.getMenuType().equals(Constants.MENU_SIGNIN)) {
                url = getSecureURLPrefix(sites.get(0)) + contextPath + StringPool.SLASH + "/account/login/accountLogin";
            } else if (menu.getMenuType().equals(Constants.MENU_SIGNOUT)) {
                url = getSecureURLPrefix(sites.get(0)) + contextPath + "/account/login/accountLogout";
            }

            if (url == null) {
                url = publicURLPrefix + contextPath + "/";
            }

            menuInfo.setMenuUrl(url);
            menuName = menu.getName();

            menuAnchor = "<a href=\"" + url + "\"" + "onclick=\"javascrpt:window.open('" + url + "', " + "'"
                    + menu.getMenuWindowTarget() + "' ";

            if (menu.getMenuWindowMode().trim().length() != 0) {
                menuAnchor += ", '" + menu.getMenuWindowMode() + "'";
            }

            menuAnchor += ");return false;\">";
            menuAnchor += menuName;
            menuAnchor += "</a>";
            menuInfo.setMenuAnchor(menuAnchor);

            menuInfos.add(menuInfo);
        }

        return menuInfos;
    }

    private String getSecureURLPrefix(Site site) {
        String secureURLPrefix = getPublicURLPrefix(site);

        boolean isSecureEnabled = site.isSecureConnectionEnabled();

        if (isSecureEnabled) {
            secureURLPrefix = "https://" + site.getDomainName();

            String defaultPortNum = Constants.PORTNUM_SECURE;

            if (StringUtils.isNotBlank(site.getSecurePort())
                    && site.getSecurePort() != defaultPortNum) {
                secureURLPrefix += ":" + site.getSecurePort();
            }
        }

        return secureURLPrefix;
    }

    private String getPublicURLPrefix(Site site) {
        String defaultPortNum = Constants.PORTNUM_PUBLIC;

        String publicURLPrefix = "http://" + site.getDomainName();

        if (StringUtils.isNotBlank(site.getPublicPort())
                && !site.getPublicPort().equals(defaultPortNum)) {
            publicURLPrefix += ":" + site.getPublicPort();
        }

        return publicURLPrefix;
    }

    private String _getHorizontalMenu(
            HttpServletRequest request, HttpServletResponse response) {
        if (horizontalMenu != null) {
            return horizontalMenu;
        }

        horizontalMenu = initHorizontalMenu(request, response);

        return horizontalMenu;
    }

    private void _setHorizontalMenu(String horizontalMenu) {
        horizontalMenu = horizontalMenu;
    }
}