package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.Category;

public interface CategoryService {
	void addCategory(Category category);

	List<Category> getCategories();

	Category getCategory(Long id);

	Category update(Category category);

	void deleteCategory(Category category);

	void save(Category category);
}