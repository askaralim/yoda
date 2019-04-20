package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Feedback;

public interface FeedbackMapper extends BaseMapper<Feedback> {
	List<Feedback> getFeedbacks();
}