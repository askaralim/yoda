package com.yoda.portal.content.frontend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.yoda.content.model.Content;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.service.MenuService;
import com.yoda.portal.content.data.ComponentInfo;
import com.yoda.portal.content.data.MenuInfo;
import com.yoda.section.model.Section;
import com.yoda.site.model.Site;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

public class MenuFactory {

	private static MenuFactory _instance = new MenuFactory();

	private String _horizontalMenu;

	public static String getHorizontalMenu(
			HttpServletRequest request, HttpServletResponse response) {
		return _instance._getHorizontalMenu(request, response);
	}

	public static void setHorizontalMenu(String horizontalMenu) {
		_instance._setHorizontalMenu(horizontalMenu);
	}

	public static void clear() {
		_instance._clear();
	}

	private String initHorizontalMenu(HttpServletRequest request, HttpServletResponse response) {
		String menuSetName = "MAIN";
		String styleClassSuffix = "";

		Site site = PortalUtil.getSite(request);

		List<MenuInfo> menus = getMenu(site, menuSetName);

		String horizontalMenuCode = MenuGenerator.generateMenu(menus, styleClassSuffix, request.getLocale());

		return horizontalMenuCode;
	}

	public String getTemplate(
			HttpServletRequest request, HttpServletResponse response,
			VelocityEngine engine, String templateName, Map<String, Object> model) {
		ComponentInfo componentInfo = new ComponentInfo();

		componentInfo.setContextPath(ServletContextUtil.getContextPath());

		model.put("componentInfo", componentInfo);

		model.put(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE,
			new RequestContext(request, response, ServletContextUtil.getServletContext(), model));

		return VelocityEngineUtils.mergeTemplateIntoString(engine, templateName, "UTF-8", model);
	}

	public List<MenuInfo> getMenu(Site site, String menuSetName) {
		Menu parent = getMenuService().getMenu(site.getSiteId(), menuSetName);

		return getMenus(site, menuSetName, parent.getMenuId());
	}

	private List<MenuInfo> getMenus(
			Site site, String menuSetName, Long menuParentId)
		{
		List<MenuInfo> menuInfos = new ArrayList<MenuInfo>();

		List<Menu> menus = getMenuService().getMenu(site.getSiteId(), menuSetName, menuParentId);

		for (Menu menu : menus) {
			MenuInfo menuInfo = new MenuInfo();

			menuInfo.setMenuTitle(menu.getMenuTitle());
			menuInfo.setMenuName(menu.getMenuName());
			menuInfo.setSeqNo(menu.getSeqNum());
			menuInfo.setMenuWindowMode(menu.getMenuWindowMode());
			menuInfo.setMenuWindowTarget(menu.getMenuWindowTarget());
			menuInfo.setMenus(getMenus(site, menuSetName, menu.getMenuId()));

			String publicURLPrefix = getPublicURLPrefix(site);

			String frontEndUrlPrefix = Constants.FRONTEND_URL_PREFIX;

			if (Validator.isNotNull(frontEndUrlPrefix)) {
				frontEndUrlPrefix = StringPool.SLASH + frontEndUrlPrefix;
			}

			String contextPath = ServletContextUtil.getContextPath();

			String menuAnchor = null;
			String url = null;
			String menuName = null;

			if (menu.getMenuType().equals(Constants.MENU_STATIC_URL) && menu.getMenuUrl() != null) {
				url = menu.getMenuUrl();
			}
			else if (menu.getMenuType().equals(Constants.MENU_HOME)) {
				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName();
			}
			else if (menu.getMenuType().equals(Constants.MENU_CONTENT) && menu.getContent() != null) {
				Content content = menu.getContent();

				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();
			}
			else if (menu.getMenuType().equals(Constants.MENU_SECTION) && menu.getSection() != null) {
				Section section = menu.getSection();

				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName() + StringPool.SLASH + Constants.FRONTEND_URL_SECTION + StringPool.SLASH + section.getSectionId();
			}
			else if (menu.getMenuType().equals(Constants.MENU_CONTACTUS)) {
				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName();
			}
			else if (menu.getMenuType().equals(Constants.MENU_SIGNIN)) {
				url = getSecureURLPrefix(site) + contextPath + StringPool.SLASH + "/account/login/accountLogin";
			}
			else if (menu.getMenuType().equals(Constants.MENU_SIGNOUT)) {
				url = getSecureURLPrefix(site) + contextPath + "/account/login/accountLogout";
			}

			if (url == null) {
				url = publicURLPrefix + contextPath + "/";
			}

			menuInfo.setMenuUrl(url);
			menuName = menu.getMenuName();

			menuAnchor = "<a href=\"" + url + "\"" + "onclick=\"javascrpt:window.open('" + url + "', " + "'" + menu.getMenuWindowTarget() + "' ";

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

			if (Validator.isNotNull(site.getSecurePort())
				&& site.getSecurePort() != defaultPortNum) {
				secureURLPrefix += ":" + site.getSecurePort();
			}
		}

		return secureURLPrefix;
	}

	private String getPublicURLPrefix(Site site) {
		String defaultPortNum = Constants.PORTNUM_PUBLIC;

		String publicURLPrefix = "http://" + site.getDomainName();

		if (Validator.isNotNull(site.getPublicPort())
			&& !site.getPublicPort().equals(defaultPortNum)) {
			publicURLPrefix += ":" + site.getPublicPort();
		}

		return publicURLPrefix;
	}

	public MenuService getMenuService() {
		MenuService menuService = (MenuService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("menuServiceImpl");

		return menuService;
	}

	private String _getHorizontalMenu(
			HttpServletRequest request, HttpServletResponse response) {
		if(_horizontalMenu != null) {
			return _horizontalMenu;
		}

		_horizontalMenu = _instance.initHorizontalMenu(request, response);

		return _horizontalMenu;
	}

	private void _setHorizontalMenu(String horizontalMenu) {
		_horizontalMenu = horizontalMenu;
	}

	public void _clear() {
		_horizontalMenu = null;
	}
}