package com.yoda.kernal.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.user.model.UserAuthority;
import com.yoda.util.Validator;

public class PortalUtil {
	public static Site getSite(HttpServletRequest request) {
		int siteId = getSiteId(request);

		if (siteId <= 0) {
			return null;
		}

		Site site = (Site)request.getAttribute(WebKeys.SITE);

		if (site == null) {
			site = PortalInstances.getSite(siteId);

			request.setAttribute(WebKeys.SITE, site);
		}

		return site;
	}

	public static Site getSiteFromSession(HttpServletRequest request) {
		Site site = (Site)request.getSession().getAttribute(WebKeys.SITE);

		if (Validator.isNull(site)) {
			site = PortalUtil.getSite(request);

			request.getSession().setAttribute(WebKeys.SITE, site);
		}

		return site;
	}

	public static int getSiteId(HttpServletRequest request) {
		return PortalInstances.getSiteId(request);
	}

	public int[] getSiteIds() {
		return PortalInstances.getSiteIds();
	}

	public static String getHost(HttpServletRequest request) {
		String host = request.getHeader("Host");

		if (host != null) {
			host = host.trim().toLowerCase();

			int pos = host.indexOf(':');

			if (pos >= 0) {
				host = host.substring(0, pos);
			}
		}
		else {
			host = null;
		}

		return host;
	}

	public static User getAuthenticatedUser() {
//		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return (User)auth.getPrincipal();
		}

		return null;
	}

	public static boolean isAdminRole(UserDetails userDetail) {
		Set<UserAuthority> authorities = (Set<UserAuthority>)userDetail.getAuthorities();

		for (UserAuthority userAuthority : authorities) {
			if (userAuthority.getAuthorityName().equals("ROLE_ADMIN")) {
				return true;
			}
		}

		return false;
	}

	public static long getUserId(HttpServletRequest request) {
		Long userIdObj = (Long)request.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		HttpSession session = request.getSession();

		userIdObj = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userIdObj != null) {
			request.setAttribute(WebKeys.USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}
}
