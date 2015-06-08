package com.yoda.category.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.category.dao.CategoryDAO;
import com.yoda.category.model.Category;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	public void addCategory(Category category) {
		User user = PortalUtil.getAuthenticatedUser();

		category.setCreateBy(user.getUserId());
		category.setCreateDate(new Date());
		category.setUpdateBy(user.getUserId());
		category.setUpdateDate(new Date());

		categoryDAO.save(category);
	}

	@Transactional(readOnly = true)
	public List<Category> getCategories() {
		return categoryDAO.getAll();
	}

	@Transactional(readOnly = true)
	public Category getCategory(Integer id) {
		return categoryDAO.getById(id);
	}

	public void deleteCategory(Category category) {
		categoryDAO.delete(category);
	}

	public Category update(
			Integer id, String name, String description, Integer parent) {
		Category category = this.getCategory(id);

		category.setDescription(description);
		category.setParent(parent);
		category.setName(name);
		category.setUpdateBy(PortalUtil.getAuthenticatedUser().getUserId());
		category.setUpdateDate(new Date());

		update(category);

		return category;
	}

	public Category update(Category category) {
		categoryDAO.update(category);

		return category;
	}
}