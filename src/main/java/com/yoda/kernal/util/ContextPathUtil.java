package com.yoda.kernal.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.yoda.util.StringPool;

public class ContextPathUtil {
	public static String getContextPath(HttpServletRequest request) {
		return getContextPath(request.getContextPath());
	}

	public static String getContextPath(ServletContext servletContext) {
		return getContextPath(servletContext.getContextPath());
	}

	public static String getContextPath(String contextPath) {
		if ((contextPath.length() == 0) ||
			contextPath.equals(StringPool.SLASH)) {

			contextPath = StringPool.BLANK;
		}
		else if (!contextPath.startsWith(StringPool.SLASH)) {
			contextPath = StringPool.SLASH.concat(contextPath);
		}

		return contextPath;
	}
}
