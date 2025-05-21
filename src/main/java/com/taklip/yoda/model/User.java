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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.annotation.TableField;
import com.taklip.yoda.validation.PasswordMatch;
import com.taklip.yoda.validation.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

@PasswordMatch
@ToString
public class User extends BaseEntity implements UserDetails {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    private Long lastVisitSiteId;

    private Date lastLoginDate;

    private String addressLine1;

    private String addressLine2;

    private String cityName;

    private String countryName;

    @NotBlank(message = "email-required")
    private String email;

    @ValidPassword
    private String password;

    @TableField(exist = false)
    private String verifyPassword;

    private String phone;

    private String profilePhoto;

    private String profilePhotoSmall;

    private String stateName;

    @NotBlank(message = "username-required")
    private String username;

    private String zipCode;

    @TableField(exist = false)
    private Set<UserAuthority> authorities = new HashSet<>();

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
    }

    @Override
    public Set<UserAuthority> getAuthorities() {
        return Collections.unmodifiableSet(sortAuthorities(this.authorities));
    }

    private static SortedSet<UserAuthority> sortAuthorities(
            Collection<? extends UserAuthority> authorities) {
        Assert.notNull(
                authorities, "Cannot pass a null GrantedAuthority collection");

        SortedSet<UserAuthority> sortedAuthorities = new TreeSet<UserAuthority>(new AuthorityComparator());

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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getLastVisitSiteId() {
        return lastVisitSiteId;
    }

    public void setLastVisitSiteId(Long lastVisitSiteId) {
        this.lastVisitSiteId = lastVisitSiteId;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}