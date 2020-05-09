package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollower;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserFollowerService {
	UserFollower getUserFollower(Long id);

	UserFollower save(UserFollower userFollower);

	void deleteUserFollower(Long userId, Long followerId);

	List<UserFollower> getUserFollowers(Long userId);

	int getUserFollowerCount(Long userId);

	Pagination<UserFollower> getUserFollowers(Long userId, RowBounds rowBounds);
}