package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;

public interface SiteService {

	Site getSite(int siteId, User signinUser) throws SecurityException;

	Site getSite(int siteId);

	List<Site> getSites();

//	void initialize(Site site);

	@Deprecated
	List<Site> getByQuery(String hql);

//	void updateSite(Site site);

//	public String getPublicURLPrefix(Site site);

//	public String getSecureURLPrefix(Site site);

//	public boolean isSiteSecureConnectionEnabled(Site site);

	Site getDefaultSite(User user) throws Exception;

	Site getSite(String serverName, int serverPort, boolean secure);

	Site addSite(Site site);

	void update(Site site);

	void deleteSite(Site site);

	List<Site> search(int siteId, String siteName, String active);
}
