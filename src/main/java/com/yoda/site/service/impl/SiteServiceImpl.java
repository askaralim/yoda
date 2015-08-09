package com.yoda.site.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.site.dao.SiteDAO;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

@Transactional
@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	private SiteDAO siteDAO;

	public Site addSite(
			String siteName, boolean active, long userId,
			String domainName, String googleAnalyticsId, String publicPort,
			String securePort, boolean secureConnectionEnabled) {
		Site site = new Site();

		site.setSiteName(siteName);
		site.setActive(active);
		site.setCreateBy(userId);
		site.setCreateDate(new Date(System.currentTimeMillis()));
		site.setDomainName(domainName);
		site.setGoogleAnalyticsId(googleAnalyticsId);
		site.setPublicPort(publicPort);
		site.setSecurePort(securePort);
		site.setUpdateBy(userId);
		site.setUpdateDate(new Date());
		site.setSecureConnectionEnabled(secureConnectionEnabled);

		siteDAO.save(site);

		return site;
	}

	public void deleteSite(Site site) {
		siteDAO.delete(site);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Site> getByQuery(String hql) {
		return (List<Site>)siteDAO.find(hql);
	}

	@Transactional(readOnly = true)
	public Site getDefaultSite(User user)
		throws Exception {
		return siteDAO.getDefaultSite(user);
	}

	@Transactional(readOnly = true)
	public Site getSite(int siteId) {
		return siteDAO.getBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public Site getSite(int siteId, User signinUser)
		throws SecurityException {
		return siteDAO.getByS_U(siteId, signinUser);
	}

	@Transactional(readOnly = true)
	public Site getSite(String serverName, int serverPort, boolean secure) {
		List<Site> sites = siteDAO.getAll();

		String port;

		for (Site site : sites) {
			if (secure) {
				port = Constants.PORTNUM_SECURE;

				if (Validator.isNotNull(site.getSecurePort())) {
					port = site.getSecurePort();
				}

				if (site.getDomainName().equals(serverName) && port.equals(serverPort)) {
					return site;
				}
			}
			else {
				port = Constants.PORTNUM_PUBLIC;

				if (Validator.isNotNull(site.getPublicPort())) {
					port = site.getPublicPort();
				}

				Logger.getLogger(SiteServiceImpl.class).debug("DomainName = " + site.getDomainName() + " , port = " + serverPort);

				if (site.getDomainName().equals(serverName) && port.equals(serverPort)) {
					return site;
				}

			}
		}

		return sites.get(0);
	}

	@Transactional(readOnly = true)
	public List<Site> getSites() {
		return siteDAO.getAll();
	}

	@Transactional(readOnly = true)
	public List<Site> search(int siteId, String siteName, String active) {
		Query query = null;

		String sql = "select site from Site site where 1 = 1 ";

		if (Validator.isNotNull(siteId)) {
			sql += "and siteId like :siteId ";
		}

		if (Validator.isNotNull(siteName)) {
			sql += "and siteName like :siteName ";
		}

		if (Validator.isNotNull(active) && !active.equals("*")) {
			sql += "and active = :active ";
		}

		query = siteDAO.getSession().createQuery(sql);

		if (Validator.isNotNull(siteId)) {
			query.setString("siteId", "%" + siteId + "%");
		}

		if (Validator.isNotNull(siteName)) {
			query.setString("siteName", "%" + siteName + "%");
		}

		if (Validator.isNotNull(active) && !active.equals("*")) {
			query.setString("active", active);
		}

		Iterator iterator = query.iterate();

		List<Site> sites = new ArrayList<Site>();

		while (iterator.hasNext()) {
			Site site = (Site) iterator.next();

//			SiteDisplayCommand siteDisplay = new SiteDisplayCommand();
//
//			siteDisplay.setSiteId(site.getSiteId());
//			siteDisplay.setSiteName(site.getSiteName());
//
//			String publicDomainNamePort = StringPool.BLANK;
//			String secureDomainNamePort = StringPool.BLANK;
//
//			publicDomainNamePort += site.getDomainName();
//
//			if (Validator.isNotNull(site.getPublicPort())) {
//				publicDomainNamePort += ":" + site.getPublicPort();
//			}
//
//			secureDomainNamePort += site.getDomainName();
//
//			if (Validator.isNotNull(site.getSecurePort())) {
//				secureDomainNamePort += ":" + site.getSecurePort();
//			}
//
//			siteDisplay.setPublicDomainNamePort(publicDomainNamePort);
//			siteDisplay.setSecureDomainNamePort(secureDomainNamePort);
//
//			siteDisplay.setActive(site.isActive());

			sites.add(site);
		}

		return sites;
	}

	public Site updataSite(Site site, long userId) {
		Site siteDb = getSite(site.getSiteId());

		siteDb.setSiteName(site.getSiteName());
		siteDb.setActive(site.isActive());
		siteDb.setDomainName(site.getDomainName());
		siteDb.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
		siteDb.setPublicPort(site.getPublicPort());
		siteDb.setSecurePort(site.getSecurePort());
		siteDb.setActive(site.isActive());
		siteDb.setUpdateBy(userId);
		siteDb.setUpdateDate(new Date());
		siteDb.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
		siteDb.setFooter(site.getFooter());
		siteDb.setListingPageSize(site.getListingPageSize());
		siteDb.setSectionPageSize(site.getSectionPageSize());

		this.updateSite(siteDb);

		return siteDb;
	}

//	public void initialize(Site site) {
//		siteDAO.initialize(site);
//	}

	public void updateSite(Site site) {
		siteDAO.update(site);
	}
}