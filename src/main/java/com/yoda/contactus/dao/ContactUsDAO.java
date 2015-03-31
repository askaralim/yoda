package com.yoda.contactus.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.contactus.model.ContactUs;

@Repository
public class ContactUsDAO extends BaseDAO<ContactUs> {
	private static final String GET_SECTION_BY_SITEID_AND_SECTIONPARENTID_PUBLISHED = "from ContactUs where siteId = ? and active = ? order by seqNum";

	public ContactUs getContactUsById(int siteId, int contactUsId)
			throws SecurityException, Exception {
		ContactUs contactUs = getById(contactUsId);

		if (contactUs.getSiteId() != siteId) {
			throw new SecurityException();
		}

		return contactUs;
	}

	public List<ContactUs> getContentBySiteIdIsActive(int siteId, boolean isActive) {
		List<ContactUs> sections = (List<ContactUs>)getHibernateTemplate().find(GET_SECTION_BY_SITEID_AND_SECTIONPARENTID_PUBLISHED, siteId, isActive);

		if (sections.size() == 0) {
			return new ArrayList<ContactUs>();
		}
		else {
			return sections;
		}
	}
}