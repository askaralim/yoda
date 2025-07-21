package com.taklip.yoda.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.HomeInfo;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.HomePageService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.ReleaseService;
import com.taklip.yoda.common.util.PortalUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Controller
@RequestMapping("/")
@Slf4j
public class PortalHomeController extends PortalBaseController {
    @Autowired
    ContentService contentService;

    @Autowired
    BrandService brandService;

    @Autowired
    ItemService itemService;

    @Autowired
    HomePageService homePageService;

    @Autowired
    ReleaseService releaseService;

    @Autowired
    FileService fileService;

    @GetMapping
    public ModelAndView setupForm(HttpServletRequest request) {
        log.info("PortalHomeController setupForm");
        ModelMap modelMap = new ModelMap();

        try {
            Site site = getSite();

            HomeInfo homeInfo = getHome();

            List<Item> items = itemService.getItemsTopViewed(8);
            List<Brand> brands = brandService.getBrandsTopViewed(8);

            modelMap.put("topViewedItems", items);
            modelMap.put("topViewedBrands", brands);
            modelMap.put("homeInfo", homeInfo);

            setUserLoginStatus(modelMap);

            modelMap.put("site", site);
            // modelMap.put("pageInfo", pageInfo);
            modelMap.put("pageTitle", site.getSiteName() + " | " + "小白的购物懒人包、生活方式的供应商");
            modelMap.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
            modelMap.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
            modelMap.put("url", request.getRequestURL().toString());
            modelMap.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
            modelMap.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
        } catch (Exception e) {
            log.error("PortalHomeController setupForm error: {}", e);
        }

        return new ModelAndView("portal/home", modelMap);
    }

    public HomeInfo getHome() {
        HomeInfo homeInfo = new HomeInfo();

        List<ContentDTO> contents = contentService.getContentsFeatureData();

        for (ContentDTO content : contents) {
            if (!PortalUtil.isContentPublished(content.getPublished(), content.getPublishDate(),
                    content.getExpireDate())) {
                break;
            }

            homeInfo.setHomePageFeatureData(content);
        }

        List<ContentDTO> dataInfos = new ArrayList<>();
        Page<ContentDTO> page = contentService.getContentsNotFeatureData(0, 4);

        contents = page.getRecords();

        for (ContentDTO content : contents) {
            if (!PortalUtil.isContentPublished(content.getPublished(), content.getPublishDate(),
                    content.getExpireDate())) {
                continue;
            }

            dataInfos.add(content);
        }

        homeInfo.setHomePageDatas(dataInfos);
        homeInfo.setPage(page);

        return homeInfo;
    }
}