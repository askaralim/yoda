package com.yoda.homepage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.model.Content;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.persistence.HomePageMapper;

@Transactional
@Service
public class HomePageServiceImpl implements HomePageService {
	@Autowired
	private HomePageMapper homePageMapper;

	public void add(int siteId, boolean featureData, Content content) {
		HomePage homePage = new HomePage();

		homePage.setSiteId(siteId);
		homePage.setFeatureData(featureData);
		homePage.setContent(content);

		homePage.preInsert();

		homePageMapper.insert(homePage);
	}

	public void delete(HomePage homePage) {
		homePageMapper.delete(homePage);
	}

	@Transactional(readOnly = true)
	public HomePage getHomePage(long homePageId) {
		return homePageMapper.getById(homePageId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePages(int siteId) {
		return homePageMapper.getBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePagesBySiteIdOrderBySeqNum(int siteId) {
		return homePageMapper.getBySiteIdOrderBySeqNum(siteId);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePagesBySiteIdAndFeatureData(int siteId) {
		return homePageMapper.getBySiteIdAndFeatureData(siteId, true);
	}

	@Transactional(readOnly = true)
	public List<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId) {
		return homePageMapper.getBySiteIdAndFeatureData(siteId, false);
	}

	public void update(HomePage homePage) {
		homePage.preUpdate();

		homePageMapper.update(homePage);
	}
}