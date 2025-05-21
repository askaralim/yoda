package com.taklip.yoda.common.util;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.support.SpringContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiteUtil {
    private static Site site = null;

    private static SiteService siteService;

    public static synchronized Site getDefaultSite() {
        if (site == null) {
            site = getService().getSites().get(0);

            if (log.isDebugEnabled()) {
                log.debug("Set default site: {}", site.getSiteName());
            }
        }
        return site;
    }

    private static SiteService getService() {
        siteService = (SiteService) SpringContextHolder.getBean("siteServiceImpl");

        return siteService;
    }
}