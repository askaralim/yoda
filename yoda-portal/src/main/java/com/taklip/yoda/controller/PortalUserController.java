package com.taklip.yoda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.Validator;
import com.taklip.yoda.validator.UserSettingsValidator;

@Controller
public class PortalUserController extends PortalBaseController {
	private final Logger logger = LoggerFactory.getLogger(PortalUserController.class);

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("userId") long userId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite(request);

		User user = userService.getUser(userId);

		if (null == user) {
			return new ModelAndView("/404", "requestURL", userId);
		}

		List<Content> contents = contentService.getContentByUserId(user.getUserId());

		model.put("user", user);
		model.put("contents", contents);

		setUserLoginStatus(request, response, model);

		model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
		model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
		model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
		model.put("site", site);

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("portal/user/profile", model);
	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		ModelMap model = new ModelMap();

		User user = AuthenticatedUtil.getAuthenticatedUser();

		if(null == user) {
			return new ModelAndView("redirect:/login", model);
		}

		model.put("user", user);
		model.put("tab", "basic");

		setUserLoginStatus(request, response, model);

		model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
		model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
		model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
		model.put("site", site);

		return new ModelAndView("portal/user/settings", model);
	}

	@RequestMapping(value = "/user/settings", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute User user,
			@RequestParam("photo") MultipartFile photo,
			BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite(request);

		setUserLoginStatus(request, response, model);

		model.put("site", site);

		new UserSettingsValidator().validate(user, result);

		if(result.hasErrors()) {
			logger.error(result.toString());
			return new ModelAndView("portal/user/settings", "errors", result.getAllErrors());
		}

		User userDb = userService.updateUser(
			site.getSiteId(), user.getUserId(), user.getUsername(),
			user.getPassword(), user.getEmail(), photo);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDb, userDb.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.put("success", "success");
		model.put("user", userDb);

		return new ModelAndView("portal/user/settings", model);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String setupForm() {
		return "portal/user/register";
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ModelAndView submit(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();

		User userDb = userService.getUserByUserName(username);

		if (null != userDb) {
			model.addObject("error", "duplicate-username");

			model.setViewName("portal/user/register");

			return model;
		}

		if (!Validator.isEmailAddress(email)) {
			model.addObject("error", "invalid-email");

			model.setViewName("portal/user/register");

			return model;
		}

		userDb = userService.getUserByEmail(email);

		if (null != userDb) {
			model.addObject("error", "duplicate-email");

			model.setViewName("portal/user/register");

			return model;
		}

		int siteId = siteService.getSites().get(0).getSiteId();

		try {
			userService.addUser(
				username, password, email, StringPool.BLANK, Constants.USER_ROLE_USER,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, siteId, true);
		}
		catch (Exception e) {
			logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email + e.getMessage());
		}

		try {
			UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email, password);

			Authentication result = authenticationManager.authenticate(authResult);

			// redirect into secured main page if authentication successful
			if (result.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(result);
				model.setViewName("redirect:/");

				return model;
			}
		}
		catch (Exception e) {
			logger.debug("Problem authenticating user" + username, e);
		}

		model.setViewName("redirect:/");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/user/register/ajax", method = RequestMethod.POST)
	public String ajaxRegister(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		User userDb = userService.getUserByUserName(username);

		RequestContext requestContext = new RequestContext(request);

		JSONObject jsonResult = new JSONObject();

		try {
			if (null != userDb) {
				jsonResult.put("error", requestContext.getMessage("duplicate-username"));
			}

			if (!Validator.isEmailAddress(email)) {
				jsonResult.put("error", requestContext.getMessage("invalid-email"));
			}

			userDb = userService.getUserByEmail(email);

			if (null != userDb) {
				jsonResult.put("error", requestContext.getMessage("duplicate-email"));
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

		if (jsonResult.isEmpty()) {
			int siteId = siteService.getSites().get(0).getSiteId();

			try {
				userService.addUser(
					username, password, email, StringPool.BLANK, Constants.USER_ROLE_USER,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, siteId, true);
			}
			catch (Exception e) {
				logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email + e.getMessage());
			}

			try {
				UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email, password);

				Authentication result = authenticationManager.authenticate(authResult);

				// redirect into secured main page if authentication successful
				if (result.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(result);
				}
			}
			catch (Exception e) {
				logger.debug("Problem authenticating user" + username, e);
			}
		}

		String jsonString = jsonResult.toString();

		return jsonString;
	}
}