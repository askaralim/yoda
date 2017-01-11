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
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.portal.account.controller.UserProfileController;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;

@Controller
@RequestMapping("/brand")
public class FrontEndBrandController extends BaseFrontendController {
	@Autowired
	BrandService brandService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showBrands(
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		String offsetStr = request.getParameter("offset");
		int offset = 0;

		if (null != offsetStr) {
			offset = Integer.valueOf(offsetStr);
		}

		Pagination<Brand> page = brandService.getBrands(new RowBounds(offset, 20));

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);
		model.put("page", page);

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("/portal/brand/brands", model);
	}

	@ResponseBody
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public String showPagination(
			@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Brand> pages = brandService.getBrands(new RowBounds(offset, 20));

		JSONArray array = new JSONArray();

		try {
			for (Brand brand : pages.getData()) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("brandId", brand.getBrandId());
				jsonObject.put("imagePath", brand.getImagePath());

				array.put(jsonObject);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return array.toString();
	}

	@RequestMapping(value="/{brandId}", method = RequestMethod.GET)
	public ModelAndView showBrand(
			@PathVariable("brandId") int brandId,
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		Brand brand = brandService.getBrand(brandId);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);
		model.put("brand", brand);

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("/portal/brand/brand", model);
	}

	Logger logger = Logger.getLogger(UserProfileController.class);
}
