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

import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.tool.Constants;

@Controller
public class SiteController {
	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/site/list", method = RequestMethod.GET)
	public String showPanel(Map<String, Object> model) {
		List<Site> sites = siteService.getSites();

		model.put("sites", sites);

		return "controlpanel/site/list";
	}

	@RequestMapping(value = "/controlpanel/site/add", method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		Site site = new Site();

		model.put("site", site);
		model.put("tabIndex", "0");

		return "controlpanel/site/edit";
	}

	@RequestMapping(value = "/controlpanel/site/add", method = RequestMethod.POST)
	public String processCreationForm(
			@ModelAttribute Site site,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		validate(site, result);

		if(result.hasErrors()) {
			return "controlpanel/site/edit";
		}

		siteService.addSite(site);

		return "redirect:/controlpanel/site/list";
	}

	public void validate(Site site, BindingResult result)
			throws Exception {
		if (StringUtils.isBlank(site.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}
	}

	@RequestMapping(value = "/controlpanel/site/{siteId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(
			@PathVariable("siteId") int siteId, Map<String, Object> model)
		throws Throwable {
		Site site = siteService.getSite(siteId);

		model.put("site", site);
		model.put("tabIndex", "0");

		return "controlpanel/site/edit";
	}

	@RequestMapping(value = "/controlpanel/site/{siteId}/edit", method = RequestMethod.POST)
	public String processUpdateForm(
			@ModelAttribute Site site, BindingResult result,
			SessionStatus status, HttpServletRequest request)
		throws Throwable {
		ModelMap model = new ModelMap();

		validateEdit(site, result);

		if(result.hasErrors()) {
			model.put("tabIndex", "0");

			return "redirect:/controlpanel/site/" + site.getSiteId() + "/edit";
		}

		siteService.update(site);

		return "redirect:/controlpanel/site/list";
	}

	private void validateEdit(Site site, BindingResult result)
			throws Exception {
		if (StringUtils.isNotBlank(site.getListingPageSize())) {
			result.rejectValue("listingPageSize", "error.float.invalid");
		}
		if (StringUtils.isNotBlank(site.getSectionPageSize())) {
			result.rejectValue("sectionPageSize", "error.float.invalid");
		}

		if (StringUtils.isBlank(site.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}

		List<Site> sites = siteService.getSites();

		for (Site siteDb : sites) {
			if (siteDb.getSiteId() == site.getSiteId()) {
				continue;
			}

			String formPortNum = Constants.PORTNUM_PUBLIC;

			if (StringUtils.isNotBlank(site.getPublicPort())) {
				formPortNum = site.getPublicPort();
			}

			String dbPortNum = Constants.PORTNUM_PUBLIC;

			if (StringUtils.isNotBlank(siteDb.getPublicPort())) {
				dbPortNum = siteDb.getPublicPort();
			}

			if (site.getDomainName().equals(siteDb.getDomainName()) && formPortNum == dbPortNum) {
				result.rejectValue("domainName", "error.domain.duplicate");
			}

			formPortNum = Constants.PORTNUM_SECURE;

			if (StringUtils.isNotBlank(site.getSecurePort())) {
				formPortNum = site.getSecurePort();
			}

//			dbPortNum = Constants.PORTNUM_SECURE;
//
//			if (Validator.isNotNull(site.getSecurePort())) {
//				dbPortNum = site.getSecurePort();
//			}
//
//			if (site.getDomainName().equals(site.getDomainName()) && formPortNum == dbPortNum) {
//				result.rejectValue("secureDomainName", "error.domain.duplicate");
//			}
		}
	}

	@RequestMapping(value="/controlpanel/site/list/search", method = RequestMethod.POST)
	public String search(
			@RequestParam("siteId") int siteId,
			@RequestParam("siteName") String siteName,
			@RequestParam("active") String active,
			HttpServletRequest request)
		throws Throwable {
		ModelMap model = new ModelMap();

		List<Site> sites = siteService.search(siteId, siteName, active);

		model.addAttribute("sites", sites);

		return "controlpanel/site/list";
	}

	@RequestMapping(value="/controlpanel/site/list/remove")
	public String removeSites(
			@RequestParam("siteIds") String siteIds,
			HttpServletRequest request) {
		String[] arrIds = siteIds.split(",");

		Site site = new Site();

		for (int i = 0; i < arrIds.length; i++) {
			site = siteService.getSite(Integer.valueOf(arrIds[i]));

			siteService.deleteSite(site);
		}

		return "redirect:/controlpanel/site/list";
	}
}