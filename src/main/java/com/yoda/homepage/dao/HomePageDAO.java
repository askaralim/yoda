package com.yoda.homepage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.homepage.model.HomePage;

@Repository
public class HomePageDAO extends BaseDAO<HomePage> {
	private static final String GET_HOME_PAGE = "from HomePage homePage where siteId = ?";

	private static final String GET_HOME_PAGE_ORDER_BY_SEQNUM = "from HomePage homePage where siteId = ? order by ?";

	private static final String GET_HOME_PAGE_BY_SITEID_AND_HOMEPAGEID = "from HomePage homePage where siteId = ? and homePageId = ?";

	private static final String GET_HOME_PAGE_BY_SITEID_AND_FEATUREDATA = "from HomePage homePage where siteId = ? and featureData = 'Y'";

	private static final String GET_HOME_PAGE_BY_SITEID_AND_FEATUREDATA_NOT_Y = "from HomePage homePage where siteId = ? and featureData != 'Y'";

	public HomePage getBySiteId_HomePageId(long siteId, long homePageId) {
		List<HomePage> homePages = (List<HomePage>)getHibernateTemplate().find(GET_HOME_PAGE_BY_SITEID_AND_HOMEPAGEID, siteId, homePageId);

		return homePages.get(0);
	}

	public List<HomePage> getBySiteId_featureData(long siteId) {
		return (List<HomePage>)getHibernateTemplate().find(GET_HOME_PAGE_BY_SITEID_AND_FEATUREDATA, siteId);
	}

	public List<HomePage> getBySiteId_featureData_NotY(long siteId) {
		return (List<HomePage>)getHibernateTemplate().find(GET_HOME_PAGE_BY_SITEID_AND_FEATUREDATA_NOT_Y, siteId);
	}

	public List<HomePage> getBySiteId(long siteId) {
		return (List<HomePage>)getHibernateTemplate().find(GET_HOME_PAGE, siteId);
	}

	public List<HomePage> getBySiteId(long siteId, String orderBy) {
		return (List<HomePage>)getHibernateTemplate().find(GET_HOME_PAGE_ORDER_BY_SEQNUM, siteId, orderBy);
	}

//	public static HomePage load(long siteId, Long homePageId)
//			throws SecurityException, Exception {
//		HomePage homepage = getById(homePageId);
//		if (homepage.getSiteId() != siteId) {
//			throw new SecurityException();
//		}
//		return homepage;
//	}
}