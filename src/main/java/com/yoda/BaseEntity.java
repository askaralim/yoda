package com.yoda;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;

public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private User updateBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:mm:ss")
	private Date updateDate;

	private User createBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:mm:ss")
	private Date createDate;

	public void preInsert(){
		User user = PortalUtil.getAuthenticatedUser();

		if ((user != null) && (user.getUserId() != null)){
			this.updateBy = user;
			this.createBy = user;
		}

		this.createDate = new Date();
		this.updateDate = this.createDate;
	}

	public void preUpdate(){
		User user = PortalUtil.getAuthenticatedUser();

		if ((user != null) && (user.getUserId() != null)){
			this.updateBy = user;
		}

		this.updateDate = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@JsonIgnore
	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}