package com.yoda.feedback.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.yoda.feedback.dao.FeedbackDAO;
import com.yoda.feedback.model.Feedback;

@Transactional
@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	FeedbackDAO feedbackDAO;

	@Transactional(readOnly = true)
	public List<Feedback> getFeedbacks() {
		return feedbackDAO.getAll();
	}

	@Transactional(readOnly = true)
	public Feedback getFeedback(long id) {
		return feedbackDAO.getById(id);
	}

	public void deleteFeedback(Feedback feedback) {
		feedbackDAO.delete(feedback);
	}

	public void addFeedback(Feedback feedback) {
		feedback.setCreateDate(new Date());

		feedback.setDescription(HtmlUtils.htmlEscape(feedback.getDescription()));
		feedback.setEmail(HtmlUtils.htmlEscape(feedback.getEmail()));
		feedback.setPhone(HtmlUtils.htmlEscape(feedback.getPhone()));
		feedback.setUsername(HtmlUtils.htmlEscape(feedback.getUsername()));

		feedbackDAO.save(feedback);
	}
}