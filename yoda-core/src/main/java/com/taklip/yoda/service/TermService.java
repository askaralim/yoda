package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Term;

import java.util.List;

/**
 * @author askar
 */
public interface TermService {
    Term getTerm(Long id);

    Term save(Term term);

    void delete(Long id);

    List<Term> getTerms();

    PageInfo<Term> getTerms(Integer offset, Integer limit);
}