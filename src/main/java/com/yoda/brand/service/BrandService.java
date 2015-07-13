package com.yoda.brand.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;

public interface BrandService {
	void addBrand(Brand brand);

	List<Brand> getBrands();

	Brand getBrand(int brandId);

	Brand update(int brandId, String country, String description, String kind, String name);

	Brand update(Brand brand);

	Brand updateImage(int brandId, MultipartFile file);

	void deleteBrand(Integer brandId);
}