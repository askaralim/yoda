package com.yoda.item.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
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

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.item.ItemValidator;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.util.Format;
import com.yoda.util.Validator;

@Controller
public class ItemEditController {
	@Autowired
	ItemService itemService;

	@Autowired
	BrandService brandService;

	@Autowired
	ContentService contentService;

	@Autowired
	CategoryService categoryService;

	@RequestMapping(value = "/controlpanel/item/{itemId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(
			@PathVariable("itemId") int itemId, Map<String, Object> model,
			HttpServletRequest request) {
		Site site = PortalUtil.getSiteFromSession(request);

		Item item = itemService.getItem(itemId);

		List<Brand> brands = brandService.getBrands();

		List<Content> contents = contentService.getContents(site.getSiteId());

		List<Category> categories = categoryService.getCategories();

		model.put("item", item);
		model.put("brands", brands);
		model.put("categories", categories);
		model.put("contents", contents);

		return "controlpanel/item/form";
	}

	@RequestMapping(value = "/controlpanel/item/{itemId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("item") Item item,
			@RequestParam("brandId") Integer brandId,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {
		new ItemValidator().validate(item, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/item/form", model);
		}
		else {
//			String brandName = StringPool.BLANK;
			Brand brand = null;

			if (Validator.isNotNull(brandId)) {
				brand = brandService.getBrand(brandId);
//				brandName = brand.getName();
			}

			item.setBrand(brand);

			Item itemDb = itemService.update(item);

			List<Brand> brands = brandService.getBrands();

			Site site = PortalUtil.getSiteFromSession(request);

			List<Content> contents = contentService.getContents(site.getSiteId());

			List<Category> categories = categoryService.getCategories();

			model.put("item", itemDb);
			model.put("brands", brands);
			model.put("categories", categories);
			model.put("contents", contents);
			model.put("success", "success");

			status.setComplete();

			return new ModelAndView("controlpanel/item/form", model);
		}
	}

	@RequestMapping(value = "/controlpanel/item/{id}/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response)
		throws Throwable {
		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/item/" + id + "/edit";
		}

		if (Format.isNullOrEmpty(file.getName())) {
			return "redirect:/controlpanel/item/" + id + "/edit";
		}

//		String savedPath = new FileUploader().saveFile(file);

		itemService.updateItemImage(id, file);

		return "redirect:/controlpanel/item/" + id + "/edit";
	}

	@RequestMapping(value="/item/{id}/rating" ,method = RequestMethod.POST)
	public void score(
			@PathVariable("id") int id,
			@RequestParam("thumb") String thumb,
			HttpServletRequest request, HttpServletResponse response) {
		Item item  = itemService.getItem(id);

		int rating = 0;

		if (thumb.equals("up")) {
			rating = item.getRating() + 1;
		}
		else if (thumb.equals("down")) {
			rating = item.getRating() - 1;
		}

		item.setRating(rating);

		itemService.update(item);

		JSONObject jsonResult = new JSONObject();

		try {
			jsonResult.put("rating", rating);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		String jsonString = jsonResult.toString();

		PrintWriter printWriter = null;

		try {
			printWriter = response.getWriter();
			printWriter.print(jsonString);
		}
		catch (IOException ex) {
		}
		finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}