package com.taklip.yoda.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.model.ChatQuestion;

public interface ChatQuestionService extends IService<ChatQuestion> {
    void addChatQuestion(ChatQuestion chatQuestion);

    Page<ChatQuestion> getChatQuestions(Integer offset, Integer limit);

    List<ChatQuestion> getChatQuestions();

    ChatQuestion getChatQuestion(int id);
}