package com.taklip.yoda.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.mapper.UserFolloweeMapper;
import com.taklip.yoda.mapper.UserFollowerMapper;
import com.taklip.yoda.model.UserFollowee;
import com.taklip.yoda.model.UserFollower;
import com.taklip.yoda.service.UserFollowRelationService;

/**
 * @author askar
 */
@Service
@Transactional
public class UserFollowRelationServiceImpl implements UserFollowRelationService {
    @Autowired
    UserFollowerMapper userFollowerMapper;

    @Autowired
    UserFolloweeMapper userFolloweeMapper;

    @Override
    public List<UserFollower> getUserFollowers(Long userId) {
        return userFollowerMapper
                .selectList(new LambdaQueryWrapper<UserFollower>().eq(UserFollower::getUserId, userId));
    }

    @Override
    public List<UserFollowee> getUserFollowees(Long userId) {
        return userFolloweeMapper
                .selectList(new LambdaQueryWrapper<UserFollowee>().eq(UserFollowee::getUserId, userId));
    }

    @Override
    public Long getUserFollowerCount(Long userId) {
        return userFollowerMapper
                .selectCount(new LambdaQueryWrapper<UserFollower>().eq(UserFollower::getUserId, userId));
    }

    @Override
    public Long getUserFolloweeCount(Long userId) {
        return userFolloweeMapper
                .selectCount(new LambdaQueryWrapper<UserFollowee>().eq(UserFollowee::getUserId, userId));
    }

    @Override
    public Page<UserFollower> getUserFollowers(Long userId, Integer offset, Integer limit) {
        return userFollowerMapper.selectPage(new Page<>(offset, limit),
                new QueryWrapper<UserFollower>().eq("user_id", userId).orderByDesc("create_time"));
    }

    @Override
    public Page<UserFollowee> getUserFollowees(Long userId, Integer offset, Integer limit) {
        return userFolloweeMapper.selectPage(new Page<>(offset, limit),
                new QueryWrapper<UserFollowee>().eq("user_id", userId).orderByDesc("create_time"));
    }

    @Override
    public Boolean isFollowing(long userId, long followeeId) {
        return userFolloweeMapper.selectCount(
                new LambdaQueryWrapper<UserFollowee>().eq(UserFollowee::getUserId, userId)
                        .eq(UserFollowee::getFolloweeId, followeeId)) > 0;
    }

    @Override
    public void follow(long userId, long followeeId) {
        LocalDateTime localDateTime = LocalDateTime.now();

        UserFollower userFollower = new UserFollower();
        userFollower.setFollowerId(userId);
        userFollower.setUserId(followeeId);
        userFollower.setCreateTime(localDateTime);

        userFollowerMapper.insert(userFollower);

        UserFollowee userFollowee = new UserFollowee();
        userFollowee.setFolloweeId(followeeId);
        userFollowee.setUserId(userId);
        userFollowee.setCreateTime(localDateTime);

        userFolloweeMapper.insert(userFollowee);
    }

    @Override
    public void unFollow(long userId, long followeeId) {
        userFollowerMapper.delete(
                new LambdaQueryWrapper<UserFollower>().eq(UserFollower::getUserId, followeeId)
                        .eq(UserFollower::getFollowerId, userId));

        userFolloweeMapper.delete(
                new LambdaQueryWrapper<UserFollowee>().eq(UserFollowee::getUserId, userId)
                        .eq(UserFollowee::getFolloweeId, followeeId));
    }
}