package com.yoda.site.service.impl;//package com.yoda.controlpanel.site.service.impl;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yoda.controlpanel.site.dao.SiteParamDAO;
//import com.yoda.controlpanel.site.model.SiteParam;
//import com.yoda.controlpanel.site.service.SiteParamService;
//
//@Service
//public class SiteParamServiceImpl implements SiteParamService {
//	@Autowired
//	SiteParamDAO siteParamDAO;
//
//	public void deleteSiteParam(SiteParam siteParam) {
//		siteParamDAO.delete(siteParam);
//	}
//
//	public SiteParam addSiteParam(
//			String siteParamName, String siteParamValue, long userId) {
//		SiteParam siteParam = new SiteParam();
//
//		siteParam.setSiteParamName(siteParamName);
//		siteParam.setSiteParamValue(siteParamValue);
//		siteParam.setRecUpdateBy(userId);
//		siteParam.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		siteParam.setRecCreateBy(userId);
//		siteParam.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//
//		siteParamDAO.save(siteParam);
//
//		return siteParam;
//	}
//}
