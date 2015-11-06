package com.yoda.site.controller;

import java.util.List;
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
import org.springframework.web.bind.support.SessionStatus;

import com.yoda.kernal.util.PortalUtil;
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
	public String initUpdateForm(
			@PathVariable("siteId") int siteId, Map<String, Object> model)
		throws Throwable {
//		User signinUser = PortalUtil.getAuthenticatedUser();

//		Site site = siteService.getSite(siteId, signinUser);
		Site site = siteService.getSite(siteId);

//		copyProperties(command, site);

//		initListInfo(command, site);

		model.put("site", site);
		model.put("tabIndex", "0");

		return "controlpanel/site/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processUpdateForm(
			@ModelAttribute Site site, BindingResult result,
			SessionStatus status, HttpServletRequest request)
		throws Throwable {
		ModelMap model = new ModelMap();

		User user = PortalUtil.getAuthenticatedUser();

		Site siteDb = new Site();

//		siteDb = siteService.getSite(site.getSiteId(), user);
		siteDb = siteService.getSite(site.getSiteId());

		validate(site, result);

		if(result.hasErrors()) {
//			initListInfo(site, siteDb);
			model.put("tabIndex", "0");

			return "redirect:/controlpanel/site/" + site.getSiteId() + "/edit";
		}

		siteService.update(site);

//		String listingPageSize = site.getListingPageSize();
//		String listingPageSize = Utility.getParam(
//			site, Constants.SITEPARAM_GENERAL_LISTING_PAGESIZE);

//		admin.init(
//			user.getUserId() , user.getUserType(), site.getSiteId(),
//			site.getSiteName(), listingPageSize);

//		initListInfo(site, siteDb);

		return "redirect:/controlpanel/site/list";
	}

//	public void initListInfo(SiteEditCommand command, Site site)
//			throws Exception {
//		command.setLogoContentType(site.getLogoContentType());
//
//		if (Format.isNullOrEmpty(command.getTabIndex())) {
//			command.setTabIndex("0");
//		}
//	}

	private void validate(Site site, BindingResult result)
			throws Exception {
		if (!Format.isNullOrEmpty(site.getListingPageSize()) && !Format.isInt(site.getListingPageSize())) {
			result.rejectValue("listingPageSize", "error.float.invalid");
		}
		if (!Format.isNullOrEmpty(site.getSectionPageSize()) && !Format.isInt(site.getSectionPageSize())) {
			result.rejectValue("sectionPageSize", "error.float.invalid");
		}

		if (Format.isNullOrEmpty(site.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}
		List<Site> sites = siteService.getSites();

		for (Site siteDb : sites) {
			if (siteDb.getSiteId() == site.getSiteId()) {
				continue;
			}

			String formPortNum = Constants.PORTNUM_PUBLIC;

			if (Validator.isNotNull(site.getPublicPort())) {
				formPortNum = site.getPublicPort();
			}

			String dbPortNum = Constants.PORTNUM_PUBLIC;

			if (Validator.isNotNull(site.getPublicPort())) {
				dbPortNum = site.getPublicPort();
			}

			if (site.getDomainName().equals(site.getDomainName()) && formPortNum == dbPortNum) {
				result.rejectValue("siteDomainName", "error.domain.duplicate");
			}

			formPortNum = Constants.PORTNUM_SECURE;

			if (Validator.isNotNull(site.getSecurePort())) {
				formPortNum = site.getSecurePort();
			}

			dbPortNum = Constants.PORTNUM_SECURE;

			if (Validator.isNotNull(site.getSecurePort())) {
				dbPortNum = site.getSecurePort();
			}

			if (site.getDomainName().equals(site.getDomainName()) && formPortNum == dbPortNum) {
				result.rejectValue("siteSecureDomainName", "error.domain.duplicate");
			}
		}
	}
}