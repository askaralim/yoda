package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Post;

import java.util.List;

/**
 * @author askar
 */
public interface PostService {
    Post getPost(Long id);

    Post save(Post post);

    void delete(Long id);

    List<Post> getPostsByUser(Long userId);

    PageInfo<Post> getPostsByUser(Long userId, Integer offset, Integer limit);
}