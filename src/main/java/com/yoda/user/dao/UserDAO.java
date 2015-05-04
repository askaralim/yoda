package com.yoda.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.user.model.User;

@Repository
public class UserDAO extends BaseDAO<User> {

	private static final String GET_USER_BY_EMAIL = "from User u where u.email = ?";
	private static final String GET_USER_BY_USERNAME = "from User u where u.username = ?";
	private static final String FINDER_ALL_USERS = "FROM User order by userId";
//	private static final String GET_USER_BY_USERID = "from User u where u.userId = ?";

	public List<User> getAll(){
		return find(FINDER_ALL_USERS);
	}

	public User getByUserName(String userName) {
		List<User> users = (List<User>)getHibernateTemplate().find(GET_USER_BY_USERNAME, userName);

		if (users.size() == 0) {
			return null;
		}
		else {
			return users.get(0);
		}
	}

	public User getByEmail(String email) {
		List<User> users = (List<User>)getHibernateTemplate().find(GET_USER_BY_EMAIL, email);

		if (users.size() == 0) {
			return null;
		}
		else {
			return users.get(0);
		}
	}

	public User getByUserId(long userId) {
		return getById(userId);
	}

//	public User getByUserId(long userId) {
//		List<User> users = (List<User>)getHibernateTemplate().find(GET_USER_BY_USERID, userId);
//
//		if (users.size() == 0) {
//			return null;
//		}
//		else {
//			return users.get(0);
//		}
//	}

//	public int getRank(User user) {
//		int rank = 0;
//
//		String userType = user.getUserType();
//
//		if (userType.equals(Constants.USERTYPE_SUPER)) {
//			rank = 3;
//		}
//
//		if (userType.equals(Constants.USERTYPE_ADMIN)) {
//			rank = 2;
//		}
//
//		if (userType.equals(Constants.USERTYPE_REGULAR)) {
//			rank = 1;
//		}
//
//		return rank;
//	}

//	public boolean hasAccess(User self, User user) {
//		if (getRank(self) >= getRank(user)) {
//			return true;
//		}
//
//		return false;
//	}

	public User getByUI_SU(long userId, User signinUser)
		throws SecurityException {
//		String userType = signinUser.getUserType();
//
//		if (!userType.equals(Constants.USERTYPE_ADMIN)
//			&& !userType.equals(Constants.USERTYPE_SUPER)) {
//
//			if (signinUser.getUserId() != userId) {
//				throw new SecurityException();
//			}
//		}

		User user = getById(userId);

//		if (!hasAccess(signinUser, user)) {
//			throw new SecurityException();
//		}

		return user;
	}
}