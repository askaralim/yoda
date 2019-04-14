package com.yoda.portal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.brand.model.Brand;
import com.yoda.brand.service.BrandService;
import com.yoda.content.service.ContentService;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/brand")
public class FrontEndBrandController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontEndBrandController.class);

	@Autowired
	BrandService brandService;

	@Autowired
	ItemService itemService;

	@Autowired
	ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showBrands(
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getSite(request);

		String offsetStr = request.getParameter("offset");
		int offset = 0;

		if (null != offsetStr) {
			offset = Integer.valueOf(offsetStr);
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offset, 20));

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("site", site);
		model.put("page", page);

		User currentUser = PortalUtil.getAuthenticatedUser();

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

				jsonObject.put("brandId", brand.getBrandId());
				jsonObject.put("imagePath", brand.getImagePath());

				array.put(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

		return array.toString();
	}

	@RequestMapping(value="/{brandId}", method = RequestMethod.GET)
	public ModelAndView showBrand(
			@PathVariable("brandId") int brandId,
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getSite(request);

		Brand brand = brandService.getBrand(brandId);

//		brand.setHitCounter(brand.getHitCounter() + 1);
//
//		brandService.update(brand);

		kafkaTemplate.send(Constants.KAFKA_TOPIC_REDIS_INCR, Constants.REDIS_BRAND_HIT_COUNTER, String.valueOf(brandId));

		List<Item> items = itemService.getItemsByBrandId(brandId);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("site", site);
		model.put("brand", brand);
		model.put("items", items);

		model.put("pageTitle", "【" + brand.getName() + "】" + brand.getName() + "品牌介绍" + " - " + site.getSiteName());
		model.put("keywords", brand.getName() + "," + brand.getName() + "品牌介绍" + "," + brand.getName() + "是什么牌子");
		model.put("description", "全方位介绍" + brand.getName() + "是什么牌子，及相应推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + brand.getImagePath());

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		pageView(request, Constants.PAGE_TYPE_BRAND, brand.getBrandId(), brand.getName());
//		PageViewUtil.viewPage(request, Constants.PAGE_TYPE_BRAND, brand.getBrandId(), brand.getName());

		return new ModelAndView("portal/brand/brand", model);
	}
}