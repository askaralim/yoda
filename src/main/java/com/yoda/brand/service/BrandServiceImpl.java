package com.yoda.brand.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.brand.persistence.BrandMapper;
import com.yoda.item.model.Item;
import com.yoda.kernal.elasticsearch.BrandIndexer;
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.util.ImageUploader;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandMapper brandMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void addBrand(Brand brand) {
		brand.preInsert();

		brandMapper.insert(brand);

		new BrandIndexer().createIndex(brand);
	}

	@Transactional(readOnly = true)
	public List<Brand> getBrands() {
		return brandMapper.getBrands();
	}

	public List<Brand> getBrandsTopViewed(int count) {
		return brandMapper.getBrandsTopViewed(count);
	}

	@Transactional(readOnly = true)
	public Pagination<Brand> getBrands(RowBounds rowBounds) {
		List<Brand> brands = sqlSessionTemplate.selectList("com.yoda.brand.persistence.BrandMapper.getBrands", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.yoda.brand.persistence.BrandMapper.count");

		Pagination<Brand> page = new Pagination<Brand>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), brands);

		return page;
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
			int brandId, String company, String country, String description,
			Date foundedDate, String kind, String name) {
		Brand brand = this.getBrand(brandId);

		brand.setCountry(country);
		brand.setCompany(company);
		brand.setDescription(description);
		brand.setFoundDate(foundedDate);
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