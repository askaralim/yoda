package com.taklip.yoda.common.util;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;

public class AuthenticatedUtil {
    public static User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return (User) auth.getPrincipal();
        }

        return null;
    }

    public static boolean isAdminRole(UserDetails userDetail) {
        Collection<? extends GrantedAuthority> authorities = userDetail.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority instanceof UserAuthority &&
                    "ROLE_ADMIN".equals(authority.getAuthority())) {
                return true;
            }
        }

        return false;
    }
}