package com.taklip.yoda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController{
	@GetMapping("/logout")
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