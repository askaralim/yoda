package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.Post;

import java.util.List;

/**
 * @author askar
 */
public interface PostService extends IService<Post> {
    Post getPost(Long id);

    boolean save(Post post);

    boolean update(Post post);

    void delete(Long id);

    List<Post> getPostsByUser(Long userId);

    Page<Post> getPostsByUser(Long userId, Integer offset, Integer limit);
}