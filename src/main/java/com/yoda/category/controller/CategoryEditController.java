package com.yoda.category.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.category.CategoryValidator;
import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;

@Controller
public class CategoryEditController {
	@Autowired
	CategoryService categoryService;

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
}