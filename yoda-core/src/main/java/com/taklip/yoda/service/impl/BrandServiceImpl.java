package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.elasticsearch.BrandIndexer;
import com.taklip.yoda.mapper.BrandMapper;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.DateUtil;

@Transactional
@Service
public class BrandServiceImpl implements BrandService {
	private static Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Autowired
	BrandMapper brandMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private RedisService redisService;

	@Autowired
	ImageUploader imageUpload;

	public void addBrand(Brand brand) {
		brand.preInsert();

		brandMapper.insert(brand);

		new BrandIndexer().createIndex(brand);

		this.setBrandIntoCached(brand);
	}

	@Transactional(readOnly = true)
	public List<Brand> getBrands() {
		return brandMapper.getBrands();
	}

	public List<Brand> getBrandsTopViewed(int count) {
		List<String> brandsIds = getBrandsTopViewedListFromCache(count);
		List<Brand> brands = new ArrayList<>();

		if (null == brandsIds || brandsIds.isEmpty()) {
			brands = brandMapper.getBrandsTopViewed(count);

			brandsIds = new ArrayList<>();

			for (Brand brand : brands) {
				brandsIds.add(String.valueOf(brand.getBrandId()));
			}

			this.setBrandsTopViewedListIntoCache(brandsIds);
		}
		else {
			for (String brandId : brandsIds) {
				Brand brand = this.getBrand(Integer.valueOf(brandId));

				brands.add(brand);
			}
		}

		return brands;
	}

	@Transactional(readOnly = true)
	public Pagination<Brand> getBrands(RowBounds rowBounds) {
		List<Brand> brands = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.BrandMapper.getBrands", null, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.BrandMapper.count");

