package com.yoda.homepage.service;

import java.util.List;

import com.yoda.content.model.Content;
import com.yoda.homepage.model.HomePage;

public interface HomePageService {
	List<HomePage> getHomePages(int siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId);

	List<HomePage> getHomePagesBySiteIdOrderBySeqNum(int siteId);

	HomePage getHomePage(long homePageId);

	void add(int siteId, boolean featureData, Content content);

	void delete(HomePage homePage);

	void update(HomePage homePage);
}
