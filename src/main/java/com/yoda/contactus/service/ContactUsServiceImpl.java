package com.yoda.contactus.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.contactus.dao.ContactUsDAO;
import com.yoda.contactus.model.ContactUs;
import com.yoda.country.dao.CountryDAO;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.state.dao.StateDAO;
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
			int siteId, boolean active, String name, String email,
			String phone, String addressLine1, String addressLine2,
			String cityName, String zipCode, String seqNum, String description) {
		ContactUs contactUs = new ContactUs();

		contactUs.setSiteId(siteId);
		contactUs.setCreateBy(PortalUtil.getAuthenticatedUser());
		contactUs.setCreateDate(new Date(System.currentTimeMillis()));
		contactUs.setActive(active);
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
		contactUs.setUpdateBy(PortalUtil.getAuthenticatedUser());
		contactUs.setUpdateDate(new Date(System.currentTimeMillis()));

		contactUsDAO.save(contactUs);

		return contactUs;
	}

	public ContactUs updateContactUs(
			int contactUsId, int siteId, boolean active,
			String name, String email, String phone, String addressLine1,
			String addressLine2, String cityName, String zipCode, String seqNum,
			String description) throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(siteId, contactUsId);

		contactUs.setActive(active);
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
		contactUs.setUpdateBy(PortalUtil.getAuthenticatedUser());
		contactUs.setUpdateDate(new Date(System.currentTimeMillis()));

		contactUsDAO.update(contactUs);

		return contactUs;
	}

	public ContactUs getContactUsById(int siteId, int contactUsId)
			throws SecurityException, Exception {
		return contactUsDAO.getContactUsById(siteId, contactUsId);
	}

	public List<ContactUs> getContent(int siteId, boolean isActive) {
		return contactUsDAO.getContentBySiteIdIsActive(siteId, isActive);
	}

	public List<ContactUs> search(int siteId, String name, Boolean srActive) {
		Query query = null;

		String sql = "select contactUs from ContactUs contactUs where siteId = :siteId ";

		if (Validator.isNotNull(name) && name.length() > 0) {
			sql += "and name like :name ";
		}

		if (srActive != null) {
			sql += "and active = :active ";
		}

		sql += "order by seq_num";

		query = contactUsDAO.getSession().createQuery(sql);

		query.setInteger("siteId", siteId);

		if (Validator.isNotNull(name) && name.length() > 0) {
			query.setString("name", "%" + name + "%");
		}

		if (srActive != null) {
			query.setBoolean("active", srActive);
		}

		return query.list();
	}

	public void update(ContactUs contactUs) {
		contactUsDAO.update(contactUs);
	}

	public void deleteContactUs(int siteId, int contactUsId)
			throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(siteId, contactUsId);

		contactUsDAO.delete(contactUs);
	}
}