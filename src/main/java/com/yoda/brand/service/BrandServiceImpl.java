package com.yoda.brand.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.dao.BrandDAO;
import com.yoda.brand.model.Brand;
import com.yoda.kernal.util.FileUploader;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {
	@Autowired
	BrandDAO brandDAO;

	public void addBrand(Brand brand) {
		User user = PortalUtil.getAuthenticatedUser();

		brand.setCreateBy(user.getUserId());
		brand.setCreateDate(new Date());
		brand.setUpdateBy(user.getUserId());
		brand.setUpdateDate(new Date());

		brandDAO.save(brand);
	}

	@Transactional(readOnly = true)
	public List<Brand> getBrands() {
		return brandDAO.getAll();
	}

	@Transactional(readOnly = true)
	public Brand getBrand(int id) {
		return brandDAO.getById(id);
	}

	public void deleteBrand(Integer brandId) {
		Brand brand = brandDAO.getById(brandId);

		brandDAO.delete(brand);
	}

	public Brand update(Brand brand) {
		brand.setUpdateBy(PortalUtil.getAuthenticatedUser().getUserId());
		brand.setUpdateDate(new Date());

		brandDAO.update(brand);

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

		FileUploader fileUpload = FileUploader.getInstance();

		fileUpload.deleteFile(brand.getImagePath());

		String imagePath = fileUpload.saveFile(file);

		brand.setImagePath(imagePath);

		update(brand);

		return brand;
	}
}