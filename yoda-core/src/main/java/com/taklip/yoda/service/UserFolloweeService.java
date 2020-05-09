package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.UserFollowee;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserFolloweeService {
	UserFollowee getUserFollowee(Long id);

	UserFollowee save(UserFollowee UserFollowee);

	void deleteUserFollowee(Long userId, Long followeeId);

	List<UserFollowee> getUserFollowees(Long userId);

	int getUserFolloweeCount(Long userId);

	Pagination<UserFollowee> getUserFollowees(Long userId, RowBounds rowBounds);
}