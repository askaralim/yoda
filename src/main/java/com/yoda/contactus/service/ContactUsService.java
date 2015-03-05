package com.yoda.contactus.service;

import java.util.List;

import com.yoda.contactus.model.ContactUs;

public interface ContactUsService {

	List<ContactUs> search(long siteId, String srContactUsName, String srActive);

	public ContactUs getContactUsById(long siteId, long contactUsId) throws SecurityException, Exception;

	void update(ContactUs contactUs);

	void deleteContactUs(long siteId, long valueOf) throws SecurityException, Exception;

	ContactUs addContactUs(
			long siteId, long userId, String active, String name, String email,
			String phone, String addressLine1, String addressLine2,
			String cityName, String zipCode, String seqNum, String description);

	ContactUs updateContactUs(
			long contactUsId, long siteId, long userId, String active,
			String name, String email, String phone, String addressLine1,
			String addressLine2, String cityName, String zipCode, String seqNum,
			String description) throws SecurityException, Exception;

	List<ContactUs> getContent(long siteId, char isActive);
}