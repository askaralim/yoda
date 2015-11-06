package com.yoda.content.persistence;

import java.util.List;

import com.yoda.content.model.Comment;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface CommentMapper extends BaseMapper<Comment> {
	List<Comment> getCommentsBySiteId(Integer siteId);

	List<Comment> getCommentsByContentId(Long contentId);

	List<Comment> getCommentsByUserId(Long userId);
}