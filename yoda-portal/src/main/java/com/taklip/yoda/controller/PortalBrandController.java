package com.taklip.yoda.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.util.AuthenticatedUtil;

@Controller
@RequestMapping("/brand")
public class PortalBrandController extends PortalBaseController {
	private final Logger logger = LoggerFactory.getLogger(PortalBrandController.class);

	@Autowired
	BrandService brandService;

	@Autowired
	ItemService itemService;

	@Autowired
	ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showBrands(
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite();

		String offsetStr = request.getParameter("offset");
		int offset = 0;

		if (null != offsetStr) {
			offset = Integer.valueOf(offsetStr);
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offset, 20));

		setUserLoginStatus(model);

		model.put("site", site);
		model.put("page", page);

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("portal/brand/brands", model);
	}

	@ResponseBody
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public String showPagination(
			@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Brand> page = brandService.getBrands(new RowBounds(offset, 20));

		JSONArray array = new JSONArray();

		try {
			for (Brand brand : page.getData()) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", brand.getId());
				jsonObject.put("imagePath", brand.getImagePath());

				array.add(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

		return array.toString();
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView showBrand(
			@PathVariable("id") Long id, HttpServletRequest request) {
		Site site = getSite();

		Brand brand = brandService.getBrand(id);

		List<Item> items = itemService.getItemsByBrandId(id);

		ModelMap model = new ModelMap();

		setUserLoginStatus(model);

		model.put("site", site);
		model.put("brand", brand);
		model.put("items", items);

		model.put("pageTitle", "【" + brand.getName() + "】" + brand.getName() + "品牌介绍" + " - " + site.getSiteName());
		model.put("keywords", brand.getName() + "," + brand.getName() + "品牌介绍" + "," + brand.getName() + "是什么牌子");
		model.put("description", "全方位介绍" + brand.getName() + "是什么牌子，及相应推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + brand.getImagePath());

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		pageViewHandler.add(request, ContentTypeEnum.BRAND.getType(), brand.getName(), brand.getId());

		return new ModelAndView("portal/brand/brand", model);
	}
}