package com.taklip.yoda.model;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author askar
 */
public class HomeInfo extends DataInfo {
    List<Content> homePageDatas;
    Content homePageFeatureData;
    String pageTitle;
    PageInfo<Content> page;

    public PageInfo<Content> getPage() {
        return page;
    }

    public void setPage(PageInfo<Content> page) {
        this.page = page;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<Content> getHomePageDatas() {
        return homePageDatas;
    }

    public void setHomePageDatas(List<Content> homePageDatas) {
        this.homePageDatas = homePageDatas;
    }

    public Content getHomePageFeatureData() {
        return homePageFeatureData;
    }

    public void setHomePageFeatureData(Content homePageFeatureData) {
        this.homePageFeatureData = homePageFeatureData;
    }
}
