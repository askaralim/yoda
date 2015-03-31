package com.yoda.home;

import java.util.List;
import java.util.Map;

import com.yoda.country.model.Country;
import com.yoda.site.model.Site;
import com.yoda.state.model.State;

public class ControlPanelHomeCommand {

	String tabName;
	long userId;
	String userName;
	String lastLoginDatetime;
	String password;
	String verifyPassword;
	String addressLine1;
	String addressLine2;
	String cityName;
	String siteName;
	String stateName;
	String stateCode;
	String countryName;
	String countryCode;
	String zipCode;
	String phone;
	String email;
	String cmd;
	List<State> states;
	List<Country> countries;
	Map<String, String> serverStats;
	Map<String, String> threadStats;
	Map<String, String> jvmStats;
	int siteId;
	List<Site> sites;

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public Map<String, String> getJvmStats() {
		return jvmStats;
	}

	public void setJvmStats(Map<String, String> jvmStats) {
		this.jvmStats = jvmStats;
	}

	public Map<String, String> getServerStats() {
		return serverStats;
	}

	public void setServerStats(Map<String, String> serverStats) {
		this.serverStats = serverStats;
	}

	public Map<String, String> getThreadStats() {
		return threadStats;
	}

	public void setThreadStats(Map<String, String> threadStats) {
		this.threadStats = threadStats;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(String userLastLoginDatetime) {
		this.lastLoginDatetime = userLastLoginDatetime;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String userAddressLine1) {
		this.addressLine1 = userAddressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String userAddressLine2) {
		this.addressLine2 = userAddressLine2;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String userCityName) {
		this.cityName = userCityName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String userCountryCode) {
		this.countryCode = userCountryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String userCountryName) {
		this.countryName = userCountryName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String userPassword) {
		this.password = userPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String userPhone) {
		this.phone = userPhone;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String userStateCode) {
		this.stateCode = userStateCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String userStateName) {
		this.stateName = userStateName;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}

	public void setVerifyPassword(String userVerifyPassword) {
		this.verifyPassword = userVerifyPassword;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String userZipCode) {
		this.zipCode = userZipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String userEmail) {
		this.email = userEmail;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
}
