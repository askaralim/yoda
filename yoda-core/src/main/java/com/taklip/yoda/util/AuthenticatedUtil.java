package com.taklip.yoda.util;

import java.util.Set;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;

public class AuthenticatedUtil {
	public static User getAuthenticatedUser() {
//		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return (User)auth.getPrincipal();
		}

		return null;
	}

	public static boolean isAdminRole(UserDetails userDetail) {
		Set<UserAuthority> authorities = (Set<UserAuthority>)userDetail.getAuthorities();

		for (UserAuthority userAuthority : authorities) {
			if (userAuthority.getAuthorityName().equals("ROLE_ADMIN")) {
				return true;
			}
		}

		return false;
	}
}