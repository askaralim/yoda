package com.taklip.yoda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.ChatQuestionMapper;
import com.taklip.yoda.model.ChatQuestion;
import com.taklip.yoda.service.ChatQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author askar
 */
@Service
@Transactional
public class ChatQuestionServiceImpl implements ChatQuestionService {
    @Autowired
    ChatQuestionMapper chatQuestionMapper;

    @Autowired
    private IdService idService;

    @Override
    public void addChatQuestion(ChatQuestion chatQuestion) {
        chatQuestion.setId(idService.generateId());

        chatQuestion.setCreateDate(new Date());

        chatQuestionMapper.insert(chatQuestion);
    }

    @Override
    public List<ChatQuestion> getChatQuestions() {
        return chatQuestionMapper.getChatQuestions();
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<ChatQuestion> getChatQuestions(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<ChatQuestion> chatQuestions = chatQuestionMapper.getChatQuestions();

        PageInfo<ChatQuestion> pageInfo = new PageInfo<>(chatQuestions);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public ChatQuestion getChatQuestion(int id) {
        return chatQuestionMapper.getById(id);
    }
}