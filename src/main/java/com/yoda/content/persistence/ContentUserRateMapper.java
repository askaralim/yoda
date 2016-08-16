package com.yoda.content.persistence;

import java.util.List;

import com.yoda.content.model.ContentUserRate;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ContentUserRateMapper extends BaseMapper<ContentUserRate> {
	ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId);

	List<ContentUserRate> getContentUserRatesByContentId(Long contentId);

	Integer getContentRate(Long contentId);
}