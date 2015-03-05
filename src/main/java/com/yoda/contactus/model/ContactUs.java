package com.yoda.contactus.model;

import java.util.Date;

import com.yoda.BaseEntity;

public class ContactUs extends BaseEntity {
	private long contactUsId;

	private long siteId;

	private String name;

	private String addressLine1;

	private String addressLine2;

	private String cityName;

	private String zipCode;

	private String email;

	private String phone;

	private String description;

	private Integer seqNum;

	private Character active;

	private long recUpdateBy;

	private Date recUpdateDatetime;

	private long recCreateBy;

	private Date recCreateDatetime;

	// Constructors

//	/** default constructor */
//	public ContactUs() {
//	}
//
//	/** minimal constructor */
//	public ContactUs(String siteId, String contactUsName,
//			String contactUsAddressLine1, String contactUsAddressLine2,
//			String contactUsCityName, String contactUsStateName,
//			String contactUsStateCode, String contactUsCountryName,
//			String contactUsCountryCode, String contactUsZipCode,
//			String contactUsEmail, String contactUsDesc, Integer seqNum,
//			Character active, String recUpdateBy, Date recUpdateDatetime,
//			String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.contactUsName = contactUsName;
//		this.contactUsAddressLine1 = contactUsAddressLine1;
//		this.contactUsAddressLine2 = contactUsAddressLine2;
//		this.contactUsCityName = contactUsCityName;
//		this.contactUsStateName = contactUsStateName;
//		this.contactUsStateCode = contactUsStateCode;
//		this.contactUsCountryName = contactUsCountryName;
//		this.contactUsCountryCode = contactUsCountryCode;
//		this.contactUsZipCode = contactUsZipCode;
//		this.contactUsEmail = contactUsEmail;
//		this.contactUsDesc = contactUsDesc;
//		this.seqNum = seqNum;
//		this.active = active;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public ContactUs(String siteId, String contactUsName,
//			String contactUsAddressLine1, String contactUsAddressLine2,
//			String contactUsCityName, String contactUsStateName,
//			String contactUsStateCode, String contactUsCountryName,
//			String contactUsCountryCode, String contactUsZipCode,
//			String contactUsEmail, String contactUsPhone, String contactUsDesc,
//			Integer seqNum, Character active, String recUpdateBy,
//			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.contactUsName = contactUsName;
//		this.contactUsAddressLine1 = contactUsAddressLine1;
//		this.contactUsAddressLine2 = contactUsAddressLine2;
//		this.contactUsCityName = contactUsCityName;
//		this.contactUsStateName = contactUsStateName;
//		this.contactUsStateCode = contactUsStateCode;
//		this.contactUsCountryName = contactUsCountryName;
//		this.contactUsCountryCode = contactUsCountryCode;
//		this.contactUsZipCode = contactUsZipCode;
//		this.contactUsEmail = contactUsEmail;
//		this.contactUsPhone = contactUsPhone;
//		this.contactUsDesc = contactUsDesc;
//		this.seqNum = seqNum;
//		this.active = active;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}

	// Property accessors
	public long getContactUsId() {
		return this.contactUsId;
	}

	public void setContactUsId(long contactUsId) {
		this.contactUsId = contactUsId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}

	public Character getActive() {
		return this.active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public long getRecUpdateBy() {
		return this.recUpdateBy;
	}

	public void setRecUpdateBy(long recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public Date getRecUpdateDatetime() {
		return this.recUpdateDatetime;
	}

	public void setRecUpdateDatetime(Date recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public long getRecCreateBy() {
		return this.recCreateBy;
	}

	public void setRecCreateBy(long recCreateBy) {
		this.recCreateBy = recCreateBy;
	}

	public Date getRecCreateDatetime() {
		return this.recCreateDatetime;
	}

	public void setRecCreateDatetime(Date recCreateDatetime) {
		this.recCreateDatetime = recCreateDatetime;
	}
}