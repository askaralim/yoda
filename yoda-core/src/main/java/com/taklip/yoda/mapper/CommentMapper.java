package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Comment;

public interface CommentMapper extends BaseMapper<Comment> {
	List<Comment> getCommentsBySiteId(Integer siteId);

	List<Comment> getCommentsByContentId(Long contentId);

	List<Comment> getCommentsByUserId(Long userId);
}