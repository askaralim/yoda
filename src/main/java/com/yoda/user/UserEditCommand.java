package com.yoda.user;

import java.util.List;

import com.yoda.site.model.Site;

public class UserEditCommand {
	Long userId;
	String username;
	String password;
	String verifyPassword;
	String email;
	String phone;
	String userType;
	String addressLine1;
	String addressLine2;
	String cityName;
	String stateName;
	String countryName;
	String zipCode;
	Character active;
	String states;
	String countries;
	List<Site> sites;
	List<Long> selectedSiteIds;
	boolean hasAdministrator;
	boolean hasSuperUser;

	public boolean isHasAdministrator() {
		return hasAdministrator;
	}

	public void setHasAdministrator(boolean hasAdministrator) {
		this.hasAdministrator = hasAdministrator;
	}

	public boolean isHasSuperUser() {
		return hasSuperUser;
	}

	public void setHasSuperUser(boolean hasSuperUser) {
		this.hasSuperUser = hasSuperUser;
	}

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setUserPhone(String phone) {
		this.phone = phone;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String verifyPassword) {
		this.verifyPassword = verifyPassword;
	}

	public List<Long> getSelectedSiteIds() {
		return selectedSiteIds;
	}

	public void setSelectedSiteIds(List<Long> selectedSiteIds) {
		this.selectedSiteIds = selectedSiteIds;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public boolean isNew() {
		return (userId == null);
	}
}