package com.yoda.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.yoda.user.UserEditCommand;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/controlpanel/user/add")
public class UserAddController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
//		String loginMessage = AdminLookup.lookUpAdmin(request, response);
//
//		if (Validator.isNotNull(loginMessage)) {
//			ModelMap modelMap = new ModelMap();
//
//			modelMap.put("loginMessage", loginMessage);
//
//			return new ModelAndView(
//				"redirect:" + Constants.LOGIN_PAGE_URL, modelMap);
//		}

		User user = PortalUtil.getAuthenticatedUser();

		User signinUser = userService.getUser(user.getUserId());

		UserEditCommand command = new UserEditCommand();

		command.setUserType(Constants.USERTYPE_REGULAR);

		command.setActive(Constants.ACTIVE_YES);

		initSearchInfo(command, user.getLastVisitSiteId(), signinUser);

		return new ModelAndView("controlpanel/user/edit", "userEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String add(
			@ModelAttribute UserEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User signinUser = PortalUtil.getAuthenticatedUser();

		new UserAddValidator().validate(command, result);

		if(result.hasErrors()) {
			initSearchInfo(command, signinUser.getLastVisitSiteId(), signinUser);

			return "controlpanel/user/edit";
		}

		status.setComplete();

		User user = userService.addUser(
			command.getUsername(), command.getPassword(),
			command.getEmail(), command.getPhone(),
			command.getUserType(), command.getAddressLine1(),
			command.getAddressLine2(), command.getCityName(),
			command.getSelectedSiteIds(), command.getActive(),
			signinUser.getUserId());

		initSearchInfo(command, signinUser.getLastVisitSiteId(), signinUser);

		return "redirect:/controlpanel/user/list";
	}

	public void initSearchInfo(
			UserEditCommand command, long siteId, User signinUser) {
		List<Site> sites = siteService.getSites();

		command.setSites(sites);

		command.setHasAdministrator(false);
		command.setHasSuperUser(false);

		if (signinUser.getUserType().equals(Constants.USERTYPE_SUPER)) {
			command.setHasAdministrator(true);
			command.setHasSuperUser(true);
		}

		if (signinUser.getUserType().equals(Constants.USERTYPE_ADMIN)) {
			command.setHasAdministrator(true);
		}
	}
}