package com.taklip.yoda.mapper;

import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Term;

import java.util.List;

public interface TermMapper extends BaseMapper<Term> {
	List<Term> getTerms();

	List<Item> getTermsByContentId(Long contentId);

	List<Item> getTermsByCategoryId(Long categoryId);
}