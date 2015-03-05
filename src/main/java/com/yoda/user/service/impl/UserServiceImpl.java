package com.yoda.user.service.impl;

import java.util.ArrayList;
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
			String userName, String password, String email,
			String phone, String userType, String addressLine1,
			String addressLine2, String cityName,
			Long siteId, Character active, long userId) throws PortalException {
		List<Long> siteIds = new ArrayList<Long>();

		siteIds.add(siteId);

		return addUser(
			userName, password, email, phone, userType, addressLine1,
			addressLine2, cityName, siteIds, active, userId);
	}

	public User addUser(
			String userName, String password, String email,
			String phone, String userType, String addressLine1,
			String addressLine2, String cityName,
			List<Long> selectedSiteIds, Character active, long userId) throws PortalException {
		User user = new User();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String hashedPassword = passwordEncoder.encode(password);

		user.setPassword(hashedPassword);
		user.setCreateBy(userId);
		user.setCreateDate(new Date());
		user.setUsername(userName);
		user.setEmail(email);
		user.setPhone(phone);
		user.setUserType(userType);
		user.setAddressLine1(addressLine1);
		user.setAddressLine2(addressLine2);
		user.setCityName(StringPool.BLANK);
		user.setStateName(StringPool.BLANK);
		user.setCountryName(StringPool.BLANK);
		user.setZipCode(StringPool.BLANK);
		user.setActive(Constants.VALUE_NO);
		user.setUpdateBy(userId);
		user.setUpdateDate(new Date());

		if (active != null && active.equals(Constants.VALUE_YES)) {
			user.setActive(Constants.VALUE_YES);
			user.setEnabled(true);
		}

		if (!user.getUserType().equals(Constants.USERTYPE_SUPER)
			&& !user.getUserType().equals(Constants.USERTYPE_ADMIN)) {
			user.getSites().clear();

			if (selectedSiteIds != null) {
				for (int i = 0; i < selectedSiteIds.size(); i++) {
					long s = selectedSiteIds.get(i);

					Site site = siteDAO.getBySiteId(s);

					user.getSites().add(site);
				}
			}
		}

		userDAO.save(user);

//		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

		if (userType.equals(Constants.USERTYPE_REGULAR)) {
			authorityDAO.save(new UserAuthority(user, "ROLE_USER"));

			user.getAuthorities().clear();

//			dbAuthsSet.add(new UserAuthority("ROLE_USER"));

//			user.setAuthorities(dbAuthsSet);
			user.getAuthorities().add(new UserAuthority(user, "ROLE_USER"));
		}

//		userDAO.save(user);

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

	public void update(User user) {
		userDAO.update(user);
	}

	public User updateUser(
		Long siteId, Long userId, String username, String password,
		String email) {
		User user = this.getUser(userId);

		List<Long> siteIds = new ArrayList<Long>();

		siteIds.add(siteId);

		return this.updateUser(
			user.getUserId(), username, password, email, user.getPhone(),
			user.getUserType(), user.getAddressLine1(), user.getAddressLine2(),
			user.getCityName(), siteIds, user.getActive(), user);
	}

	public User updateUser(
			Long userId,String userName, String password,
			String userEmail, String userPhone, String userType,
			String userAddressLine1, String userAddressLine2,
			String userCityName, List<Long> selectedSiteIds, Character active,
			User signinUser) {
		User user = getByUI_SU(userId, signinUser);

		String hashedPassword = StringPool.BLANK;

		if (Validator.isNull(password)) {
			hashedPassword = user.getPassword();
		}
		else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			hashedPassword = passwordEncoder.encode(password);
		}

		user.setPassword(hashedPassword);
		user.setCreateBy(userId);
		user.setCreateDate(new Date());
		user.setUsername(userName);
		user.setEmail(userEmail);
		user.setPhone(userPhone);
		user.setUserType(userType);
		user.setAddressLine1(userAddressLine1);
		user.setAddressLine2(userAddressLine2);
		user.setActive(Constants.VALUE_NO);
		user.setUpdateBy(userId);
		user.setUpdateDate(new Date());

		if (active != null && active.equals(Constants.VALUE_YES)) {
			user.setActive(Constants.VALUE_YES);
			user.setEnabled(true);
		}

		if (!user.getUserType().equals(Constants.USERTYPE_SUPER)
				&& !user.getUserType().equals(Constants.USERTYPE_ADMIN)) {
			user.getSites().clear();

			if (selectedSiteIds != null) {
				for (int i = 0; i < selectedSiteIds.size(); i++) {
					long s = selectedSiteIds.get(i);

					Site site = siteDAO.getBySiteId(s);

					user.getSites().add(site);
				}
			}
		}

		userDAO.update(user);

		return user;
	}

	public List<User> search(
			long userId, String userName, String userType,
			String active) {
		Query query = null;

		String sql = "select user from User user where 1 = 1";

		if (userId > 0) {
			sql += "and userId like :userId ";
		}

		if (Validator.isNotNull(userName) && userName.length() > 0) {
			sql += "and userName like :userName ";
		}

		if (userType != null && !userType.equals("*")) {
			sql += "and userType = :userType ";
		}

		if (!active.equals("*")) {
			sql += "and active = :active ";
		}

		sql += "order by user_id";

		query = userDAO.getSession().createQuery(sql);

		if (userId > 0) {
			query.setString("userId", "%" + userId + "%");
		}

		if (Validator.isNotNull(userName) && userName.length() > 0) {
			query.setString("userName", "%" + userName + "%");
		}

		if (userType != null && !userType.equals("*")) {
			query.setString("userType", userType);
		}

		if (!active.equals("*")) {
			query.setString("active", active);
		}

		return query.list();
	}

	public boolean hasAccess(User self, User user) {
		return userDAO.hasAccess(self, user);
	}
}