package com.taklip.yoda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.PageViewDataMapper;
import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author askar
 */
@Service
@Transactional
public class PageViewServiceImpl implements PageViewService {
    @Autowired
    PageViewDataMapper pageViewDataMapper;

    @Autowired
    private IdService idService;

    @Override
    public void addPageViewData(PageViewData pageView) {
        pageView.setId(idService.generateId());

        pageView.preInsert();

        pageViewDataMapper.insert(pageView);
    }

    @Override
    public List<PageViewData> getPageViewDatas() {
        return pageViewDataMapper.getPageViewDatas();
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<PageViewData> getPageViewDatas(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<PageViewData> pageViews = pageViewDataMapper.getPageViewDatas();

        PageInfo<PageViewData> pageInfo = new PageInfo<>(pageViews);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public PageViewData getPageViewData(int id) {
        return pageViewDataMapper.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewData> getPageViewDatasByIP(String ip) {
        return pageViewDataMapper.getPageViewDatasByIP(ip);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewData> getPageViewDatasByPage(int pageType, int pageId) {
        return pageViewDataMapper.getPageViewDatasByPage(pageType, pageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageViewData> getPageViewDatasByUser(int userId) {
        return pageViewDataMapper.getPageViewDatasByUserId(userId);
    }
}