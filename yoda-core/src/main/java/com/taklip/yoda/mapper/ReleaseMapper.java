package com.taklip.yoda.mapper;

import com.taklip.yoda.annotation.YodaMyBatisMapperAnnotation;
import com.taklip.yoda.model.Release;

@YodaMyBatisMapperAnnotation
public interface ReleaseMapper extends BaseMapper<Release> {
	Release getReleaseByBuildNumber(int buildNumber);

	void insertRelease(Release release);
}