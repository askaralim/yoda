package com.yoda.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.contactus.model.ContactUs;
import com.yoda.contactus.service.ContactUsService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;

@Controller
@RequestMapping("/contact")
public class FrontendContactUsController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontendContactUsController.class);

	@Autowired
	private ContactUsService contactUsService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showContactUs(
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite(request);

		List<ContactUs> contactUsList = contactUsService.getContactUs(site.getSiteId(), true);

		getHorizontalMenu(request, response, model);

		model.put("contactUsList", contactUsList);
		model.put("site", site);
		model.put("pageTitle", site.getSiteName() + " - " + "Contact Us");
		model.put("keywords", "");
		model.put("description", "");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "");

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

		if (csrfToken != null) {
			model.put("_csrf", csrfToken);
		}

		return new ModelAndView("portal/contactUs/contactUs", model);
	}
}