package com.yoda.portal.account.controller;

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

import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.portal.controller.BaseFrontendController;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/user/{userId}")
public class UserProfileController extends BaseFrontendController {
	Logger logger = Logger.getLogger(UserProfileController.class);

	@Autowired
	protected ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("userId") long userId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite(request);

		User user = userService.getUser(userId);

		if (Validator.isNull(user)) {
			return new ModelAndView("/404", "requestURL", userId);
		}

		List<Content> contents = contentService.getContentByUserId(user.getUserId());

		model.put("user", user);
		model.put("contents", contents);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("site", site);

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("/portal/user/profile", model);
	}
}