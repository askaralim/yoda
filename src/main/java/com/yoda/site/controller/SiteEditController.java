package com.yoda.site.controller;

import java.util.List;

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
import com.yoda.site.SiteEditCommand;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/controlpanel/site/{siteId}/edit")
public class SiteEditController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	static String TABINDEX_GENERAL = "0";
	static String TABINDEX_SITELOGO = "1";
	static String TABINDEX_MAIL = "2";
	static String TABINDEX_TEMPLATE = "3";
	static String TABINDEX_BUSINESS = "4";
	static String TABINDEX_SHIPPING = "5";
	static String TABINDEX_CHECKOUT = "6";
	static String TABINDEX_PAYPAL = "7";
	static String TABINDEX_PSIGATE = "8";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("siteId") long siteId,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
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

		User signinUser = PortalUtil.getAuthenticatedUser();

		SiteEditCommand command = new SiteEditCommand();

		Site site = siteService.getSite(siteId, signinUser);

		copyProperties(command, site);

		initListInfo(command, site);

		return new ModelAndView("controlpanel/site/edit", "siteEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String updateContent(
			@ModelAttribute SiteEditCommand command, BindingResult result,
			SessionStatus status, HttpServletRequest request)
		throws Throwable {

		User user = PortalUtil.getAuthenticatedUser();

		Site site = new Site();

		site = siteService.getSite(command.getSiteId(), user);

		validate(command, result);

		if(result.hasErrors()) {
			initListInfo(command, site);

			return "redirect:/controlpanel/site/" + command.getSiteId() + "/edit";
		}

		site = siteService.updataSite(command, user.getUserId());

//		String listingPageSize = site.getListingPageSize();
//		String listingPageSize = Utility.getParam(
//			site, Constants.SITEPARAM_GENERAL_LISTING_PAGESIZE);

//		admin.init(
//			user.getUserId() , user.getUserType(), site.getSiteId(),
//			site.getSiteName(), listingPageSize);

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

	public void copyProperties(SiteEditCommand command, Site site)
			throws Exception {
		command.setSiteId(site.getSiteId());
		command.setSiteName(site.getSiteName());
		command.setDomainName(site.getDomainName());
		command.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
		command.setPublicPort(site.getPublicPort());
		command.setSecurePort(site.getSecurePort());
		command.setActive(site.getActive());
		command.setFooter(site.getFooter());
		command.setListingPageSize(site.getListingPageSize());
		command.setSectionPageSize(site.getSectionPageSize());
		command.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
	}

	private void validate(SiteEditCommand command, BindingResult result)
			throws Exception {
//		if (!Format.isNullOrEmpty(command.getPaymentPaypalExtraAmount()) && !Format.isFloat(command.getPaymentPaypalExtraAmount())) {
//			result.rejectValue("paymentPaypalExtraAmount", "error.float.invalid");
//
//			command.setTabIndex(TABINDEX_PAYPAL);
//		}
//		if (!Format.isNullOrEmpty(command.getPaymentPaypalExtraPercentage()) && !Format.isFloat(command.getPaymentPaypalExtraPercentage())) {
//			result.rejectValue("paymentPaypalExtraPercentage", "error.float.invalid");
//
//			command.setTabIndex(TABINDEX_PAYPAL);
//		}

		if (!Format.isNullOrEmpty(command.getListingPageSize()) && !Format.isInt(command.getListingPageSize())) {
			result.rejectValue("listingPageSize", "error.float.invalid");

			command.setTabIndex(TABINDEX_GENERAL);
		}
		if (!Format.isNullOrEmpty(command.getSectionPageSize()) && !Format.isInt(command.getSectionPageSize())) {
			result.rejectValue("sectionPageSize", "error.float.invalid");

			command.setTabIndex(TABINDEX_GENERAL);
		}

		if (Format.isNullOrEmpty(command.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}
		List<Site> sites = siteService.getSites();

		for (Site site : sites) {
			if (site.getSiteId() == command.getSiteId()) {
				continue;
			}

			String formPortNum = Constants.PORTNUM_PUBLIC;

			if (Validator.isNotNull(command.getPublicPort())) {
				formPortNum = command.getPublicPort();
			}

			String dbPortNum = Constants.PORTNUM_PUBLIC;

			if (Validator.isNotNull(site.getPublicPort())) {
				dbPortNum = site.getPublicPort();
			}

			if (command.getDomainName().equals(site.getDomainName()) && formPortNum == dbPortNum) {
				result.rejectValue("siteDomainName", "error.domain.duplicate");
			}

			formPortNum = Constants.PORTNUM_SECURE;

			if (Validator.isNotNull(command.getSecurePort())) {
				formPortNum = command.getSecurePort();
			}

			dbPortNum = Constants.PORTNUM_SECURE;

			if (Validator.isNotNull(site.getSecurePort())) {
				dbPortNum = site.getSecurePort();
			}

			if (command.getDomainName().equals(site.getDomainName()) && formPortNum == dbPortNum) {
				result.rejectValue("siteSecureDomainName", "error.domain.duplicate");
			}
		}
	}
}