package com.yoda.kernal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.kernal.model.Release;

@Repository
public class ReleaseDAO extends BaseDAO<Release> {
	private static final String GET_RELEASE_BY_BUILDNUMBER = "from Release release where release.buildNumber = ?";

	public Release getByBuildNumber(int buildNumber) {
		List<Release> releases = (List<Release>)getHibernateTemplate().find(GET_RELEASE_BY_BUILDNUMBER, buildNumber);

		if (releases.size() == 0) {
			return null;
		}
		else {
			return releases.get(0);
		}
	}
}
