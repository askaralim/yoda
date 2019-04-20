package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.ContentUserRate;

public interface ContentUserRateMapper extends BaseMapper<ContentUserRate> {
	ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId);

	List<ContentUserRate> getContentUserRatesByContentId(Long contentId);

	Integer getContentRate(Long contentId);
}