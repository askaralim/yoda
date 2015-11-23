package com.yoda.contactus.service;

import java.util.List;

import com.yoda.contactus.model.ContactUs;

public interface ContactUsService {

	List<ContactUs> search(int siteId, String srContactUsName, Boolean srActive);

	public ContactUs getContactUsById(int contactUsId) throws SecurityException, Exception;

	void update(ContactUs contactUs);

	void deleteContactUs(int siteId, int valueOf) throws SecurityException, Exception;

	ContactUs addContactUs(
			int siteId, boolean active, String name, String email,
			String phone, String addressLine1, String addressLine2,
			String cityName, String zipCode, String seqNum, String description);

	ContactUs updateContactUs(
			int contactUsId, int siteId, boolean active,
			String name, String email, String phone, String addressLine1,
			String addressLine2, String cityName, String zipCode, String seqNum,
			String description) throws SecurityException, Exception;

	List<ContactUs> getContactUs(int siteId, boolean isActive);

	List<ContactUs> getContactUs(int siteId);
}