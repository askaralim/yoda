package com.yoda.kernal.util;

import java.util.List;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.servlet.ServletContextUtil;

/*
 * 
 * TEST ONLY
 * 
 * */
public class ChangeImagePath {
	public static void setImagePath() {
		BrandService brandService = (BrandService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("brandServiceImpl");

		ContentService contentService = (ContentService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("contentServiceImpl");

		ItemService itemService = (ItemService)WebApplicationContextUtils.getRequiredWebApplicationContext(
			ServletContextUtil.getServletContext()).getBean("itemServiceImpl");

		List<Brand> brands = brandService.getBrands();

		for (Brand brand : brands) {
			if (!brand.getImagePath().startsWith("/yoda")) {
				brand.setImagePath("/yoda" + brand.getImagePath());

				brandService.update(brand);

				System.out.println("Brand [" + brand.getName() + "] - image path set to: " + brand.getImagePath());
			}
		}

		List<Content> contents= contentService.getContents(1);

		for (Content content : contents) {
			if (!content.getFeaturedImage().startsWith("/yoda")) {
				content.setFeaturedImage("/yoda" + content.getFeaturedImage());

				System.out.println("Content [" + content.getTitle() + "] - image path set to: " + content.getFeaturedImage());
			}

			content.setDescription(content.getDescription().replaceAll("/uploads", "/yoda/uploads"));

			contentService.updateContent(content);
		}

		List<Item> items= itemService.getItems(1);

		for (Item item : items) {
			item.setImagePath("/yoda" + item.getImagePath());

			item.setDescription(item.getDescription().replaceAll("/uploads", "/yoda/uploads"));

			itemService.update(item);

			System.out.println("Item [" + item.getName() + "] - image path set to: " + item.getImagePath());
		}
	}
}