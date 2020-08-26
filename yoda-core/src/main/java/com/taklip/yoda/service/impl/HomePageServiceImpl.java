package com.taklip.yoda.service.impl;

import com.taklip.yoda.mapper.HomePageMapper;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.HomePage;
import com.taklip.yoda.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author askar
 */
@Transactional
@Service
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private HomePageMapper homePageMapper;

    public void add(int siteId, boolean featureData, Content content) {
        HomePage homePage = new HomePage();

        homePage.setSiteId(siteId);
        homePage.setFeatureData(featureData);
        homePage.setContent(content);

        homePage.preInsert();

        homePageMapper.insert(homePage);
    }

    public void delete(HomePage homePage) {
        homePageMapper.delete(homePage);
    }

    @Transactional(readOnly = true)
    public HomePage getHomePage(long homePageId) {
        return homePageMapper.getById(homePageId);
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePages(int siteId) {
        return homePageMapper.getBySiteId(siteId);
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdOrderBySeqNum(int siteId) {
        return homePageMapper.getBySiteIdOrderBySeqNum(siteId);
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId) {
        return homePageMapper.getBySiteIdAndFeatureData(siteId, true);
    }

    @Transactional(readOnly = true)
    public List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId) {
        return homePageMapper.getBySiteIdAndFeatureData(siteId, false);
    }

//    @Transactional(readOnly = true)
//    public Pagination<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId, RowBounds rowBounds) {
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        params.put("param1", siteId);
//        params.put("param2", false);
//
//        List<HomePage> homepages = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.HomePageMapper.getBySiteIdAndFeatureData", params, rowBounds);
//
//        List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.HomePageMapper.getBySiteIdAndFeatureDataCount", params);
//
//        Pagination<HomePage> page = new Pagination<HomePage>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), homepages);
//
//        return page;
//    }

    public void update(HomePage homePage) {
        homePage.preUpdate();

        homePageMapper.update(homePage);
    }
}