package com.yoda.contactus;

import java.util.List;

import com.yoda.country.model.Country;
import com.yoda.state.model.State;

public class ContactUsEditCommand {
	int contactUsId;
	String contactUsName;
	String contactUsEmail;
	String contactUsPhone;
	String contactUsAddressLine1;
	String contactUsAddressLine2;
	String contactUsCityName;
	String contactUsZipCode;
	String contactUsDesc;
	String seqNum;
	boolean active;
	List<State> states;
	List<Country> countries;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getContactUsAddressLine1() {
		return contactUsAddressLine1;
	}

	public void setContactUsAddressLine1(String contactUsAddressLine1) {
		this.contactUsAddressLine1 = contactUsAddressLine1;
	}

	public String getContactUsAddressLine2() {
		return contactUsAddressLine2;
	}

	public void setContactUsAddressLine2(String contactUsAddressLine2) {
		this.contactUsAddressLine2 = contactUsAddressLine2;
	}

	public String getContactUsCityName() {
		return contactUsCityName;
	}

	public void setContactUsCityName(String contactUsCityName) {
		this.contactUsCityName = contactUsCityName;
	}

	public String getContactUsEmail() {
		return contactUsEmail;
	}

	public void setContactUsEmail(String contactUsEmail) {
		this.contactUsEmail = contactUsEmail;
	}

	public int getContactUsId() {
		return contactUsId;
	}

	public void setContactUsId(int contactUsId) {
		this.contactUsId = contactUsId;
	}

	public String getContactUsName() {
		return contactUsName;
	}

	public void setContactUsName(String contactUsName) {
		this.contactUsName = contactUsName;
	}

	public String getContactUsPhone() {
		return contactUsPhone;
	}

	public void setContactUsPhone(String contactUsPhone) {
		this.contactUsPhone = contactUsPhone;
	}

	public String getContactUsZipCode() {
		return contactUsZipCode;
	}

	public void setContactUsZipCode(String contactUsZipCode) {
		this.contactUsZipCode = contactUsZipCode;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getContactUsDesc() {
		return contactUsDesc;
	}

	public void setContactUsDesc(String contactUsDesc) {
		this.contactUsDesc = contactUsDesc;
	}

	public boolean isNew() {
		return (contactUsId == 0);
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
}