package com.taklip.yoda.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taklip.yoda.tool.Constants;

@Controller
public class SystemController {
	@RequestMapping(value="/controlpanel/system/edit", method = RequestMethod.GET)
	public String showPanel(
		HttpServletRequest request, HttpServletResponse response) {
//		User user = PortalUtil.getAuthenticatedUser();
//
//		String loginMessage = AdminLookup.lookUpAdmin(request, response);
//
//		if (Validator.isNotNull(loginMessage)) {
//			ModelMap modelMap = new ModelMap();
//
//			modelMap.put("loginMessage", loginMessage);
//
//			return "controlpanel/system/edit";
//		}

		return "controlpanel/system/edit";
	}

	@RequestMapping(value="/controlpanel/system/edit", method = RequestMethod.POST)
	public String reset(
		HttpServletRequest request, HttpServletResponse response) {
		//String filename = Utility.getServletLocation(this.getServlet().getServletContext()) + Constants.CONFIG_COMPLETED_FILENAME;
//		String filename = ServletContextUtil.getRealPath() + Constants.CONFIG_COMPLETED_FILENAME;
		String filename = request.getServletContext().getRealPath(Constants.CONFIG_COMPLETED_FILENAME);

		File file = new File(filename);

		if (null != file) {
			file.delete();
		}

		return "controlpanel/system/done";
	}
}
