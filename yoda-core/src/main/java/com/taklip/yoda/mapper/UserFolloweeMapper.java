package com.taklip.yoda.mapper;

import com.taklip.yoda.model.UserFollowee;

import java.util.List;

public interface UserFolloweeMapper extends BaseMapper<UserFollowee> {
	List<UserFollowee> getUserFollowees(Long userId);

	List<Integer> getUserFolloweeCount(Long userId);

	void deleteUserFollowee(Long userId, Long followeeId);
}