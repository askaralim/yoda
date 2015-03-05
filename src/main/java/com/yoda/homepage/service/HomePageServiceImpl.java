package com.yoda.homepage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.model.Content;
import com.yoda.homepage.dao.HomePageDAO;
import com.yoda.homepage.model.HomePage;

@Transactional
@Service
public class HomePageServiceImpl implements HomePageService {
	@Autowired
	private HomePageDAO homePageDAO;

	public void addHomePage(
			long siteId, long userId, char featureData, Content content) {
		HomePage homePage = new HomePage();

		homePage.setSiteId(siteId);
		homePage.setFeatureData(featureData);
		homePage.setContent(content);
		homePage.setUpdateBy(userId);
		homePage.setUpdateDate(new Date());
		homePage.setCreateBy(userId);
		homePage.setCreateDate(new Date());

		homePageDAO.save(homePage);
	}

	public void deleteHomePage(HomePage homePage) {
		homePageDAO.delete(homePage);
	}

	@Transactional(readOnly = true)
	public HomePage getHomePage(long siteId, long homePageId) {
		return homePageDAO.getBySiteId_HomePageId(siteId, homePageId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePages(long siteId) {
		return homePageDAO.getBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePages(long siteId, String orderBy) {
		return homePageDAO.getBySiteId(siteId, orderBy);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePagesBySiteIdAndFeatureData(long siteId) {
		return homePageDAO.getBySiteId_featureData(siteId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(long siteId) {
		return homePageDAO.getBySiteId_featureData_NotY(siteId);
	}

	public void updateHomePage(HomePage homePage) {
		homePageDAO.update(homePage);
	}
}