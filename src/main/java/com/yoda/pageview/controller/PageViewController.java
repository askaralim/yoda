package com.yoda.pageview.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.model.Pagination;
import com.yoda.pageview.model.PageViewData;
import com.yoda.pageview.service.PageViewService;

@Controller
public class PageViewController {
	@Autowired
	PageViewService pageViewService;

	@RequestMapping(value="/controlpanel/pageview", method = RequestMethod.GET)
	public String showPageViews(
		Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtil.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<PageViewData> page = pageViewService.getPageViewDatas(new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/pageview/list";
	}

	@RequestMapping(value = "/controlpanel/pageview/{id}", method = RequestMethod.GET)
	public ModelAndView viewComment(@PathVariable("id") int id) {
		PageViewData pageView = pageViewService.getPageViewData(id);

		return new ModelAndView(
			"controlpanel/pageview/view", "pageView", pageView);
	}

//	@RequestMapping(value = "/controlpanel/pageview/{pageType}/{pageId}", method = RequestMethod.GET)
//	public ModelAndView viewPageView(@PathVariable("pageType") int pageType,
//		@PathVariable("pageId") int pageId) {
//		
//
//		return new ModelAndView(
//			"controlpanel/content/comment", "comment", comment);
//	}
//
//	@RequestMapping(value = "/comment/new", method = RequestMethod.POST)
//	public String addComment(HttpServletRequest request, @ModelAttribute Comment comment) {
//		comment.setSiteId(PortalUtil.getSiteFromSession(request).getSiteId().intValue());
//		comment.setCreateDate(new Date());
//		comment.setUser(PortalUtil.getAuthenticatedUser());
//		comment.setDescription(HtmlUtils.htmlEscape(comment.getDescription()));
//
//		contentService.addComment(comment);
//
//		return "redirect:/content/" + comment.getContentId();
//	}
//
//	@RequestMapping(value = "/controlpanel/comment/remove")
//	public String removeComments(
//			@RequestParam("ids") String ids,
//			HttpServletRequest request) {
//		String[] arrIds = ids.split(",");
//
//		for (int i = 0; i < arrIds.length; i++) {
//			contentService.deleteComment(Integer.valueOf(arrIds[i]));
//		}
//
//		return "redirect:/controlpanel/comment";
//	}
}