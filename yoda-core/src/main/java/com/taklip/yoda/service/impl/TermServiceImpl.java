package com.taklip.yoda.service.impl;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.BrandMapper;
import com.taklip.yoda.mapper.TermMapper;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.service.TermService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class TermServiceImpl implements TermService {
	private static Logger logger = LoggerFactory.getLogger(TermServiceImpl.class);

	@Autowired
	TermMapper termMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private IdService idService;

	public Term save(Term term) {
		if (null == term.getId()) {
			return this.add(term);
		}
		else {
			return this.update(term);
		}
	}

	public Term add(Term term) {
		term.setId(idService.generateId());

		term.preInsert();

		termMapper.insert(term);

		return term;
	}

	public Term update(Term term) {
		Term termDb = termMapper.getById(term.getId());

		termDb.setTitle(term.getTitle());
		termDb.setDescription(term.getDescription());
		termDb.setCategoryId(term.getCategoryId());
		termDb.setContentId(term.getContentId());

		termDb.preUpdate();

		termMapper.update(termDb);

		return termDb;
	}

	@Transactional(readOnly = true)
	public List<Term> getTerms() {
		return termMapper.getTerms();
	}

	@Transactional(readOnly = true)
	public Pagination<Term> getTerms(RowBounds rowBounds) {
		List<Term> terms = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.TermMapper.getTerms", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.TermMapper.count");

		Pagination<Term> page = new Pagination<Term>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), terms);

		return page;
	}

	@Transactional(readOnly = true)
	public Term getTerm(Long id) {
		return termMapper.getById(id);
	}

	public void delete(Long id) {
	}
}