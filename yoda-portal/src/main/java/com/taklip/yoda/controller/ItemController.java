package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.*;
import com.taklip.yoda.util.ExtraFieldUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/controlpanel/item")
public class ItemController {
	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	SiteService siteService;

	@Autowired
	BrandService brandService;

	@Autowired
	ContentService contentService;

	@Autowired
	CategoryService categoryService;

	@GetMapping
	public String showItems(
		Map<String, Object> model,
		@RequestParam(name = "offset", required = false) String offset) {
		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Item> page = itemService.getItems(new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/item/list";
	}

	@GetMapping("/add")
	public ModelAndView initCreationForm(Map<String, Object> model) {
		Item item = new Item();

		populageForm(model, item);

		return new ModelAndView("controlpanel/item/form", model);
	}

	@GetMapping("/{id}/edit")
	public String initUpdateForm(
			@PathVariable("id") Long id, Map<String, Object> model) {
		Item item = itemService.getItem(id);

		populageForm(model, item);

		return "controlpanel/item/form";
	}

	@PostMapping("/save")
	public ModelAndView save(
			@Valid Item item, @RequestParam("brandId") Long brandId,
			BindingResult result, RedirectAttributes redirect,
			HttpServletRequest request) {

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");
			return new ModelAndView("controlpanel/item/form", model);
		}

		Brand brand = null;

		if (null != brandId) {
			brand = brandService.getBrand(brandId);
		}

		item.setBrand(brand);

		ExtraFieldUtil.setExtraFields(request, item);
		ExtraFieldUtil.setBuyLinks(request, item);

		itemService.save(item);

//		Item itemDb = itemService.update(item);

		redirect.addFlashAttribute("globalMessage", "success");

		return new ModelAndView("redirect:/controlpanel/item/" + item.getId() + "/edit", model);
	}

	@PostMapping("/{id}/uploadImage")
	public String uploadImage(
			@RequestParam("file") MultipartFile file, @PathVariable("id") Long id)
		throws Throwable {
		if (file.getBytes().length <= 0) {
			return "redirect:/controlpanel/item/" + id + "/edit";
		}

		if (StringUtils.isBlank(file.getName())) {
			return "redirect:/controlpanel/item/" + id + "/edit";
		}

//		String savedPath = new FileUploader().saveFile(file);

		itemService.updateItemImage(id, file);

		return "redirect:/controlpanel/item/" + id + "/edit";
	}

	@ResponseBody
	@PostMapping("/item/{id}/rating")
	public String score(
			@PathVariable("id") Long id, @RequestParam("thumb") String thumb) {
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

		jsonResult.put("rating", rating);

		return jsonResult.toString();
	}

	@RequestMapping("/remove")
	public String removeItems(@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			itemService.remove(Long.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/item";
	}

	private void populageForm(Map<String, Object> model, Item item) {
		List<Content> contents = contentService.getContents();
		List<Category> categories = categoryService.getCategories();
		List<Brand> brands = brandService.getBrands();

		model.put("item", item);
		model.put("brands", brands);
		model.put("categories", categories);
		model.put("contents", contents);
		model.put("extraFields", ExtraFieldUtil.getExtraFields(item));
		model.put("buyLinks", ExtraFieldUtil.getBuyLinks(item));
	}
}