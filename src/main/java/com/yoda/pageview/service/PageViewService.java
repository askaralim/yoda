package com.yoda.pageview.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.yoda.kernal.model.Pagination;
import com.yoda.pageview.model.PageViewData;

public interface PageViewService {
	void addPageViewData(PageViewData pageView);

	Pagination<PageViewData> getPageViewDatas(RowBounds rowBounds);

	List<PageViewData> getPageViewDatas();

	PageViewData getPageViewData(int id);

	List<PageViewData> getPageViewDatasByIP(String ip);

	List<PageViewData> getPageViewDatasByPage(int pageType, int pageId);

	List<PageViewData> getPageViewDatasByUser(int userId);
}