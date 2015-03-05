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

import com.yoda.site.SiteDisplayCommand;
import com.yoda.site.SiteEditCommand;
import com.yoda.site.SiteListCommand;
import com.yoda.site.dao.SiteDAO;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Transactional
@Service
public class SiteServiceImpl implements SiteService {

	@Autowired
	private SiteDAO siteDAO;

	public Site addSite(
			String siteName, char active, long userId,
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
	public Site getSite(long siteId) {
		return siteDAO.getBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public Site getSite(long siteId, User signinUser)
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

				Logger.getLogger(SiteServiceImpl.class).info("DomainName = " + site.getDomainName() + " , port = " + serverPort);

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
	public List<SiteDisplayCommand> search(SiteListCommand command) {
		Query query = null;

		String sql = "select site from Site site where 1 = 1 ";

		if (Validator.isNotNull(command.getSiteId())) {
			sql += "and siteId like :siteId ";
		}

		if (Validator.isNotNull(command.getSiteName())) {
			sql += "and siteName like :siteName ";
		}

		if (Validator.isNotNull(command.getActive()) && !command.getActive().equals("*")) {
			sql += "and active = :active ";
		}

		query = siteDAO.getSession().createQuery(sql);

		if (Validator.isNotNull(command.getSiteId())) {
			query.setString("siteId", "%" + command.getSiteId() + "%");
		}

		if (Validator.isNotNull(command.getSiteName())) {
			query.setString("siteName", "%" + command.getSiteName() + "%");
		}

		if (Validator.isNotNull(command.getActive()) && !command.getActive().equals("*")) {
			query.setString("active", command.getActive());
		}

		Iterator iterator = query.iterate();

		List<SiteDisplayCommand> sites = new ArrayList<SiteDisplayCommand>();

		while (iterator.hasNext()) {
			Site site = (Site) iterator.next();

			SiteDisplayCommand siteDisplay = new SiteDisplayCommand();

			siteDisplay.setSiteId(site.getSiteId());
			siteDisplay.setSiteName(site.getSiteName());

			String publicDomainNamePort = StringPool.BLANK;
			String secureDomainNamePort = StringPool.BLANK;

			publicDomainNamePort += site.getDomainName();

			if (Validator.isNotNull(site.getPublicPort())) {
				publicDomainNamePort += ":" + site.getPublicPort();
			}

			secureDomainNamePort += site.getDomainName();

			if (Validator.isNotNull(site.getSecurePort())) {
				secureDomainNamePort += ":" + site.getSecurePort();
			}

			siteDisplay.setPublicDomainNamePort(publicDomainNamePort);
			siteDisplay.setSecureDomainNamePort(secureDomainNamePort);

			siteDisplay.setActive(site.getActive());

			sites.add(siteDisplay);
		}

		return sites;
	}

	public Site updataSite(SiteEditCommand command, long userId) {
		Site site = getSite(command.getSiteId());

		site.setSiteName(command.getSiteName());
		site.setActive(command.getActive());
		site.setDomainName(command.getDomainName());
		site.setGoogleAnalyticsId(command.getGoogleAnalyticsId());
		site.setPublicPort(command.getPublicPort());
		site.setSecurePort(command.getSecurePort());
		site.setActive(Constants.VALUE_YES);
		site.setUpdateBy(userId);
		site.setUpdateDate(new Date());
		site.setSecureConnectionEnabled(command.isSecureConnectionEnabled());
		site.setFooter(command.getFooter());
		site.setListingPageSize(command.getListingPageSize());
		site.setSectionPageSize(command.getSectionPageSize());

		siteDAO.update(site);

		return site;
	}

//	public void initialize(Site site) {
//		siteDAO.initialize(site);
//	}

	public void updateSite(Site site) {
		siteDAO.update(site);
	}
}