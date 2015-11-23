package com.yoda.feedback.persistence;

import java.util.List;

import com.yoda.feedback.model.Feedback;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
	List<Feedback> getFeedbacks();
}