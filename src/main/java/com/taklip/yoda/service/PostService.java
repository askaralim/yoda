package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.PostDTO;
import com.taklip.yoda.model.Post;

/**
 * @author askar
 */
public interface PostService extends IService<Post> {
    PostDTO getById(Long id);

    PostDTO create(PostDTO post);

    PostDTO update(PostDTO post);

    void delete(Long id);

    Page<PostDTO> getByUser(Long userId, int offset, int limit);

    Page<PostDTO> getByPage(int offset, int limit);
}
