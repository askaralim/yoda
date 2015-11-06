package com.yoda.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.portal.FrontendContentEditValidator;
import com.yoda.content.model.Content;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;

@Controller
@RequestMapping(value = "/content/{contentId}/edit")
public class FrontendContentEditController extends BaseFrontendController {

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

		User currentUser = PortalUtil.getAuthenticatedUser();

		if ((currentUser == null) || (currentUser.getUserId() != content.getCreateBy().getUserId()) ) {
			return new ModelAndView("redirect:/login");
		}

		String horizontalMenu = getHorizontalMenu(request, response);

		ModelMap model = new ModelMap();

		model.put("user", currentUser);
		model.put("horizontalMenu", horizontalMenu);
		model.put("content", content);
		model.put("siteInfo", siteInfo);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submitUpdate(
			@ModelAttribute Content content,
			BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		Site site = getSite(request);

		ModelMap model = new ModelMap();

		new FrontendContentEditValidator().validate(content, result);

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("portal/user/contentEdit", model);
		}

//		User currentUser = PortalUtil.getAuthenticatedUser();

		contentService.updateContent(
			site.getSiteId(),
			content.getContentId(), content.getTitle(),
			content.getShortDescription(), content.getDescription());

		model.put("success", "success");

		return new ModelAndView("redirect:/content/" + content.getContentId() + "/edit", model);
	}
}