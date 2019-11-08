package com.taklip.yoda.api;

import com.taklip.yoda.model.*;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
public class RedisController {
	@Autowired
	private RedisService redisService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ItemService itemService;

	@RequestMapping("/delete/content")
	@ResponseBody
	public Response deleteContents() {
		List<Content> contents = contentService.getContents();

		for (Content content : contents) {
			contentService.deleteContentFromCache(content);
//			redisService.delete(Constants.REDIS_CONTENT + ":" + content.getId());
//
//			List<ContentContributor> cc = content.getContentContributors();
//
//			for (ContentContributor c : cc) {
//				redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getId());
//			}
		}

		return new Response(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	}

	@RequestMapping("/delete/contentBrand")
	@ResponseBody
	public Response contentBrands() {
		List<ContentBrand> list = contentService.getContentBrands();

		for (ContentBrand contentBrand : list) {
			if (StringUtils.isBlank(contentBrand.getBrandLogo())) {
				Brand brand = brandService.getBrand(contentBrand.getBrandId());
				contentBrand.setBrandLogo(brand.getImagePath());
			}
			String logo = contentBrand.getBrandLogo().replace("//", "/");
			contentBrand.setBrandLogo(logo);
			contentService.saveContentBrand(contentBrand);
		}

		return new Response(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	}

	@RequestMapping("/delete/brand")
	@ResponseBody
	public Response deleteBrands() {
		List<Brand> list = brandService.getBrands();

		for (Brand brand : list) {
			redisService.delete(Constants.REDIS_BRAND + ":" + brand.getId());
		}

		return new Response(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	}

	@RequestMapping("/delete/item")
	@ResponseBody
	public Response deleteItems() {
		List<Item> list = itemService.getItems();

		for (Item item : list) {
			redisService.delete(Constants.REDIS_ITEM + ":" + item.getId());
		}

		return new Response(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), null);
	}
}
