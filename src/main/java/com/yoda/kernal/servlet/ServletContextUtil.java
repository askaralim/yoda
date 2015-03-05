package com.yoda.kernal.servlet;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.yoda.util.StringPool;
import com.yoda.util.Validator;

public class ServletContextUtil {
	public static String getContextPath() {
		return _instance._getContextPath();
	}

	public static ServletContext getServletContext() {
		return _instance._getServletContext();
	}

	public static String getRealPath() {
		return _instance._getRealPath();
	}

	public static void setServletContext(ServletContext servletContext) {
		_instance._setServletContext(servletContext);
	}

	public static void setContextPath(String contextPath) {
		_instance._setContextPath(contextPath);
	}

	private String _getContextPath() {
		return _contextPath;
	}

	private ServletContext _getServletContext() {
		if (_log.isDebugEnabled()) {
			_log.debug("Get " + _servletContext);
		}

		return _servletContext;
	}

	private String _getRealPath() {
		if (_servletContext == null) {
			return StringPool.BLANK;
		}

		if (Validator.isNotNull(_realPath)) {
			return _realPath;
		}

		_realPath = _servletContext.getRealPath(StringPool.SLASH);

		return _realPath;
	}

	private void _setContextPath(String contextPath) {
		_contextPath = contextPath;
	}

	private void _setServletContext(ServletContext servletContext) {
		if (_log.isDebugEnabled()) {
			_log.debug("Put " + servletContext);
		}

		_servletContext = servletContext;
	}

	Logger _log = Logger.getLogger(ServletContextUtil.class);

	private static ServletContextUtil _instance = new ServletContextUtil();

	private ServletContext _servletContext;

	private String _realPath;

	private String _contextPath;
}
