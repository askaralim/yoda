package com.taklip.yoda.mapper;

import com.taklip.yoda.model.Brand;

import java.util.List;

/**
 * @author askar
 */
public interface BrandMapper extends BaseMapper<Brand> {
    List<Brand> getBrands();

    List<Brand> getBrandsOrderByHitCounter();

    List<Brand> getBrandsTopViewed(int count);
}