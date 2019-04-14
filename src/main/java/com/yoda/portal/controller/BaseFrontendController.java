package com.yoda.portal.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.web.csrf.CsrfToken;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.pageview.model.PageViewData;
import com.yoda.portal.content.data.DefaultTemplateEngine;
import com.yoda.portal.content.frontend.MenuFactory;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;

public class BaseFrontendController {
	@Autowired
	protected UserService userService;

	@Autowired
	protected KafkaTemplate<String, String> kafkaTemplate;

	public String getHorizontalMenu(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		User loginUser = PortalUtil.getAuthenticatedUser();

		if (loginUser != null) {
			model.put("userLogin", true);
			model.put("userId", loginUser.getUserId());
			model.put("username", loginUser.getUsername());

			if (PortalUtil.isAdminRole(loginUser)) {
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

	public void pageView(HttpServletRequest request, int pageType, int pageId, String pageName) {
		String ip = PortalUtil.getClientIP(request);

		PageViewData pageView = new PageViewData();

		pageView.setCreateTime(Format.getFullDatetime(new Date()));
		pageView.setPageId(pageId);
		pageView.setPageName(pageName);
		pageView.setPageType(pageType);
		pageView.setPageUrl(request.getRequestURL().toString());
		pageView.setUserIPAddress(ip);

		User user = PortalUtil.getAuthenticatedUser();

		if (null != user) {
			pageView.setUserId(user.getUserId());
			pageView.setUsername(user.getUsername());
		}

		kafkaTemplate.send(Constants.KAFKA_TOPIC_PAGE_VIEW, pageView.toString());
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