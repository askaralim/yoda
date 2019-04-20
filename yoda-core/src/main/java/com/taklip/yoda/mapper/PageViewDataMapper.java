package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.PageViewData;

public interface PageViewDataMapper extends BaseMapper<PageViewData> {
	List<PageViewData> getPageViewDatas();

	List<PageViewData> getPageViewDatasByIP(String ip);

	List<PageViewData> getPageViewDatasByPage(int pageType, int pageId);

	List<PageViewData> getPageViewDatasByUserId(int userId);
}