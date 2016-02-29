package com.yoda.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.contactus.model.ContactUs;
import com.yoda.content.model.Content;
import com.yoda.menu.model.Menu;
import com.yoda.portal.content.data.ContentInfo;
import com.yoda.portal.content.data.DefaultTemplateEngine;
import com.yoda.portal.content.data.HomeInfo;
import com.yoda.portal.content.data.PageInfo;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/")
public class FrontendController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontendController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		return process(Constants.MENU_HOME, request, response);
	}

	@RequestMapping(value="/{menuName}" ,method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("menuName") String menuName,
			HttpServletRequest request, HttpServletResponse response) {
		return process(menuName, request, response);
	}

	public ModelAndView process(
			String menuName, HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();

		try {
			Site site = getSite(request);

			SiteInfo siteInfo = getSite(site);

			Menu menu = menuService.getMenuBySiteIdMenuName(
				site.getSiteId(), menuName);

			if (Validator.isNull(menu)) {
				return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
			}

			PageInfo pageInfo = fetchPageInfo(request, response, menu);

			String horizontalMenu = getHorizontalMenu(request, response);

			modelMap.put("horizontalMenu", horizontalMenu);
			modelMap.put("siteInfo", siteInfo);
			modelMap.put("pageInfo", pageInfo);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}

		return new ModelAndView("template", modelMap);
	}

	public PageInfo fetchPageInfo(
			HttpServletRequest request, HttpServletResponse response, Menu menu)
		throws Exception {
		if (menu.getMenuType().equals(Constants.MENU_SECTION)) {
//			Section section = menu.getSection();

//			return getSection(request, response, section);
			return null;
		}
		else if (menu.getMenuType().equals(Constants.MENU_CONTACTUS)) {
			return getContactUs(request, response);
		}
		else if (menu.getMenuType().equals(Constants.MENU_CONTENT)) {
			Content content = menu.getContent();

			return getContent(request, response, content);
		}
		else {
			return getHome(request, response);
		}
	}

	public PageInfo getHome(
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		PageInfo pageInfo = new PageInfo();

		HomeInfo homeInfo = getHome(site.getSiteId());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("homeInfo", homeInfo);

		String text = DefaultTemplateEngine.getTemplate(request, response, "home/home.vm", model);

		pageInfo.setPageBody(text);

		String pageTitle = homeInfo.getPageTitle();

		if (Validator.isNotNull(pageTitle)) {
			pageInfo.setPageTitle(site.getSiteName() + " - " + pageTitle);
		}
		else {
			pageInfo.setPageTitle(site.getSiteName());
		}

		return pageInfo;
	}

	public PageInfo getContent(
			HttpServletRequest request, HttpServletResponse response,
			Content content) {
		Site site = getSite(request);

		PageInfo pageInfo = new PageInfo();

		boolean updateStatistics = true;

		boolean checkExpiry = true;

		ContentInfo contentInfo = getContent(site.getSiteId(), content, checkExpiry, updateStatistics);

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("contentInfo", contentInfo);

		String text = StringPool.BLANK;

		if (contentInfo != null) {
			text = DefaultTemplateEngine.getTemplate(request, response, "content/content.vm", model);

			pageInfo.setPageTitle(site.getSiteName() + " - " + contentInfo.getPageTitle());
		}
		else {
			text = DefaultTemplateEngine.getTemplate(request, response, "messages/moved.vm", model);

			pageInfo.setPageTitle(site.getSiteName() + " - " + "Page not found");
		}

		pageInfo.setPageBody(text);

		return pageInfo;
	}

//	public PageInfo getSection(
//			HttpServletRequest request, HttpServletResponse response,
//			Section section) throws Exception {
//		Site site = getSite(request);
//
//		PageInfo pageInfo = new PageInfo();
//
//		String topSectionNaturalKey = section.getNaturalKey();
//		String sectionNaturalKey = section.getNaturalKey();
//
//		String value = getCategoryParameter(request, 4);
//
//		if (value == null) {
//			value = "1";
//		}
//
//		int pageNum = Format.getInt(value);
//
//		String sortBy = getCategoryParameter(request, 5);
//
//		SectionInfo sectionInfo =
//			getSection(site.getSiteId(),
//				sectionNaturalKey, topSectionNaturalKey, Integer.valueOf(site.getListingPageSize()),
//				Constants.PAGE_NAV_COUNT, pageNum, sortBy);
//
//		Map<String, Object> model = new HashMap<String, Object>();
//
//		model.put("sectionInfo", sectionInfo);
//
//		String text = DefaultTemplateEngine.getTemplate(request, response, "section/section.vm", model);
//
//		pageInfo.setPageBody(text);
//		pageInfo.setPageTitle(site.getSiteName() + " - " + sectionInfo.getSectionTitle());
//
//		return pageInfo;
//	}

	public PageInfo getContactUs(
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		PageInfo pageInfo = new PageInfo();

		List<ContactUs> contactUsList = getContactUs(site.getSiteId());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put("contactUsList", contactUsList);

		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

		if (csrfToken != null) {
			model.put("_csrf", csrfToken);
		}

		String text = DefaultTemplateEngine.getTemplate(request, response, "contactUs/contactUs.vm", model);

		pageInfo.setPageBody(text);
		pageInfo.setPageTitle(site.getSiteName() + " - " + "Contact Us");

		return pageInfo;
	}

	public String getCategory(HttpServletRequest request) {
		String category = request.getRequestURI();
//		String prefix = "/" + ApplicationGlobal.getContextPath() + Constants.FRONTEND_URL_PREFIX;
		String prefix = "/" + Constants.FRONTEND_URL_PREFIX;

		if (!category.matches(prefix + "/.*")) {
			return null;
		}

		category = category.substring(prefix.length());

		return category;
	}

	public String getCategoryName(HttpServletRequest request) {
		String category = getCategory(request);

		if (category == null) {
			return null;
		}

		String tokens[] = category.split("/");

		return tokens[1];
	}

	public String getCategoryParameter(HttpServletRequest request, int pos) {
		String category = getCategory(request);
		String tokens[] = category.split("/");

		if (pos > tokens.length - 1) {
			return null;
		}

		return tokens[pos];
	}
}