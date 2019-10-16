package com.taklip.yoda.model;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Feedback extends BaseEntity {
	private Long id;

	private Date createDate;

	private String description;

	@NotEmpty(message = "error.string.required")
	private String email;

	private String phone;

	@NotEmpty(message = "error.string.required")
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}