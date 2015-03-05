package com.yoda.site.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.SiteDisplayCommand;
import com.yoda.site.SiteListCommand;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;

@Controller
public class SiteController {
	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/site/list", method = RequestMethod.GET)
	public ModelAndView showPanel(
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

		SiteListCommand command = new SiteListCommand();

		List<SiteDisplayCommand> sites = siteService.search(command);

		command.setSites(sites);

//		command.setSites(null);
		if (command.getActive() == null) {
			command.setActive("*");
		}

		return new ModelAndView(
			"controlpanel/site/list", "siteListCommand", command);
	}

	@RequestMapping(value="/controlpanel/site/list/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute SiteListCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		List<SiteDisplayCommand> sites = siteService.search(command);

		command.setSites(sites);

		return "controlpanel/site/list";
	}

	@RequestMapping(value="/controlpanel/site/list/remove", method = RequestMethod.DELETE)
	public void removeContents(
			@RequestParam("siteIds") String siteIds,
			HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		String[] arrIds = siteIds.split(",");

		Site site = new Site();

		for (int i = 0; i < arrIds.length; i++) {
			siteService.getSite(user.getLastVisitSiteId());

			siteService.deleteSite(site);
		}
	}
}