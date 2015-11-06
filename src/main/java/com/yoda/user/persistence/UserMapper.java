package com.yoda.user.persistence;

import java.util.List;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.user.model.User;

@YodaMyBatisMapper
public interface UserMapper extends BaseMapper<User> {
	User getUserByEmail(String email);

	User getUserByUsername(String userName);

	List<User> getUsers();

	List<User> search(Long userId, String username, String authorityName, Boolean enabled);
}