package com.yoda.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yoda.user.model.User;
import com.yoda.user.persistence.UserMapper;
import com.yoda.util.Validator;

@Service("loginUserDetailsService")
public class LoginUserDetailsService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

//	public UserDetails loadUserByUsername(String username)
//			throws UsernameNotFoundException {
//		com.yoda.user.model.User user = userDAO.getByUserName(username);
//
//		List<GrantedAuthority> authorities = buildUserAuthority(user.getAuthorities());
//
//		return buildUserForAuthentication(user, authorities);
//	}
//
//	private User buildUserForAuthentication(com.yoda.user.model.User user,
//			List<GrantedAuthority> authorities) {
//		return new User(
//			user.getUsername(), user.getPassword(), user.isEnabled(), true,
//			true, true, authorities);
//	}
//
//	private List<GrantedAuthority> buildUserAuthority(Set<UserAuthority> authorities) {
//		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
//
//		// Build user's authorities
//		for (UserAuthority authority : authorities) {
//			dbAuthsSet.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
//		}
//
//		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
//
//		return dbAuths;
//	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
//		User user = userDAO.getByUserName(username);

		User user = userMapper.getUserByEmail(username);

		if (Validator.isNotNull(user)) {
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
		}

		return user;
	}
}