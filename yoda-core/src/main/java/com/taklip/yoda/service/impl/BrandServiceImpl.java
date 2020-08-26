package com.taklip.yoda.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.BrandMapper;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author askar
 */
@Service
public class BrandServiceImpl implements BrandService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    ImageUploader imageUpload;

    @Autowired
    private IdService idService;

    @Autowired
    FileService fileService;

    @Override
    public Brand save(Brand brand) {
        if (null == brand.getId()) {
            return this.add(brand);
        } else {
            return this.update(brand);
        }
    }

    @Override
    public Brand add(Brand brand) {
        brand.setId(idService.generateId());

        brand.preInsert();

        brandMapper.insert(brand);

//		new BrandIndexer().createIndex(brand);

        this.setBrandIntoCached(brand);

        return brand;
    }

    @Override
    public Brand update(Brand brand) {
        Brand brandDb = brandMapper.getById(brand.getId());

        brandDb.setCountry(brand.getCountry());
        brandDb.setCompany(brand.getCompany());
        brandDb.setDescription(brand.getDescription());
        brandDb.setFoundDate(brand.getFoundDate());
        brandDb.setName(brand.getName());
        brandDb.setKind(brand.getKind());
        brandDb.setHitCounter(brand.getHitCounter());

        brandDb.preUpdate();

        brandMapper.update(brandDb);

//		new BrandIndexer().updateIndex(brandDb);

        deleteBrandFromCached(brandDb.getId());

        return brandDb;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> getBrands() {
        return brandMapper.getBrands();
    }

    @Override
    public List<Brand> getBrandsTopViewed(int count) {
        List<String> ids = getBrandsTopViewedListFromCache(count);
        List<Brand> brands = new ArrayList<>();

        if (CollectionUtils.isEmpty(ids)) {
            brands = brandMapper.getBrandsTopViewed(count);

            ids = new ArrayList<>();

            for (Brand brand : brands) {
                ids.add(String.valueOf(brand.getId()));
            }

            this.setBrandsTopViewedListIntoCache(ids);
        } else {
            for (String id : ids) {
                Brand brand = this.getBrand(Long.valueOf(id));

                brands.add(brand);
            }
        }

        return brands;
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Brand> getBrands(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<Brand> brands = brandMapper.getBrands();

        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<Brand> getHotBrands(Integer offset, Integer limit) {
        PageHelper.offsetPage(offset, limit);

        List<Brand> brands = brandMapper.getBrandsOrderByHitCounter();

        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return pageInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Brand getBrand(Long id) {
        Brand brand = getBrandFromCached(id);

        if (null != brand) {
            return brand;
        }

        brand = brandMapper.getById(id);

        setBrandIntoCached(brand);

        return brand;
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandMapper.getById(id);

//		new BrandIndexer().deleteIndex(id);

        brandMapper.delete(brand);
    }

    @Override
    public void increaseBrandHitCounter(Long id) {
        Brand brandDb = brandMapper.getById(id);

        brandDb.setHitCounter(brandDb.getHitCounter() + 1);

        brandMapper.update(brandDb);

        this.setBrandHitCounterIntoCached(id, brandDb.getHitCounter());
    }

    @Override
    public void updateBrandRating(Long id, int rating) {
        Brand brandDb = brandMapper.getById(id);
        brandDb.setScore(rating);
        brandMapper.update(brandDb);
    }

    @Override
    public Brand updateImage(Long id, MultipartFile file) {
        Brand brand = brandMapper.getById(id);

        imageUpload.deleteImage(brand.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.BRAND.getType(), id, file);

            brand.setImagePath(imagePath);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        brand.preUpdate();

        brandMapper.update(brand);

        setBrandIntoCached(brand);

        return brand;
    }

    private List<String> getBrandsTopViewedListFromCache(int count) {
        List<String> ids = redisService.getList(Constants.REDIS_BRAND_TOP_VIEW_LIST, 0, count - 1);

        return ids;
    }

    private long setBrandsTopViewedListIntoCache(List<String> ids) {
        long result = redisService.setList(Constants.REDIS_BRAND_TOP_VIEW_LIST, ids, 3600);

        return result;
    }

    private void deleteBrandFromCached(Long id) {
        redisService.delete(Constants.REDIS_BRAND + ":" + id);
    }

    private Brand getBrandFromCached(Long brandId) {
        Brand brand = null;

        String key = Constants.REDIS_BRAND + ":" + brandId;

        Map<String, String> map = redisService.getMap(key);

        if (null != map && map.size() > 0) {
            brand = new Brand();

            String id = redisService.getMap(key, "id");
            String company = redisService.getMap(key, "company");
            String country = redisService.getMap(key, "country");
            String foundedDate = redisService.getMap(key, "foundedDate");
            String description = redisService.getMap(key, "description");
            String imagePath = redisService.getMap(key, "imagePath");
            String kind = redisService.getMap(key, "kind");
            String name = redisService.getMap(key, "name");
            String createBy = redisService.getMap(key, "createBy");
            String createDate = redisService.getMap(key, "createDate");
            String updateBy = redisService.getMap(key, "updateBy");
            String updateDate = redisService.getMap(key, "updateDate");

            brand.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
            brand.setCompany(StringUtils.isNoneBlank(company) && !"nil".equalsIgnoreCase(company) ? company : null);
            brand.setCountry(StringUtils.isNoneBlank(country) && !"nil".equalsIgnoreCase(country) ? country : null);
            brand.setFoundDate(StringUtils.isNoneBlank(foundedDate) && !"nil".equalsIgnoreCase(foundedDate) ? DateUtil.getDate(foundedDate) : null);
            brand.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
            brand.setImagePath(StringUtils.isNoneBlank(imagePath) && !"nil".equalsIgnoreCase(imagePath) ? imagePath : null);
            brand.setKind(StringUtils.isNoneBlank(kind) && !"nil".equalsIgnoreCase(kind) ? kind : null);
            brand.setName(StringUtils.isNoneBlank(name) && !"nil".equalsIgnoreCase(name) ? name : null);

            if (StringUtils.isNoneBlank(createBy) && !"nil".equalsIgnoreCase(createBy)) {
                User user = new User();
                user.setId(Long.valueOf(createBy));
                brand.setCreateBy(user);
            }

            brand.setCreateDate(StringUtils.isNoneBlank(createDate) && !"nil".equalsIgnoreCase(createDate) ? DateUtil.getDate(createDate) : null);

            if (StringUtils.isNoneBlank(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
                User user = new User();
                user.setId(Long.valueOf(updateBy));
                brand.setUpdateBy(user);
            }

            brand.setUpdateDate(StringUtils.isNoneBlank(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? DateUtil.getDate(updateDate) : null);

            brand.setHitCounter(getBrandHitCounter(brand.getId()));
            brand.setScore(getBrandScoreFromCached(brand.getId()));
        }

        return brand;
    }

    private void setBrandIntoCached(Brand brand) {
        Map<String, String> value = new HashMap<>();

        value.put("description", brand.getDescription());
        value.put("company", null != brand.getCompany() ? brand.getCompany() : StringPool.BLANK);
        value.put("country", brand.getCountry());
        value.put("imagePath", brand.getImagePath());
        value.put("kind", brand.getKind());
        value.put("name", brand.getName());
        value.put("id", String.valueOf(brand.getId()));
        value.put("foundedDate", null != brand.getFoundDate() ? DateUtil.getDate(brand.getFoundDate()) : StringPool.BLANK);
        value.put("updateBy", String.valueOf(brand.getUpdateBy().getId()));
        value.put("updateDate", DateUtil.getDate(brand.getUpdateDate()));
        value.put("createBy", String.valueOf(brand.getCreateBy().getId()));
        value.put("createDate", DateUtil.getDate(brand.getCreateDate()));

        redisService.setMap(Constants.REDIS_BRAND + ":" + brand.getId(), value);

        setBrandHitCounterIntoCached(brand.getId(), brand.getHitCounter());
        setBrandScoreIntoCached(brand.getId(), brand.getScore());
    }

    @Override
    public int getBrandHitCounter(Long id) {
        String hit = redisService.get(Constants.REDIS_BRAND_HIT_COUNTER + ":" + id);

        if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
            return Integer.valueOf(hit);
        } else {
            Brand brand = brandMapper.getById(id);

            setBrandHitCounterIntoCached(brand.getId(), brand.getHitCounter());

            return brand.getHitCounter();
        }
    }

    private void setBrandHitCounterIntoCached(Long id, int hitCounter) {
        redisService.set(Constants.REDIS_BRAND_HIT_COUNTER + ":" + id, String.valueOf(hitCounter));
    }

    private int getBrandScoreFromCached(Long id) {
        String score = redisService.get(Constants.REDIS_BRAND_RATE + ":" + id);

        if (StringUtils.isNoneBlank(score) && !"nil".equalsIgnoreCase(score)) {
            return Integer.valueOf(score);
        } else {
            Brand brand = brandMapper.getById(id);

            setBrandScoreIntoCached(brand.getId(), brand.getScore());

            return brand.getScore();
        }
    }

    private void setBrandScoreIntoCached(Long id, int score) {
        redisService.set(Constants.REDIS_BRAND_RATE + ":" + id, String.valueOf(score));
    }
}