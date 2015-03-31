package com.yoda.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.yoda.site.model.Site;

@Entity
@Table(name = "user_")
public class User implements UserDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	//~ Instance fields ================================================================================================
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "create_by")
	private long createBy;

	@Column(name = "update_by")
	private long updateBy;

	@Column(name = "last_visit_site_id")
	private int lastVisitSiteId;

	/* Should be removed, use enabled instead. */
	@Column(name = "active")
	private Character active;

	@Column(name = "last_login_date")
	private Date lastLoginDate;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "address_line1")
	private String addressLine1;

	@Column(name = "address_line2")
	private String addressLine2;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Transient
	private String verifyPassword;

	@Column(name = "phone")
	private String phone;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "username")
	private String username;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "zip_code")
	private String zipCode;

//	private Set<GrantedAuthority> authorities;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "user_site", joinColumns = {@JoinColumn(name ="user_id" )}, inverseJoinColumns = {@JoinColumn(name = "site_id") })
	private Set<Site> sites = new HashSet<Site>();

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private Set<UserAuthority> authorities = new HashSet<UserAuthority>();

	//~ Constructors ===================================================================================================
	public User(){
	}

	public User(
			Long userId, boolean enabled, long createBy, long updateBy,
			int lastVisitSiteId, Character active, Date lastLoginDate,
			Date createDate, Date updateDate, Set<UserAuthority> authorities,
			Set<Site> sites, String addressLine1, String addressLine2,
			String cityName, String countryName, String email, String password,
			String phone, String stateName, String username, String userType,
			String zipCode) {
		this(
			userId, true, true, true, enabled, createBy, updateBy,
			lastVisitSiteId, active, lastLoginDate, createDate, updateDate,
			authorities, sites, addressLine1, addressLine2, cityName,
			countryName, email, password, phone, stateName, username, userType,
			zipCode);
	}

	public User(
			Long userId, boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired, boolean enabled, long createBy,
			long updateBy, int lastVisitSiteId, Character active,
			Date lastLoginDate, Date createDate, Date updateDate,
			Set<UserAuthority> authorities, Set<Site> sites,
			String addressLine1, String addressLine2, String cityName,
			String countryName, String email, String password, String phone,
			String stateName, String username, String userType, String zipCode) {
		if (((username == null) || "".equals(username)) || (password == null)) {
			throw new IllegalArgumentException(
				"Cannot pass null or empty values to constructor");
		}

		this.userId = userId;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.createBy = createBy;
		this.updateBy = updateBy;
		this.lastVisitSiteId = lastVisitSiteId;
		this.active = active;
		this.lastLoginDate = lastLoginDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
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
		this.stateName = stateName;
		this.username = username;
		this.userType = userType;
		this.zipCode = zipCode;
	}

	//~ Methods ========================================================================================================

	private static SortedSet<UserAuthority> sortAuthorities(
//			Collection<? extends GrantedAuthority> authorities) {
			Collection<? extends UserAuthority> authorities) {
		Assert.notNull(
			authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per
		// UserDetails.getAuthorities() contract and SEC-717)

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
		return username.hashCode();
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

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getLastVisitSiteId() {
		return this.lastVisitSiteId;
	}

	public void setLastVisitSiteId(int lastVisitSiteId) {
		this.lastVisitSiteId = lastVisitSiteId;
	}

	public Character getActive() {
		return this.active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
}