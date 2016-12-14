package com.yoda.brand.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.brand.persistence.BrandMapper;
import com.yoda.kernal.elasticsearch.BrandIndexer;
import com.yoda.kernal.util.ImageUploader;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandMapper brandMapper;

	public void addBrand(Brand brand) {
		brand.preInsert();

		brandMapper.insert(brand);

		new BrandIndexer().createIndex(brand);
	}

	@Transactional(readOnly = true)
	public List<Brand> getBrands() {
		return brandMapper.getBrands();
	}

	@Transactional(readOnly = true)
	public Brand getBrand(int id) {
		return brandMapper.getById(id);
	}

	public void deleteBrand(Integer brandId) {
		Brand brand = brandMapper.getById(brandId);

		new BrandIndexer().deleteIndex(brandId);

		brandMapper.delete(brand);
	}

	public Brand update(Brand brand) {
		brand.preUpdate();

		brandMapper.update(brand);

		new BrandIndexer().updateIndex(brand);

		return brand;
	}

	public Brand update(
			int brandId, String country, String description,
			String kind, String name) {
		Brand brand = this.getBrand(brandId);

		brand.setCountry(country);
		brand.setDescription(description);
		brand.setName(name);
		brand.setKind(kind);

		update(brand);

		return brand;
	}

	public Brand updateImage(int id, MultipartFile file) {
		Brand brand = getBrand(id);


		ImageUploader imageUpload = new ImageUploader();

		imageUpload.deleteImage(brand.getImagePath());

		String imagePath;

		try {
			imagePath = imageUpload.uploadBrandImage(file.getInputStream(), file.getOriginalFilename());

			brand.setImagePath(imagePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		update(brand);

		return brand;
	}
}