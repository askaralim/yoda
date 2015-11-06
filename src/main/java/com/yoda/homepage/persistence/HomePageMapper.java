package com.yoda.homepage.persistence;

import java.util.List;

import com.yoda.homepage.model.HomePage;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface HomePageMapper extends BaseMapper<HomePage> {
	List<HomePage> getHomePages();

	List<HomePage> getBySiteId(int siteId);

	List<HomePage> getBySiteIdAndFeatureData(int siteId, boolean featureData);

	List<HomePage> getBySiteIdOrderBySeqNum(int siteId);
}