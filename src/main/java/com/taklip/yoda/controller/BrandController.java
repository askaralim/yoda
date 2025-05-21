package com.taklip.yoda.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Map;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/brand")
public class BrandController {
    @Autowired
    BrandService brandService;

    @GetMapping
    public String showBrands(Map<String, Object> model,
            @RequestParam(defaultValue = "1") Integer offset) {
        Page<Brand> page = brandService.getBrands(offset, 10);

        model.put("page", page);

        return "controlpanel/brand/list";
    }

    @GetMapping("/add")
    public ModelAndView initCreationForm(Map<String, Object> model) {
        Brand brand = new Brand();

        model.put("brand", brand);
        model.put("contentType", "brand");

        return new ModelAndView("controlpanel/brand/form", model);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(
            @PathVariable Long id, Map<String, Object> model) {
        Brand brand = brandService.getBrand(id);

        model.put("contentType", "brand");
        model.put("brand", brand);

        return "controlpanel/brand/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            @ModelAttribute Brand brand, BindingResult result, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");
            return new ModelAndView("controlpanel/brand/form", model);
        }

        if (brand.getId() == null) {
            brandService.save(brand);
        } else {
            brandService.update(brand);
        }

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/brand/" + brand.getId() + "/edit", model);
    }

    @PostMapping("/{id}/uploadImage")
    public String uploadImage(
            @RequestParam MultipartFile file, @PathVariable Long id)
            throws Exception {
        if (file.getBytes().length <= 0) {
            return "redirect:/controlpanel/brand/" + id + "/edit";
        }

        if (StringUtils.isBlank(file.getName())) {
            return "redirect:/controlpanel/brand/" + id + "/edit";
        }

        // String savedPath = new FileUploader().saveFile(file);

        brandService.updateImage(id, file);

        return "redirect:/controlpanel/brand/" + id + "/edit";
    }

    @GetMapping("/controlpanel/brand/remove")
    public String removeBrands(@RequestParam String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            brandService.removeByIds(Arrays.asList(arrIds));
        }

        return "redirect:/controlpanel/brand";
    }
}