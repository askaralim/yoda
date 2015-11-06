package com.yoda.brand.persistence;

import java.util.List;

import com.yoda.brand.model.Brand;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface BrandMapper extends BaseMapper<Brand> {
	List<Brand> getBrands();
}