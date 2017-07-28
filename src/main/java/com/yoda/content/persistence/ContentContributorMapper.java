package com.yoda.content.persistence;

import java.util.List;

import com.yoda.content.model.ContentContributor;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ContentContributorMapper extends BaseMapper<ContentContributor> {
	List<ContentContributor> getByContentIdAndUserId(long contentId, long userId);
}