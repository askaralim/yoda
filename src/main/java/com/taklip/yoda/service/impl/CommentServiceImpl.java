package com.taklip.yoda.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.mapper.CommentMapper;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.service.CommentService;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {
    @Autowired
    private ModelConvertor modelConvertor;

    @Override
    public CommentDTO create(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContentId(commentDTO.getContentId());
        comment.setRating(commentDTO.getRating());
        comment.setDescription(HtmlUtils.htmlEscape(commentDTO.getDescription()));
        comment.setUserId(commentDTO.getUserId());
        this.save(comment);
        return modelConvertor.convertToCommentDTO(comment);
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContentId(commentDTO.getContentId());
        comment.setRating(commentDTO.getRating());
        comment.setDescription(HtmlUtils.htmlEscape(commentDTO.getDescription()));
        comment.setUserId(commentDTO.getUserId());
        this.updateById(comment);
        return modelConvertor.convertToCommentDTO(comment);
    }

    @Override
    public void delete(Long commentId) {
        this.removeById(commentId);
    }

    @Override
    public CommentDTO getCommentById(Long commentId) {
        return modelConvertor.convertToCommentDTO(this.getById(commentId));
    }

    @Override
    public Page<CommentDTO> getByPage(int offset, int limit) {
        Page<Comment> commentPage = this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Comment>().orderByDesc(Comment::getCreateTime));
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setTotal(commentPage.getTotal());
        commentDTOPage.setCurrent(commentPage.getCurrent());
        commentDTOPage.setSize(commentPage.getSize());
        commentDTOPage.setRecords(commentPage.getRecords().stream()
                .map(modelConvertor::convertToCommentDTO).collect(Collectors.toList()));
        return commentDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> getByContentId(long contentId, int offset, int limit) {

        Page<Comment> commentPage =
                this.page(new Page<>(offset, limit), new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getContentId, contentId).orderByDesc(Comment::getCreateTime));
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setTotal(commentPage.getTotal());
        commentDTOPage.setCurrent(commentPage.getCurrent());
        commentDTOPage.setSize(commentPage.getSize());
        commentDTOPage.setRecords(commentPage.getRecords().stream()
                .map(modelConvertor::convertToCommentDTO).collect(Collectors.toList()));
        return commentDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> getByUserId(long userId, int offset, int limit) {
        Page<Comment> commentPage =
                this.page(new Page<>(offset, limit), new LambdaQueryWrapper<Comment>()
                        .eq(Comment::getUserId, userId).orderByDesc(Comment::getCreateTime));
        Page<CommentDTO> commentDTOPage = new Page<>();
        commentDTOPage.setTotal(commentPage.getTotal());
        commentDTOPage.setCurrent(commentPage.getCurrent());
        commentDTOPage.setSize(commentPage.getSize());
        commentDTOPage.setRecords(commentPage.getRecords().stream()
                .map(modelConvertor::convertToCommentDTO).collect(Collectors.toList()));
        return commentDTOPage;
    }
}
