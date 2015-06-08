package com.yoda.category.service;

import java.util.List;

import com.yoda.category.model.Category;

public interface CategoryService {
	void addCategory(Category category);

	List<Category> getCategories();

	Category getCategory(Integer id);

	Category update(Category category);

	Category update(Integer id, String name, String description, Integer parent);

	void deleteCategory(Category category);
}