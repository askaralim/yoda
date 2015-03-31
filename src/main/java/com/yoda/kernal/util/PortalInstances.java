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

	public static int getSiteId(HttpServletRequest request) {
		return _instance._getSiteId(request);
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

	private int _getSiteId(HttpServletRequest request) {
		if (_log.isDebugEnabled()) {
			_log.debug("Get site id");
		}

		Integer siteIdObj = (Integer)request.getAttribute(WebKeys.SITE_ID);

		if (_log.isDebugEnabled()) {
			_log.debug("Site id from request " + siteIdObj);
		}

		if (siteIdObj != null) {
			return siteIdObj;
		}

		int siteId = _getSiteIdByVirtualHosts(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Site id from host " + siteId);
		}

//		if (siteId <= 0) {
//			long cookieSiteId = GetterUtil.getLong(
//				CookieKeys.getCookie(request, CookieKeys.SITE_ID, false));
//
//			if (cookieSiteId > 0) {
//				try {
//					Site site = siteService.getSite(siteId);
//
//					if (Validator.isNotNull(site)) {
//						siteId = cookieSiteId;
//
//						if (_log.isDebugEnabled()) {
//							_log.debug("Site id from cookie " + siteId);
//						}
//					}
//
//				}
//				catch (Exception e) {
//					_log.error(e, e);
//				}
//			}
//		}

		if (siteId <= 0) {
			siteId = _getDefaultSiteId();

			if (_log.isDebugEnabled()) {
				_log.debug("Default site id " + siteId);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set site id " + siteId);
		}

		request.setAttribute(WebKeys.SITE_ID, new Integer(siteId));

		return siteId;
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

	private int _getSiteIdByVirtualHosts(HttpServletRequest request) {
		String host = PortalUtil.getHost(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Host " + host);
		}

		if (Validator.isNull(host)) {
			return 0;
		}

		try {
			Site site = getService().getSite(
				host, request.getServerPort(), request.isSecure());

			return site.getSiteId();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
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