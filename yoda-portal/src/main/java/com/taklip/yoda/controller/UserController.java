package com.taklip.yoda.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.validator.UserAddValidator;
import com.taklip.yoda.validator.UserEditValidator;
import com.taklip.yoda.vo.UserSearchForm;

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

	@RequestMapping(value="/controlpanel/user/add", method = RequestMethod.GET)
	public String setupForm(Map<String, Object> model) {
		User user = new User();

		user.setEnabled(true);
		user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_USER"));

		model.put("user", user);
		model.put("sites", siteService.getSites());

		return "controlpanel/user/edit";
	}

	@RequestMapping(value="/controlpanel/user/add", method = RequestMethod.POST)
	public ModelAndView add(
			@ModelAttribute User user, BindingResult result,
			SessionStatus status, HttpServletRequest request)
		throws Throwable {

		new UserAddValidator().validate(user, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			user.setEnabled(true);
			user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_USER"));

			model.put("sites", siteService.getSites());
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/user/edit", model);
		}

		status.setComplete();

		String role = request.getParameter("userRole");
		String[] selectedSiteIds = request.getParameterValues("selectedSiteIds");

		if (StringUtils.isBlank(role)) {
			role = StringPool.BLANK;
		}

		Integer[] siteIds = new Integer[0];

		if (null != selectedSiteIds) {
			siteIds = new Integer[selectedSiteIds.length];

			for (int i = 0; i < selectedSiteIds.length; i++) {
				siteIds[i] = Integer.valueOf(selectedSiteIds[i]);
			}
		}

		int siteId = siteService.getSites().get(0).getSiteId();

		User userDb = userService.addUser(user.getUsername(), user.getPassword(),
			user.getEmail(), user.getPhone(), role, user.getAddressLine1(),
			user.getAddressLine2(), user.getCityName(), siteIds,
			siteId, user.isEnabled());

		return new ModelAndView("redirect:/controlpanel/user/" + userDb.getUserId() + "/edit", model);
	}

	@RequestMapping(value = "/controlpanel/user/{userId}/edit", method = RequestMethod.GET)
	public String setupForm(
			@PathVariable("userId") long userId, Map<String, Object> model) {
		model.put("user", userService.getUser(userId));
		model.put("sites", siteService.getSites());

		return "controlpanel/user/edit";
	}

	@RequestMapping(value = "/controlpanel/user/{userId}/edit", method = RequestMethod.POST)
	public ModelAndView update(
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

			return new ModelAndView("controlpanel/user/edit/{userId}", model);
		}

		String role = request.getParameter("userRole");
		String[] selectedSiteIds = request.getParameterValues("selectedSiteIds");

		if (StringUtils.isBlank(role)) {
			role = StringPool.BLANK;
		}

		Integer[] siteIds = new Integer[0];

		if (null != selectedSiteIds) {
			siteIds = new Integer[selectedSiteIds.length];

			for (int i = 0; i < selectedSiteIds.length; i++) {
				siteIds[i] = Integer.valueOf(selectedSiteIds[i]);
			}
		}

		User userDb = userService.updateUser(
			user.getUserId(), user.getUsername(), user.getPassword(),
			user.getEmail(), user.getPhone(), photo,
			user.getAddressLine1(), user.getAddressLine2(),
			user.getCityName(), siteIds, user.isEnabled());

		model.put("sites", siteService.getSites());
		model.put("success", "success");
		model.put("user", userDb);

		return new ModelAndView("controlpanel/user/edit", model);
	}

	@RequestMapping(value = "/controlpanel/user/list/remove", method = RequestMethod.GET)
	public String removeUsers(
			@RequestParam("userIds") String userIds,
			HttpServletRequest request) {
		String[] arrIds = userIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			userService.deleteUser(Long.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/user/list";
	}
}