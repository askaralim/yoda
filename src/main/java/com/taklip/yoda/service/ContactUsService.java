package com.taklip.yoda.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.ContactUs;

public interface ContactUsService extends IService<ContactUs> {
        List<ContactUs> search(Long siteId, String srContactUsName, Boolean srActive);

        public ContactUs getContactUsById(long contactUsId) throws SecurityException, Exception;

        void update(ContactUs contactUs);

        void deleteContactUs(long contactUsId) throws SecurityException, Exception;

        ContactUs addContactUs(
                        Long siteId, boolean active, String name, String email,
                        String phone, String addressLine1, String addressLine2,
                        String cityName, String zipCode, String seqNum, String description);

        ContactUs updateContactUs(
                        long contactUsId, Long siteId, boolean active,
                        String name, String email, String phone, String addressLine1,
                        String addressLine2, String cityName, String zipCode, String seqNum,
                        String description) throws SecurityException, Exception;

        List<ContactUs> getContactUs(Long siteId, boolean isActive);

        List<ContactUs> getContactUs(Long siteId);
}