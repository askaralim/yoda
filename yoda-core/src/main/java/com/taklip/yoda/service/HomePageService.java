package com.taklip.yoda.service;

import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.HomePage;

import java.util.List;
//import com.taklip.yoda.model.Pagination;

public interface HomePageService {
    List<HomePage> getHomePages(int siteId);

    List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId);

    List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId);

//	Pagination<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId, RowBounds rowBounds);

    List<HomePage> getHomePagesBySiteIdOrderBySeqNum(int siteId);

    HomePage getHomePage(long homePageId);

    void add(int siteId, boolean featureData, Content content);

    void delete(HomePage homePage);

    void update(HomePage homePage);
}
