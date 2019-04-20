package com.taklip.yoda.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.ModelMap;

import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.PortalUtil;

public class PortalBaseController {
	@Autowired
	protected UserService userService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	protected KafkaTemplate<String, String> kafkaTemplate;

	public void setUserLoginStatus(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		User loginUser = AuthenticatedUtil.getAuthenticatedUser();

		if (loginUser != null) {
			model.put("userLogin", true);
			model.put("userId", loginUser.getUserId());
			model.put("username", loginUser.getUsername());

			if (PortalUtil.isAdminRole(loginUser)) {
				model.put("roleAdmin", true);
			}
		}

		CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

		if (csrfToken != null) {
			model.put("_csrf", csrfToken);
		}

//		String horizontalMenuCode = MenuFactory.getHorizontalMenu(request, response);
//
//		model.put("horizontalMenuCode", horizontalMenuCode);

//		return DefaultTemplateEngine.getTemplate(request, response, "components/menus/horizontalMenu.vm", model);
	}

	public Site getSite(HttpServletRequest request) {
		return siteService.getSites().get(0);
	}

	public void pageView(HttpServletRequest request, int pageType, int pageId, String pageName) {
		String ip = PortalUtil.getClientIP(request);

		PageViewData pageView = new PageViewData();

		pageView.setCreateTime(DateUtil.getFullDatetime(new Date()));
		pageView.setPageId(pageId);
		pageView.setPageName(pageName);
		pageView.setPageType(pageType);
		pageView.setPageUrl(request.getRequestURL().toString());
		pageView.setUserIPAddress(ip);

		User user = AuthenticatedUtil.getAuthenticatedUser();

		if (null != user) {
			pageView.setUserId(user.getUserId());
			pageView.setUsername(user.getUsername());
		}

		kafkaTemplate.send(Constants.KAFKA_TOPIC_PAGE_VIEW, pageView.toString());
	}
}