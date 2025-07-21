package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.BrandDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.BrandMapper;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.RedisService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Service
@Slf4j
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ImageUploader imageUpload;

    @Autowired
    private FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelConvertor modelConvertor;

    @Override
    public boolean create(Brand brand) {
        return this.save(brand);
    }

    @Override
    public boolean update(Brand brand) {
        deleteBrandFromCached(brand.getId());

        return this.updateById(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> getBrands() {
        return this.list();
    }

    @Override
    public List<Brand> getBrandsTopViewed(int count) {
        List<String> ids = getBrandsTopViewedListFromCache(count);
        List<Brand> brands = new ArrayList<>();

        if (CollectionUtils.isEmpty(ids)) {
            brands = this
                    .list(new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getHitCounter).last("limit " + count));

            ids = brands.stream().map(brand -> String.valueOf(brand.getId())).collect(Collectors.toList());

            this.setBrandsTopViewedListIntoCache(ids);
        } else {
            brands = this.list(new LambdaQueryWrapper<Brand>().in(Brand::getId, ids));
        }

        return brands;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Brand> getBrands(Integer offset, Integer limit) {
        log.info("getBrands offset: {}, limit: {}", offset, limit);
        return page(new Page<>(offset, limit), new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Brand> getHotBrands(Integer offset, Integer limit) {
        return page(new Page<>(offset, limit), new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getHitCounter));
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getBrandDetail(Long id) {
        Brand brandDb = this.getById(id);
        BrandDTO brandDTO = modelConvertor.convertBrandToDTO(brandDb);
        return brandDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Brand getBrand(Long id) {
        return this.getById(id);
    }

    @Override
    public void deleteBrand(Long id) {
        this.removeById(id);
    }

    @Override
    public void increaseBrandHitCounter(Long id) {
        baseMapper.increaseHitCounter(id);
    }

    @Override
    public void updateBrandRating(Long id, int rating) {
        Brand brandDb = this.getById(id);
        brandDb.setScore(rating);
        this.updateById(brandDb);
    }

    @Override
    public Brand updateImage(Long id, MultipartFile file) {
        Brand brand = this.getById(id);

        imageUpload.deleteImage(brand.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.BRAND.getType(), id, file);

            brand.setImagePath(imagePath);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        this.updateById(brand);

        setBrandIntoCached(brand);

        return brand;
    }

    @Override
    public int getBrandHitCounter(Long id) {
        String hit = redisService.get(Constants.REDIS_BRAND_HIT_COUNTER + ":" + id);

        if (StringUtils.isNotBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
            return Integer.parseInt(hit);
        }

        Brand brand = this.getById(id);
        if (brand != null) {
            int hitCounter = brand.getHitCounter();
            // setBrandHitCounterIntoCached(id, hitCounter);
            return hitCounter;
        }

        return 0;
    }

    private int getBrandScoreFromCached(Long id) {
        String score = redisService.get(Constants.REDIS_BRAND_RATE + ":" + id);

        if (StringUtils.isNotBlank(score) && !"nil".equalsIgnoreCase(score)) {
            return Integer.parseInt(score);
        }

        Brand brand = this.getById(id);
        if (brand != null) {
            int brandScore = brand.getScore();
            setBrandScoreIntoCached(id, brandScore);
            return brandScore;
        }

        return 0;
    }

    private List<String> getBrandsTopViewedListFromCache(int count) {
        return redisService.getList(Constants.REDIS_BRAND_TOP_VIEW_LIST, 0, count - 1);
    }

    private long setBrandsTopViewedListIntoCache(List<String> ids) {
        return redisService.setList(Constants.REDIS_BRAND_TOP_VIEW_LIST, ids, 3600);
    }

    private void deleteBrandFromCached(Long id) {
        redisService.delete(Constants.REDIS_BRAND + ":" + id);
    }

    private Brand getBrandFromCached(Long brandId) {
        String key = Constants.REDIS_BRAND + ":" + brandId;
        Map<String, String> map = redisService.getMap(key);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            Brand brand = objectMapper.convertValue(map, Brand.class);

            brand.setHitCounter(getBrandHitCounter(brand.getId()));
            brand.setScore(getBrandScoreFromCached(brand.getId()));

            return brand;
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to Brand: {}", e.getMessage());
            return null;
        }
    }

    private void setBrandIntoCached(Brand brand) {
        if (Objects.isNull(brand)) {
            return;
        }

        try {
            Map<String, String> brandMap = objectMapper.convertValue(brand, new TypeReference<Map<String, String>>() {
            });

            redisService.setMap(Constants.REDIS_BRAND + ":" + brand.getId(), brandMap);

            // setBrandHitCounterIntoCached(brand.getId(), brand.getHitCounter());
            setBrandScoreIntoCached(brand.getId(), brand.getScore());
        } catch (Exception e) {
            log.error("Error serializing brand to cache: {}", e.getMessage());
        }
    }

    private void setBrandScoreIntoCached(Long id, int score) {
        redisService.set(Constants.REDIS_BRAND_RATE + ":" + id, String.valueOf(score));
    }
}