package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.model.Comment;

public interface CommentService extends IService<Comment> {
    CommentDTO create(CommentDTO comment);

    void delete(Long commentId);

    CommentDTO update(CommentDTO comment);

    CommentDTO getCommentById(Long commentId);

    Page<CommentDTO> getByPage(int offset, int limit);

    Page<CommentDTO> getByContentId(long contentId, int offset, int limit);

    Page<CommentDTO> getByUserId(long userId, int offset, int limit);
}
