package com.taklip.yoda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.SiteService;

public class SiteUtil {
	private static final Logger logger = LoggerFactory.getLogger(SiteUtil.class);

	private static Site site = null;

	private static SiteService siteService;

	public static synchronized Site getDefaultSite() {
		if (site == null) {
			site = getService().getSites().get(0);

			if (logger.isDebugEnabled()) {
				logger.debug("Set default site:" + site.getSiteName());
			}
		}
		return site;
	}

	private static SiteService getService() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (null != requestAttributes) {
			siteService = (SiteService)WebApplicationContextUtils.getRequiredWebApplicationContext(
				((ServletRequestAttributes) requestAttributes).getRequest().getServletContext()).getBean("siteServiceImpl");
		}

		return siteService;
	}
}