package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.ContentContributor;

public interface ContentContributorService {
    void create(ContentContributor contentContributor);

    void update(ContentContributor contentContributor);

    void delete(Long id);

    ContentContributor getById(long id);

    List<ContentContributor> getByContentIdAndUserId(long contentId, long userId);

    List<ContentContributor> getContentContributorsByContentId(long contentId);
}
