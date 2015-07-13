package com.yoda.brand.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;

@Controller
public class BrandController {
	@Autowired
	BrandService brandService;

	@RequestMapping(value = "/controlpanel/brand", method = RequestMethod.GET)
	public ModelAndView showBrands() {

		List<Brand> brands = new ArrayList<Brand>();

		brands = brandService.getBrands();

		return new ModelAndView(
			"controlpanel/brand/list", "brands", brands);
	}

	@RequestMapping(value = "/controlpanel/brand/{brandId}", method = RequestMethod.GET)
	public ModelAndView viewBrand(@PathVariable("brandId") int brandId) {
		Brand brand = brandService.getBrand(brandId);

		return new ModelAndView(
			"controlpanel/brand/view", "brand", brand);
	}

	@RequestMapping(value="/controlpanel/brand/remove")
	public String removeBrands(@RequestParam("brandIds") String brandIds) {
		String[] arrIds = brandIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			brandService.deleteBrand(Integer.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/brand";
	}
}