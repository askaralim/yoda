package com.yoda.homepage.service;

import java.util.List;

import com.yoda.content.model.Content;
import com.yoda.homepage.model.HomePage;

public interface HomePageService {
	List<HomePage> getHomePages(int siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId);

	List<HomePage> getHomePages(int siteId, String orderBy);

	HomePage getHomePage(int siteId, long homePageId);

	void addHomePage(int siteId, long userId, boolean featureData, Content content);

	void deleteHomePage(HomePage homePage);

	void updateHomePage(HomePage homePage);
}
