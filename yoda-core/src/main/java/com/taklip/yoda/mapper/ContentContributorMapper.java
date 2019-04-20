package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.ContentContributor;

public interface ContentContributorMapper extends BaseMapper<ContentContributor> {
	List<ContentContributor> getByContentIdAndUserId(long contentId, long userId);
}