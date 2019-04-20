package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.ContactUs;

public interface ContactUsMapper extends BaseMapper<ContactUs> {
	List<ContactUs> getContactUsBySiteId(int siteId);

	List<ContactUs> getContactUsBySiteIdAndIsActive(int siteId, boolean isActive);
}