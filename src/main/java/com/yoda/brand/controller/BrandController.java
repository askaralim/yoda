package com.yoda.brand.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.kernal.model.Pagination;

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

		if (!StringUtil.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/brand/list";
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