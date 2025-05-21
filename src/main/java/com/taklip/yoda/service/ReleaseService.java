package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.Release;

public interface ReleaseService extends IService<Release> {
	Release getRelease(int buildNumber);

	Release addRelease(int buildNumber);
}
