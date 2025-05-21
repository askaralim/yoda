package com.taklip.yoda.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.mapper.ChatQuestionMapper;
import com.taklip.yoda.model.ChatQuestion;
import com.taklip.yoda.service.ChatQuestionService;

/**
 * @author askar
 */
@Service
@Transactional
public class ChatQuestionServiceImpl extends ServiceImpl<ChatQuestionMapper, ChatQuestion> implements ChatQuestionService {
    @Override
    public void addChatQuestion(ChatQuestion chatQuestion) {
        this.save(chatQuestion);
    }

    @Override
    public List<ChatQuestion> getChatQuestions() {
        return this.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChatQuestion> getChatQuestions(Integer offset, Integer limit) {
        return page(new Page<>(offset, limit), new LambdaQueryWrapper<ChatQuestion>().orderByDesc(ChatQuestion::getCreateTime));
    }

    @Override
    @Transactional(readOnly = true)
    public ChatQuestion getChatQuestion(int id) {
        return this.getById(id);
    }
}