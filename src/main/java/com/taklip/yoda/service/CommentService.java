package com.taklip.yoda.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.Comment;

public interface CommentService extends IService<Comment> {
    void addComment(Comment comment);

    void deleteComment(Long commentId);

    void updateComment(Comment comment);

    Comment getCommentById(Long commentId);

    List<Comment> getCommentsByContentId(long contentId);

    List<Comment> getCommentsBySiteId(long siteId);

    List<Comment> getCommentsByUserId(long userId);
}
