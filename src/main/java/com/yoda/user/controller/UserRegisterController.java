package com.yoda.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;

import com.yoda.exception.PortalException;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;
import com.yoda.user.service.LoginUserDetailsService;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
@RequestMapping(value = "/user/register")
public class UserRegisterController {
	@Autowired
	UserService userService;

	@Autowired
	LoginUserDetailsService loginUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm() {
		return "portal/user/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView sumbit(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();

		User userDb = userService.getUserByUserName(username);

		if (Validator.isNotNull(userDb)) {
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

		if (Validator.isNotNull(userDb)) {
			model.addObject("error", "duplicate-email");

			model.setViewName("portal/user/register");

			return model;
		}

		int siteId = PortalUtil.getSiteIdFromSession(request);

		try {
			userService.addUser(
				username, password, email, StringPool.BLANK, Constants.USER_ROLE_USER,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, siteId, true);
		}
		catch (PortalException e) {
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
	@RequestMapping(value = "/ajax", method = RequestMethod.POST)
	public String ajaxRegister(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		User userDb = userService.getUserByUserName(username);

		RequestContext requestContext = new RequestContext(request);

		JSONObject jsonResult = new JSONObject();

		try {
			if (Validator.isNotNull(userDb)) {
				jsonResult.put("error", requestContext.getMessage("duplicate-username"));
			}

			if (!Validator.isEmailAddress(email)) {
				jsonResult.put("error", requestContext.getMessage("invalid-email"));
			}

			userDb = userService.getUserByEmail(email);

			if (Validator.isNotNull(userDb)) {
				jsonResult.put("error", requestContext.getMessage("duplicate-email"));
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

		if (jsonResult.length() == 0) {
			int siteId = PortalUtil.getSiteIdFromSession(request);

			try {
				userService.addUser(
					username, password, email, StringPool.BLANK, Constants.USER_ROLE_USER,
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, siteId, true);
			}
			catch (PortalException e) {
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

	Logger logger = Logger.getLogger(UserRegisterController.class);
}