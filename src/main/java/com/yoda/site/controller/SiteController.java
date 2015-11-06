package com.yoda.site.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;

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