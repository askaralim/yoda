package com.yoda.feedback.service;

import java.util.List;

import com.yoda.feedback.model.Feedback;

public interface FeedbackService {
	void addFeedback(Feedback feedback);

	List<Feedback> getFeedbacks();

	Feedback getFeedback(long id);

	void deleteFeedback(Feedback feedback);
}