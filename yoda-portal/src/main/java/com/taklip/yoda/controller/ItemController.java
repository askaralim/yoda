package com.taklip.yoda.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.util.ExtraFieldUtil;
import com.taklip.yoda.util.SiteUtil;
import com.taklip.yoda.validator.ItemValidator;

@Controller
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

	@RequestMapping(value="/controlpanel/item", method = RequestMethod.GET)
	public String showItems(
		Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Site site = SiteUtil.getDefaultSite();

		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtils.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Item> page = itemService.getItems(site.getSiteId(), new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/item/list";
	}

	@RequestMapping(value = "/controlpanel/item/new", method = RequestMethod.GET)
	public ModelAndView initCreationForm(
			Map<String, Object> model, HttpServletRequest request) {
		Site site = SiteUtil.getDefaultSite();

		List<Content> contents = contentService.getContents(site.getSiteId());

		List<Category> categories = categoryService.getCategories();

		Item item = new Item();

		List<Brand> brands = brandService.getBrands();

		model.put("item", item);
		model.put("brands", brands);
		model.put("categories", categories);
		model.put("contents", contents);
		model.put("extraFields", ExtraFieldUtil.getExtraFields(item));
		model.put("buyLinks", ExtraFieldUtil.getBuyLinks(item));

		return new ModelAndView("controlpanel/item/form", model);
	}

	@RequestMapping(value = "/controlpanel/item/new", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("item") Item item, 
			@RequestParam("brandId") Integer brandId, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		new ItemValidator().validate(item, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/item/form", model);
		}
		else {
			Site site = SiteUtil.getDefaultSite();

			if (null != brandId) {
				Brand brand = brandService.getBrand(brandId);

				item.setBrand(brand);
			}

			ExtraFieldUtil.setExtraFields(request, item);
			//ExtraFieldUtil.setBuyLinks(request, item);

			item.setHitCounter(0);
			item.setSiteId(site.getSiteId());

			itemService.save(item);

			status.setComplete();

			return new ModelAndView("redirect:/controlpanel/item/" + item.getId() + "/edit", model);
		}
	}

	@RequestMapping(value = "/controlpanel/item/{itemId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(
			@PathVariable("itemId") int itemId, Map<String, Object> model,
			HttpServletRequest request) {
		Site site = SiteUtil.getDefaultSite();

		Item item = itemService.getItem(itemId);

		List<Brand> brands = brandService.getBrands();

		List<Content> contents = contentService.getContents(site.getSiteId());

		List<Category> categories = categoryService.getCategories();

		model.put("item", item);
		model.put("brands", brands);
		model.put("categories", categories);
		model.put("contents", contents);
		model.put("extraFields", ExtraFieldUtil.getExtraFields(item));
		model.put("buyLinks", ExtraFieldUtil.getBuyLinks(item));

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
			Brand brand = null;

			if (null != brandId) {
				brand = brandService.getBrand(brandId);
			}

			item.setBrand(brand);

			ExtraFieldUtil.setExtraFields(request, item);
			ExtraFieldUtil.setBuyLinks(request, item);

			Item itemDb = itemService.update(item);

			List<Brand> brands = brandService.getBrands();

			Site site = SiteUtil.getDefaultSite();

			List<Content> contents = contentService.getContents(site.getSiteId());

			List<Category> categories = categoryService.getCategories();

			model.put("item", itemDb);
			model.put("brands", brands);
			model.put("categories", categories);
			model.put("contents", contents);
			model.put("extraFields", ExtraFieldUtil.getExtraFields(item));
			model.put("buyLinks", ExtraFieldUtil.getBuyLinks(item));
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

		if (StringUtils.isBlank(file.getName())) {
			return "redirect:/controlpanel/item/" + id + "/edit";
		}

//		String savedPath = new FileUploader().saveFile(file);

		itemService.updateItemImage(id, file);

		return "redirect:/controlpanel/item/" + id + "/edit";
	}

	@ResponseBody
	@RequestMapping(value="/item/{id}/rating" ,method = RequestMethod.POST)
	public String score(
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

		jsonResult.put("rating", rating);

		return jsonResult.toString();
	}

	@RequestMapping(value="/controlpanel/item/remove")
	public String removeItems(@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			itemService.remove(Integer.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/item";
	}
}