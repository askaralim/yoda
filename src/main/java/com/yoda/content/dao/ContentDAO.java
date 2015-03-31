package com.yoda.content.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.content.model.Content;
import com.yoda.util.StringPool;

@Repository
public class ContentDAO extends BaseDAO<Content> {
	private static final String GET_CONTENT_BY_SITEID_AND_CONTENTNATURALKEY = "from Content where siteId = ? and naturalKey = ?";

	private static final String GET_CONTENT_BY_SITEID = "select content from Content content where siteId = ?";

	private static final String GET_CONTENT_BY_CREATE_BY_USERID= "select content from Content content where create_by = ?";

	private static final String GET_CONTENT_BY_SITEID_AND_CONTENT_TITLE = "from Content content where siteId = ? and title = ? ";

	private static final String GET_CONTENTS_BY_SITEID_AND_CONTENT_TITLE = "from Content content where siteId = ? and title like ? ";

	public Content getContentById(int siteId, long contentId) {
		Content content = getById(contentId);

		if (content.getSiteId() != siteId) {
			throw new SecurityException();
		}

		return content;
	}

	public List<Content> getContents(int siteId) {
		List<Content> contents = (List<Content>)getHibernateTemplate().find(GET_CONTENT_BY_SITEID, siteId);

		return contents;
	}

	public Content getContent(int siteId, String title) {
		List<Content> contents = (List<Content>) getHibernateTemplate().find(GET_CONTENT_BY_SITEID_AND_CONTENT_TITLE, siteId, title);

		if (contents.size() == 0) {
			return null;
		}
		else return contents.get(0);
	}

	public List<Content> getContents(int siteId, String title) {
		List<Content> contents = (List<Content>) getHibernateTemplate().find(GET_CONTENTS_BY_SITEID_AND_CONTENT_TITLE, siteId, StringPool.PERCENT + title + StringPool.PERCENT);

		return contents;
	}

	public Content getContentBySiteId_NaturalKey(int siteId, String naturalKey) {
		List<Content> contents = (List<Content>) getHibernateTemplate().find(GET_CONTENT_BY_SITEID_AND_CONTENTNATURALKEY, siteId, naturalKey);

		return contents.get(0);
	}

	public List<Content> getByUserId(Long userId) {
		List<Content> contents = (List<Content>)getHibernateTemplate().find(GET_CONTENT_BY_CREATE_BY_USERID, userId);

		return contents;
	}
}