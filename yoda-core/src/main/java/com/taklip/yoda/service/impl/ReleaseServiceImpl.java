package com.taklip.yoda.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taklip.yoda.mapper.ReleaseMapper;
import com.taklip.yoda.model.Release;
import com.taklip.yoda.service.ReleaseService;

@Service
class ReleaseServiceImpl implements ReleaseService {
//	@Autowired
//	ReleaseDAO releaseDAO;

	@Autowired
	ReleaseMapper releaseMapper;

	public Release getRelease(int buildNumber) {
//		return releaseDAO.getByBuildNumber(buildNumber);
		return releaseMapper.getReleaseByBuildNumber(buildNumber);
	}

	@Transactional
	public Release addRelease(int buildNumber) {
		Release release = new Release();

		release.setBuildNumber(buildNumber);
		release.setCreateDate(new Date());
		release.setModifiedDate(new Date());
		release.setVerified(true);

//		releaseDAO.save(release);
		releaseMapper.insertRelease(release);

		return release;
	}
}
