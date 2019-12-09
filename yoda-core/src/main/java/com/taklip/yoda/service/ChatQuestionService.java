package com.taklip.yoda.service;

import com.taklip.yoda.model.ChatQuestion;
import com.taklip.yoda.model.Pagination;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ChatQuestionService {
	void addChatQuestion(ChatQuestion chatQuestion);

	Pagination<ChatQuestion> getChatQuestions(RowBounds rowBounds);

	List<ChatQuestion> getChatQuestions();

	ChatQuestion getChatQuestion(int id);
}