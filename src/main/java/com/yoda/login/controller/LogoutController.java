package com.yoda.login.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController{
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(
			HttpServletRequest request, HttpServletResponse response) throws ServletException {
		request.logout();

		HttpSession session = request.getSession(false);

		if (null != session) {
			session.invalidate();
		}

		return "redirect:/";
	}
}