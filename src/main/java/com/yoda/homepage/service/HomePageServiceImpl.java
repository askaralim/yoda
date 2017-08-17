package com.yoda.homepage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.model.Content;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.persistence.HomePageMapper;
import com.yoda.kernal.model.Pagination;

@Transactional
@Service
public class HomePageServiceImpl implements HomePageService {
	@Autowired
	private HomePageMapper homePageMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

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

	@Transactional(readOnly = true)
	public Pagination<HomePage> getHomePagesBySiteIdAndFeatureDataNotY(int siteId, RowBounds rowBounds) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("param1", siteId);
		params.put("param2", false);

		List<HomePage> homepages = sqlSessionTemplate.selectList("com.yoda.homepage.persistence.HomePageMapper.getBySiteIdAndFeatureData", params, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.yoda.homepage.persistence.HomePageMapper.getBySiteIdAndFeatureDataCount", params);

		Pagination<HomePage> page = new Pagination<HomePage>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), homepages);

		return page;
	}

	public void update(HomePage homePage) {
		homePage.preUpdate();

		homePageMapper.update(homePage);
	}
}