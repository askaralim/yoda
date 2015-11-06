package com.yoda.site.service.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.dao.SiteDAO;
import com.yoda.site.model.Site;
import com.yoda.site.persistence.SiteMapper;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

@Transactional
@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	private SiteDAO siteDAO;

	@Autowired
	private SiteMapper siteMapper;

//	public Site addSite(
//			String siteName, boolean active,
//			String domainName, String googleAnalyticsId, String publicPort,
//			String securePort, boolean secureConnectionEnabled) {
	public Site addSite(Site site) {
		site.preInsert();

		siteMapper.insert(site);

//		Site siteDb = new Site();
//
//		siteDb.setSiteName(site.getSiteName());
//		siteDb.setActive(site.isActive());
//		siteDb.setCreateBy(PortalUtil.getAuthenticatedUser());
//		siteDb.setCreateDate(new Date(System.currentTimeMillis()));
//		siteDb.setDomainName(site.getDomainName());
//		siteDb.setFooter(site.getFooter());
//		siteDb.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
//		siteDb.setLogoPath("");
//		siteDb.setPublicPort(site.getPublicPort());
//		siteDb.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
//		siteDb.setSecurePort(site.getSecurePort());
//		siteDb.setThemeId(0);
//		siteDb.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		siteDb.setUpdateDate(new Date());
//
//		siteDAO.save(siteDb);

		return site;
	}

	public void deleteSite(Site site) {
		siteMapper.delete(site);
	}

	@Deprecated
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Site> getByQuery(String hql) {
		return (List<Site>)siteDAO.find(hql);
	}

	@Transactional(readOnly = true)
	public Site getDefaultSite(User user)
		throws Exception {
		if (PortalUtil.isAdminRole(user)) {
			List<Site> sites = this.getSites();

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

//		return siteDAO.getDefaultSite(user);
		return null;
	}

	@Transactional(readOnly = true)
	public Site getSite(int siteId) {
		return siteMapper.getById(siteId);
	}

	@Deprecated
	@Transactional(readOnly = true)
	public Site getSite(int siteId, User signinUser)
		throws SecurityException {
		if (!PortalUtil.isAdminRole(signinUser)) {
			throw new SecurityException();
		}

		return siteMapper.getById(siteId);
	}

	@Transactional(readOnly = true)
	public Site getSite(String serverName, int serverPort, boolean secure) {
//		List<Site> sites = siteDAO.getAll();
		List<Site> sites = siteMapper.getSites();

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
//		return siteDAO.getAll();
		return siteMapper.getSites();
	}

	@Transactional(readOnly = true)
	public List<Site> search(int siteId, String siteName, String active) {
		Site site = new Site();

		site.setSiteId(siteId);
		site.setSiteName(siteName);

		if (active.equals("1")) {
			site.setActive(true);
		}
		else if (active.equals("0")) {
			site.setActive(false);
		}

		List<Site> sites = siteMapper.search(site);

//		Query query = null;
//
//		String sql = "select site from Site site where 1 = 1 ";
//
//		if (Validator.isNotNull(siteId)) {
//			sql += "and siteId like :siteId ";
//		}
//
//		if (Validator.isNotNull(siteName)) {
//			sql += "and siteName like :siteName ";
//		}
//
//		if (Validator.isNotNull(active) && !active.equals("*")) {
//			sql += "and active = :active ";
//		}
//
//		query = siteDAO.getSession().createQuery(sql);
//
//		if (Validator.isNotNull(siteId)) {
//			query.setString("siteId", "%" + siteId + "%");
//		}
//
//		if (Validator.isNotNull(siteName)) {
//			query.setString("siteName", "%" + siteName + "%");
//		}
//
//		if (Validator.isNotNull(active) && !active.equals("*")) {
//			query.setString("active", active);
//		}
//
//		Iterator iterator = query.iterate();
//
//		List<Site> sites = new ArrayList<Site>();
//
//		while (iterator.hasNext()) {
//			Site site = (Site) iterator.next();
//
////			SiteDisplayCommand siteDisplay = new SiteDisplayCommand();
////
////			siteDisplay.setSiteId(site.getSiteId());
////			siteDisplay.setSiteName(site.getSiteName());
////
////			String publicDomainNamePort = StringPool.BLANK;
////			String secureDomainNamePort = StringPool.BLANK;
////
////			publicDomainNamePort += site.getDomainName();
////
////			if (Validator.isNotNull(site.getPublicPort())) {
////				publicDomainNamePort += ":" + site.getPublicPort();
////			}
////
////			secureDomainNamePort += site.getDomainName();
////
////			if (Validator.isNotNull(site.getSecurePort())) {
////				secureDomainNamePort += ":" + site.getSecurePort();
////			}
////
////			siteDisplay.setPublicDomainNamePort(publicDomainNamePort);
////			siteDisplay.setSecureDomainNamePort(secureDomainNamePort);
////
////			siteDisplay.setActive(site.isActive());
//
//			sites.add(site);
//		}

		return sites;
	}

	public void update(Site site) {
		site.preUpdate();

		siteMapper.update(site);

//		Site siteDb = getSite(site.getSiteId());
//
//		siteDb.setSiteName(site.getSiteName());
//		siteDb.setActive(site.isActive());
//		siteDb.setDomainName(site.getDomainName());
//		siteDb.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
//		siteDb.setPublicPort(site.getPublicPort());
//		siteDb.setLogoPath("");
//		siteDb.setSecurePort(site.getSecurePort());
//		siteDb.setActive(site.isActive());
//		siteDb.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		siteDb.setUpdateDate(new Date());
//		siteDb.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
//		siteDb.setFooter(site.getFooter());
//		siteDb.setListingPageSize(site.getListingPageSize());
//		siteDb.setSectionPageSize(site.getSectionPageSize());
//
////		this.updateSite(siteDb);
//		siteDAO.update(site);

//		return siteDb;
	}

//	public void initialize(Site site) {
//		siteDAO.initialize(site);
//	}

//	public void updateSite(Site site) {
//		siteDAO.update(site);
//	}
}