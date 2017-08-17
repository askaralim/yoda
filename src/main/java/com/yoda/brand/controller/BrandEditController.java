package com.yoda.brand.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.BrandValidator;
import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.util.Format;

@Controller
public class BrandEditController {
	@Autowired
	BrandService brandService;

	@RequestMapping(value = "/controlpanel/brand/{brandId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("brandId") int brandId, Map<String, Object> model) {
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
			Brand brandDb = brandService.update(
				brand.getBrandId(), brand.getCompany(), brand.getCountry(), 
				brand.getDescription(), brand.getFoundDate(), brand.getKind(),
				brand.getName());

			model.put("brand", brandDb);
			model.put("success", "success");

			status.setComplete();

			return new ModelAndView("controlpanel/brand/edit", model);
		}
	}

	@RequestMapping(value = "/controlpanel/brand/{id}/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response)
		throws Throwable {
		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/brand/" + id + "/edit";
		}

		if (Format.isNullOrEmpty(file.getName())) {
			return "redirect:/controlpanel/brand/" + id + "/edit";
		}

//		String savedPath = new FileUploader().saveFile(file);

		brandService.updateImage(id, file);

		return "redirect:/controlpanel/brand/" + id + "/edit";
	}
}