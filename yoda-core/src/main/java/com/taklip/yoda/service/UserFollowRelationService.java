package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.model.UserFollower;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserFollowRelationService {
	List<UserFollower> getUserFollowers(Long userId);

	List<UserFollowee> getUserFollowees(Long userId);

	int getUserFollowerCount(Long userId);

	int getUserFolloweeCount(Long userId);

	Pagination<UserFollower> getUserFollowers(Long userId, RowBounds rowBounds);

	Pagination<UserFollowee> getUserFollowees(Long userId, RowBounds rowBounds);

	Boolean isFollowing(long followerId, long followeeId);

	void follow(long followerId, long followeeId);

	void unFollow(long followerId, long followeeId);
}