package com.taklip.yoda.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.support.SpringContextHolder;

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
		siteService = (SiteService) SpringContextHolder.getBean("siteServiceImpl");

		return siteService;
	}
}