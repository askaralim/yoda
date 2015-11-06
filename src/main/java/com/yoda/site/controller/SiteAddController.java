package com.yoda.site.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Format;

@Controller
@RequestMapping("/controlpanel/site/add")
public class SiteAddController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(method = RequestMethod.GET)
	public String initCreationForm(Map<String, Object> model) {
		Site site = new Site();

		model.put("site", site);
		model.put("tabIndex", "0");

		return "controlpanel/site/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processCreationForm(
			@ModelAttribute Site site,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		validate(site, result);

//		Site site = new Site();

		if(result.hasErrors()) {
//			initListInfo(command, site);

			return "controlpanel/site/edit";
		}

		siteService.addSite(site);

//		SiteLoader siteLoader = new SiteLoader(command.getSiteId(), user.getUserId());

//		siteLoader.load();

//		Currency currency = CurrencyDAO.loadByCurrencyCode(command.getSiteId(), "USD");

//		command.setCurrencyId(currency.getCurrencyId().toString());

//		saveSiteParam(adminBean, site, Constants.SITEPARAM_GENERAL_CURRENCY_DEFAULT, currency.getCurrencyId().toString());

//		SiteCache.removeSite(command.getSiteId());

//		adminBean.init(adminBean.getUser().getUserId(), adminBean.getSiteId());

//		initListInfo(command, site);

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

	public void validate(Site site, BindingResult result)
			throws Exception {
		if (Format.isNullOrEmpty(site.getSiteName())) {
			result.rejectValue("siteName", "error.string.required");
		}
	}
}