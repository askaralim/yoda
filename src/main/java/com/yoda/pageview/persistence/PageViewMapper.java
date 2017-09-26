package com.yoda.pageview.persistence;

import java.util.List;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.pageview.model.PageView;

@YodaMyBatisMapper
public interface PageViewMapper extends BaseMapper<PageView> {
	List<PageView> getPageViews();

	List<PageView> getPageViewsByIP(String ip);

	List<PageView> getPageViewsByPage(int pageType, int pageId);

	List<PageView> getPageViewsByUserId(int userId);
}