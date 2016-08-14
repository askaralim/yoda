package com.yoda.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.DateTool;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.portal.content.data.ComponentInfo;
import com.yoda.portal.content.data.ContentInfo;
import com.yoda.portal.content.data.DefaultTemplateEngine;
import com.yoda.portal.content.data.PageInfo;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
@RequestMapping(value="/content/{contentId}")
public class FrontendContentController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontendContentController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("contentId") String contentId,
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		Content content = new Content();

		try {
			content = contentService.getContent(Long.valueOf(contentId));
		}
		catch (HibernateObjectRetrievalFailureException e) {
			logger.error(e.getMessage());

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		if (Validator.isNull(content)) {
			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		PageInfo pageInfo = getContent(request, response, content);

		String horizontalMenu = getHorizontalMenu(request, response);

		ComponentInfo componentInfo = new ComponentInfo();

		componentInfo.setContextPath(ServletContextUtil.getContextPath());

		ModelMap model = new ModelMap();

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);
		model.put("pageInfo", pageInfo);
		model.put("componentInfo", componentInfo);

		return new ModelAndView("template", model);
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

		String text = StringPool.BLANK;

		if (contentInfo != null) {
			List<Comment> comments = getComments(content.getContentId());

			CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

			if (csrfToken != null) {
				model.put("_csrf", csrfToken);
			}

			model.put("contentInfo", contentInfo);
			model.put("comments", comments);
			model.put("date", new DateTool());

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (!(auth instanceof AnonymousAuthenticationToken)) {
				model.put("userLogin", true);
			}
			else {
				model.put("userLogin", false);
			}

			text = DefaultTemplateEngine.getTemplate(request, response, "content/content.vm", model);

			pageInfo.setPageTitle(site.getSiteName() + " - " + contentInfo.getPageTitle());

			pageInfo.setKeywords(contentInfo.getKeywords());
		}
		else {
			text = DefaultTemplateEngine.getTemplate(request, response, "messages/moved.vm", model);

			pageInfo.setPageTitle(site.getSiteName() + " - " + "Page not found");

			pageInfo.setKeywords(StringPool.BLANK);
		}

		pageInfo.setPageBody(text);

		return pageInfo;
	}

	@ResponseBody
	@RequestMapping(value="/score" ,method = RequestMethod.POST)
	public String score(
			@PathVariable("contentId") Long contentId,
			@RequestParam("thumb") String thumb,
			HttpServletRequest request, HttpServletResponse response) {
//		Site site = getSite(request);

		Content content = contentService.getContent(contentId);

		int score = 0;

		if (thumb.equals("up")) {
			score = content.getScore() + 1;
		}
		else if (thumb.equals("down")) {
			score = content.getScore() - 1;
		}

		content.setScore(score);

		contentService.updateContent(content);

		JSONObject jsonResult = new JSONObject();

		try {
			jsonResult.put("score", score);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonResult.toString();
	}
}