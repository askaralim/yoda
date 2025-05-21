package com.taklip.yoda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.common.util.AuthenticatedUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Controller
@RequestMapping("/brand")
@Slf4j
public class PortalBrandController extends PortalBaseController {
    @Autowired
    BrandService brandService;

    @Autowired
    ItemService itemService;

    @GetMapping
    public ModelAndView showBrands(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer limit) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        Page<Brand> page = brandService.getHotBrands(offset, limit);

        setUserLoginStatus(model);

        model.put("site", site);
        model.put("page", page);

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        model.put("currentUser", currentUser);

        return new ModelAndView("portal/brand/brands", model);
    }

    @ResponseBody
    @GetMapping("/page")
    public Page<Brand> showPagination(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "20") Integer limit) {
        Page<Brand> page = brandService.getHotBrands(offset, limit);

        return page;
    }

    @GetMapping("/{id}")
    public ModelAndView showBrand(
            @PathVariable Long id, HttpServletRequest request) {
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