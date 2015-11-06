package com.yoda.content.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.content.ContentSearchForm;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.service.MenuService;
import com.yoda.section.service.SectionService;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;

@Controller
public class ContentController {

	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@Autowired
	SectionService sectionService;

	@Autowired
	ContentService contentService;

	@Autowired
	MenuService menuService;

	@RequestMapping(value="/controlpanel/content", method = RequestMethod.GET)
	public String showPanel(Map<String, Object> model) {

		User user = PortalUtil.getAuthenticatedUser();

		List<Content> contents = contentService.getContents(user.getLastVisitSiteId());

		model.put("contents", contents);
		model.put("searchForm", new ContentSearchForm());

		return "controlpanel/content/list";
	}

	@RequestMapping(value="/controlpanel/content/remove")
	public String removeContents(
			@RequestParam("contentIds") String contentIds,
			HttpServletRequest request) {
		String[] arrIds = contentIds.split(",");

		Content content = new Content();

		for (int i = 0; i < arrIds.length; i++) {
			content = contentService.getContent(Long.valueOf(arrIds[i]));

			contentService.deleteContent(content);
		}

		return "redirect:/controlpanel/content";
	}

	@RequestMapping(value="/controlpanel/content/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute ContentSearchForm form, Map<String, Object> model)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		int siteId = user.getLastVisitSiteId();

		List<Content> contents = contentService.search(
			siteId, form.getTitle(), form.getPublished(),
			null, null, form.getPublishDateStart(), form.getPublishDateEnd(),
			form.getExpireDateStart(), form.getExpireDateEnd());

		model.put("contents", contents);
		model.put("searchForm", form);

		return "controlpanel/content/list";
	}

//	protected void calcPage(
//			User user, ContentListCommand command, List list, int pageNo) {
//
//		Site site = siteService.getSite(user.getLastVisitSiteId());
//
//		command.setPageNo(pageNo);
//
//		/* Calc Page Count */
//		int pageCount = (list.size() - list.size()
//			% Format.getInt(site.getListingPageSize()))
//			/ Format.getInt(site.getListingPageSize());
//
//		if (list.size() % Format.getInt(site.getListingPageSize()) > 0) {
//			pageCount++;
//		}
//
//		command.setPageCount(pageCount);
//
//		int half = Constants.DEFAULT_LISTING_PAGE_COUNT / 2;
//
//		/* Calc Start Page */
//		int startPage = pageNo - half + 1;
//
//		if (startPage < 1) {
//			startPage = 1;
//		}
//
//		command.setStartPage(startPage);
//
//		/* Calc End Page */
//		/* Trying to make sure the maximum number of navigation is visible */
//		int endPage = startPage + Constants.DEFAULT_LISTING_PAGE_COUNT - 1;
//
//		while (endPage > pageCount && startPage > 1) {
//			endPage--;
//			startPage--;
//		}
//		/* Still not possible. Trimming navigation. */
//
//		if (endPage > pageCount) {
//
//			if (pageCount == 0) {
//				endPage = 1;
//			} else {
//				endPage = pageCount;
//			}
//
//		}
//
//		command.setStartPage(startPage);
//		command.setEndPage(endPage);
//	}
}