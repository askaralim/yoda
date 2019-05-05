package com.taklip.yoda.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;

public interface BrandService {
	void addBrand(Brand brand);

	List<Brand> getBrands();

	List<Brand> getBrandsTopViewed(int count);

	Brand getBrand(Long brandId);

	Brand update(Brand brand);

	void updateBrandHitCounter(Long id, int hitCounter);

	void updateBrandRating(Long id, int rating);

	Brand updateImage(Long brandId, MultipartFile file);

	void deleteBrand(Long brandId);

	Pagination<Brand> getBrands(RowBounds rowBounds);
}