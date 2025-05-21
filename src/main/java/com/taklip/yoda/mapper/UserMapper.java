package com.taklip.yoda.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.User;

public interface UserMapper extends BaseMapper<User> {
    User getUserById(long id);

    User getUserByEmail(String email);

    User getUserByUsername(String userName);

    List<User> getUsers();

    List<User> search(Long id, String username, String authorityName, Boolean enabled);

    // @Select("SELECT r.* FROM authority r WHERE r.user_id = #{userId}")
    // Set<UserAuthority> selectAuthoritiesByUserId(Long userId);
}