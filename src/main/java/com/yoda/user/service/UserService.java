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

	List<User> getUsers(String hql);

	User getByUI_SU(long userId, User signinUser) throws SecurityException;

	List<User> search(long srUserId, String srUserName, String srUserType, String srActive);

	boolean hasAccess(User self, User user);

	User addUser(String userName, String password, String email,
				 String phone, String userType, String addressLine1,
				 String addressLine2, String cityName,
				 Long siteId, Character active, long userId) throws PortalException;

	User addUser(String userName, String userPassword, String userEmail,
				 String userPhone, String userType, String userAddressLine1,
				 String userAddressLine2, String userCityName,
				 List<Long> selectedSiteIds, Character active, long userId) throws PortalException;

	User updateUser(Long userId, String userName, String userPassword, String userEmail,
					String userPhone, String userType, String userAddressLine1,
					String userAddressLine2, String userCityName,
					List<Long> selectedSiteIds, Character active, User signinUser) throws PortalException;

	void deleteUser(long userId, User signinUser) throws SecurityException;

	User updateUser(Long siteId, Long userId, String username, String password, String email);
}
