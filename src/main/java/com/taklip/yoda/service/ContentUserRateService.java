package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.ContentUserRate;

public interface ContentUserRateService {
    void create(Long contentId, String thumb);

    void update(ContentUserRate contentUserRate);

    void delete(Long id);

    ContentUserRate getById(Long id);

    List<ContentUserRate> getByContentId(Long contentId);

    Integer getTotalRateByContentId(Long contentId);

    ContentUserRate getByContentIdAndUserId(Long contentId, Long userId);
}
