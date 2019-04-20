package com.taklip.yoda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InvalidSessionController {
	@RequestMapping("/invalidSession")
	public ModelAndView logout(
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(
			"/portal/login/login", "loginMessage", "error.login.sessionexpire");
	}
}
