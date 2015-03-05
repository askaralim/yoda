package com.yoda.site.controller;

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
import com.yoda.site.SiteEditCommand;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;

@Controller
@RequestMapping("/controlpanel/site/add")
public class SiteAddController {
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

		SiteEditCommand command = new SiteEditCommand();

		command.setSiteId(null);
		command.setSiteName("");
		command.setDomainName("");
		command.setPublicPort(null);
		command.setSecurePort(null);
		command.setActive(Constants.ACTIVE_NO);

		return new ModelAndView("controlpanel/site/edit", "siteEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute SiteEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User user = PortalUtil.getAuthenticatedUser();

		validate(command, result);

		Site site = new Site();

		if(result.hasErrors()) {
			initListInfo(command, site);

			return "controlpanel/site/edit";
		}

		char active;

		if ((command.getActive() != null) && (command.getActive() == Constants.ACTIVE_YES)) {
			active = Constants.ACTIVE_YES;
		}
		else {
			active = Constants.PUBLISHED_NO;
		}

		site = siteService.addSite(
			command.getSiteName(), active, user.getUserId(),
			command.getDomainName(), command.getGoogleAnalyticsId(),
			command.getPublicPort(), command.getSecurePort(),
			command.isSecureConnectionEnabled());

//		SiteLoader siteLoader = new SiteLoader(command.getSiteId(), user.getUserId());

//		siteLoader.load();

//		Currency currency = CurrencyDAO.loadByCurrencyCode(command.getSiteId(), "USD");

//		command.setCurrencyId(currency.getCurrencyId().toString());

//		saveSiteParam(adminBean, site, Constants.SITEPARAM_GENERAL_CURRENCY_DEFAULT, currency.getCurrencyId().toString());

//		SiteCache.removeSite(command.getSiteId());

//		adminBean.init(adminBean.getUser().getUserId(), adminBean.getSiteId());

		initListInfo(command, site);

		return "redirect:/controlpanel/site/list";
	}

	public void initListInfo(SiteEditCommand command, Site site)
			throws Exception {
		command.setLogoContentType(site.getLogoContentType());

		if (Format.isNullOrEmpty(command.getTabIndex())) {
			command.setTabIndex("0");
		}
	}

	public void validate(SiteEditCommand command, BindingResult result)
			throws Exception {
		if (Format.isNullOrEmpty(command.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}
	}
}