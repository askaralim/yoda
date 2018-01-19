package com.yoda.pageview.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.yoda.kernal.model.Pagination;
import com.yoda.pageview.model.PageView;

public interface PageViewService {
	void addPageView(PageView pageView);

	Pagination<PageView> getPageViews(RowBounds rowBounds);

	List<PageView> getPageViews();

	PageView getPageView(int id);

	List<PageView> getPageViewsByIP(String ip);

	List<PageView> getPageViewsByPage(int pageType, int pageId);

	List<PageView> getPageViewsByUser(int userId);
}