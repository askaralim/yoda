package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.ContentBrand;

public interface ContentBrandMapper extends BaseMapper<ContentBrand> {
	List<ContentBrand> getByBrandId(Long brandId);
}