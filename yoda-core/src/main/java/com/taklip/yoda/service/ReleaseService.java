package com.taklip.yoda.service;

import com.taklip.yoda.model.Release;

public interface ReleaseService {
	Release getRelease(int buildNumber);

	public Release addRelease(int buildNumber);
}
