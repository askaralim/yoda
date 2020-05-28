package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.UserFolloweeMapper;
import com.taklip.yoda.mapper.UserFollowerMapper;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.model.UserFollower;
import com.taklip.yoda.service.UserFollowRelationService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserFollowRelationServiceImpl implements UserFollowRelationService {
	@Autowired
	UserFollowerMapper userFollowerMapper;

	@Autowired
	UserFolloweeMapper userFolloweeMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

	@Override
	public List<UserFollower> getUserFollowers(Long userId) {
		return userFollowerMapper.getUserFollowers(userId);
	}

	@Override
	public List<UserFollowee> getUserFollowees(Long userId) {
		return userFolloweeMapper.getUserFollowees(userId);
	}

	@Override
	public int getUserFollowerCount(Long userId) {
		return userFollowerMapper.getUserFollowerCount(userId).get(0);
	}

	@Override
	public int getUserFolloweeCount(Long userId) {
		return userFolloweeMapper.getUserFolloweeCount(userId).get(0);
	}

	@Override
	public Pagination<UserFollower> getUserFollowers(Long userId, RowBounds rowBounds) {
		List<UserFollower> followers = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFollowerMapper.getUserFollowers", userId, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFollowerMapper.getUserFollowerCount", userId);

		Pagination<UserFollower> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), followers);

		return page;
	}

	@Override
	public Pagination<UserFollowee> getUserFollowees(Long userId, RowBounds rowBounds) {
		List<UserFollowee> followees = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFolloweeMapper.getUserFollowers", userId, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.UserFolloweeMapper.getUserFolloweeCount", userId);

		Pagination<UserFollowee> page = new Pagination<>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), followees);

		return page;
	}

	@Override
	public Boolean isFollowing(long userId, long followeeId) {
		return !userFolloweeMapper.isFollowing(userId, followeeId).isEmpty();
	}

	@Override
	public void follow(long userId, long followeeId) {
		Date date = new Date();

		UserFollower userFollower = new UserFollower();
		userFollower.setId(idService.generateId());
		userFollower.setFollowerId(userId);
		userFollower.setUserId(followeeId);
		userFollower.setCreateDate(date);

		userFollowerMapper.insert(userFollower);

		UserFollowee userFollowee = new UserFollowee();
		userFollowee.setId(idService.generateId());
		userFollowee.setFolloweeId(followeeId);
		userFollowee.setUserId(userId);
		userFollowee.setCreateDate(date);

		userFolloweeMapper.insert(userFollowee);
	}

	@Override
	public void unFollow(long userId, long followeeId) {
		userFollowerMapper.delete(followeeId, userId);
		userFolloweeMapper.delete(userId, followeeId);
	}
}