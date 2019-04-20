package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.HomePage;

public interface HomePageMapper extends BaseMapper<HomePage> {
	List<HomePage> getHomePages();

	List<HomePage> getBySiteId(int siteId);

	List<HomePage> getBySiteIdAndFeatureData(int siteId, boolean featureData);

	List<HomePage> getBySiteIdOrderBySeqNum(int siteId);
}