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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.validator.BrandValidator;

@Controller
public class BrandController {
	@Autowired
	BrandService brandService;

	@RequestMapping(value = "/controlpanel/brand", method = RequestMethod.GET)
	public String showBrands(
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/brand/list";
	}

	@RequestMapping(value = "/controlpanel/brand/{brandId}", method = RequestMethod.GET)
	public ModelAndView viewBrand(@PathVariable("brandId") Long brandId) {
		Brand brand = brandService.getBrand(brandId);

		return new ModelAndView(
			"controlpanel/brand/view", "brand", brand);
	}

	@RequestMapping(value = "/controlpanel/brand/new", method = RequestMethod.GET)
	public ModelAndView initCreationForm(Map<String, Object> model) {
		Brand brand = new Brand();

		model.put("brand", brand);

		return new ModelAndView("controlpanel/brand/edit", model);
	}

	@RequestMapping(value = "/controlpanel/brand/new", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("brand") Brand brand, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		new BrandValidator().validate(brand, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/brand/form", model);
		}
		else {
			brand.setHitCounter(0);

			brandService.addBrand(brand);

			status.setComplete();

			return new ModelAndView("redirect:/controlpanel/brand/" + brand.getBrandId() + "/edit", model);
		}
	}

	@RequestMapping(value = "/controlpanel/brand/{brandId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("brandId") Long brandId, Map<String, Object> model) {
		Brand brand = brandService.getBrand(brandId);

		model.put("brand", brand);

		return "controlpanel/brand/edit";
	}

	@RequestMapping(value = "/controlpanel/brand/{brandId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("brand") Brand brand, BindingResult result, SessionStatus status) {
		new BrandValidator().validate(brand, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/brand/edit", model);
		}
		else {
			Brand brandDb = brandService.update(brand);

			model.put("brand", brandDb);
			model.put("success", "success");

			status.setComplete();

			return new ModelAndView("controlpanel/brand/edit", model);
		}
	}

	@RequestMapping(value = "/controlpanel/brand/{id}/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") Long id, HttpServletRequest request,
			HttpServletResponse response)
		throws Throwable {
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