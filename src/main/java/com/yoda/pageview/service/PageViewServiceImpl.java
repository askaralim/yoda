package com.yoda.pageview.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.kernal.model.Pagination;
import com.yoda.pageview.model.PageViewData;
import com.yoda.pageview.persistence.PageViewDataMapper;

@Service
@Transactional
public class PageViewServiceImpl implements PageViewService {
	@Autowired
	PageViewDataMapper pageViewDataMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void addPageViewData(PageViewData pageView) {
		pageViewDataMapper.insert(pageView);
	}

	public List<PageViewData> getPageViewDatas() {
		return pageViewDataMapper.getPageViewDatas();
	}

	@Transactional(readOnly = true)
	public Pagination<PageViewData> getPageViewDatas(RowBounds rowBounds) {
		List<PageViewData> pageViews = sqlSessionTemplate.selectList("com.yoda.pageview.persistence.PageViewDataMapper.getPageViewDatas", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.yoda.pageview.persistence.PageViewDataMapper.count");

		Pagination<PageViewData> page = new Pagination<PageViewData>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), pageViews);

		return page;
	}

	@Transactional(readOnly = true)
	public PageViewData getPageViewData(int id) {
		return pageViewDataMapper.getById(id);
	}

	@Transactional(readOnly = true)
	public List<PageViewData> getPageViewDatasByIP(String ip) {
		return pageViewDataMapper.getPageViewDatasByIP(ip);
	}

	@Transactional(readOnly = true)
	public List<PageViewData> getPageViewDatasByPage(int pageType, int pageId) {
		return pageViewDataMapper.getPageViewDatasByPage(pageType, pageId);
	}

	@Transactional(readOnly = true)
	public List<PageViewData> getPageViewDatasByUser(int userId) {
		return pageViewDataMapper.getPageViewDatasByUserId(userId);
	}
}