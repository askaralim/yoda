package com.yoda.portal.content;

import com.yoda.site.model.Site;

public class ContentBean {
	long siteId;
	String templateName;
//	String contextPath;
	Site site;

	public void setSite(Site site) {
		this.site = site;
	}

//	public String getContextPath() {
//		return contextPath;
//	}
//
//	public void setContextPath(String contextPath) {
//		this.contextPath = contextPath;
//	}

//	public ContentBean(long siteId) {
//		this.siteId = siteId;
//	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public long getSiteId() {
		return siteId;
	}

	public Site getSite() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Site site = (Site)session.load(Site.class, siteId);
		return this.site;
	}

//	public String getSiteParamValueString(String name) throws Exception {
////		Site site = getSite();
//
//		Iterator iterator = site.getSiteParams().iterator();
//
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//
//			if (siteParam.getSiteParamName().equals(name))
//				return siteParam.getSiteParamValue();
//		}
//
//		return null;
//	}

//	public Float getSiteParamValueFloat(String name) throws Exception {
////		Site site = getSite();
//
//		Iterator iterator = site.getSiteParams().iterator();
//
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//
//			if (siteParam.getSiteParamName().equals(name))
//				return Format.getFloatObj(siteParam.getSiteParamValue());
//		}
//
//		return null;
//	}
}