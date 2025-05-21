package com.taklip.yoda.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;

public interface SiteService extends IService<Site> {

    Site getSite(Long siteId, User signinUser) throws SecurityException;

    Site getSite(Long siteId);

    List<Site> getSites();

    // void initialize(Site site);

    // void updateSite(Site site);

    // public String getPublicURLPrefix(Site site);

    String getSecureURLPrefix(Site site);

    // public boolean isSiteSecureConnectionEnabled(Site site);

    Site getDefaultSite(User user) throws Exception;

    Site getSite(String serverName, String serverPort, boolean secure);

    Site addSite(Site site);

    void update(Site site);

    void deleteSite(Site site);

    List<Site> search(Long siteId, String siteName, String active);
}
