package com.yoda.login.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.kernal.util.WebKeys;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private SiteService siteService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();

		Site site = PortalUtil.getSite(request);

		if (error != null) {
//			model.addObject("error", error);
			model.addObject(
				"exception",
				getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		model.addObject("siteTitle", site.getSiteName());

		model.setViewName("/portal/login/login");

		return model;
	}

	@RequestMapping(value = "/login/success", method = RequestMethod.GET)
	public String loginSuccess(HttpServletRequest request) throws Exception {
		User currentUser = PortalUtil.getAuthenticatedUser();

		Site site = null;

		if (!Validator.isNull(currentUser.getLastVisitSiteId())) {
			try {
				site = siteService.getSite(currentUser.getLastVisitSiteId());
			}
			catch (ObjectNotFoundException e) {
				logger.info("Site " + currentUser.getLastVisitSiteId()
					+ " not found for use " + currentUser.getUserId());

			}
		}

		if (site == null) {
			site = siteService.getDefaultSite(currentUser);
		}

		currentUser.setLastVisitSiteId(site.getSiteId());
		currentUser.setLastLoginDate(new Date());

		userService.update(currentUser);

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.SITE, site);

		return "redirect:/";
	}

	private String getErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception)request.getSession().getAttribute(key);

		String error = StringPool.BLANK;

		if (exception instanceof BadCredentialsException) {
			error = "invalid-email-and-password";
		}
		else if (exception instanceof LockedException) {
			error = exception.getMessage();
		}
		else {
			error = "user-not-exit";
		}

		return error;
	}

	@RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (Validator.isNotNull(auth) && !(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");

		return model;
	}

	@RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView resourceUnavailable(HttpServletRequest request) {
		String requestURL = request.getRequestURL().toString();

		return new ModelAndView("/404", "requestURL", requestURL);
	}

	@RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ServerError(HttpServletRequest request) {
		String requestURL = request.getRequestURL().toString();

		return new ModelAndView("/500", "requestURL", requestURL);
	}

	Logger logger = Logger.getLogger(LoginController.class);
}