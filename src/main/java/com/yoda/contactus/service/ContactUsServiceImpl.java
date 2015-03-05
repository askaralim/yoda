package com.yoda.contactus.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.contactus.dao.ContactUsDAO;
import com.yoda.contactus.model.ContactUs;
import com.yoda.country.dao.CountryDAO;
import com.yoda.state.dao.StateDAO;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.Validator;

@Service
public class ContactUsServiceImpl implements ContactUsService {
	@Autowired
	private ContactUsDAO contactUsDAO;

	@Autowired
	private StateDAO stateDAO;

	@Autowired
	private CountryDAO countryDAO;

	public ContactUs addContactUs(
			long siteId, long userId, String active, String name, String email,
			String phone, String addressLine1, String addressLine2,
			String cityName, String zipCode, String seqNum, String description) {
		ContactUs contactUs = new ContactUs();

		contactUs.setSiteId(siteId);
		contactUs.setRecCreateBy(userId);
		contactUs.setRecCreateDatetime(new Date(System.currentTimeMillis()));
		contactUs.setActive(new Character(Constants.ACTIVE_NO));

		if (active != null && active.equals(String.valueOf(Constants.ACTIVE_YES))) {
			contactUs.setActive(new Character(Constants.ACTIVE_YES));
		}

		contactUs.setName(name);
		contactUs.setEmail(email);
		contactUs.setPhone(phone);
		contactUs.setAddressLine1(addressLine1);
		contactUs.setAddressLine2(addressLine2);
		contactUs.setCityName(cityName);
		contactUs.setZipCode(zipCode);

		if (!Format.isNullOrEmpty(seqNum)) {
			contactUs.setSeqNum(Format.getIntObj(seqNum));
		}
		else {
			contactUs.setSeqNum(new Integer(0));
		}

		contactUs.setDescription(description);
		contactUs.setRecUpdateBy(userId);
		contactUs.setRecUpdateDatetime(new Date(System.currentTimeMillis()));

		contactUsDAO.save(contactUs);

		return contactUs;
	}

	public ContactUs updateContactUs(
			long contactUsId, long siteId, long userId, String active,
			String name, String email, String phone, String addressLine1,
			String addressLine2, String cityName, String zipCode, String seqNum,
			String description) throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(siteId, contactUsId);

		contactUs.setActive(new Character(Constants.ACTIVE_NO));

		if (active != null && active.equals(String.valueOf(Constants.ACTIVE_YES))) {
			contactUs.setActive(new Character(Constants.ACTIVE_YES));
		}

		contactUs.setName(name);
		contactUs.setEmail(email);
		contactUs.setPhone(phone);
		contactUs.setAddressLine1(addressLine1);
		contactUs.setAddressLine2(addressLine2);
		contactUs.setCityName(cityName);
		contactUs.setZipCode(zipCode);

		if (!Format.isNullOrEmpty(seqNum)) {
			contactUs.setSeqNum(Format.getIntObj(seqNum));
		}
		else {
			contactUs.setSeqNum(new Integer(0));
		}

		contactUs.setDescription(description);
		contactUs.setRecUpdateBy(userId);
		contactUs.setRecUpdateDatetime(new Date(System.currentTimeMillis()));

		contactUsDAO.update(contactUs);

		return contactUs;
	}

	public ContactUs getContactUsById(long siteId, long contactUsId)
			throws SecurityException, Exception {
		return contactUsDAO.getContactUsById(siteId, contactUsId);
	}

	public List<ContactUs> getContent(long siteId, char isActive) {
		return contactUsDAO.getContentBySiteIdIsActive(siteId, isActive);
	}

	public List<ContactUs> search(long siteId, String name, String srActive) {
		Query query = null;

		String sql = "select contactUs from ContactUs contactUs where siteId = :siteId ";

		if (Validator.isNotNull(name) && name.length() > 0) {
			sql += "and name like :name ";
		}

		if (srActive != null && !srActive.equals("*")) {
			sql += "and active = :active ";
		}

		sql += "order by seq_num";

		query = contactUsDAO.getSession().createQuery(sql);

		query.setLong("siteId", siteId);

		if (Validator.isNotNull(name) && name.length() > 0) {
			query.setString("name", "%" + name + "%");
		}

		if (!srActive.equals("*")) {
			query.setString("active", srActive);
		}

		return query.list();
	}

	public void update(ContactUs contactUs) {
		contactUsDAO.update(contactUs);
	}

	public void deleteContactUs(long siteId, long contactUsId)
			throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(siteId, contactUsId);

		contactUsDAO.delete(contactUs);
	}
}