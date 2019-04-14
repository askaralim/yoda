package com.yoda.portal.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.homepage.service.HomePageService;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.portal.content.data.HomeInfo;
import com.yoda.site.model.Site;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;

@Controller
@RequestMapping("/")
public class FrontendHomeController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontendHomeController.class);

	@Autowired
	ContentService contentService;

	@Autowired
	BrandService brandService;

	@Autowired
	ItemService itemService;

	@Autowired
	protected HomePageService homePageService;

//	@Autowired
//	protected MenuService menuService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		return process(Constants.MENU_HOME, request, response);
	}

	@Deprecated
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

//			Menu menu = menuService.getMenuBySiteIdMenuName(
//				site.getSiteId(), menuName);
//
//			if (Validator.isNull(menu)) {
//				return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
//			}

			HomeInfo homeInfo = getHome(site.getSiteId());

			List<Item> items = itemService.getItemsTopViewed(8);
			List<Brand> brands = brandService.getBrandsTopViewed(8);

			modelMap.put("topViewedItems", items);
			modelMap.put("topViewedBrands", brands);
			modelMap.put("homeInfo", homeInfo);

			String horizontalMenu = getHorizontalMenu(request, response);

			modelMap.put("horizontalMenu", horizontalMenu);
			modelMap.put("site", site);
//			modelMap.put("pageInfo", pageInfo);
			modelMap.put("pageTitle", site.getSiteName() + " | " + "小白的购物懒人包、生活方式的供应商");
			modelMap.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
			modelMap.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
			modelMap.put("url", request.getRequestURL().toString());
			modelMap.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
			modelMap.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
		}
		catch (Exception e) {
			logger.error(e);
		}

		return new ModelAndView("portal/home/home", modelMap);
	}

	public HomeInfo getHome(int siteId) {
		HomeInfo homeInfo = new HomeInfo();

		List<Content> contents = contentService.getContentsFeatureData();
//		List<HomePage> homePages = homePageService.getHomePagesBySiteIdAndFeatureData(siteId);

//		for (HomePage homePage : homePages) {
//			if (homePage.getContent() != null) {
//				Content content = contentService.getContent(homePage.getContent().getContentId());
//
//				if (!Utility.isContentPublished(content)) {
//					break;
//				}
//
//				Content contentInfo = formatContent(content);
//
//				contentInfo.setFeature(true);
//
//				homeInfo.setHomePageFeatureData(contentInfo);
//			}
//		}

		for (Content content : contents) {
			if (!Utility.isContentPublished(content)) {
				break;
			}

			String contentUrl = ServletContextUtil.getContextPath() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

			content.setContentUrl(contentUrl);

			homeInfo.setHomePageFeatureData(content);
		}

		List<Content> dataInfos = new ArrayList<Content>();
		Pagination<Content> page = new Pagination<>();

		page = contentService.getContentsNotFeatureData(0, 4);
//		List<String> ids = getHomePagesFromCache(0, 4);
//
//		if (null == ids || ids.isEmpty()) {
//			ids = new ArrayList<>();
//
//			page = homePageService.getHomePagesBySiteIdAndFeatureDataNotY(siteId, new RowBounds(0, 4));
//
//			homePages = page.getData();
//
//			for (HomePage homePage : homePages) {
//				if (homePage.getContent() != null) {
//					String hopePageId = String.valueOf(homePage.getContent().getContentId());
//
//					ids.add(hopePageId);
//
//					redisService.listAddAsLast(Constants.REDIS_HOME_PAGE_CONTENT_ID_LIST, hopePageId);
//				}
//			}
//		}

		contents = page.getData();

		for (Content content : contents) {
			if (!Utility.isContentPublished(content)) {
				continue;
			}

			String contentUrl = ServletContextUtil.getContextPath() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

			content.setContentUrl(contentUrl);

			dataInfos.add(content);
		}

		homeInfo.setHomePageDatas(dataInfos);
		homeInfo.setPage(page);

		return homeInfo;
	}

//	public PageInfo getHome(
//			HttpServletRequest request, HttpServletResponse response) {
//		Site site = getSite(request);
//
////		PageInfo pageInfo = new PageInfo();
//
//		HomeInfo homeInfo = getHome(site.getSiteId());
//
//		Map<String, Object> model = new HashMap<String, Object>();
//
//		List<Item> items = itemService.getItemsTopViewed(8);
//		List<Brand> brands = brandService.getBrandsTopViewed(8);
//
//		model.put("topViewedItems", items);
//		model.put("topViewedBrands", brands);
//		model.put("homeInfo", homeInfo);
//
//		try {
//			model.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
//
//			String text = DefaultTemplateEngine.getTemplate(request, response, "home/home.vm", model);
//
//			pageInfo.setPageBody(text);
//
//			String pageTitle = homeInfo.getPageTitle();
//
//			if (Validator.isNotNull(pageTitle)) {
//				pageInfo.setPageTitle(pageTitle + " - " + site.getSiteName());
//			}
//			else {
//				pageInfo.setPageTitle(site.getSiteName() + " | " + "小白的购物懒人包、生活方式的供应商");
//				pageInfo.setKeywords("如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
//				pageInfo.setAttribute("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
//				pageInfo.setAttribute("url", request.getRequestURL().toString());
//				pageInfo.setAttribute("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
//			}
//		}
//		catch (UnsupportedEncodingException e) {
//			logger.error(e.getMessage());
//		}
//
//		return pageInfo;
//	}

//	public PageInfo getContent(
//			HttpServletRequest request, HttpServletResponse response,
//			Content content) {
//		Site site = getSite(request);
//
//		PageInfo pageInfo = new PageInfo();
//
//		Content contentInfo = processContent(site.getSiteId(), content);
//
//		Map<String, Object> model = new HashMap<String, Object>();
//
//		model.put("contentInfo", content);
//
//		String text = StringPool.BLANK;
//
//		if (contentInfo != null) {
//			text = DefaultTemplateEngine.getTemplate(request, response, "content/content.vm", model);
//
//			pageInfo.setPageTitle(site.getSiteName() + " - " + contentInfo.getPageTitle());
//		}
//		else {
//			text = DefaultTemplateEngine.getTemplate(request, response, "messages/moved.vm", model);
//
//			pageInfo.setPageTitle(site.getSiteName() + " - " + "Page not found");
//		}
//
//		pageInfo.setPageBody(text);
//
//		return pageInfo;
//	}

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