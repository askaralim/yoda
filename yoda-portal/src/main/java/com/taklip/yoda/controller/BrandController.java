package com.taklip.yoda.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.service.BrandService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/controlpanel/brand")
public class BrandController {
	@Autowired
	BrandService brandService;

	@GetMapping
	public String showBrands(
			Map<String, Object> model,
			@RequestParam(name = "offset", required = false) String offset) {
		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offsetInt, 10));

		model.put("page", page);
		return "controlpanel/brand/list";
	}

	@GetMapping("/add")
	public String initCreationForm(@ModelAttribute Brand brand) {
		return "controlpanel/brand/form";
	}

	@GetMapping(value = "/{id}/edit")
	public String initUpdateForm(
			@PathVariable("id") Long id, Map<String, Object> model) {
		Brand brand = brandService.getBrand(id);

		model.put("brand", brand);

		return"controlpanel/brand/form";
	}

	@PostMapping("/save")
	public ModelAndView save(
			@Valid Brand brand, BindingResult result, RedirectAttributes redirect) {
		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");
			return new ModelAndView("controlpanel/brand/form", model);
		}

		brandService.save(brand);

		redirect.addFlashAttribute("globalMessage", "success");

		return new ModelAndView("redirect:/controlpanel/brand/" + brand.getId() + "/edit", model);
	}

	@PostMapping("/{id}/uploadImage")
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") Long id)
		throws Exception {
		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/brand/" + id + "/edit";
		}

		if (StringUtils.isBlank(file.getName())) {
			return "redirect:/controlpanel/brand/" + id + "/edit";
		}

//		String savedPath = new FileUploader().saveFile(file);

		brandService.updateImage(id, file);

		return "redirect:/controlpanel/brand/" + id + "/edit";
	}

	@GetMapping("/controlpanel/brand/remove")
	public String removeBrands(@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			brandService.deleteBrand(Long.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/brand";
	}
}