package com.yoda.portal.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.yoda.util.Format;
import com.yoda.util.StringPool;

@Controller
@RequestMapping(value = "/content/add")
public class FrontendContentAddController extends BaseFrontendController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		User currentUser = PortalUtil.getAuthenticatedUser();

		if (currentUser == null) {
			return new ModelAndView("redirect:/login");
		}

		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("user", currentUser);
		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);

		Content content = new Content();

		model.put("content", content);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submitAdd(
			@ModelAttribute("content") Content content,
			BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		Site site = getSite(request);

		User currentUser = PortalUtil.getAuthenticatedUser();

		if (currentUser == null) {
			return new ModelAndView("redirect:/login");
		}

		new FrontendContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("portal/user/contentEdit", model);
		}

		Calendar calendar = Calendar.getInstance(request.getLocale());

		Date publishDate = calendar.getTime();

		calendar.add(Calendar.YEAR, 1);

		Date expireDate = calendar.getTime();

		content = contentService.addContent(
			site.getSiteId(), currentUser.getUserId(),
			StringPool.BLANK, content.getTitle(),
			content.getShortDescription(), content.getDescription(),
			StringPool.BLANK, null, Format.getFullDatetime(publishDate),
			Format.getFullDatetime(expireDate), currentUser.getUserId(), false);

		return new ModelAndView("redirect:/content/" + content.getContentId() + "/edit", model);
	}
}