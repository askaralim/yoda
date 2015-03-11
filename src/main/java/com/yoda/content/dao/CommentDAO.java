package com.yoda.content.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.content.model.Comment;

@Repository
public class CommentDAO extends BaseDAO<Comment> {
	private static final String GET_COMMENT_BY_CONTENTID = "from Comment where content.contentId = ? order by createDate desc";
	private static final String GET_COMMENT_BY_SITEID = "from Comment comment where comment.siteId = ?";
	private static final String GET_COMMENT_BY_USERID = "from Comment comment where comment.create_by = ?";

	public Comment getComment(long siteId, int commentId) {
		Comment comment = getById(commentId);

		if (comment.getSiteId() != siteId) {
			throw new SecurityException();
		}
		return comment;
	}

	public List<Comment> getCommentsByContentId(long contentId) {
		return (List<Comment>) getHibernateTemplate().find(GET_COMMENT_BY_CONTENTID, contentId);
	}

	public List<Comment> getCommentsByUserId(long userId) {
		return (List<Comment>) getHibernateTemplate().find(GET_COMMENT_BY_USERID, userId);
	}

	public List<Comment> getCommentsBySiteId(int siteId) {
		return (List<Comment>) getHibernateTemplate().find(GET_COMMENT_BY_SITEID, siteId);
	}
}