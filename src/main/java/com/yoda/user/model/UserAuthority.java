package com.yoda.user.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class UserAuthority implements GrantedAuthority {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private Long id;

	private User user;

	private String authorityName;

	public UserAuthority() {
	}

	public UserAuthority(User user, String authorityName) {
		this.user = user;
		this.authorityName = authorityName;
	}

	public String getAuthority() {
		return authorityName;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof UserAuthority) {
			return authorityName.equals(((UserAuthority) obj).authorityName);
		}

		return false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public int hashCode() {
		return this.authorityName.hashCode();
	}

	public String toString() {
		return this.authorityName;
	}
}