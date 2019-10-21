package com.taklip.yoda.service;

import com.taklip.yoda.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
	User getUser(long userId);

	User getUserByUserName(String userName);

	User getUserByEmail(String email);

	void update(User user);

	List<User> getUsers();

	List<User> search(Long userId, String username, String role, Boolean enabled);

	User add(User user);

	void deleteUser(long userId);

	User update(User user, MultipartFile profilePhoto);
}