package com.yoda.menu;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.service.MenuService;
import com.yoda.user.model.User;

@Controller
@RequestMapping("/controlpanel/lookup/menusLookup")
public class MenusLookUpController {
	@Autowired
	MenuService menuService;

	@RequestMapping(method=RequestMethod.GET)
	public void lookUpSection(
			HttpServletRequest request,HttpServletResponse response)
		throws Exception {
		User user = PortalUtil.getAuthenticatedUser();

		JSONObject jsonSection = menuService.makeJSONMenuTree(user.getLastVisitSiteId());

		String jsonString = jsonSection.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
	}
}