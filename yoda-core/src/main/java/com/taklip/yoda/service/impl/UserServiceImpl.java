package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.mapper.SiteMapper;
import com.taklip.yoda.mapper.UserAuthorityMapper;
import com.taklip.yoda.mapper.UserMapper;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ThumbnailUploader;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.FileUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserAuthorityMapper authorityMapper;

	@Autowired
	private SiteMapper siteMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	ThumbnailUploader thumbnailUploader;

	public User addUser(
			String userName, String password, String email, String phone,
			String role, String addressLine1, String addressLine2,
			String cityName, int siteId, boolean enabled)
					throws Exception {
		return addUser(
			userName, password, email, phone, role, addressLine1,
			addressLine2, cityName, new Integer[]{siteId}, siteId, enabled);
	}

	public User addUser(
			String userName, String password, String email, String phone,
			String role, String addressLine1, String addressLine2,
			String cityName, Integer[] selectedSiteIds, int lastVisitSiteId,
			boolean enabled)
		throws Exception {
		User user = new User();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(password);

		user.setPassword(hashedPassword);
//		user.setUsername(StringEscapeUtils.escapeHtml4(userName));
		user.setUsername(userName);
		user.setEnabled(enabled);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddressLine1(addressLine1);
		user.setAddressLine2(addressLine2);
		user.setCityName(StringUtils.EMPTY );
		user.setStateName(StringUtils.EMPTY );
		user.setCountryName(StringUtils.EMPTY );
		user.setZipCode(StringUtils.EMPTY );
		user.setLastVisitSiteId(lastVisitSiteId);

		user.getSites().clear();

		if (selectedSiteIds.length > 0) {
			for (int i = 0; i < selectedSiteIds.length; i++) {
				int s = selectedSiteIds[i];

				Site site = siteMapper.getById(s);

				user.getSites().add(site);
			}
		}

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		if ((user != null) && (user.getUserId() != null)){
			user.setCreateBy(currentUser);
			user.setUpdateBy(currentUser);
		}
		else {
			currentUser = this.getDefaultUser();

			user.setCreateBy(currentUser);
			user.setUpdateBy(currentUser);
		}

		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());

		userMapper.insert(user);

		saveUserRole(user, role);

		return user;
	}

	public void deleteUser(long userId) {
		User user = getUser(userId);

		userMapper.delete(user);

		for (UserAuthority ua : user.getAuthorities()) {
			authorityMapper.delete(ua);
		}
	}

	@Transactional(readOnly = true)
	public User getDefaultUser() {
		return userMapper.getById(1);
	}

	@Transactional(readOnly = true)
	public User getUser(long userId) {
		return userMapper.getById(userId);
	}

	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		return userMapper.getUserByEmail(email);
	}

	@Transactional(readOnly = true)
	public User getUserByUserName(String username) {
		return userMapper.getUserByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	public void update(User user) {
		user.preUpdate();

		userMapper.update(user);
	}

	public User updateUser(
		int siteId, Long userId, String username, String password,
		String email, MultipartFile profilePhoto) {
		User user = this.getUser(userId);

		return this.updateUser(
			user.getUserId(), username, password, email, user.getPhone(),
			profilePhoto, user.getAddressLine1(), user.getAddressLine2(),
			user.getCityName(), new Integer[]{siteId}, user.isEnabled());
	}

	public User updateUser(
			long userId, String username, String password,
			String userEmail, String phone, MultipartFile profilePhoto,
			String userAddressLine1, String userAddressLine2,
			String userCityName, Integer[] selectedSiteIds, boolean enabled) {
		User user = getUser(userId);

		String hashedPassword = StringUtils.EMPTY ;

		if (StringUtils.isNotBlank(password)) {
			hashedPassword = user.getPassword();
		}
		else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			hashedPassword = passwordEncoder.encode(password);
		}

		user.setPassword(hashedPassword);
		user.setUsername(username);
		user.setEmail(userEmail);
		user.setEnabled(enabled);
		user.setPhone(phone);
		user.setAddressLine1(userAddressLine1);
		user.setAddressLine2(userAddressLine2);

		user.getSites().clear();

		if (selectedSiteIds.length > 0) {
			for (int i = 0; i < selectedSiteIds.length; i++) {
				int s = selectedSiteIds[i];

				Site site = siteMapper.getById(s);

				user.getSites().add(site);
			}
		}

		if (!profilePhoto.isEmpty()) {
			thumbnailUploader.deleteFile(user.getProfilePhoto());
			thumbnailUploader.deleteFile(user.getProfilePhotoSmall());

			if (FileUtil.isImage(profilePhoto.getOriginalFilename())) {
				try {
					user.setProfilePhoto(thumbnailUploader.createThumbnailLarge(profilePhoto.getInputStream()));
					user.setProfilePhotoSmall(thumbnailUploader.createThumbnailMedium(profilePhoto.getInputStream()));
				}
				catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}

		user.preUpdate();

		userMapper.update(user);

		return user;
	}

	private void saveUserRole(User user, String role) {
		if (role.equals(Constants.USER_ROLE_ADMINISTRATOR)) {
			authorityMapper.insert(new UserAuthority(user.getUserId(), "ROLE_ADMIN"));
			authorityMapper.insert(new UserAuthority(user.getUserId(), "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_ADMIN"));
			user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_USER"));
		}
		else if (role.equals(Constants.USER_ROLE_SUPERUSER)) {
			authorityMapper.insert(new UserAuthority(user.getUserId(), "ROLE_SUPERUSER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_SUPERUSER"));
		}
		else {
			authorityMapper.insert(new UserAuthority(user.getUserId(), "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getUserId(), "ROLE_USER"));
		}
	}

	public List<User> search(
			Long userId, String username, String authorityName, Boolean enabled) {
		return userMapper.search(userId, username, authorityName, enabled);
//		Query query = null;
//
//		String sql = "select user from User user where 1 = 1";
//
//		if (Validator.isNotNull(userId) && userId > 0) {
//			sql += "and userId like :userId ";
//		}
//
//		if (Validator.isNotNull(username) && username.length() > 0) {
//			sql += "and username like :username ";
//		}
//
////		if (userType != null && !userType.equals("*")) {
////			sql += "and userType = :userType ";
////		}
//
//		if (Validator.isNotNull(enabled)) {
//			sql += "and enabled = :enabled ";
//		}
//
//		sql += "order by user_id";
//
//		query = userDAO.getSession().createQuery(sql);
//
//		if (Validator.isNotNull(userId) && userId > 0) {
//			query.setString("userId", "%" + userId + "%");
//		}
//
//		if (Validator.isNotNull(username) && username.length() > 0) {
//			query.setString("username", "%" + username + "%");
//		}
//
////		if (userType != null && !userType.equals("*")) {
////			query.setString("userType", userType);
////		}
//
//		if (Validator.isNotNull(enabled)) {
//			query.setBoolean("enabled", enabled);
//		}
//
//		return query.list();
	}

//	public boolean hasAccess(User self, User user) {
//		return userDAO.hasAccess(self, user);
//	}
}