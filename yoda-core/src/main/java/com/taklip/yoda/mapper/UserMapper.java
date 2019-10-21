package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.annotation.YodaMyBatisMapperAnnotation;
import com.taklip.yoda.model.User;

@YodaMyBatisMapperAnnotation
public interface UserMapper extends BaseMapper<User> {
	User getUserByEmail(String email);

	User getUserByUsername(String userName);

	List<User> getUsers();

	List<User> search(Long id, String username, String authorityName, Boolean enabled);
}