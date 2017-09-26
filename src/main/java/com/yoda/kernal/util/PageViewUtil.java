package com.yoda.kernal.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.pageview.model.PageView;
import com.yoda.pageview.service.PageViewService;
import com.yoda.user.model.User;

public class PageViewUtil {

	public static void viewPage(HttpServletRequest request, int pageType, int pageId, String pageName) {
		String ip = PortalUtil.getClientIP(request);

		PageView pageView = new PageView();

		pageView.setCreateDate(new Date());
		pageView.setPageId(pageId);
		pageView.setPageName(pageName);
		pageView.setPageType(pageType);
		pageView.setPageUrl(request.getRequestURL().toString());
		pageView.setUserIPAddress(ip);

		User user = PortalUtil.getAuthenticatedUser();

		if (null != user) {
			pageView.setUserId(user.getUserId());
			pageView.setUsername(user.getUsername());
		}

		getService().addPageView(pageView);
	}

	private static PageViewService getService() {
		PageViewService pageViewService = (PageViewService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("pageViewServiceImpl");

		return pageViewService;
	}
}