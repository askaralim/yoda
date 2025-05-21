package com.taklip.yoda.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.taklip.yoda.model.Category;
import com.taklip.yoda.service.CategoryService;

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
    public String initUpdateForm(@PathVariable Long id, Map<String, Object> model) {
        Category category = categoryService.getById(id);

        List<Category> categories = categoryService.getCategories();

        model.put("categories", categories);
        model.put("category", category);

        return "controlpanel/category/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            Category category, BindingResult result, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");
            return new ModelAndView("controlpanel/category/form", model);
        }

        if (category.getId() == null) {
            this.categoryService.create(category);
        } else {
            this.categoryService.update(category);
        }

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/category/" + category.getId() + "/edit", model);
    }

    @GetMapping("/controlpanel/category/remove")
    public String removeCategories(@RequestParam String ids) {
        String[] arrIds = ids.split(",");

        categoryService.removeByIds(Arrays.asList(arrIds));

        return "redirect:/controlpanel/category";
    }
}