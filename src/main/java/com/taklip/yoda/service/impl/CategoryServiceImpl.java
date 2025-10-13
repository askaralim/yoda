package com.taklip.yoda.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.CategoryDTO;
import com.taklip.yoda.mapper.CategoryMapper;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    @Autowired
    private ModelConvertor modelConvertor;

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

    @Override
    public Page<Category> getByPage(int offset, int limit) {
        return this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Category>().orderByDesc(Category::getCreateTime));
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryDTO getCategoryDetail(Long id) {
        Category category = this.getById(id);

        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = modelConvertor.convertToCategoryDTO(category);
        // categoryDTO.setCreateBy(userService.getUserDetail(category.getCreateBy()));
        // categoryDTO.setUpdateBy(userService.getUserDetail(category.getUpdateBy()));

        return categoryDTO;
    }

    @Transactional(readOnly = true)
    @Override
    public Category getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void deleteCategory(Long id) {
        this.removeById(id);
    }

    @Override
    public Category update(Category category) {
        this.updateById(category);

        return category;
    }

}
