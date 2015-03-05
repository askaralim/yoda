package com.yoda.homepage.service;

import java.util.List;

import com.yoda.content.model.Content;
import com.yoda.homepage.model.HomePage;

public interface HomePageService {
	List<HomePage> getHomePages(long siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureData(long siteId);

	List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(long siteId);

	List<HomePage> getHomePages(long siteId, String orderBy);

	HomePage getHomePage(long siteId, long homePageId);

	void addHomePage(long siteId, long userId, char featureData, Content content);

	void deleteHomePage(HomePage homePage);

	void updateHomePage(HomePage homePage);
}
