package com.yoda.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

@Entity
@Table(name = "authority")
public class UserAuthority implements GrantedAuthority {
	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "authority_name")
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