package com.yoda.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.site.service.SiteService;
import com.yoda.user.UserEditValidator;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/controlpanel/user/{userId}/edit")
public class UserEditController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(
			@PathVariable("userId") long userId, Map<String, Object> model) {
		model.put("user", userService.getUser(userId));
		model.put("sites", siteService.getSites());

		return "controlpanel/user/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(
			@ModelAttribute User user,
			@RequestParam("photo") MultipartFile photo,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		new UserEditValidator().validate(user, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("sites", siteService.getSites());
			model.put("errors", "errors");

			return "controlpanel/user/edit/{userId}";
		}

		String role = request.getParameter("userRole");
		String[] selectedSiteIds = request.getParameterValues("selectedSiteIds");

		if (Validator.isNull(role)) {
			role = StringPool.BLANK;
		}

		Integer[] siteIds = new Integer[0];

		if (Validator.isNotNull(selectedSiteIds)) {
			siteIds = new Integer[selectedSiteIds.length];

			for (int i = 0; i < selectedSiteIds.length; i++) {
				siteIds[i] = Integer.valueOf(selectedSiteIds[i]);
			}
		}

		userService.updateUser(
			user.getUserId(), user.getUsername(), user.getPassword(),
			user.getEmail(), user.getPhone(), photo,
			user.getAddressLine1(), user.getAddressLine2(),
			user.getCityName(), siteIds, user.isEnabled());

		model.put("sites", siteService.getSites());
		model.put("success", "success");

		return "controlpanel/user/edit";
	}

//	@RequestMapping(value = "/remove", method = RequestMethod.GET)
//	public String delete(
//			@PathVariable("userId") long userId,
//			HttpServletRequest request) {
//		User user = PortalUtil.getAuthenticatedUser();
//
//		User signinUser = userService.getUser(admin.getUserId());
//
//		userService.deleteUser(userId, signinUser);
//
//		return "redirect:/controlpanel/user/list";
//	}
}