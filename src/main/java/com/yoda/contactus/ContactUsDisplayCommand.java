package com.yoda.contactus;

public class ContactUsDisplayCommand {
//	boolean remove;
	long contactUsId;
	String contactUsName;
	String contactUsEmail;
	String contactUsPhone;
	String seqNum;
	String active;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getContactUsEmail() {
		return contactUsEmail;
	}

	public void setContactUsEmail(String contactUsEmail) {
		this.contactUsEmail = contactUsEmail;
	}

	public long getContactUsId() {
		return contactUsId;
	}

	public void setContactUsId(long contactUsId) {
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

//	public boolean isRemove() {
//		return remove;
//	}
//
//	public void setRemove(boolean remove) {
//		this.remove = remove;
//	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
}