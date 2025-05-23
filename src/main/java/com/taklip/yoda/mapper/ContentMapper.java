package com.taklip.yoda.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.Content;

/**
 * @author askar
 */
public interface ContentMapper extends BaseMapper<Content> {
    Content getContentById(long id);

    List<Content> getContentByIds(List<Long> ids);

    List<Content> getContents();

    List<Content> getContentsByTitle(String title);

    List<Content> getContentsByUserId(Long userId);

    Page<Content> getContentsByFeatureData(Page<Content> page, Boolean featureData);

    Long getContentsByFeatureDataCount(Boolean featureData);

    List<Content> search(Long siteId, String title, Boolean published, String createBy, String updateBy, String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd);
}