package com.taklip.yoda.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.SiteMapper;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.AuthenticatedUtil;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {
    private final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    // public Site addSite(
    // String siteName, boolean active,
    // String domainName, String googleAnalyticsId, String publicPort,
    // String securePort, boolean secureConnectionEnabled) {
    public Site addSite(Site site) {
        this.save(site);

        // Site siteDb = new Site();
        //
        // siteDb.setSiteName(site.getSiteName());
        // siteDb.setActive(site.isActive());
        // siteDb.setCreateBy(PortalUtil.getAuthenticatedUser());
        // siteDb.setCreateDate(new Date(System.currentTimeMillis()));
        // siteDb.setDomainName(site.getDomainName());
        // siteDb.setFooter(site.getFooter());
        // siteDb.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
        // siteDb.setLogoPath("");
        // siteDb.setPublicPort(site.getPublicPort());
        // siteDb.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
        // siteDb.setSecurePort(site.getSecurePort());
        // siteDb.setThemeId(0);
        // siteDb.setUpdateBy(PortalUtil.getAuthenticatedUser());
        // siteDb.setUpdateDate(new Date());
        //
        // siteDAO.save(siteDb);

        return site;
    }

    public void deleteSite(Site site) {
        this.removeById(site.getId());
    }

    // @Deprecated
    // @Transactional(readOnly = true)
    // @SuppressWarnings("unchecked")
    public List<Site> getByQuery(String hql) {
        // return (List<Site>)siteDAO.find(hql);
        return null;
    }

    @Transactional(readOnly = true)
    public Site getDefaultSite(User user)
            throws Exception {
        if (AuthenticatedUtil.isAdminRole(user)) {
            List<Site> sites = this.getSites();

            while (sites.size() > 0) {
                Site site = sites.get(0);

                return site;
            }

        } else {
            // Iterator iterator = user.getSites().iterator();

            // while (iterator.hasNext()) {
            //     Site site = (Site) iterator.next();
            //     return site;
            // }

        }

        return null;
    }

    @Transactional(readOnly = true)
    public Site getSite(Long siteId) {
        return this.getById(siteId);
    }

    @Deprecated
    @Transactional(readOnly = true)
    public Site getSite(Long siteId, User signinUser)
            throws SecurityException {
        if (!AuthenticatedUtil.isAdminRole(signinUser)) {
            throw new SecurityException();
        }

        return this.getById(siteId);
    }

    @Transactional(readOnly = true)
    public Site getSite(String serverName, String serverPort, boolean secure) {
        // List<Site> sites = siteDAO.getAll();
        List<Site> sites = this.list();

        String port;

        for (Site site : sites) {
            if (secure) {
                port = Constants.PORTNUM_SECURE;

                if (StringUtils.isNotBlank(site.getSecurePort())) {
                    port = site.getSecurePort();
                }

                if (site.getDomainName().equals(serverName) && port.equals(serverPort)) {
                    return site;
                }
            } else {
                port = Constants.PORTNUM_PUBLIC;

                if (StringUtils.isNotBlank(site.getPublicPort())) {
                    port = site.getPublicPort();
                }

                logger.debug("DomainName = " + site.getDomainName() + " , port = " + serverPort);

                if (site.getDomainName().equals(serverName) && port.equals(serverPort)) {
                    return site;
                }

            }
        }

        return sites.get(0);
    }

    @Transactional(readOnly = true)
    public List<Site> getSites() {
        return this.list();
    }

    @Transactional(readOnly = true)
    public List<Site> search(Long siteId, String siteName, String active) {
        List<Site> sites = this.baseMapper.selectList(new LambdaQueryWrapper<Site>().eq(Site::getId, siteId)
                .like(Site::getSiteName, siteName).eq(Site::isActive, active));

        return sites;
    }

    public void update(Site site) {
        this.updateById(site);

        // Site siteDb = getSite(site.getSiteId());
        //
        // siteDb.setSiteName(site.getSiteName());
        // siteDb.setActive(site.isActive());
        // siteDb.setDomainName(site.getDomainName());
        // siteDb.setGoogleAnalyticsId(site.getGoogleAnalyticsId());
        // siteDb.setPublicPort(site.getPublicPort());
        // siteDb.setLogoPath("");
        // siteDb.setSecurePort(site.getSecurePort());
        // siteDb.setActive(site.isActive());
        // siteDb.setUpdateBy(PortalUtil.getAuthenticatedUser());
        // siteDb.setUpdateDate(new Date());
        // siteDb.setSecureConnectionEnabled(site.isSecureConnectionEnabled());
        // siteDb.setFooter(site.getFooter());
        // siteDb.setListingPageSize(site.getListingPageSize());
        // siteDb.setSectionPageSize(site.getSectionPageSize());
        //
        //// this.updateSite(siteDb);
        // siteDAO.update(site);

        // return siteDb;
    }

    @Override
    public String getSecureURLPrefix(Site site) {
        return "https://" + site.getDomainName() + ":" + site.getSecurePort();
    }

    // public void initialize(Site site) {
    // siteDAO.initialize(site);
    // }

    // public void updateSite(Site site) {
    // siteDAO.update(site);
    // }
}