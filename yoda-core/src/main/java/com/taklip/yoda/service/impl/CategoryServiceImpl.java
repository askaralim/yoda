package com.taklip.yoda.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.mapper.CategoryMapper;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.DateUtil;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	private IdService idService;

	public void addCategory(Category category) {
		category.setId(idService.generateId());
		category.preInsert();

		categoryMapper.insert(category);
	}

	@Transactional(readOnly = true)
	public List<Category> getCategories() {
		return categoryMapper.getCategories();
	}

	@Transactional(readOnly = true)
	public Category getCategory(Long id) {
		Category category = this.getCategoryFromCached(id);

		if (null != category) {
			return category;
		}

		category = categoryMapper.getById(id);

		this.setCategoryIntoCached(category);

		return category;
	}

	public void deleteCategory(Category category) {
		categoryMapper.delete(category);
	}

	@Override
	public void save(Category category) {
		if (null == category.getId()) {
			this.addCategory(category);
		}
		else {
			this.update(category);
		}
	}

	public Category update(Category category) {
		category.preUpdate();

		categoryMapper.update(category);

		deleteCategoryFromCached(category.getId());

		return category;
	}

	private void deleteCategoryFromCached(long id) {
		redisService.delete(Constants.REDIS_CATEGORY + ":" + id);
	}

	private Category getCategoryFromCached(long categoryId) {
		Category category = null;

		String key = Constants.REDIS_CATEGORY + ":" + categoryId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() >0) {
			category = new Category();

			String id = redisService.getMap(key, "id");
			String description = redisService.getMap(key, "description");
			String name = redisService.getMap(key, "name");
			String parent = redisService.getMap(key, "parent");
			String createBy = redisService.getMap(key, "createBy");
			String createDate = redisService.getMap(key, "createDate");
			String updateBy = redisService.getMap(key, "updateBy");
			String updateDate = redisService.getMap(key, "updateDate");

			category.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			category.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
			category.setName(StringUtils.isNoneBlank(name) && !"nil".equalsIgnoreCase(name) ? name : null);
			category.setParent(StringUtils.isNoneBlank(parent) && !"nil".equalsIgnoreCase(parent) ? Long.valueOf(parent) : null);
			category.setCreateDate(StringUtils.isNoneBlank(createDate) && !"nil".equalsIgnoreCase(createDate) ? DateUtil.getDate(createDate) : null);
			category.setUpdateDate(StringUtils.isNoneBlank(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? DateUtil.getDate(updateDate) : null);

			if (StringUtils.isNoneBlank(createBy) && !"nil".equalsIgnoreCase(createBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(createBy));
				category.setCreateBy(user);
			}

			if (StringUtils.isNoneBlank(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(updateBy));
				category.setUpdateBy(user);
			}
		}

		return category;
	}

	private void setCategoryIntoCached(Category category) {
		Map<String, String> value = new HashMap<>();

		value.put("id", String.valueOf(category.getId()));
		value.put("parent", (null != category.getParent() ? String.valueOf(category.getParent()) : StringPool.BLANK));
		value.put("description", category.getDescription());
		value.put("name", category.getName());
		value.put("updateBy", String.valueOf(category.getUpdateBy().getUserId()));
		value.put("updateDate", DateUtil.getDate(category.getUpdateDate()));
		value.put("createBy", String.valueOf(category.getCreateBy().getUserId()));
		value.put("createDate", DateUtil.getDate(category.getCreateDate()));

		redisService.setMap(Constants.REDIS_CATEGORY + ":" + category.getId(), value);
	}
}