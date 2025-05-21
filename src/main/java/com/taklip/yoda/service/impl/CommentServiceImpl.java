package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.CommentMapper;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.service.CommentService;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Override
    public void addComment(Comment comment) {
        this.save(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        this.updateById(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        this.removeById(commentId);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return this.getById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByContentId(long contentId) {
        return this.list(new LambdaQueryWrapper<Comment>().eq(Comment::getContentId, contentId).orderByDesc(Comment::getCreateTime));
    }


    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsBySiteId(long siteId) {
        return this.list(new LambdaQueryWrapper<Comment>().eq(Comment::getSiteId, siteId).orderByDesc(Comment::getCreateTime));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByUserId(long userId) {
        return this.list(new LambdaQueryWrapper<Comment>().eq(Comment::getUserId, userId).orderByDesc(Comment::getCreateTime));
    }
}