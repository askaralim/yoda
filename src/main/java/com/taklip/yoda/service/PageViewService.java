package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.PageView;

import java.util.List;

/**
 * @author askar
 */
public interface PageViewService extends IService<PageView> {
    void addPageView(PageView pageView);

    Page<PageView> getPageViews(Integer offset, Integer limit);

    List<PageView> getPageViews();

    PageView getPageView(int id);

    List<PageView> getPageViewsByIP(String ip);

    List<PageView> getPageViewsByPage(int pageType, int pageId);

    List<PageView> getPageViewsByUser(int userId);
}