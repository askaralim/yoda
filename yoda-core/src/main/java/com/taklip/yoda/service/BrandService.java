package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Brand;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author askar
 */
public interface BrandService {
    Brand save(Brand brand);

    Brand add(Brand brand);

    List<Brand> getBrands();

    List<Brand> getBrandsTopViewed(int count);

    PageInfo<Brand> getHotBrands(Integer offset, Integer limit);

    Brand getBrand(Long id);

    Brand update(Brand brand);

    Brand updateImage(Long id, MultipartFile file);

    int getBrandHitCounter(Long id);

    void increaseBrandHitCounter(Long id);

    void updateBrandRating(Long id, int rating);

    void deleteBrand(Long id);

    PageInfo<Brand> getBrands(Integer offset, Integer limit);
}