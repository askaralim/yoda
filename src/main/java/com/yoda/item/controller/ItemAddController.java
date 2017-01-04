package com.yoda.item.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.item.ItemValidator;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.util.Validator;

@Controller
public class ItemAddController {
	@Autowired
	ItemService itemService;

	@Autowired
	ContentService contentService;

	@Autowired
	BrandService brandService;

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/controlpanel/item/new", method = RequestMethod.GET)
	public ModelAndView initCreationForm(
			Map<String, Object> model,
			HttpServletRequest request) {
		Site site = PortalUtil.getSiteFromSession(request);

		List<Content> contents = contentService.getContents(site.getSiteId());

		List<Category> categories = categoryService.getCategories();

		Item item = new Item();

		List<Brand> brands = brandService.getBrands();

		model.put("item", item);
		model.put("brands", brands);
		model.put("categories", categories);
		model.put("contents", contents);

		return new ModelAndView("controlpanel/item/form", model);
	}

	@RequestMapping(value = "/controlpanel/item/new", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("item") Item item, 
			@RequestParam("brandId") Integer brandId, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		new ItemValidator().validate(item, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/item/form", model);
		}
		else {
			Site site = PortalUtil.getSiteFromSession(request);

			if (Validator.isNotNull(brandId)) {
				Brand brand = brandService.getBrand(brandId);

				item.setBrand(brand);
			}

			itemService.save(site.getSiteId(), item);

			status.setComplete();

			return new ModelAndView("redirect:/controlpanel/item/" + item.getId() + "/edit", model);
		}
	}
}