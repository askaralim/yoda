package com.yoda.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.category.model.Category;
import com.yoda.category.persistence.CategoryMapper;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;

	public void addCategory(Category category) {
		category.preInsert();

		categoryMapper.insert(category);
	}

	@Transactional(readOnly = true)
	public List<Category> getCategories() {
		return categoryMapper.getCategories();
	}

	@Transactional(readOnly = true)
	public Category getCategory(Integer id) {
		return categoryMapper.getById(id);
	}

	public void deleteCategory(Category category) {
		categoryMapper.delete(category);
	}

	public Category update(
			Integer id, String name, String description, Integer parent) {
		Category category = this.getCategory(id);

		category.setDescription(description);
		category.setParent(parent);
		category.setName(name);

		update(category);

		return category;
	}

	public Category update(Category category) {
		category.preUpdate();

		categoryMapper.update(category);

		return category;
	}
}