package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.ChatQuestionMapper;
import com.taklip.yoda.model.ChatQuestion;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.service.ChatQuestionService;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChatQuestionServiceImpl implements ChatQuestionService {
	@Autowired
	ChatQuestionMapper chatQuestionMapper;

	@Autowired
	private IdService idService;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void addChatQuestion(ChatQuestion chatQuestion) {
		chatQuestion.setId(idService.generateId());

		chatQuestion.setCreateDate(new Date());

		chatQuestionMapper.insert(chatQuestion);
	}

	public List<ChatQuestion> getChatQuestions() {
		return chatQuestionMapper.getChatQuestions();
	}

	@Transactional(readOnly = true)
	public Pagination<ChatQuestion> getChatQuestions(RowBounds rowBounds) {
		List<ChatQuestion> chatQuestions = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ChatQuestionMapper.getChatQuestions", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ChatQuestionMapper.count");

		Pagination<ChatQuestion> page = new Pagination<ChatQuestion>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), chatQuestions);

		return page;
	}

	@Transactional(readOnly = true)
	public ChatQuestion getChatQuestion(int id) {
		return chatQuestionMapper.getById(id);
	}
}