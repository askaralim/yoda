package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.ContactUs;

public interface ContactUsService extends IService<ContactUs> {
        public ContactUs getById(long id);

        void delete(long id);

        ContactUs create(ContactUs contactUs);

        ContactUs update(ContactUs contactUs);

        Page<ContactUs> getByPage(Integer offset, Integer limit);
}
