package com.yoda.site.service;

import java.util.List;

import com.yoda.site.SiteDisplayCommand;
import com.yoda.site.SiteEditCommand;
import com.yoda.site.SiteListCommand;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;

public interface SiteService {

	Site getSite(long siteId, User signinUser) throws SecurityException;

	Site getSite(long siteId);

	List<Site> getSites();

//	void initialize(Site site);

	List<Site> getByQuery(String hql);

	public void updateSite(Site site);

//	public String getPublicURLPrefix(Site site);

//	public String getSecureURLPrefix(Site site);

//	public boolean isSiteSecureConnectionEnabled(Site site);

	public Site getDefaultSite(User user) throws Exception;

	Site getSite(String serverName, int serverPort, boolean secure);

	Site addSite(String siteName, char active, long userId, String siteDomainName, String googleAnalyticsId, String publicPort, String securePort, boolean secureConnectionEnabled);

	Site updataSite(SiteEditCommand command, long userId);

	void deleteSite(Site site);

	List<SiteDisplayCommand> search(SiteListCommand command);
}
