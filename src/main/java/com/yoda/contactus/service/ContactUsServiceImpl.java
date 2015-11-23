package com.yoda.contactus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.contactus.model.ContactUs;
import com.yoda.contactus.persistence.ContactUsMapper;
import com.yoda.country.dao.CountryDAO;
import com.yoda.state.dao.StateDAO;
import com.yoda.util.Format;

@Service
public class ContactUsServiceImpl implements ContactUsService {
	@Autowired
//	private ContactUsDAO contactUsDAO;
	private ContactUsMapper contactUsMapper;

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
//		contactUs.setCreateBy(PortalUtil.getAuthenticatedUser());
//		contactUs.setCreateDate(new Date(System.currentTimeMillis()));
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
//		contactUs.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		contactUs.setUpdateDate(new Date(System.currentTimeMillis()));

		contactUs.preInsert();

		contactUsMapper.insert(contactUs);

		return contactUs;
	}

	public ContactUs updateContactUs(
			int contactUsId, int siteId, boolean active,
			String name, String email, String phone, String addressLine1,
			String addressLine2, String cityName, String zipCode, String seqNum,
			String description) throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(contactUsId);

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
//		contactUs.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		contactUs.setUpdateDate(new Date(System.currentTimeMillis()));

		contactUs.preUpdate();

		update(contactUs);

		return contactUs;
	}

	public ContactUs getContactUsById(int contactUsId)
			throws SecurityException, Exception {
		return contactUsMapper.getById(contactUsId);
	}

	public List<ContactUs> getContactUs(int siteId) {
		return contactUsMapper.getContactUsBySiteId(siteId);
	}

	public List<ContactUs> getContactUs(int siteId, boolean isActive) {
		return contactUsMapper.getContactUsBySiteIdAndIsActive(siteId, isActive);
	}

	public List<ContactUs> search(int siteId, String name, Boolean srActive) {
//		Query query = null;
//
//		String sql = "select contactUs from ContactUs contactUs where siteId = :siteId ";
//
//		if (Validator.isNotNull(name) && name.length() > 0) {
//			sql += "and name like :name ";
//		}
//
//		if (srActive != null) {
//			sql += "and active = :active ";
//		}
//
//		sql += "order by seq_num";
//
//		query = contactUsDAO.getSession().createQuery(sql);
//
//		query.setInteger("siteId", siteId);
//
//		if (Validator.isNotNull(name) && name.length() > 0) {
//			query.setString("name", "%" + name + "%");
//		}
//
//		if (srActive != null) {
//			query.setBoolean("active", srActive);
//		}
//
//		return query.list();
		return null;
	}

	public void update(ContactUs contactUs) {
		contactUs.preUpdate();

		contactUsMapper.update(contactUs);
	}

	public void deleteContactUs(int siteId, int contactUsId)
			throws SecurityException, Exception {
		ContactUs contactUs = getContactUsById(contactUsId);

		contactUsMapper.delete(contactUs);
	}
}