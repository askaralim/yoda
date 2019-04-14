package com.yoda.category.service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.category.model.Category;
import com.yoda.category.persistence.CategoryMapper;
import com.yoda.kernal.redis.RedisService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private RedisService redisService;

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

	private Category getCategoryFromCached(int categoryId) {
		Category category = null;

		String key = Constants.REDIS_CATEGORY + ":" + categoryId;

		try {
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

				category.setCategoryId(StringUtils.isNotEmpty(id) && !"nil".equalsIgnoreCase(id) ? Integer.valueOf(id) : null);
				category.setDescription(StringUtils.isNotEmpty(description) && !"nil".equalsIgnoreCase(description) ? description : null);
				category.setName(StringUtils.isNotEmpty(name) && !"nil".equalsIgnoreCase(name) ? name : null);
				category.setParent(StringUtils.isNotEmpty(parent) && !"nil".equalsIgnoreCase(parent) ? Integer.valueOf(parent) : null);
				category.setCreateDate(StringUtils.isNotEmpty(createDate) && !"nil".equalsIgnoreCase(createDate) ? Format.getDate(createDate) : null);
				category.setUpdateDate(StringUtils.isNotEmpty(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? Format.getDate(updateDate) : null);

				if (StringUtils.isNotEmpty(createBy) && !"nil".equalsIgnoreCase(createBy)) {
					User user = new User();
					user.setUserId(Long.valueOf(createBy));
					category.setCreateBy(user);
				}

				if (StringUtils.isNotEmpty(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
					User user = new User();
					user.setUserId(Long.valueOf(updateBy));
					category.setUpdateBy(user);
				}
			}
		}
		catch (ParseException e) {
			logger.warn(e.getMessage());
		}

		return category;
	}

	private void setCategoryIntoCached(Category category) {
		Map<String, String> value = new HashMap<>();

		value.put("id", String.valueOf(category.getCategoryId()));
		value.put("parent", (null != category.getParent() ? String.valueOf(category.getParent()) : StringPool.BLANK));
		value.put("description", category.getDescription());
		value.put("name", category.getName());
		value.put("updateBy", String.valueOf(category.getUpdateBy().getUserId()));
		value.put("updateDate", Format.getDate(category.getUpdateDate()));
		value.put("createBy", String.valueOf(category.getCreateBy().getUserId()));
		value.put("createDate", Format.getDate(category.getCreateDate()));

		redisService.setMap(Constants.REDIS_CATEGORY + ":" + category.getCategoryId(), value);
	}
}