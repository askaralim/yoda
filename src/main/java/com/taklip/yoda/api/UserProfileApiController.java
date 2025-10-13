package com.taklip.yoda.api;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taklip.yoda.dto.UserDTO;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.util.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserProfileApiController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    /**
     * Get current user profile using @AuthenticationPrincipal
     */
    @GetMapping("/me")
    public UserDTO getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return null; // Not authenticated
        }
        
        String email = userDetails.getUsername(); // This is the email
        User user = userService.getUserByEmail(email);
        
        return convertToUserDTO(user);
    }

    /**
     * Get current user profile using SecurityUtils
     */
    @GetMapping("/current")
    public UserDTO getCurrentUser() {
        User user = securityUtils.getCurrentUser();
        if (user == null) {
            return null; // Not authenticated
        }
        
        return convertToUserDTO(user);
    }

    /**
     * Check if user is authenticated
     */
    @GetMapping("/auth-status")
    public AuthStatusResponse getAuthStatus() {
        boolean isAuthenticated = securityUtils.isAuthenticated();
        String email = securityUtils.getCurrentUserEmail();
        
        return AuthStatusResponse.builder()
            .authenticated(isAuthenticated)
            .email(email)
            .build();
    }

    private UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        
        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .profilePhotoSmall(user.getProfilePhotoSmall())
            // .createTime(user.getCreateTime())
            // .updateTime(user.getUpdateTime())
            // .followerCount(user.getFollowerCount())
            // .followeeCount(user.getFolloweeCount())
            .build();
    }

    // Response DTO
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class AuthStatusResponse {
        private boolean authenticated;
        private String email;
    }
}
