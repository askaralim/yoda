package com.yoda.user.service;

import java.util.List;

import com.yoda.exception.PortalException;
import com.yoda.user.model.User;

public interface UserService {
	User getUser(long userId);

	User getUserByUserName(String userName);

	User getUserByEmail(String email);

	void update(User user);

//	List<User> getUserBySiteId(String siteId);

//	List<User> getUsers(String hql);

	List<User> getUsers();

	List<User> search(Long userId, String username, String role, Boolean enabled);

//	User getByUI_SU(long userId, User signinUser) throws SecurityException;

	User addUser(String userName, String password, String email, String phone, String role, String addressLine1, String addressLine2, String cityName, int siteId, boolean enabled) throws PortalException;

	User addUser(String userName, String userPassword, String userEmail, String phone, String role, String userAddressLine1, String userAddressLine2, String userCityName, Integer[] selectedSiteIds, boolean enabled) throws PortalException;

	void deleteUser(long userId);

	User updateUser(long userId, String userName, String userPassword, String userEmail, String userPhone, String userAddressLine1, String userAddressLine2, String userCityName, Integer[] selectedSiteIds, boolean enabled) throws PortalException;

	User updateUser(int siteId, Long userId, String username, String password, String email);
}