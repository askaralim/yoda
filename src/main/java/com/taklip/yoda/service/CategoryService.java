package com.taklip.yoda.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.CategoryDTO;
import com.taklip.yoda.model.Category;

public interface CategoryService extends IService<Category> {
    CategoryDTO getCategoryDetail(Long id);

    Category create(Category category);

    Category update(Category category);

    void deleteCategory(Long id);

    List<Category> getCategories();

    Category getById(Long id);

    Page<Category> getByPage(int offset, int limit);
}