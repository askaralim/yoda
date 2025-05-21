package com.taklip.yoda.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.mapper.ContentBrandMapper;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.common.contant.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ContentBrandServiceImpl extends ServiceImpl<ContentBrandMapper, ContentBrand>
        implements ContentBrandService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void create(ContentBrand contentBrand) {
        this.save(contentBrand);
        this.setContentBrandIntoCache(contentBrand);
    }

    @Override
    public void update(ContentBrand contentBrand) {
        this.updateById(contentBrand);
        deleteContentBrandFromCache(contentBrand.getId());
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentBrand getById(long contentBrandId) {
        ContentBrand contentBrand = getContentBrandFromCache(contentBrandId);

        if (null != contentBrand) {
            return contentBrand;
        }

        contentBrand = this.getById(contentBrandId);

        this.setContentBrandIntoCache(contentBrand);

        return contentBrand;
    }

    @Override
    public List<ContentBrand> getContentBrandsByContentId(long contentId) {
        return this.list(new LambdaQueryWrapper<ContentBrand>().eq(ContentBrand::getContentId, contentId)
                .orderByDesc(ContentBrand::getCreateTime));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBrand> getContentBrands() {
        return this.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentBrand> getContentBrandByBrandId(long brandId) {
        return this.list(new LambdaQueryWrapper<ContentBrand>().eq(ContentBrand::getBrandId, brandId));
    }

    private ContentBrand getContentBrandFromCache(long contentBrandId) {
        Map<String, String> map = redisService.getMap(Constants.REDIS_CONTENT_BRAND + ":" + contentBrandId);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            return objectMapper.convertValue(map, ContentBrand.class);

        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to ContentBrand: {}", e.getMessage());
            return null;
        }
    }

    private void deleteContentBrandFromCache(Long id) {
        redisService.delete(Constants.REDIS_CONTENT_BRAND + ":" + id);
    }

    private void setContentBrandIntoCache(ContentBrand contentBrand) {
        if (Objects.isNull(contentBrand)) {
            return;
        }

        try {
            Map<String, String> value = objectMapper.convertValue(contentBrand,
                    new TypeReference<Map<String, String>>() {
                    });

            redisService.setMap(Constants.REDIS_CONTENT_BRAND + ":" + contentBrand.getId(), value);
        } catch (IllegalArgumentException e) {
            log.error("Failed to cache content brand {}: {}", contentBrand.getId(), e.getMessage());
        }
    }
}
