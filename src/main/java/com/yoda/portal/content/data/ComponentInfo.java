package com.yoda.portal.content.data;


//public class ComponentInfo extends DataInfo {
public class ComponentInfo {
//	String requestURL;
	String contextPath;
//	String resourcePrefix;
//	String servletResourcePrefix;
//	String templateResourcePrefix;
	String templateName;

//	public ComponentInfo(HttpServletRequest request) {
//		contextPath = request.getContextPath();
//	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

//	public String getTemplateResourcePrefix() {
//		return templateResourcePrefix;
//	}
//
//	public void setTemplateResourcePrefix(String templateResourcePrefix) {
//		this.templateResourcePrefix = templateResourcePrefix;
//	}
//
//	public String getServletResourcePrefix() {
//		return servletResourcePrefix;
//	}
//
//	public void setServletResourcePrefix(String servletResourcePrefix) {
//		this.servletResourcePrefix = servletResourcePrefix;
//	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

//	public String getResourcePrefix() {
//		return resourcePrefix;
//	}
//
//	public void setResourcePrefix(String resourcePrefix) {
//		this.resourcePrefix = resourcePrefix;
//	}

//	public String getRequestURL() {
//		return requestURL;
//	}
//
//	public void setRequestURL(String requestURL) {
//		this.requestURL = requestURL;
//	}
}