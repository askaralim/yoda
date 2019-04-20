package com.taklip.yoda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.validator.CategoryValidator;

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

	@RequestMapping(value = "/controlpanel/category/new", method = RequestMethod.GET)
	public ModelAndView initCreationForm(Map<String, Object> model) {
		Category category = new Category();

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("category", category);

		return new ModelAndView("controlpanel/category/form", model);
	}

	@RequestMapping(value = "/controlpanel/category/new", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("category") Category category, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		new CategoryValidator().validate(category, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/category/form", model);
		}
		else {
			this.categoryService.addCategory(category);

			status.setComplete();

			return new ModelAndView("redirect:/controlpanel/category/" + category.getCategoryId() + "/edit", model);
		}
	}

	@RequestMapping(value = "/controlpanel/category/{id}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("id") Integer id, Map<String, Object> model) {
		Category category = categoryService.getCategory(id);

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("category", category);

		return "controlpanel/category/form";
	}

	@RequestMapping(value = "/controlpanel/category/{id}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("category") Category category,
			BindingResult result, SessionStatus status) {
		new CategoryValidator().validate(category, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/category/form", model);
		}
		else {
			Category categoryDb = categoryService.update(
				category.getCategoryId(), category.getName(), category.getDescription(),
				category.getParent());

			List<Category> categories = categoryService.getCategories();

			model.put("categories", categories);
			model.put("category", categoryDb);
			model.put("success", "success");

			status.setComplete();

			return new ModelAndView("controlpanel/category/form", model);
		}
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