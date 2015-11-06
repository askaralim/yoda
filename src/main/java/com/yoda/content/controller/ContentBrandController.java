package com.yoda.content.controller;

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

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.content.model.ContentBrand;
import com.yoda.content.service.ContentService;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

@Controller
public class ContentBrandController {
	@Autowired
	ContentService contentService;

	@Autowired
	BrandService brandService;

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/add", method = RequestMethod.GET)
	public ModelAndView initCreationForm(
			@PathVariable("contentId") long contentId, Map<String, Object> model) {
		ContentBrand contentBrand = new ContentBrand();

		contentBrand.setContentId(contentId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return new ModelAndView("controlpanel/content/editContentBrand", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/add", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("contentBrand") ContentBrand contentBrand,
			@RequestParam("brandId") Integer brandId, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		ModelMap model = new ModelMap();

		String brandName = StringPool.BLANK;

		Brand brand = null;

		if (Validator.isNotNull(brandId)) {
			brand = brandService.getBrand(brandId);
			brandName = brand.getName();
		}

		contentBrand.setBrandName(brandName);

		contentService.addContentBrand(contentBrand);

		return new ModelAndView("redirect:/controlpanel/content/" + contentBrand.getContentId() + "/brand/" + contentBrand.getContentBrandId() + "/edit", model);
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/{contentBrandId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("contentBrandId") int contentBrandId, Map<String, Object> model) {
		ContentBrand contentBrand = contentService.getContentBrand(contentBrandId);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);

		return "controlpanel/content/editContentBrand";
	}

	@RequestMapping(value = "/controlpanel/content/{contentId}/brand/{contentBrandId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("contentBrand") ContentBrand contentBrand,
			@RequestParam("brandId") Integer brandId,
			BindingResult result, SessionStatus status) {
		ModelMap model = new ModelMap();

		String brandName = StringPool.BLANK;
		Brand brand = null;

		if (Validator.isNotNull(brandId)) {
			brand = brandService.getBrand(brandId);
			brandName = brand.getName();
		}

		contentBrand.setBrandName(brandName);

		contentService.updateContentBrand(contentBrand);

		List<Brand> brands = brandService.getBrands();

		model.put("contentBrand", contentBrand);
		model.put("brands", brands);
		model.put("success", "success");

		status.setComplete();

		return new ModelAndView("controlpanel/content/editContentBrand", model);
	}
}