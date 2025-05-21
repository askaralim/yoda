package com.taklip.yoda.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.model.UserFollower;

/**
 * @author askar
 */
public interface UserFollowRelationService {
    List<UserFollower> getUserFollowers(Long userId);

    List<UserFollowee> getUserFollowees(Long userId);

    Long getUserFollowerCount(Long userId);

    Long getUserFolloweeCount(Long userId);

    Page<UserFollower> getUserFollowers(Long userId, Integer offset, Integer limit);

    Page<UserFollowee> getUserFollowees(Long userId, Integer offset, Integer limit);

    Boolean isFollowing(long followerId, long followeeId);

    void follow(long followerId, long followeeId);

    void unFollow(long followerId, long followeeId);
}