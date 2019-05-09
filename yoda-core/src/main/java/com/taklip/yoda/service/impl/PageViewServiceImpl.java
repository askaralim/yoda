package com.taklip.yoda.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.PageViewDataMapper;
import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.service.PageViewService;

@Service
@Transactional
public class PageViewServiceImpl implements PageViewService {
	@Autowired
	PageViewDataMapper pageViewDataMapper;

	@Autowired
	private IdService idService;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void addPageViewData(PageViewData pageView) {
		pageView.setId(idService.generateId());

		pageView.preInsert();

		pageViewDataMapper.insert(pageView);
	}

	public List<PageViewData> getPageViewDatas() {
		return pageViewDataMapper.getPageViewDatas();
	}

	@Transactional(readOnly = true)
	public Pagination<PageViewData> getPageViewDatas(RowBounds rowBounds) {
		List<PageViewData> pageViews = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.PageViewDataMapper.getPageViewDatas", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.PageViewDataMapper.count");

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