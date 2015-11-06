package com.yoda.kernal.persistence;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.model.Release;

@YodaMyBatisMapper
public interface ReleaseMapper extends BaseMapper<Release> {
	Release getReleaseByBuildNumber(int buildNumber);

	void insertRelease(Release release);
}