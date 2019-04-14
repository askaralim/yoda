package com.yoda.pageview.persistence;

import java.util.List;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.pageview.model.PageViewData;

@YodaMyBatisMapper
public interface PageViewDataMapper extends BaseMapper<PageViewData> {
	List<PageViewData> getPageViewDatas();

	List<PageViewData> getPageViewDatasByIP(String ip);

	List<PageViewData> getPageViewDatasByPage(int pageType, int pageId);

	List<PageViewData> getPageViewDatasByUserId(int userId);
}