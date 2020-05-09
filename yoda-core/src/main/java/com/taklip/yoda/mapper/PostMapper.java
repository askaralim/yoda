package com.taklip.yoda.mapper;

import com.taklip.yoda.model.Post;

import java.util.List;

public interface PostMapper extends BaseMapper<Post> {
	List<Post> getPostsByUser(Long userId);

	List<Integer> getPostCountByUser(Long userId);
}