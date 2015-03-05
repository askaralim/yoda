package com.yoda.user.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.UserEditCommand;
import com.yoda.user.UserEditValidator;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/controlpanel/user/{userId}/edit")
public class UserEditController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("userId") long userId,
			HttpServletRequest request, HttpServletResponse response) {
		User signinUser = PortalUtil.getAuthenticatedUser();

		UserEditCommand command = new UserEditCommand();

		User user = new User();

		try {
			user = userService.getByUI_SU(userId, signinUser);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Set<Site> sites = user.getSites();

		List<Long> siteIds = new ArrayList<Long>();

		for (Site site : sites) {
			siteIds.add(site.getSiteId());
		}

		command.setSelectedSiteIds(siteIds);

		copyProperties(command, user);

		initSearchInfo(command, signinUser.getLastVisitSiteId(), signinUser);

		return new ModelAndView("controlpanel/user/edit", "userEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String update(
			@ModelAttribute UserEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User signinUser = PortalUtil.getAuthenticatedUser();

		new UserEditValidator().validate(command, result);

		if(result.hasErrors()) {
			initSearchInfo(command, signinUser.getLastVisitSiteId(), signinUser);

			return "controlpanel/user/edit/{userId}";
		}

		userService.updateUser(
			command.getUserId(),
			command.getUsername(), command.getPassword(),
			command.getEmail(), command.getPhone(),
			command.getUserType(), command.getAddressLine1(),
			command.getAddressLine2(), command.getCityName(),
			command.getSelectedSiteIds(), command.getActive(), signinUser);

		initSearchInfo(command, signinUser.getLastVisitSiteId(), signinUser);

		return "redirect:/controlpanel/user/list";
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

	private void copyProperties(UserEditCommand command, User user) {
		command.setUserId(user.getUserId());
		command.setUsername(user.getUsername());
		command.setEmail(user.getEmail());
		command.setAddressLine1(user.getAddressLine1());
		command.setAddressLine2(user.getAddressLine2());
//		command.setUserCityName(user.getUserCityName());
//		command.setUserStateCode(user.getUserStateCode());
//		command.setUserCountryCode(user.getUserCountryCode());
//		command.setUserZipCode(user.getUserZipCode());
		command.setUserPhone(user.getPhone());
		command.setUserType(user.getUserType());
		command.setActive(user.getActive());
		command.setPassword("**********");
		command.setVerifyPassword("**********");

		List<Long> siteIds = new ArrayList<Long>();

		if (user.getUserType().equals(Constants.USERTYPE_SUPER)
			|| user.getUserType().equals(Constants.USERTYPE_ADMIN)) {
			List<Site> sites = siteService.getSites();

			for (Site site : sites) {
				siteIds.add(site.getSiteId());
			}
		}
		else {
			Iterator iterator = user.getSites().iterator();

			while (iterator.hasNext()) {
				Site site = (Site) iterator.next();
				siteIds.add(site.getSiteId());
			}
		}

		command.setSelectedSiteIds(siteIds);
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
