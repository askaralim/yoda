package com.yoda.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.service.SiteService;
import com.yoda.user.UserSearchForm;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/user/list", method = RequestMethod.GET)
	public String showPanel(Map<String, Object> model) {
		List<User> users = userService.getUsers();

		model.put("users", users);
		model.put("searchForm", new UserSearchForm());

		return "controlpanel/user/list";
	}

	@RequestMapping(value="/controlpanel/user/list/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute UserSearchForm form, Map<String, Object> model) {
		List<User> users = userService.search(
			form.getUserId(), form.getUsername(), form.getRole(),
			form.getEnabled());

		model.put("users", users);
		model.put("searchForm", form);

		return "controlpanel/user/list";
	}

	@RequestMapping(value = "/controlpanel/user/list/remove", method = RequestMethod.GET)
	public String removeUsers(
			@RequestParam("userIds") String userIds,
			HttpServletRequest request) {
		User signinUser = PortalUtil.getAuthenticatedUser();

		String[] arrIds = userIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			userService.deleteUser(Long.valueOf(arrIds[i]), signinUser);
		}

		return "redirect:/controlpanel/user/list";
	}
}