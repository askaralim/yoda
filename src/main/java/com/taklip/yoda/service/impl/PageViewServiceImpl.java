package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.PageViewMapper;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.service.PageViewService;

/**
 * @author askar
 */
@Service
@Transactional
public class PageViewServiceImpl extends ServiceImpl<PageViewMapper, PageView> implements PageViewService {
    @Override
    public void addPageView(PageView pageView) {
        this.save(pageView);
    }

    @Override
    public List<PageView> getPageViews() {
        return this.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PageView> getPageViews(Integer offset, Integer limit) {
        return this.page(new Page<>(offset, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public PageView getPageView(int id) {
        return this.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageView> getPageViewsByIP(String ip) {
        return this.list(new LambdaQueryWrapper<PageView>().like(PageView::getUserIpAddress, ip));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageView> getPageViewsByPage(int pageType, int pageId) {
        return this.list(new LambdaQueryWrapper<PageView>().eq(PageView::getPageType, pageType).eq(PageView::getPageId, pageId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PageView> getPageViewsByUser(int userId) {
        return this.list(new LambdaQueryWrapper<PageView>().eq(PageView::getUserId, userId));
    }
}