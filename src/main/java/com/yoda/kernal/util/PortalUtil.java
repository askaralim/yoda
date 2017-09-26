package com.yoda.kernal.util;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.user.model.UserAuthority;
import com.yoda.util.Validator;

public class PortalUtil {
	public static Site getSite(HttpServletRequest request) {
		return PortalInstances.getSite(request);
	}

	public static Site getSiteFromSession(HttpServletRequest request) {
		Site site = (Site)request.getSession().getAttribute(WebKeys.SITE);

		if (Validator.isNull(site)) {
			site = getSite(request);

			request.getSession().setAttribute(WebKeys.SITE, site);
		}

		return site;
	}

	public static int getSiteIdFromSession(HttpServletRequest request) {
		return getSiteFromSession(request).getSiteId();
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

	public static String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");

		if ((xfHeader == null) || (!Validator.isIPAddress(xfHeader.split(",")[0]))) {
			return request.getRemoteAddr();
		}

		return xfHeader.split(",")[0];
	}

	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

		if (null != requestAttributes) {
			return ((ServletRequestAttributes) requestAttributes).getRequest();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
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