package com.taklip.yoda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class InvalidSessionController {
	@RequestMapping("/invalidSession")
	public ModelAndView logout(
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(
				"/portal/login/login", "loginMessage", "error.login.sessionexpire");
	}
}
