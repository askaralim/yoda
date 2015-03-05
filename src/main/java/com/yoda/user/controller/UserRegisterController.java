package com.yoda.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

		if (!Validator.isEmailAddress(email)) {
			model.addObject("error", "invalid-email");

			model.setViewName("portal/user/register");

			return model;
		}

		User userDb = userService.getUserByUserName(username);

		if (Validator.isNotNull(userDb)) {
			model.addObject("error", "duplicate-username");

			model.setViewName("portal/user/register");

			return model;
		}

		userDb = userService.getUserByEmail(email);

		if (Validator.isNotNull(userDb)) {
			model.addObject("error", "duplicate-email");

			model.setViewName("portal/user/register");

			return model;
		}

		long siteId = PortalUtil.getSiteId(request);

		try {
			User user = userService.addUser(
				username, password, email, StringPool.BLANK, Constants.USERTYPE_REGULAR,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, siteId, Constants.ACTIVE_YES, 0);
		}
		catch (PortalException e) {
			e.printStackTrace();
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

	Logger logger = Logger.getLogger(UserRegisterController.class);
}