package com.taklip.yoda.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.CategoryDTO;
import com.taklip.yoda.mapper.CategoryMapper;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.common.contant.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private UserService userService;

    @Override
    public Category create(Category category) {
        this.save(category);

        return category;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategories() {
        return this.list();
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO getCategoryDetail(Long id) {
        Category category = this.getByCategoryId(id);

        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = modelConvertor.convertToCategoryDTO(category);
        categoryDTO.setCreateBy(userService.getUserDetail(category.getCreateBy()));
        categoryDTO.setUpdateBy(userService.getUserDetail(category.getUpdateBy()));

        return categoryDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Category getByCategoryId(Long id) {
        return this.getById(id);
    }

    @Override
    public void deleteCategory(Long id) {
        this.removeById(id);
    }

    @Override
    public Category update(Category category) {
        this.updateById(category);

        deleteCategoryFromCached(category.getId());

        return category;
    }

    private void deleteCategoryFromCached(long id) {
        redisService.delete(Constants.REDIS_CATEGORY + ":" + id);
    }

    private Category getCategoryFromCached(long categoryId) {
        String key = Constants.REDIS_CATEGORY + ":" + categoryId;
        Map<String, String> map = redisService.getMap(key);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            return objectMapper.convertValue(map, Category.class);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to Category: {}", e.getMessage());
            return null;
        }
    }

    private void setCategoryIntoCached(Category category) {
        if (Objects.isNull(category)) {
            return;
        }

        String key = Constants.REDIS_CATEGORY + ":" + category.getId();

        try {
            Map<String, String> map = objectMapper.convertValue(category, new TypeReference<Map<String, String>>() {
            });

            redisService.setMap(key, map);
        } catch (IllegalArgumentException e) {
            log.error("Failed to cache category {}: {}", category.getId(), e.getMessage());
        }
    }
}