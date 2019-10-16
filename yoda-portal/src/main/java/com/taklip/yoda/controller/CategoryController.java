package com.taklip.yoda.controller;

import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/controlpanel/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping
	public ModelAndView showCategories() {

		List<Category> categories = categoryService.getCategories();

		return new ModelAndView(
			"controlpanel/category/list", "categories", categories);
	}

	@GetMapping("/add")
	public ModelAndView initCreationForm(Map<String, Object> model) {
		Category category = new Category();

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("category", category);

		return new ModelAndView("controlpanel/category/form", model);
	}

	@GetMapping("/{id}/edit")
	public String initUpdateForm(@PathVariable("id") Long id, Map<String, Object> model) {
		Category category = categoryService.getCategory(id);

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("category", category);

		return "controlpanel/category/form";
	}

	@PostMapping("/save")
	public ModelAndView save(
			@Valid Category category, BindingResult result, RedirectAttributes redirect) {
		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");
			return new ModelAndView("controlpanel/category/form", model);
		}

		this.categoryService.save(category);

		redirect.addFlashAttribute("globalMessage", "success");

		return new ModelAndView("redirect:/controlpanel/category/" + category.getId() + "/edit", model);
	}

	@RequestMapping(value = "/controlpanel/category/remove", method = RequestMethod.GET)
	public String removeCategories(
			@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			Category category = categoryService.getCategory(Long.valueOf(arrIds[i]));

			categoryService.deleteCategory(category);
		}

		return "redirect:/controlpanel/category";
	}
}