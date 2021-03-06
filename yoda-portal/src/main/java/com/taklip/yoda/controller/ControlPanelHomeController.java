package com.taklip.yoda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.PortalUtil;
import com.taklip.yoda.util.Validator;
import com.taklip.yoda.vo.ControlPanelHomeCommand;
import com.taklip.yoda.vo.SystemStatistics;

@Controller
@RequestMapping("/controlpanel/home")
public class ControlPanelHomeController {
	@Autowired
	SiteService siteService;

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showPanel(
			HttpServletRequest request,HttpServletResponse response){
		User user = AuthenticatedUtil.getAuthenticatedUser();

		Site site = siteService.getSite(user.getLastVisitSiteId());

		ControlPanelHomeCommand homePageCommand = new ControlPanelHomeCommand();

		try {
			initInfo(homePageCommand, request, site, user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		homePageCommand.setUserId(user.getId());
		homePageCommand.setUserName(user.getUsername());
		homePageCommand.setEmail(user.getEmail());
		homePageCommand.setAddressLine1(user.getAddressLine1());
		homePageCommand.setAddressLine2(user.getAddressLine2());
		homePageCommand.setCityName(user.getCityName());
		homePageCommand.setPassword("");
		homePageCommand.setSiteId(site.getSiteId());
		homePageCommand.setSiteName(site.getSiteName());
		homePageCommand.setStateName(user.getStateName());
		homePageCommand.setCountryName(user.getCountryName());
		homePageCommand.setZipCode(user.getZipCode());
		homePageCommand.setPhone(user.getPhone());
		homePageCommand.setTabName("password");

		ModelAndView mav = new ModelAndView(
			"controlpanel/home/home", "homePageCommand", homePageCommand);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(
			@ModelAttribute ControlPanelHomeCommand homePageCommand,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		User user = AuthenticatedUtil.getAuthenticatedUser();

		Site site = siteService.getSite(user.getLastVisitSiteId());

		try {
			initInfo(homePageCommand, request, site, user);

			homePageCommand.setSiteName(site.getSiteName());

			String cmd = homePageCommand.getCmd();

			if (cmd.equals("password")) {
				password(homePageCommand, result);
			}
			else if (cmd.equals("update")) {
				update(homePageCommand, result);
			}
			else if (cmd.equals("switchSite")) {
				switchSite(request, homePageCommand, user, result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (result.hasErrors()) {
			mav.addObject("homePageCommand", homePageCommand);

			mav.setViewName("controlpanel/home/home");

			return mav;
		}

		status.setComplete();

		mav.addObject("homePageCommand", homePageCommand);
		mav.addObject("success", "success");

		mav.setViewName("controlpanel/home/home");

		return mav;
	}

	public void initInfo(
			ControlPanelHomeCommand homePageCommand,
			HttpServletRequest httpServletRequest, Site userSite, User user)
		throws Exception {

		Date userLastLoginDatetime = user.getLastLoginDate();

		if (userLastLoginDatetime != null) {
			homePageCommand.setLastLoginDatetime(
				DateUtil.getFullDatetime(userLastLoginDatetime));
		}

		SystemStatistics statistics = new SystemStatistics();

		homePageCommand.setServerStats(statistics.getServerStats());
		homePageCommand.setThreadStats(statistics.getThreadStats());
		homePageCommand.setJvmStats(statistics.getJvmStats());


		List<Site> sites = new ArrayList<Site>();

		Iterator iterator = null;

		if (PortalUtil.isAdminRole(user)) {
			iterator = siteService.getSites().iterator();

			while (iterator.hasNext()) {
				Site site = (Site)iterator.next();
				sites.add(site);
			}

			homePageCommand.setSites(sites);
		}
		else {
			iterator = user.getSites().iterator();

			while (iterator.hasNext()) {
				Site site = (Site)iterator.next();
				sites.add(site);
			}

			homePageCommand.setSites(sites);
		}

//		List<Country> countries = countryService.getBySiteId(
//			userSite.getSiteId());
//
//		homePageCommand.setCountries(countries);
//
//		List<State> states = stateService.getBySiteId(userSite.getSiteId());
//
//		homePageCommand.setStates(states);
	}

	public void password(
			ControlPanelHomeCommand homePageCommand, BindingResult result) {

		if (StringUtils.isBlank(homePageCommand.getPassword())) {
			result.rejectValue("password", "password-required");
		}
		else if (StringUtils.isNotBlank(homePageCommand.getPassword())) {
			if (!homePageCommand.getPassword().equals(homePageCommand.getVerifyPassword())) {
				result.rejectValue("password", "password-not-match");
			}

			if (Validator.isValidPassword(homePageCommand.getPassword())) {
				result.rejectValue("password", "invalid-password");
			}
		}

		if (result.hasErrors()) {
			homePageCommand.setTabName("password");

			return;
		}

		User user = userService.getUser(homePageCommand.getUserId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(homePageCommand.getPassword());

		user.setPassword(hashedPassword);

		userService.update(user);
	}

	public void switchSite(
			HttpServletRequest request, ControlPanelHomeCommand homePageCommand,
			User user, BindingResult result)
		throws Exception {

		int siteId = homePageCommand.getSiteId();

		Site site = null;

		if (user.getLastVisitSiteId() == siteId) {
			return;
		}

		if (!PortalUtil.isAdminRole(user)) {
			Iterator iterator = user.getSites().iterator();

			boolean found = false;

			while (iterator.hasNext()) {
				site = (Site)iterator.next();

				if (site.getSiteId() == homePageCommand.getSiteId()) {
					found = true;

					break;
				}
			}

			if (!found) {
				logger.equals(
					"Security violated - unable to switch site: userId = "
					+ user.getId() + ", siteId = " + siteId);

				throw new Exception(
						"Unable to switch site: userId = " + user.getId()
						+ ", siteId = " + siteId);
			}

		}

//		String listingPageSize = site.getListingPageSize();
//		String listingPageSize = Utility.getParam(
//			site, Constants.SITEPARAM_GENERAL_LISTING_PAGESIZE);

//		admin.init(
//			user.getUserId(), user.getUserType(), site.getSiteId(),
//			site.getSiteName(), listingPageSize);

		user.setLastVisitSiteId(siteId);

		userService.update(user);

		try {
			initInfo(homePageCommand, request, site, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(
			ControlPanelHomeCommand homePageCommand, BindingResult result) {

		if (StringUtils.isBlank(homePageCommand.getUserName())) {
			result.rejectValue("userName", "error.string.required");
		}

		if (result.hasErrors()) {
			homePageCommand.setTabName("profile");
			return;
		}

//		State state = stateService.getState(
//			homePageCommand.getSiteId(),
//			homePageCommand.getStateCode());
//
//		Country country = countryService.getCountry(
//			homePageCommand.getSiteId(),
//			homePageCommand.getCountryCode());

		User user = userService.getUser(homePageCommand.getUserId());

		user.setUsername(homePageCommand.getUserName());
		user.setEmail(homePageCommand.getEmail());
		user.setPhone(homePageCommand.getPhone());
		user.setAddressLine1(homePageCommand.getAddressLine1());
		user.setAddressLine2(homePageCommand.getAddressLine2());
		user.setCityName(homePageCommand.getCityName());
//		user.setStateName(state.getStateName());
//		user.setCountryName(country.getCountryName());
		user.setZipCode(homePageCommand.getZipCode());
		user.setUpdateBy(user);
		user.setUpdateDate(new Date());

		userService.update(user);
	}

	private final Logger logger = LoggerFactory.getLogger(ControlPanelHomeController.class);
}