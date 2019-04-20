package com.taklip.yoda.util;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.UserAuthority;


public class PortalUtil {
	public static final String USER_ID = "USER_ID";

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
		Long userIdObj = (Long)request.getAttribute(USER_ID);

		if (userIdObj != null) {
			return userIdObj.longValue();
		}

		HttpSession session = request.getSession();

		userIdObj = (Long)session.getAttribute(USER_ID);

		if (userIdObj != null) {
			request.setAttribute(USER_ID, userIdObj);

			return userIdObj.longValue();
		}
		else {
			return 0;
		}
	}

	static public boolean isContentPublished(Content content) {
		if (!content.isPublished()) {
			return false;
		}

		Date current = new Date();

		if (current.before(content.getPublishDate())
			|| current.after(content.getExpireDate())) {

			return false;
		}

		return true;
	}
}