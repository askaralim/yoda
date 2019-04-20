package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.Category;

public interface CategoryService {
	void addCategory(Category category);

	List<Category> getCategories();

	Category getCategory(Integer id);

	Category update(Category category);

	Category update(Integer id, String name, String description, Integer parent);

	void deleteCategory(Category category);
}