package com.yoda.category.persistence;

import java.util.List;

import com.yoda.category.model.Category;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface CategoryMapper extends BaseMapper<Category> {
	List<Category> getCategories();
}