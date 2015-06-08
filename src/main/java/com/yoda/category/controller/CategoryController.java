package com.yoda.category.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/controlpanel/category", method = RequestMethod.GET)
	public ModelAndView showCategories() {

		List<Category> categories = new ArrayList<Category>();

		categories = categoryService.getCategories();

		return new ModelAndView(
			"controlpanel/category/list", "categories", categories);
	}

	@RequestMapping(value = "/controlpanel/category/remove", method = RequestMethod.GET)
	public String removeCategories(
			@RequestParam("categoryIds") String categoryIds,
			HttpServletRequest request) {
		String[] arrIds = categoryIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			Category category = categoryService.getCategory(Integer.valueOf(arrIds[i]));

			categoryService.deleteCategory(category);
		}

		return "redirect:/controlpanel/category";
	}
}