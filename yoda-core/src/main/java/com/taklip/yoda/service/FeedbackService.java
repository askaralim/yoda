package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.Feedback;

public interface FeedbackService {
	void addFeedback(Feedback feedback);

	List<Feedback> getFeedbacks();

	Feedback getFeedback(long id);

	void deleteFeedback(Feedback feedback);
}