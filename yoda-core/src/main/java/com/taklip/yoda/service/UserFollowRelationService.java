package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.model.UserFollower;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author askar
 */
public interface UserFollowRelationService {
    List<UserFollower> getUserFollowers(Long userId);

    List<UserFollowee> getUserFollowees(Long userId);

    int getUserFollowerCount(Long userId);

    int getUserFolloweeCount(Long userId);

    PageInfo<UserFollower> getUserFollowers(Long userId, Integer offset, Integer limit);

    PageInfo<UserFollowee> getUserFollowees(Long userId, Integer offset, Integer limit);

    Boolean isFollowing(long followerId, long followeeId);

    void follow(long followerId, long followeeId);

    void unFollow(long followerId, long followeeId);
}