package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.UserAuthorityMapper;
import com.taklip.yoda.mapper.UserMapper;
import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ThumbnailUploader;
import com.taklip.yoda.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserAuthorityMapper authorityMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	ThumbnailUploader thumbnailUploader;

	@Autowired
	private IdService idService;

	public User add(User user) {
		user.setId(idService.generateId());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(hashedPassword);

		user.preInsert();

		userMapper.insert(user);

		saveUserRole(user, Constants.USER_ROLE_USER);

		return user;
	}

	public void update(User user) {
		user.preUpdate();

		userMapper.update(user);
	}

	public User update(User user, MultipartFile profilePhoto) {
		User userDb = getUser(user.getId());

		String hashedPassword;

		if (!StringUtils.isBlank(user.getPassword())) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			hashedPassword = passwordEncoder.encode(user.getPassword());

			userDb.setPassword(hashedPassword);
		}

		userDb.setUsername(user.getUsername());
		userDb.setEmail(user.getEmail());
		userDb.setEnabled(user.isEnabled());
		userDb.setPhone(user.getPhone());
		userDb.setAddressLine1(user.getAddressLine1());
		userDb.setAddressLine2(user.getAddressLine2());

		if (!profilePhoto.isEmpty()) {
			thumbnailUploader.deleteFile(userDb.getProfilePhoto());
			thumbnailUploader.deleteFile(userDb.getProfilePhotoSmall());

			if (FileUtil.isImage(profilePhoto.getOriginalFilename())) {
				try {
					userDb.setProfilePhoto(thumbnailUploader.createThumbnailLarge(profilePhoto.getInputStream()));
					userDb.setProfilePhotoSmall(thumbnailUploader.createThumbnailMedium(profilePhoto.getInputStream()));
				}
				catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}

		userDb.preUpdate();

		userMapper.update(userDb);

		return userDb;
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

	private void saveUserRole(User user, String role) {
		if (role.equals(Constants.USER_ROLE_ADMINISTRATOR)) {
			authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_ADMIN"));
			authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_ADMIN"));
			user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_USER"));
		}
		else if (role.equals(Constants.USER_ROLE_SUPERUSER)) {
			authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_SUPERUSER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_SUPERUSER"));
		}
		else {
			authorityMapper.insert(new UserAuthority(user.getId(), "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_USER"));
		}
	}

	public List<User> search(
			Long userId, String username, String authorityName, Boolean enabled) {
		return userMapper.search(userId, username, authorityName, enabled);
	}

//	public boolean hasAccess(User self, User user) {
//		return userDAO.hasAccess(self, user);
//	}
}