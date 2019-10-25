package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Term;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface TermService {
	Term getTerm(Long id);

	Term save(Term term);

	void delete(Long id);

	List<Term> getTerms();

	Pagination<Term> getTerms(RowBounds rowBounds);
}