package com.taklip.yoda.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.taklip.yoda.validator.BrandValidator;

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

	@GetMapping(value = "/{brandId}/edit")
	public ModelAndView initUpdateForm(
			@PathVariable("brandId") Long brandId) {
		Brand brand = brandService.getBrand(brandId);

		return new ModelAndView("controlpanel/brand/form", "brand", brand);
	}

	@PostMapping("/save")
	public ModelAndView save(
			@ModelAttribute("brand") Brand brand, BindingResult result) {
		new BrandValidator().validate(brand, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");
			return new ModelAndView("controlpanel/brand/form", model);
		}

//		Brand brandDb = brandService.save(brand);

		brandService.save(brand);

		model.put("brand", brand);
		model.put("success", "success");

		return new ModelAndView("redirect:/controlpanel/brand/" + brand.getBrandId() + "/edit", model);
	}

	@RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
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

	@RequestMapping(value="/controlpanel/brand/remove")
	public String removeBrands(@RequestParam("brandIds") String brandIds) {
		String[] arrIds = brandIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			brandService.deleteBrand(Long.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/brand";
	}
}