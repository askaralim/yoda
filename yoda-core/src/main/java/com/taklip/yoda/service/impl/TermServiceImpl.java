package com.taklip.yoda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.TermMapper;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author askar
 */
@Transactional
@Service
public class TermServiceImpl implements TermService {
    @Autowired
    TermMapper termMapper;

    @Autowired
    private IdService idService;

    @Override
    public Term save(Term term) {
        if (null == term.getId()) {
            return this.add(term);
        } else {
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

    @Override
    @Transactional(readOnly = true)
    public List<Term> getTerms() {
        return termMapper.getTerms();
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Term> getTerms(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<Term> terms = termMapper.getTerms();

        PageInfo<Term> pageInfo = new PageInfo<>(terms);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Term getTerm(Long id) {
        return termMapper.getById(id);
    }

    @Override
    public void delete(Long id) {
    }
}