package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.util.AuthenticatedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author askar
 */
@Controller
@RequestMapping("/brand")
public class PortalBrandController extends PortalBaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BrandService brandService;

    @Autowired
    ItemService itemService;

    @Autowired
    ContentService contentService;

    @GetMapping
    public ModelAndView showBrands(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                   @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        PageInfo<Brand> page = brandService.getHotBrands(offset, limit);

        setUserLoginStatus(model);

        model.put("site", site);
        model.put("page", page);

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        model.put("currentUser", currentUser);

        return new ModelAndView("portal/brand/brands", model);
    }

    @ResponseBody
    @GetMapping("/page")
    public PageInfo<Brand> showPagination(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        PageInfo<Brand> page = brandService.getHotBrands(offset, limit);

        return page;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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