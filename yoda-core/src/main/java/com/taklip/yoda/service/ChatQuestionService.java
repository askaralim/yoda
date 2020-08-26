package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.ChatQuestion;

import java.util.List;

public interface ChatQuestionService {
    void addChatQuestion(ChatQuestion chatQuestion);

    PageInfo<ChatQuestion> getChatQuestions(Integer offset, Integer limit);

    List<ChatQuestion> getChatQuestions();

    ChatQuestion getChatQuestion(int id);
}