package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.FeedbackMapper;
import com.taklip.yoda.model.Feedback;
import com.taklip.yoda.service.FeedbackService;

@Transactional
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
    @Transactional(readOnly = true)
    public List<Feedback> getFeedbacks() {
        return this.list();
    }

    @Transactional(readOnly = true)
    public Feedback getFeedback(long id) {
        return this.getById(id);
    }

    public void deleteFeedback(Feedback feedback) {
        this.removeById(feedback.getId());
    }

    public void addFeedback(Feedback feedback) {
        feedback.setDescription(HtmlUtils.htmlEscape(feedback.getDescription()));
        feedback.setEmail(HtmlUtils.htmlEscape(feedback.getEmail()));
        feedback.setPhone(HtmlUtils.htmlEscape(feedback.getPhone()));
        feedback.setUsername(HtmlUtils.htmlEscape(feedback.getUsername()));

        this.save(feedback);
    }
}