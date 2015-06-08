package com.yoda.category.controller;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.category.CategoryValidator;
import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;

@Controller
public class CategoryAddController {
	@Autowired
	CategoryService categoryService;

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

			return new ModelAndView("redirect:/controlpanel/category/" + category.getId() + "/edit", model);
		}
	}
}