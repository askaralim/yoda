package com.yoda.brand.controller;

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

import com.yoda.brand.BrandValidator;
import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;

@Controller
public class BrandAddController {
	@Autowired
	BrandService brandService;

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
}