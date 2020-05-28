package com.taklip.yoda.mapper;

import com.taklip.yoda.model.UserFollower;

import java.util.List;

public interface UserFollowerMapper extends BaseMapper<UserFollower> {
	List<UserFollower> getUserFollowers(Long userId);

	List<Integer> getUserFollowerCount(Long userId);

	void delete(Long userId, Long followerId);
}