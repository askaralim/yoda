package com.yoda.kernal.util;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.util.Validator;

public class PortalInstances {
	public static void addSiteId(int siteId) {
		_instance._addSiteId(siteId);
	}

	public static Site getSite(HttpServletRequest request) {
		return _instance._getSite(request);
	}

	public static Site getSite(int siteId) {
		return _instance._getSite(siteId);
	}

	public static int[] getSiteIds() {
		return _instance._getSiteIds();
	}

	public static void initSites(ServletContext servletContext) {
		_instance._initSites(servletContext);
	}

	private PortalInstances() {
		_siteIds = new int[0];
	}

	private int _getDefaultSiteId() {
		return _siteIds[0];
	}

	private Site _getSite(int siteId) {
		return getService().getSite(siteId);
	}

	private Site _getSite(HttpServletRequest request) {
		if (_log.isDebugEnabled()) {
			_log.debug("Get site id");
		}

		Site site = _getSiteByVirtualHosts(request);

		if (Validator.isNull(site)) {
			int siteId = _getDefaultSiteId();

			if (_log.isDebugEnabled()) {
				_log.debug("Default site id " + siteId);
			}

			site = _getSite(siteId);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set site id " + site.getSiteId());
		}

		return site;
	}

	private void _addSiteId(int siteId) {
		for (int i = 0; i < _siteIds.length; i++) {
			if (siteId == _siteIds[i]) {
				return;
			}
		}

		int[] siteIds = new int[_siteIds.length + 1];

		System.arraycopy(_siteIds, 0, siteIds, 0, _siteIds.length);

		siteIds[_siteIds.length] = siteId;

		_siteIds = siteIds;
	}

	private Site _getSiteByVirtualHosts(HttpServletRequest request) {
		String host = PortalUtil.getHost(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Host " + host);
		}

		if (Validator.isNull(host)) {
			return null;
		}

		try {
			Site site = getService().getSite(
				host, request.getServerPort(), request.isSecure());

			return site;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private int[] _getSiteIds() {
		return _siteIds;
	}

	private void _initSites(ServletContext servletContext) {
		// Begin initializing site
		if (_log.isDebugEnabled()) {
			_log.debug("Begin initializing site");
		}

		try {
			List<Site> sites = getService().getSites();

			for(Site site : sites) {
				addSiteId(site.getSiteId());
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private SiteService getService() {
		SiteService siteService = (SiteService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("siteServiceImpl");

		return siteService;
	}

	private static Logger _log = Logger.getLogger(PortalInstances.class);

	private static PortalInstances _instance = new PortalInstances();

	private int[] _siteIds;
}