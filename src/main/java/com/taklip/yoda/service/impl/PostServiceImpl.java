package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.PostMapper;
import com.taklip.yoda.model.Post;
import com.taklip.yoda.service.PostService;

/**
 * @author askar
 */
@Service
@Transactional
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Override
    public boolean save(Post post) {
        return this.baseMapper.insert(post) > 0;
    }

    @Override
    public boolean update(Post post) {
        return this.baseMapper.updateById(post) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByUser(Long userId) {
        return this.baseMapper.selectList(new QueryWrapper<Post>().eq("user_id", userId).orderByDesc("update_time"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> getPostsByUser(Long userId, Integer offset, Integer limit) {
        return this.page(new Page<>(offset, limit),
                new QueryWrapper<Post>().eq("user_id", userId).orderByDesc("update_time"));
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void delete(Long id) {
        this.baseMapper.deleteById(id);
    }
}