package com.yoda.user.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.UserAddValidator;
import com.yoda.user.model.User;
import com.yoda.user.model.UserAuthority;
import com.yoda.user.service.UserService;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/controlpanel/user/add")
public class UserAddController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(Map<String, Object> model) {
		User user = new User();

		user.setEnabled(true);
		user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_USER"));

		model.put("user", user);
		model.put("sites", siteService.getSites());

		return "controlpanel/user/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
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

		User userDb = userService.addUser(user.getUsername(), user.getPassword(),
			user.getEmail(), user.getPhone(), role, user.getAddressLine1(),
			user.getAddressLine2(), user.getCityName(), siteIds,
			user.isEnabled());

		return new ModelAndView("redirect:/controlpanel/user/" + userDb.getUserId() + "/edit", model);
	}
}