		Pagination<Brand> page = new Pagination<Brand>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), brands);

		return page;
	}

	@Transactional(readOnly = true)
	public Brand getBrand(int id) {
		Brand brand = getBrandFromCached(id);

		if (null != brand) {
			return brand;
		}

		brand = brandMapper.getById(id);

		setBrandIntoCached(brand);

		return brand;
	}

	public void deleteBrand(Integer brandId) {
		Brand brand = brandMapper.getById(brandId);

		new BrandIndexer().deleteIndex(brandId);

		brandMapper.delete(brand);
	}

	public Brand update(Brand brand) {
		Brand brandDb = brandMapper.getById(brand.getBrandId());

		brandDb.setCountry(brand.getCountry());
		brandDb.setCompany(brand.getCompany());
		brandDb.setDescription(brand.getDescription());
		brandDb.setFoundDate(brand.getFoundDate());
		brandDb.setName(brand.getName());
		brandDb.setKind(brand.getKind());
		brandDb.setHitCounter(brand.getHitCounter());
		brandDb.setImagePath(brand.getImagePath());

		brandDb.preUpdate();

		brandMapper.update(brandDb);

		new BrandIndexer().updateIndex(brand);

		return brand;
	}

	public void updateBrandHitCounter(int id, int hitCounter) {
		Brand brandDb = brandMapper.getById(id);
		brandDb.setHitCounter(hitCounter);
		brandMapper.update(brandDb);
	}

	public void updateBrandRating(int id, int rating) {
		Brand brandDb = brandMapper.getById(id);
		brandDb.setScore(rating);
		brandMapper.update(brandDb);
	}

	public Brand updateImage(int id, MultipartFile file) {
		Brand brand = brandMapper.getById(id);

		imageUpload.deleteImage(brand.getImagePath());

		try {
			String imagePath = imageUpload.uploadBrandImage(file.getInputStream(), file.getOriginalFilename());

			brand.setImagePath(imagePath);
		}
		catch (IOException e) {
			logger.warn(e.getMessage());
		}

		brand.preUpdate();

		brandMapper.update(brand);

		return brand;
	}

	private List<String> getBrandsTopViewedListFromCache(int count) {
		List<String> ids = redisService.getList(Constants.REDIS_BRAND_TOP_VIEW_LIST, 0, count - 1);

		return ids;
	}

	private long setBrandsTopViewedListIntoCache(List<String> ids) {
		long result = redisService.setList(Constants.REDIS_BRAND_TOP_VIEW_LIST, ids, 3600);

		return result;
	}

	private Brand getBrandFromCached(int brandId) {
		Brand brand = null;

		String key = Constants.REDIS_BRAND + ":" + brandId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() >0) {
			brand = new Brand();

			String id = redisService.getMap(key, "brandId");
			String company = redisService.getMap(key, "company");
			String country = redisService.getMap(key, "country");
			String foundedDate = redisService.getMap(key, "foundedDate");
			String description = redisService.getMap(key, "description");
			String imagePath = redisService.getMap(key, "imagePath");
			String kind = redisService.getMap(key, "kind");
			String name = redisService.getMap(key, "name");
			String createBy = redisService.getMap(key, "createBy");
			String createDate = redisService.getMap(key, "createDate");
			String updateBy = redisService.getMap(key, "updateBy");
			String updateDate = redisService.getMap(key, "updateDate");

			brand.setBrandId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Integer.valueOf(id) : null);
			brand.setCompany(StringUtils.isNoneBlank(company) && !"nil".equalsIgnoreCase(company) ? company : null);
			brand.setCountry(StringUtils.isNoneBlank(country) && !"nil".equalsIgnoreCase(country) ? country : null);
			brand.setFoundDate(StringUtils.isNoneBlank(foundedDate) && !"nil".equalsIgnoreCase(foundedDate) ? DateUtil.getDate(foundedDate) : null);
			brand.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
			brand.setImagePath(StringUtils.isNoneBlank(imagePath) && !"nil".equalsIgnoreCase(imagePath) ? imagePath : null);
			brand.setKind(StringUtils.isNoneBlank(kind) && !"nil".equalsIgnoreCase(kind) ? kind : null);
			brand.setName(StringUtils.isNoneBlank(name) && !"nil".equalsIgnoreCase(name) ? name : null);

			if (StringUtils.isNoneBlank(createBy) && !"nil".equalsIgnoreCase(createBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(createBy));
				brand.setCreateBy(user);
			}

			brand.setCreateDate(StringUtils.isNoneBlank(createDate) && !"nil".equalsIgnoreCase(createDate) ? DateUtil.getDate(createDate) : null);

			if (StringUtils.isNoneBlank(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(updateBy));
				brand.setUpdateBy(user);
			}

			brand.setUpdateDate(StringUtils.isNoneBlank(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? DateUtil.getDate(updateDate) : null);

			brand.setHitCounter(getBrandHitCounterFromCached(brand.getBrandId()));
			brand.setScore(getBrandScoreFromCached(brand.getBrandId()));
		}

		return brand;
	}

	private void setBrandIntoCached(Brand brand) {
		Map<String, String> value = new HashMap<>();

		value.put("description", brand.getDescription());
		value.put("company", null != brand.getCompany() ? brand.getCompany() : StringPool.BLANK);
		value.put("country", brand.getCountry());
		value.put("imagePath", brand.getImagePath());
		value.put("kind", brand.getKind());
		value.put("name", brand.getName());
		value.put("brandId", String.valueOf(brand.getBrandId()));
		value.put("foundedDate", null != brand.getFoundDate() ? DateUtil.getDate(brand.getFoundDate()) : StringPool.BLANK);
		value.put("updateBy", String.valueOf(brand.getUpdateBy().getUserId()));
		value.put("updateDate", DateUtil.getDate(brand.getUpdateDate()));
		value.put("createBy", String.valueOf(brand.getCreateBy().getUserId()));
		value.put("createDate", DateUtil.getDate(brand.getCreateDate()));

		redisService.setMap(Constants.REDIS_BRAND + ":" + brand.getBrandId(), value);

		setBrandHitCounterIntoCached(brand.getBrandId(), brand.getHitCounter());
		setBrandScoreIntoCached(brand.getBrandId(), brand.getScore());
	}

	private int getBrandHitCounterFromCached(int brandId) {
		String hit = redisService.get(Constants.REDIS_BRAND_HIT_COUNTER + ":" + brandId);

		if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
			return Integer.valueOf(hit);
		}
		else {
			Brand brand = brandMapper.getById(brandId);

			setBrandHitCounterIntoCached(brand.getBrandId(), brand.getHitCounter());

			return brand.getHitCounter();
		}
	}

	private void setBrandHitCounterIntoCached(int brandId, int hitCounter) {
		redisService.set(Constants.REDIS_BRAND_HIT_COUNTER + ":" + brandId, String.valueOf(hitCounter));
	}

	private int getBrandScoreFromCached(int brandId) {
		String score = redisService.get(Constants.REDIS_BRAND_RATE + ":" + brandId);

		if (StringUtils.isNoneBlank(score) && !"nil".equalsIgnoreCase(score)) {
			return Integer.valueOf(score);
		}
		else {
			Brand brand = brandMapper.getById(brandId);

			setBrandScoreIntoCached(brand.getBrandId(), brand.getScore());

			return brand.getScore();
		}
	}

	private void setBrandScoreIntoCached(int brandId, int score) {
		redisService.set(Constants.REDIS_BRAND_RATE + ":" + brandId, String.valueOf(score));
	}
}