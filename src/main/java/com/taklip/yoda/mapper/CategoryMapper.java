package com.taklip.yoda.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.Category;

public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> getCategories();
}