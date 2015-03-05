package com.yoda.portal.content.data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import com.yoda.kernal.servlet.ServletContextUtil;

public class DefaultTemplateEngine {
	private static final String TEMPLATE_ENCODING = "UTF-8";

	private static final String WEB_CONTEXT_SERVLET_NAME = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.Spring MVC Dispatcher Servlet";

	static Logger logger = Logger.getLogger(DefaultTemplateEngine.class);

	public static String encodeURL(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "UTF-8");
	}

	public static String getTemplate(
			HttpServletRequest request, HttpServletResponse response,
			String templateName, Map<String, Object> model) {
		model.put(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE,
			new RequestContext(request, response, ServletContextUtil.getServletContext(), model));
		return VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(), templateName, TEMPLATE_ENCODING, model);
	}

	private static VelocityEngine getVelocityEngine() {
		VelocityConfig velocityConfig = (VelocityConfig)WebApplicationContextUtils.getWebApplicationContext(
			ServletContextUtil.getServletContext(), WEB_CONTEXT_SERVLET_NAME).getBean("velocityConfig");

		return velocityConfig.getVelocityEngine();
	}
}