package com.yoda.content.persistence;

import java.util.List;

import com.yoda.content.model.ContentBrand;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ContentBrandMapper extends BaseMapper<ContentBrand> {
	List<ContentBrand> getByBrandId(Long brandId);
}