package com.taklip.yoda.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.PostDTO;
import com.taklip.yoda.mapper.PostMapper;
import com.taklip.yoda.model.Post;
import com.taklip.yoda.service.PostService;

/**
 * @author askar
 */
@Service
@Transactional
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    private ModelConvertor modelConvertor;
    // @Autowired
    // private SecurityUtils securityUtils;

    @Override
    public PostDTO create(PostDTO postDto) {
        Post post = new Post();
        post.setDescription(postDto.getDescription());
        // User user = securityUtils.getCurrentUser();
        // post.setUserId(user.getId());
        this.save(post);
        return modelConvertor.convertToPostDTO(post);
    }

    @Override
    public PostDTO update(PostDTO postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setDescription(postDto.getDescription());
        this.updateById(post);
        return modelConvertor.convertToPostDTO(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> getByUser(Long userId, int offset, int limit) {
        Page<Post> postPage = this.page(new Page<>(offset, limit),
                new QueryWrapper<Post>().eq("create_by", userId).orderByDesc("update_time"));
        Page<PostDTO> postDTOPage = new Page<>();
        postDTOPage.setTotal(postPage.getTotal());
        postDTOPage.setCurrent(postPage.getCurrent());
        postDTOPage.setSize(postPage.getSize());
        postDTOPage.setRecords(postPage.getRecords().stream().map(modelConvertor::convertToPostDTO)
                .collect(Collectors.toList()));
        return postDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDTO> getByPage(int offset, int limit) {
        Page<Post> postPage = this.page(new Page<>(offset, limit),
                new QueryWrapper<Post>().orderByDesc("update_time"));
        Page<PostDTO> postDTOPage = new Page<>();
        postDTOPage.setTotal(postPage.getTotal());
        postDTOPage.setCurrent(postPage.getCurrent());
        postDTOPage.setSize(postPage.getSize());
        postDTOPage.setRecords(postPage.getRecords().stream().map(modelConvertor::convertToPostDTO)
                .collect(Collectors.toList()));
        return postDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public PostDTO getById(Long id) {
        Post post = baseMapper.selectById(id);
        PostDTO postDTO = modelConvertor.convertToPostDTO(post);
        // postDTO.setCreateBy(modelConvertor.convertToUserDTO(userService.getById(post.getCreateBy())));
        // postDTO.setUpdateBy(modelConvertor.convertToUserDTO(userService.getById(post.getUpdateBy())));
        return postDTO;
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }
}
