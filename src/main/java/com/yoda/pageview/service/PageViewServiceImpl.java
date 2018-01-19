package com.yoda.pageview.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.model.Comment;
import com.yoda.kernal.model.Pagination;
import com.yoda.pageview.model.PageView;
import com.yoda.pageview.persistence.PageViewMapper;

@Service
@Transactional
public class PageViewServiceImpl implements PageViewService {
	@Autowired
	PageViewMapper pageViewMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void addPageView(PageView pageView) {
		pageView.preInsert();

		pageViewMapper.insert(pageView);
	}

	public List<PageView> getPageViews() {
		return pageViewMapper.getPageViews();
	}

	@Transactional(readOnly = true)
	public Pagination<PageView> getPageViews(RowBounds rowBounds) {
		List<PageView> pageViews = sqlSessionTemplate.selectList("com.yoda.pageview.persistence.PageViewMapper.getPageViews", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.yoda.pageview.persistence.PageViewMapper.count");

		Pagination<PageView> page = new Pagination<PageView>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), pageViews);

		return page;
	}

	@Transactional(readOnly = true)
	public PageView getPageView(int id) {
		return pageViewMapper.getById(id);
	}

	@Transactional(readOnly = true)
	public List<PageView> getPageViewsByIP(String ip) {
		return pageViewMapper.getPageViewsByIP(ip);
	}

	@Transactional(readOnly = true)
	public List<PageView> getPageViewsByPage(int pageType, int pageId) {
		return pageViewMapper.getPageViewsByPage(pageType, pageId);
	}

	@Transactional(readOnly = true)
	public List<PageView> getPageViewsByUser(int userId) {
		return pageViewMapper.getPageViewsByUserId(userId);
	}
}