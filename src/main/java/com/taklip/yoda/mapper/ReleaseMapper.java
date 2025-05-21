package com.taklip.yoda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.Release;

public interface ReleaseMapper extends BaseMapper<Release> {
    Release getReleaseByBuildNumber(int buildNumber);

    void insertRelease(Release release);
}