package com.yoda.kernal.service;

import com.yoda.kernal.model.Release;

public interface ReleaseService {
	Release getRelease(int buildNumber);

	public Release addRelease(int buildNumber);
}
