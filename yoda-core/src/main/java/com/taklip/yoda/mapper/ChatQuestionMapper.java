package com.taklip.yoda.mapper;

import com.taklip.yoda.model.ChatQuestion;

import java.util.List;

public interface ChatQuestionMapper extends BaseMapper<ChatQuestion> {
	List<ChatQuestion> getChatQuestions();

	List<ChatQuestion> getChatQuestionByPage(int pageType, int pageId);
}