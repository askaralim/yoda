package com.yoda.portal.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.portal.account.UserSettingsValidator;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.portal.controller.BaseFrontendController;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/user/settings")
public class UserSettingsController extends BaseFrontendController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		ModelMap model = new ModelMap();

		User user = PortalUtil.getAuthenticatedUser();

		if(Validator.isNull(user)) {
			return new ModelAndView("redirect:/login", model);
		}

		model.put("user", user);

		SiteInfo siteInfo = getSite(site);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);

		return new ModelAndView("/portal/user/settings", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute User user,
			BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		ModelAndView model = new ModelAndView();

		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.addObject("horizontalMenu", horizontalMenu);
		model.addObject("siteInfo", siteInfo);

		new UserSettingsValidator().validate(user, result);

		if(result.hasErrors()) {
			model.addObject("errors", "errors");

			model.setViewName("/portal/user/settings");

			return model;
		}

		User userDb = userService.updateUser(
			site.getSiteId(), user.getUserId(),
			user.getUsername(), user.getPassword(), user.getEmail());

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDb, userDb.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.addObject("success", "success");

		model.setViewName("/portal/user/settings");

		return model;
	}

	Logger logger = Logger.getLogger(UserProfileController.class);
}