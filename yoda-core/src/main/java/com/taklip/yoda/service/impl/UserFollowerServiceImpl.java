package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.UserFollowerMapper;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollower;
import com.taklip.yoda.service.UserFollowerService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFollowerServiceImpl implements UserFollowerService {
	@Autowired
	UserFollowerMapper userFollowerMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

	@Override
	public UserFollower getUserFollower(Long id) {
		return userFollowerMapper.getById(id);
	}

	@Override
	public UserFollower save(UserFollower userFollower) {
		userFollower.setId(idService.generateId());
		userFollower.setCreateDate(new Date());

		userFollowerMapper.insert(userFollower);

		return userFollower;
	}

	@Override
	public void deleteUserFollower(Long userId, Long followerId) {
		userFollowerMapper.deleteUserFollower(userId, followerId);
	}

	@Override
	public List<UserFollower> getUserFollowers(Long userId) {
		return userFollowerMapper.getUserFollowers(userId);
	}

	@Override
	public int getUserFollowerCount(Long userId) {
		return userFollowerMapper.getUserFollowerCount(userId).get(0);
	}

	@Override
	public Pagination<UserFollower> getUserFollowers(Long userId, RowBounds rowBounds) {
		List<UserFollower> followers = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFollowerMapper.getUserFollowers", userId, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFollowerMapper.getUserFollowerCount", userId);

		Pagination<UserFollower> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), followers);

		return page;
	}
}