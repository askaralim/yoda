package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.PostMapper;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Post;
import com.taklip.yoda.service.PostService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PostServiceImpl implements PostService {
	@Autowired
	PostMapper postMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

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

	@Transactional(readOnly = true)
	public List<Post> getPostsByUser(Long userId) {
		return postMapper.getPostsByUser(userId);
	}

	@Transactional(readOnly = true)
	public Pagination<Post> getPostsByUser(Long userId, RowBounds rowBounds) {
		List<Post> posts = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.PostMapper.getPostsByUser", userId, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.PostMapper.getPostCountByUser", userId);

		Pagination<Post> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), posts);

		return page;
	}

	@Transactional(readOnly = true)
	public Post getPost(Long id) {
		return postMapper.getById(id);
	}

	public void delete(Long id) {
	}
}