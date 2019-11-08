package com.taklip.yoda.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	//~ Instance fields ================================================================================================
	private Long id;

	private Boolean accountNonExpired = true;

	private Boolean accountNonLocked = true;

	private Boolean credentialsNonExpired = true;

	private Boolean enabled = true;

	private Integer lastVisitSiteId;

	private Date lastLoginDate;

	private String addressLine1;

	private String addressLine2;

	private String cityName;

	private String countryName;

	private String email;

	private String password;

	private String verifyPassword;

	private String phone;

	private String profilePhoto;

	private String profilePhotoSmall;

	private String stateName;

	private String username;

	private String zipCode;

	private Set<Site> sites = new HashSet<Site>();

	private Set<UserAuthority> authorities = new HashSet<>();

	//~ Constructors ===================================================================================================
	public User(){
	}

	public User(
			Long id, Boolean enabled, User createBy, User updateBy,
			Integer lastVisitSiteId, Character active, Date lastLoginDate,
			Date createDate, Date updateDate, Set<UserAuthority> authorities,
			Set<Site> sites, String addressLine1, String addressLine2,
			String cityName, String countryName, String email, String password,
			String phone, String profilePhoto, String profilePhotoSmall,
			String stateName, String username, String userType, String zipCode) {
		this(id, true, true, true, enabled, createBy, updateBy,
			lastVisitSiteId, active, lastLoginDate, createDate, updateDate,
			authorities, sites, addressLine1, addressLine2, cityName,
			countryName, email, password, phone, profilePhoto, profilePhotoSmall,
			stateName, username, userType, zipCode);
	}

	public User(
			Long id, Boolean accountNonExpired, Boolean accountNonLocked,
			Boolean credentialsNonExpired, Boolean enabled, User createBy,
			User updateBy, int lastVisitSiteId, Character active,
			Date lastLoginDate, Date createDate, Date updateDate,
			Set<UserAuthority> authorities, Set<Site> sites,
			String addressLine1, String addressLine2, String cityName,
			String countryName, String email, String password, String phone,
			String profilePhoto, String profilePhotoSmall, String stateName,
			String username, String userType, String zipCode) {
		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor");
		}

		this.id = id;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.lastVisitSiteId = lastVisitSiteId;
		this.lastLoginDate = lastLoginDate;
		this.authorities =
			Collections.unmodifiableSet(sortAuthorities(authorities));
		this.sites = sites;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.cityName = cityName;
		this.countryName = countryName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.profilePhoto = profilePhoto;
		this.profilePhotoSmall = profilePhotoSmall;
		this.stateName = stateName;
		this.username = username;
		this.zipCode = zipCode;
	}

	//~ Methods ========================================================================================================

	private static SortedSet<UserAuthority> sortAuthorities(
			Collection<? extends UserAuthority> authorities) {
		Assert.notNull(
			authorities, "Cannot pass a null GrantedAuthority collection");

		SortedSet<UserAuthority> sortedAuthorities =
			new TreeSet<UserAuthority>(new AuthorityComparator());

		for (UserAuthority grantedAuthority : authorities) {
			Assert.notNull(
				grantedAuthority, "GrantedAuthority list cannot contain any null elements");

			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements
			Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			// Neither should ever be null as each entry is checked before
			// adding it to the set.
			// If the authority is null, it is a custom authority and should
			// precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return username.equals(((User) rhs).username);
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		if (StringUtils.isNotBlank(this.username)) {
			return username.hashCode();
		}
		else return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

		if (!authorities.isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : authorities) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		}
		else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

	//~ Getter and Setter ========================================================================================================

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public String getAddressLine1() {
		return this.addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return this.addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getProfilePhotoSmall() {
		return profilePhotoSmall;
	}

	public void setProfilePhotoSmall(String profilePhotoSmall) {
		this.profilePhotoSmall = profilePhotoSmall;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getLastVisitSiteId() {
		return this.lastVisitSiteId;
	}

	public void setLastVisitSiteId(int lastVisitSiteId) {
		this.lastVisitSiteId = lastVisitSiteId;
	}

	public Set<UserAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
	}

	public Set<Site> getSites() {
		return this.sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

//	public void eraseCredentials() {
//		password = null;
//	}

	public boolean isNew() {
		return (this.id == null);
	}
}