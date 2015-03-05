package com.yoda.kernal.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.kernal.dao.ReleaseDAO;
import com.yoda.kernal.model.Release;
import com.yoda.kernal.service.ReleaseService;

@Service
 class ReleaseServiceImpl implements ReleaseService{
	@Autowired
	ReleaseDAO releaseDAO;

	public Release getRelease(int buildNumber) {
		return releaseDAO.getByBuildNumber(buildNumber);
	}

	public Release addRelease(int buildNumber) {
		Release release = new Release();

		release.setBuildNumber(buildNumber);
		release.setCreateDate(new Date());
		release.setModifiedDate(new Date());
		release.setVerified(true);

		releaseDAO.save(release);

		return release;
	}
}
