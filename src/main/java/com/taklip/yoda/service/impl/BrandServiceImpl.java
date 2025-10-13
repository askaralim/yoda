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
    private ModelConvertor modelConvertor;

    @Override
    public BrandDTO create(Brand brand) {
        this.save(brand);

        return modelConvertor.convertBrandToDTO(brand);
    }

    @Override
    public BrandDTO update(Brand brand) {
        this.updateById(brand);

        return modelConvertor.convertBrandToDTO(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> getBrands() {
        return this.list();
    }

    @Override
    public List<BrandDTO> getBrandsTopViewed(int count) {
        List<Brand> brands = this.list(new LambdaQueryWrapper<Brand>()
                .orderByDesc(Brand::getHitCounter).last("limit " + count));
        return brands.stream().map(modelConvertor::convertBrandToDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> getBrands(Integer offset, Integer limit) {
        log.info("getBrands offset: {}, limit: {}", offset, limit);
        Page<Brand> brandPage = page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getHitCounter));
        Page<BrandDTO> brandDTOPage = new Page<>();
        brandDTOPage.setTotal(brandPage.getTotal());
        brandDTOPage.setCurrent(brandPage.getCurrent());
        brandDTOPage.setSize(brandPage.getSize());
        brandDTOPage.setRecords(brandPage.getRecords().stream()
                .map(modelConvertor::convertBrandToDTO).collect(Collectors.toList()));
        return brandDTOPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> getHotBrands(Integer offset, Integer limit) {
        Page<Brand> brandPage = page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Brand>().orderByDesc(Brand::getHitCounter));
        Page<BrandDTO> brandDTOPage = new Page<>();
        brandDTOPage.setTotal(brandPage.getTotal());
        brandDTOPage.setCurrent(brandPage.getCurrent());
        brandDTOPage.setSize(brandPage.getSize());
        brandDTOPage.setRecords(brandPage.getRecords().stream()
                .map(modelConvertor::convertBrandToDTO).collect(Collectors.toList()));
        return brandDTOPage;
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
    public BrandDTO updateImage(Long id, MultipartFile file) {
        Brand brand = this.getById(id);

        imageUpload.deleteImage(brand.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.BRAND.getType(), id, file);

            brand.setImagePath(imagePath);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }

        this.updateById(brand);

        return modelConvertor.convertBrandToDTO(brand);
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
}
