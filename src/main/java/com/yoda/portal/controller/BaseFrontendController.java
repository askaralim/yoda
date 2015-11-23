package com.yoda.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;

import com.yoda.contactus.model.ContactUs;
import com.yoda.contactus.service.ContactUsService;
import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.kernal.util.WebKeys;
import com.yoda.menu.service.MenuService;
import com.yoda.portal.content.data.ContentInfo;
import com.yoda.portal.content.data.DataInfo;
import com.yoda.portal.content.data.DefaultTemplateEngine;
import com.yoda.portal.content.data.HomeInfo;
import com.yoda.portal.content.data.SectionInfo;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.portal.content.frontend.MenuFactory;
import com.yoda.section.model.Section;
import com.yoda.section.service.SectionService;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

public class BaseFrontendController {
//	@Autowired
//	private SectionService sectionService;

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected ItemService itemService;

	@Autowired
	protected MenuService menuService;

	@Autowired
	private HomePageService homePageService;

	@Autowired
	private ContactUsService contactUsService;

	@Autowired
	private SiteService siteService;

	Logger logger = Logger.getLogger(BaseFrontendController.class);

	public String getHorizontalMenu(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails)auth.getPrincipal();

			model.put("userLogin", true);
			model.put("username", userDetail.getUsername());

			if (PortalUtil.isAdminRole(userDetail)) {
				model.put("roleAdmin", true);
			}
		}

		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

		if (csrfToken != null) {
			model.put("_csrf", csrfToken);
		}

		String horizontalMenuCode = MenuFactory.getHorizontalMenu(request, response);

		model.put("horizontalMenuCode", horizontalMenuCode);

		return DefaultTemplateEngine.getTemplate(request, response, "components/menus/horizontalMenu.vm", model);
	}

	public Site getSite(HttpServletRequest request) {
		return PortalUtil.getSiteFromSession(request);
	}

	public SiteInfo getSite(Site site) {
		SiteInfo siteInfo = new SiteInfo();

		siteInfo.setSiteId(site.getSiteId());
		siteInfo.setSiteName(site.getSiteName());

		siteInfo.setSiteDomainName(site.getDomainName());
		siteInfo.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
		siteInfo.setSiteFooter(site.getFooter());

		return siteInfo;
	}

	public List<ContactUs> getContactUs(int siteId) {
		return contactUsService.getContactUs(siteId, true);
	}

	/******************************************************************************************************/
	public ContentInfo getContent(
			int siteId, Content content, boolean checkExpiry,
			boolean updateStatistics) {
		return processContent(siteId, content, checkExpiry, updateStatistics);
	}

//	public ContentInfo getContent(
//			int siteId, String contentNaturalKey, boolean checkExpiry,
//			boolean updateStatistics) {
//		Content content = contentService.getContent(siteId, contentNaturalKey);
//
//		return processContent(siteId, content, checkExpiry, updateStatistics);
//	}

	public List<Comment> getComments(long contentId) {
		return contentService.getComments(contentId);
	}

	public ContentInfo processContent(
			int siteId, Content content, boolean checkExpiry, boolean updateStatistics) {
		if (content == null) {
			return null;
		}

		if (checkExpiry) {
			if (!Utility.isContentPublished(content)) {
				return null;
			}
		}

		if (content.getSiteId() != siteId) {
			return null;
		}

		if (updateStatistics) {
			content.setHitCounter(content.getHitCounter() + 1);
		}

		contentService.updateContent(content);

		ContentInfo contentInfo = formatContent(content);

		return contentInfo;
	}

	public ContentInfo formatContent(Content content) {
		ContentInfo contentInfo = new ContentInfo();

		contentInfo.setNaturalKey(content.getNaturalKey());
		contentInfo.setContentId(content.getContentId());
		contentInfo.setTitle(content.getTitle());
		contentInfo.setShortDescription(content.getShortDescription());
		contentInfo.setDescription(content.getDescription());
		contentInfo.setHitCounter(content.getHitCounter());
		contentInfo.setScore(content.getScore());

		List<Item> items = itemService.getItemsByContentId(content.getContentId());

		contentInfo.setItems(items);

		contentInfo.setContentBrands(content.getContentBrands());
		contentInfo.setCategory(content.getCategory());

//		User user = userService.getUser(content.getCreateBy());
		User user = content.getCreateBy();

		contentInfo.setUpdateDate(Format.getDate(content.getUpdateDate()));
		contentInfo.setCreateBy(user.getUsername());
		contentInfo.setCreateDate(Format.getDate(content.getCreateDate()));

		if (Format.isNullOrEmpty(content.getPageTitle())) {
			contentInfo.setKeywords(content.getTitle());
		}
		else {
			contentInfo.setKeywords(content.getPageTitle());
		}

		contentInfo.setPageTitle(content.getTitle());

		String contextPath = ServletContextUtil.getContextPath();

		String frontEndUrlPrefix = Constants.FRONTEND_URL_PREFIX;

		if (Validator.isNotNull(frontEndUrlPrefix)) {
			frontEndUrlPrefix = StringPool.SLASH + frontEndUrlPrefix;
		}

		String contentUrl = contextPath + frontEndUrlPrefix + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

		contentInfo.setContentUrl(contentUrl);

		contentInfo.setDefaultImageUrl(null);

//		String imageUrlPrefix = contextPath + "/images";
//		String imageUrlPrefix = "/service/imageprovider";

		/*if (content.getImage() != null) {*/
		if (content.getFeaturedImage() != null) {
			contentInfo.setDefaultImageUrl(content.getFeaturedImage());
			/*contentInfo.setDefaultImageUrl(imageUrlPrefix + "?type=C&imageId=" + content.getImage().getImageId());*/
		}

		return contentInfo;
	}

	public HomeInfo getHome(int siteId) {
		HomeInfo homeInfo = new HomeInfo();

		List<HomePage> homePages = homePageService.getHomePagesBySiteIdAndFeatureData(siteId);

		for (HomePage homePage : homePages) {
			if (homePage.getContent() != null) {
				Content content = homePage.getContent();

				if (!Utility.isContentPublished(content)) {
					break;
				}

				ContentInfo contentInfo = formatContent(content);

				contentInfo.setFeature(true);

				homeInfo.setHomePageFeatureData(contentInfo);
			}
		}

		homePages = homePageService.getHomePagesBySiteIdAndFeatureDataNotY(siteId);

		List<DataInfo> dataInfos = new ArrayList<DataInfo>();

//		Vector<DataInfo> vector = new Vector<DataInfo>();

		for (HomePage homePage : homePages) {
			if (homePage.getContent() != null) {
//				Content content = homePage.getContent();
				Content content = contentService.getContent(homePage.getContent().getContentId());

				if (!Utility.isContentPublished(content)) {
					continue;
				}

				dataInfos.add(formatContent(content));
			}
		}

//		Object homePageDatas[] = new Object[vector.size()];

//		vector.copyInto(homePageDatas);
//		homeInfo.setHomePageDatas(homePageDatas);
		homeInfo.setHomePageDatas(dataInfos);

		Site site = siteService.getSite(siteId);

//		String pageTitle = Utility.getParam(site, Constants.HOME_TITLE);

		homeInfo.setPageTitle(site.getTitle());

		return homeInfo;
	}

