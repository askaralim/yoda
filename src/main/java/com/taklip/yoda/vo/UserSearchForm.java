package com.taklip.yoda.vo;

import com.taklip.yoda.common.contant.StringPool;

public class UserSearchForm {
    Long userId;
    String username;
    String role;
    Boolean enabled;

    public UserSearchForm() {
        this.userId = null;
        this.username = StringPool.BLANK;
        this.role = StringPool.BLANK;
        this.enabled = null;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}