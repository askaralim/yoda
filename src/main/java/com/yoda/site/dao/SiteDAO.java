package com.yoda.site.dao;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Constants;

@Repository
public class SiteDAO extends BaseDAO<Site> {

	private static final String FINDER_ALL_SITES = "FROM Site order by siteId";

	static Vector<Site> sites = new Vector<Site>();

	public List<Site> getAll(){
		return find(FINDER_ALL_SITES);
	}

	public Site getByS_U(int siteId, User signinUser)
			throws SecurityException{

		String userType = signinUser.getUserType();

		if (!userType.equals(Constants.USERTYPE_ADMIN)
			&& !userType.equals(Constants.USERTYPE_SUPER)) {
			throw new SecurityException();
		}

		return this.getBySiteId(siteId);
	}

	public Site getBySiteId(int siteId) {

		Enumeration e = sites.elements();

		Site site = null;

		while (e.hasMoreElements()) {
			site = (Site)e.nextElement();

			if (site.getSiteId() == siteId) {
				return site;
			}

		}

		site = getById(siteId);

		if (site != null) {
			initialize(site);

			sites.add(site);
		}

		return site;
	}

	public Site getDefaultSite(User user) throws Exception {

		if (user.getUserType().equals(Constants.USERTYPE_ADMIN)
			|| user.getUserType().equals(Constants.USERTYPE_SUPER)) {

			List<Site> sites = find("from Site site");

			while (sites.size() > 0) {
				Site site = sites.get(0);

				return site;
			}

		}
		else {
			Iterator iterator = user.getSites().iterator();

			while (iterator.hasNext()) {
				Site site = (Site)iterator.next();
				return site;
			}

		}

		return null;
	}

//	public void initialize(Site site) {
//		Hibernate.initialize(site);
//		Hibernate.initialize(site.getSiteParams());
//		Hibernate.initialize(site.getDomains());
//	}

	/*
	 * public SiteDAO(Site site) { this.siteId = site.getSiteId(); this.siteName
	 * = site.getSiteName();
	 * 
	 * SiteDomain siteDomain = null; Iterator domains =
	 * site.getDomains().iterator(); if (domains.hasNext()) { siteDomain =
	 * (SiteDomain) domains.next();
	 * this.setSiteDomainName(siteDomain.getSiteDomainName());
	 * this.setSitePublicPortNum(siteDomain.getSitePublicPortNum());
	 * this.setSiteSecurePortNum(siteDomain.getSiteSecurePortNum()); }
	 * 
	 * this.siteLogoValue = site.getSiteLogoValue(); this.siteLogoContentType =
	 * site.getSiteLogoContentType(); this.active = site.getActive();
	 * 
	 * String defaultPortNum = ""; publicURLPrefix = "http://" +
	 * getSiteDomainName(); defaultPortNum = Constants.PORTNUM_PUBLIC; if
	 * (!Format.isNullOrEmpty(getSitePublicPortNum()) &&
	 * !getSitePublicPortNum().equals(defaultPortNum)) { publicURLPrefix += ":"
	 * + getSitePublicPortNum(); }
	 * 
	 * secureURLPrefix = publicURLPrefix; String secureEnabled =
	 * Utility.getSiteParamValueString(site,
	 * Constants.SITEPARAM_CONNECTION_SECURE_ENABLED); if (secureEnabled != null
	 * && secureEnabled.equals(String.valueOf(Constants.VALUE_YES))) {
	 * secureURLPrefix = "https://" + getSiteDomainName(); defaultPortNum =
	 * Constants.PORTNUM_SECURE; if
	 * (!Format.isNullOrEmpty(getSiteSecurePortNum()) &&
	 * !getSiteSecurePortNum().equals(defaultPortNum)) { secureURLPrefix += ":"
	 * + getSiteSecurePortNum(); } siteSecureConnectionEnabled = true; }
	 * 
	 * Iterator iterator = site.getSiteParams().iterator(); while
	 * (iterator.hasNext()) { SiteParam siteParam = (SiteParam) iterator.next();
	 * siteParamDAOs.add(new SiteParamDAO(siteParam)); }
	 * 
	 * 
	 * }
	 * 
	 * public String getParam(String name) { String value = null; Iterator
	 * iterator = siteParamDAOs.iterator(); while (iterator.hasNext()) {
	 * SiteParamDAO siteParamDAO = (SiteParamDAO) iterator.next(); if
	 * (siteParamDAO.getSiteParamName().equals(name)) { return
	 * siteParamDAO.getSiteParamValue(); } } return value; }
	 */
}