//	public SectionInfo getSection(
//			int siteId, String sectionNaturalKey, String topSectionNaturalKey, int pageSize,
//			int pageNavCount, int pageNum, String sortBy)
//		throws Exception {
//		String contextPath = ServletContextUtil.getContextPath();
//
//		SectionInfo sectionInfo = new SectionInfo();
//
//		String key = Utility.reEncode(sectionNaturalKey);
//		String topKey = Utility.reEncode(topSectionNaturalKey);
//
//		Section section = sectionService.getSectionBySiteId_NaturalKey(siteId, key);
//
//		if (section == null) {
//			return null;
//		}
//
//		sectionInfo.setSectionNaturalKey(sectionNaturalKey);
//		sectionInfo.setSectionId(section.getSectionId());
//		sectionInfo.setTopSectionId(0);
//		sectionInfo.setTopSectionNaturalKey(topSectionNaturalKey);
//		sectionInfo.setSectionShortTitle(section.getShortTitle());
//		sectionInfo.setSectionTitle(section.getTitle());
//		sectionInfo.setSectionDesc(section.getDescription());
//
////		String url = "/" + ApplicationGlobal.getContextPath()
//		String url = contextPath + StringPool.SLASH
//			+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//			+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH + topSectionNaturalKey + // topSection
//			StringPool.SLASH + section.getNaturalKey(); // section
//
//		sectionInfo.setSectionUrl(url);
//		sectionInfo.setSortBy(sortBy == null ? "" : sortBy);
//
//		List<Section> childSections = sectionService.getSectionBySiteId_SectionParentId_Published(siteId, section.getSectionId(), true);
//
//		Vector<Object> vector = new Vector<Object>();
//
//		for (Section childSection : childSections) {
//			SectionInfo childSectionInfo = new SectionInfo();
//
//			childSectionInfo.setSectionId(childSection.getSectionId());
//			childSectionInfo.setSectionShortTitle(childSection.getShortTitle());
//			childSectionInfo.setSectionTitle(childSection.getTitle());
//			childSectionInfo.setSectionDesc(childSection.getDescription());
//
////			url = "/" + ApplicationGlobal.getContextPath()
//			url = contextPath + StringPool.SLASH
//				+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//				+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH
//				+ topSectionNaturalKey + // topSection
//				StringPool.SLASH + childSection.getNaturalKey(); // section
//
//			childSectionInfo.setSectionUrl(url);
//
//			vector.add(childSectionInfo);
//		}
//
//		SectionInfo childSectionInfos[] = new SectionInfo[vector.size()];
//
//		vector.copyInto(childSectionInfos);
//
//		sectionInfo.setChildSectionInfos(childSectionInfos);
//		sectionInfo.setChildCount(childSectionInfos.length);
//
//		int itemStart = (pageNum - 1) * pageSize;
//		int itemEnd = itemStart + pageSize - 1;
//		int index = 0;
//
//		String sql = "select type, objectId, description, price " +
//			"from (select 'I' type, item_id objectId, item_short_desc description, item_price price " +
//				"from item where site_id = '" + siteId + "' and section_id = " + section.getSectionId() + " and published = 'Y' and now() between item_publish_on and item_expire_on " +
//					"union select 'C' type, content_id objectId, content_title description, 0 price from content where site_id = '" + siteId + "' and section_id = " + section.getSectionId() +
//					" and published = 'Y'and now() between content_publish_on and content_expire_on) as mytable ";
//
//		List<Section> list = sectionService.search(sql, sortBy);
//
//		int totalCount = list.size();
//
//		Iterator sqlIterator = list.iterator();
//
//		vector = new Vector<Object>();
//
//		while (sqlIterator.hasNext()) {
//			Object object = sqlIterator.next();
//
//			if (index >= itemStart && index <= itemEnd) {
//				Object results[] = (Object[]) object;
//
//				String type = (String) results[0];
//
//				String objectId = (String) results[1];
//
//				if (type.equals("I")) {
////					Item item = itemService.getItem(siteId, Format.getLong(objectId));
////
////					ItemInfo itemInfo = formatItem(item);
////
////					vector.add(itemInfo);
//				}
//				else {
//					Content content = contentService.getContent(Format.getLong(objectId));
//
//					ContentInfo contentInfo = formatContent(content);
//
//					vector.add(contentInfo);
//				}
//
//				if (index > itemEnd) {
//					break;
//				}
//			}
//
//			index++;
//		}
//
//		int midpoint = pageNavCount / 2;
//		int recordCount = totalCount;
//		int pageTotal = recordCount / pageSize;
//
//		if (recordCount % pageSize > 0) {
//			pageTotal++;
//		}
//
//		sectionInfo.setPageTotal(pageTotal);
//		sectionInfo.setPageNum(pageNum);
//
//		int pageStart = pageNum - midpoint;
//
//		if (pageStart < 1) {
//			pageStart = 1;
//		}
//
//		int pageEnd = pageStart + pageNavCount - 1;
//
//		if (pageEnd > pageTotal) {
//			pageEnd = pageTotal;
//
//			if (pageEnd - pageStart + 1 < pageNavCount) {
//				pageStart = pageEnd - pageNavCount + 1;
//				if (pageStart < 1) {
//					pageStart = 1;
//				}
//			}
//		}
//
//		sectionInfo.setPageStart(pageStart);
//		sectionInfo.setPageEnd(pageEnd);
//
//		Object sectionDatas[] = new Object[vector.size()];
//
//		vector.copyInto(sectionDatas);
//		sectionInfo.setSectionDatas(sectionDatas);
//
//		Vector<SectionInfo> titleSectionVector = new Vector<SectionInfo>();
//
//		int sectionParentId = section.getParentId();
//
//		while (true) {
//			key = Utility.reEncode(section.getNaturalKey());
//
//			if (key.equals(topKey)) {
//				break;
//			}
//
//			section = sectionService.getSectionBySiteId_SectionId(siteId, sectionParentId);
//
//			if (section.getParentId() == 0) {
//				break;
//			}
//
//			SectionInfo titleSectionInfo = new SectionInfo();
//
//			titleSectionInfo.setSectionNaturalKey(section.getNaturalKey());
//			titleSectionInfo.setSectionId(section.getSectionId());
//			titleSectionInfo.setSectionShortTitle(section.getShortTitle());
//			titleSectionInfo.setSectionTitle(section.getTitle());
//			titleSectionInfo.setSectionDesc(section.getDescription());
//
////			url = "/" + ApplicationGlobal.getContextPath()
//			url = contextPath + StringPool.SLASH
//					+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//					+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH
//					+ topSectionNaturalKey + // topSection
//					StringPool.SLASH + section.getNaturalKey(); // section
//
//			titleSectionInfo.setSectionUrl(url);
//
//			titleSectionVector.add(titleSectionInfo);
//
//			sectionParentId = section.getParentId();
//		}
//
//		// Reverse sequence order
//		SectionInfo titleSectionInfos[] = new SectionInfo[titleSectionVector.size()];
//
//		int pos = 0;
//
//		for (int i = titleSectionVector.size() - 1; i >= 0; i--) {
//			titleSectionInfos[pos++] = (SectionInfo) titleSectionVector.get(i);
//		}
//
//		sectionInfo.setTitleSectionInfos(titleSectionInfos);
//
//		// TODO need to be implemented
//		Object sectionFeatureDatas[] = new Object[0];
//
//		sectionInfo.setSectionFeatureDatas(sectionFeatureDatas);
//
//		return sectionInfo;
//	}
}