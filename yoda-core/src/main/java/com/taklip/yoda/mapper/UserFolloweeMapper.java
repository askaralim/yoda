package com.taklip.yoda.mapper;

import com.taklip.yoda.model.UserFollowee;

import java.util.List;

public interface UserFolloweeMapper extends BaseMapper<UserFollowee> {
	List<UserFollowee> getUserFollowees(Long userId);

	List<Integer> getUserFolloweeCount(Long userId);

	void delete(Long userId, Long followeeId);

	List<UserFollowee> isFollowing(long userId, long followeeId);
}