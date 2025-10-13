package com.taklip.yoda.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.taklip.yoda.model.User;
import com.taklip.yoda.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserService userService;

    /**
     * Get the current authenticated user's email
     * @return email of current user, or null if not authenticated
     */
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            return authentication.getName(); // This will be the email
        }
        
        return null;
    }

    /**
     * Get the current authenticated user object
     * @return User object of current user, or null if not authenticated
     */
    public User getCurrentUser() {
        String email = getCurrentUserEmail();
        if (email != null) {
            return userService.getUserByEmail(email);
        }
        return null;
    }

    /**
     * Check if current user is authenticated
     * @return true if user is authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null 
            && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * Get current user's UserDetails
     * @return UserDetails of current user, or null if not authenticated
     */
    public UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            return (UserDetails) authentication.getPrincipal();
        }
        
        return null;
    }
}
