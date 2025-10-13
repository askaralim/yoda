package com.taklip.yoda.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ContactUsMapper;
import com.taklip.yoda.model.ContactUs;
import com.taklip.yoda.service.ContactUsService;

@Service
public class ContactUsServiceImpl extends ServiceImpl<ContactUsMapper, ContactUs>
        implements ContactUsService {
    public ContactUs create(ContactUs contactUs) {
        this.save(contactUs);
        return contactUs;
    }

    public ContactUs getById(long id) {
        return this.getById(id);
    }

    public Page<ContactUs> getByPage(Integer offset, Integer limit) {
        return this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<ContactUs>().orderByAsc(ContactUs::getSeqNum));
    }

    public ContactUs update(ContactUs contactUs) {
        this.updateById(contactUs);
        return contactUs;
    }

    public void delete(long id) {
        this.removeById(id);
    }
}
