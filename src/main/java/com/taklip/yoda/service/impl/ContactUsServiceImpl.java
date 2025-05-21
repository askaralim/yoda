package com.taklip.yoda.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ContactUsMapper;
import com.taklip.yoda.model.ContactUs;
import com.taklip.yoda.service.ContactUsService;

@Service
public class ContactUsServiceImpl extends ServiceImpl<ContactUsMapper, ContactUs> implements ContactUsService {
    public ContactUs addContactUs(
            Long siteId, boolean active, String name, String email,
            String phone, String addressLine1, String addressLine2,
            String cityName, String zipCode, String seqNum, String description) {
        ContactUs contactUs = new ContactUs();

        contactUs.setSiteId(siteId);
        // contactUs.setCreateBy(PortalUtil.getAuthenticatedUser());
        // contactUs.setCreateDate(new Date(System.currentTimeMillis()));
        contactUs.setActive(active);
        contactUs.setName(name);
        contactUs.setEmail(email);
        contactUs.setPhone(phone);
        contactUs.setAddressLine1(addressLine1);
        contactUs.setAddressLine2(addressLine2);
        contactUs.setCityName(cityName);
        contactUs.setZipCode(zipCode);

        if (StringUtils.isNoneBlank(seqNum)) {
            contactUs.setSeqNum(Integer.valueOf(seqNum));
        } else {
            contactUs.setSeqNum(0);
        }

        contactUs.setDescription(description);
        // contactUs.setUpdateBy(PortalUtil.getAuthenticatedUser());
        // contactUs.setUpdateDate(new Date(System.currentTimeMillis()));

        this.save(contactUs);

        return contactUs;
    }

    public ContactUs updateContactUs(
            long contactUsId, Long siteId, boolean active,
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

        if (StringUtils.isNoneBlank(seqNum)) {
            contactUs.setSeqNum(Integer.valueOf(seqNum));
        } else {
            contactUs.setSeqNum(Integer.valueOf(0));
        }

        contactUs.setDescription(description);

        this.updateById(contactUs);

        return contactUs;
    }

    public ContactUs getContactUsById(long contactUsId)
            throws SecurityException, Exception {
        return this.getById(contactUsId);
    }

    public List<ContactUs> getContactUs(Long siteId) {
        return this.list(new LambdaQueryWrapper<ContactUs>().eq(ContactUs::getSiteId, siteId)
                .orderByAsc(ContactUs::getId));
    }

    public List<ContactUs> getContactUs(Long siteId, boolean isActive) {
        return this.list(new LambdaQueryWrapper<ContactUs>().eq(ContactUs::getSiteId, siteId)
                .eq(ContactUs::isActive, isActive).orderByAsc(ContactUs::getSeqNum));
    }

    public List<ContactUs> search(Long siteId, String name, Boolean srActive) {
        // Query query = null;
        //
        // String sql = "select contactUs from ContactUs contactUs where siteId =
        // :siteId ";
        //
        // if (Validator.isNotNull(name) && name.length() > 0) {
        // sql += "and name like :name ";
        // }
        //
        // if (srActive != null) {
        // sql += "and active = :active ";
        // }
        //
        // sql += "order by seq_num";
        //
        // query = contactUsDAO.getSession().createQuery(sql);
        //
        // query.setInteger("siteId", siteId);
        //
        // if (Validator.isNotNull(name) && name.length() > 0) {
        // query.setString("name", "%" + name + "%");
        // }
        //
        // if (srActive != null) {
        // query.setBoolean("active", srActive);
        // }
        //
        // return query.list();
        return null;
    }

    public void update(ContactUs contactUs) {
        this.updateById(contactUs);
    }

    public void deleteContactUs(long contactUsId)
            throws SecurityException, Exception {
        ContactUs contactUs = getContactUsById(contactUsId);

        this.removeById(contactUs);
    }
}