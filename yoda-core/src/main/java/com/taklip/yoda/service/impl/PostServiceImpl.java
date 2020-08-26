package com.taklip.yoda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.PostMapper;
import com.taklip.yoda.model.Post;
import com.taklip.yoda.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author askar
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;

    @Autowired
    private IdService idService;

    @Override
    public Post save(Post post) {
        if (null == post.getId()) {
            return this.add(post);
        } else {
            return this.update(post);
        }
    }

    public Post add(Post post) {
        post.setId(idService.generateId());

        post.preInsert();

        postMapper.insert(post);

        return post;
    }

    public Post update(Post post) {
        Post postDb = postMapper.getById(post.getId());

        postDb.setDescription(post.getDescription());

        postDb.preUpdate();

        postMapper.update(postDb);

        return postDb;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsByUser(Long userId) {
        return postMapper.getPostsByUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Post> getPostsByUser(Long userId, Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<Post> posts = postMapper.getPostsByUser(userId);

        PageInfo<Post> pageInfo = new PageInfo<>(posts);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postMapper.getById(id);
    }

    @Override
    public void delete(Long id) {
    }
}