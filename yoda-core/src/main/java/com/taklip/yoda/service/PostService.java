package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Post;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PostService {
	Post getPost(Long id);

	Post save(Post post);

	void delete(Long id);

	List<Post> getPostsByUser(Long userId);

	Pagination<Post> getPostsByUser(Long userId, RowBounds rowBounds);
}