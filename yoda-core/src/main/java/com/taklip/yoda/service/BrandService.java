package com.taklip.yoda.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;

public interface BrandService {
	Brand save(Brand brand);

	Brand add(Brand brand);

	List<Brand> getBrands();

	List<Brand> getBrandsTopViewed(int count);

	Brand getBrand(Long id);

	Brand update(Brand brand);

	Brand updateImage(Long id, MultipartFile file);

	int getBrandHitCounter(Long id);

	void increaseBrandHitCounter(Long id);

	void updateBrandRating(Long id, int rating);

	void deleteBrand(Long id);

	Pagination<Brand> getBrands(RowBounds rowBounds);
}