package com.yoda.user;

public class UserDisplayCommand {
	boolean remove;
	Long userId;
	String userName;
	String email;
	String phone;
	String type;
	String contactUs;
	String active;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setUserPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setUserType(String type) {
		this.type = type;
	}

	public String getContactUs() {
		return contactUs;
	}

	public void setContactUs(String contactUs) {
		this.contactUs = contactUs;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
