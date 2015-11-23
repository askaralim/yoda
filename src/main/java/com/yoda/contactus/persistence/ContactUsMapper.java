package com.yoda.contactus.persistence;

import java.util.List;

import com.yoda.contactus.model.ContactUs;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ContactUsMapper extends BaseMapper<ContactUs> {
	List<ContactUs> getContactUsBySiteId(int siteId);

	List<ContactUs> getContactUsBySiteIdAndIsActive(int siteId, boolean isActive);
}