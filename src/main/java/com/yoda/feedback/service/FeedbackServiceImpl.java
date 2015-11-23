package com.yoda.feedback.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.yoda.feedback.model.Feedback;
import com.yoda.feedback.persistence.FeedbackMapper;

@Transactional
@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	FeedbackMapper feedbackMapper;
//	FeedbackDAO feedbackDAO;

	@Transactional(readOnly = true)
	public List<Feedback> getFeedbacks() {
		return feedbackMapper.getFeedbacks();
	}

	@Transactional(readOnly = true)
	public Feedback getFeedback(long id) {
		return feedbackMapper.getById(id);
	}

	public void deleteFeedback(Feedback feedback) {
		feedbackMapper.delete(feedback);
	}

	public void addFeedback(Feedback feedback) {
		feedback.setCreateDate(new Date());

		feedback.setDescription(HtmlUtils.htmlEscape(feedback.getDescription()));
		feedback.setEmail(HtmlUtils.htmlEscape(feedback.getEmail()));
		feedback.setPhone(HtmlUtils.htmlEscape(feedback.getPhone()));
		feedback.setUsername(HtmlUtils.htmlEscape(feedback.getUsername()));

		feedbackMapper.insert(feedback);
	}
}