package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.PageViewData;

import java.util.List;

/**
 * @author askar
 */
public interface PageViewService {
    void addPageViewData(PageViewData pageView);

    PageInfo<PageViewData> getPageViewDatas(Integer offset, Integer limit);

    List<PageViewData> getPageViewDatas();

    PageViewData getPageViewData(int id);

    List<PageViewData> getPageViewDatasByIP(String ip);

    List<PageViewData> getPageViewDatasByPage(int pageType, int pageId);

    List<PageViewData> getPageViewDatasByUser(int userId);
}