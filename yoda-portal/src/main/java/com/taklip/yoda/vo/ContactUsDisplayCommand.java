package com.taklip.yoda.vo;

public class ContactUsDisplayCommand {
//	boolean remove;
	int contactUsId;
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