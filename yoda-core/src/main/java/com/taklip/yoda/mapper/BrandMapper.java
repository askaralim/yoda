package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Brand;

public interface BrandMapper extends BaseMapper<Brand> {
	List<Brand> getBrands();

	List<Brand> getBrandsTopViewed(int count);
}