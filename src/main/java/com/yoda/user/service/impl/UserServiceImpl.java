package com.yoda.user.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.exception.PortalException;
import com.yoda.site.dao.SiteDAO;
import com.yoda.site.model.Site;
import com.yoda.user.dao.AuthorityDAO;
import com.yoda.user.dao.UserDAO;
import com.yoda.user.model.User;
import com.yoda.user.model.UserAuthority;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthorityDAO authorityDAO;

	@Autowired
	private SiteDAO siteDAO;

	@Autowired
	private UserDAO userDAO;

	public User addUser(
			String userName, String password, String email, String phone,
			String role, String addressLine1, String addressLine2,
			String cityName, int siteId, boolean enabled, long userId)
					throws PortalException {
		return addUser(
			userName, password, email, phone, role, addressLine1,
			addressLine2, cityName, new Integer[]{siteId}, enabled, userId);
	}

	public User addUser(
			String userName, String password, String email, String phone,
			String role, String addressLine1, String addressLine2,
			String cityName, Integer[] selectedSiteIds, boolean enabled,
			long userId)
		throws PortalException {
		User user = new User();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(password);

		user.setPassword(hashedPassword);
		user.setCreateBy(userId);
		user.setCreateDate(new Date());
		user.setUsername(userName);
		user.setEnabled(enabled);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddressLine1(addressLine1);
		user.setAddressLine2(addressLine2);
		user.setCityName(StringPool.BLANK);
		user.setStateName(StringPool.BLANK);
		user.setCountryName(StringPool.BLANK);
		user.setZipCode(StringPool.BLANK);
		user.setUpdateBy(userId);
		user.setUpdateDate(new Date());

		user.getSites().clear();

		if (selectedSiteIds.length > 0) {
			for (int i = 0; i < selectedSiteIds.length; i++) {
				int s = selectedSiteIds[i];

				Site site = siteDAO.getBySiteId(s);

				user.getSites().add(site);
			}
		}

		userDAO.save(user);

		saveUserRole(user, role);

		return user;
	}

	public void deleteUser(long userId, User signinUser)
		throws SecurityException {
		User user = getByUI_SU(userId, signinUser);

		userDAO.delete(user);

		for (UserAuthority ua : user.getAuthorities()) {
			authorityDAO.delete(ua);
		}
	}

	@Transactional(readOnly = true)
	public User getUser(long userId) {
		return userDAO.getByUserId(userId);
	}

	@Transactional(readOnly = true)
	public User getUserByEmail(String email) {
		return userDAO.getByEmail(email);
	}

	@Transactional(readOnly = true)
	public User getUserByUserName(String userName) {
		return userDAO.getByUserName(userName);
	}

	@Transactional(readOnly = true)
	public User getByUI_SU(long userId, User signinUser)
		throws SecurityException {
		return userDAO.getByUI_SU(userId, signinUser);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers(String hql) {
		return userDAO.find(hql);
	}

	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userDAO.getAll();
	}

	public void update(User user) {
		userDAO.update(user);
	}

	public User updateUser(
		int siteId, Long userId, String username, String password,
		String email, long signinUserId) {
		User user = this.getUser(userId);

		return this.updateUser(
			user.getUserId(), username, password, email, user.getPhone(),
			user.getAddressLine1(), user.getAddressLine2(),
			user.getCityName(), new Integer[]{siteId}, user.isEnabled(), signinUserId);
	}

	public User updateUser(
			long userId, String userName, String password,
			String userEmail, String userPhone,
			String userAddressLine1, String userAddressLine2,
			String userCityName, Integer[] selectedSiteIds, boolean enabled,
			long signinUserId) {
		User user = getUser(userId);

		String hashedPassword = StringPool.BLANK;

		if (Validator.isNull(password)) {
			hashedPassword = user.getPassword();
		}
		else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			hashedPassword = passwordEncoder.encode(password);
		}

		user.setPassword(hashedPassword);
//		user.setCreateBy(userId);
//		user.setCreateDate(new Date());
		user.setUsername(userName);
		user.setEmail(userEmail);
		user.setEnabled(enabled);
		user.setPhone(userPhone);
		user.setAddressLine1(userAddressLine1);
		user.setAddressLine2(userAddressLine2);
		user.setUpdateBy(signinUserId);
		user.setUpdateDate(new Date());

		user.getSites().clear();

		if (selectedSiteIds.length > 0) {
			for (int i = 0; i < selectedSiteIds.length; i++) {
				int s = selectedSiteIds[i];

				Site site = siteDAO.getBySiteId(s);

				user.getSites().add(site);
			}
		}

		userDAO.update(user);

		return user;
	}

	private void saveUserRole(User user, String role) {
		if (role.equals(Constants.USER_ROLE_ADMINISTRATOR)) {
			authorityDAO.save(new UserAuthority(user, "ROLE_ADMIN"));
			authorityDAO.save(new UserAuthority(user, "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user, "ROLE_ADMIN"));
			user.getAuthorities().add(new UserAuthority(user, "ROLE_USER"));
		}
		else if (role.equals(Constants.USER_ROLE_SUPERUSER)) {
			authorityDAO.save(new UserAuthority(user, "ROLE_SUPERUSER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user, "ROLE_SUPERUSER"));
		}
		else {
			authorityDAO.save(new UserAuthority(user, "ROLE_USER"));

			user.getAuthorities().clear();

			user.getAuthorities().add(new UserAuthority(user, "ROLE_USER"));
		}
	}

	public List<User> search(
			Long userId, String username, String role, Boolean enabled) {
		Query query = null;

		String sql = "select user from User user where 1 = 1";

		if (Validator.isNotNull(userId) && userId > 0) {
			sql += "and userId like :userId ";
		}

		if (Validator.isNotNull(username) && username.length() > 0) {
			sql += "and username like :username ";
		}

//		if (userType != null && !userType.equals("*")) {
//			sql += "and userType = :userType ";
//		}

		if (Validator.isNotNull(enabled)) {
			sql += "and enabled = :enabled ";
		}

		sql += "order by user_id";

		query = userDAO.getSession().createQuery(sql);

		if (Validator.isNotNull(userId) && userId > 0) {
			query.setString("userId", "%" + userId + "%");
		}

		if (Validator.isNotNull(username) && username.length() > 0) {
			query.setString("username", "%" + username + "%");
		}

//		if (userType != null && !userType.equals("*")) {
//			query.setString("userType", userType);
//		}

		if (Validator.isNotNull(enabled)) {
			query.setBoolean("enabled", enabled);
		}

		return query.list();
	}

//	public boolean hasAccess(User self, User user) {
//		return userDAO.hasAccess(self, user);
//	}
}