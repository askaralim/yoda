package com.taklip.yoda.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.HomePageMapper;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.HomePage;
import com.taklip.yoda.service.HomePageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author askar
 */
@Transactional
@Service
public class HomePageServiceImpl extends ServiceImpl<HomePageMapper, HomePage> implements HomePageService {
    public void add(int siteId, boolean featureData, Content content) {
        HomePage homePage = new HomePage();

        homePage.setSiteId(siteId);
        homePage.setFeatureData(featureData);
        homePage.setContent(content);

        this.save(homePage);
    }

    public void delete(HomePage homePage) {
        this.removeById(homePage);
    }

    @Transactional(readOnly = true)
    public HomePage getHomePage(long homePageId) {
        return this.getById(homePageId);
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePages(int siteId) {
        return this.list(new LambdaQueryWrapper<HomePage>().eq(HomePage::getSiteId, siteId));
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdOrderBySeqNum(int siteId) {
        return this.list(
                new LambdaQueryWrapper<HomePage>().eq(HomePage::getSiteId, siteId).orderByAsc(HomePage::getSeqNum));
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId) {
        return this.list(
                new LambdaQueryWrapper<HomePage>().eq(HomePage::getSiteId, siteId).eq(HomePage::isFeatureData, true));
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId) {
        return this.list(
                new LambdaQueryWrapper<HomePage>().eq(HomePage::getSiteId, siteId).eq(HomePage::isFeatureData, false));
    }

    public void update(HomePage homePage) {
        this.updateById(homePage);
    }
}