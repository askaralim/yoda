package com.taklip.yoda.api;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.*;
import com.taklip.yoda.util.PortalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/home")
public class HomeApiController extends PortalBaseController {
    private final Logger logger = LoggerFactory.getLogger(HomeApiController.class);

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
//			modelMap.put("pageInfo", pageInfo);
            modelMap.put("pageTitle", site.getSiteName() + " | " + "小白的购物懒人包、生活方式的供应商");
            modelMap.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
            modelMap.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
            modelMap.put("url", request.getRequestURL().toString());
            modelMap.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
            modelMap.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return new ModelAndView("portal/home", modelMap);
    }

    public HomeInfo getHome() {
        HomeInfo homeInfo = new HomeInfo();

        List<Content> contents = contentService.getContentsFeatureData();

        for (Content content : contents) {
            if (!PortalUtil.isContentPublished(content)) {
                break;
            }

            homeInfo.setHomePageFeatureData(content);
        }

        List<Content> dataInfos = new ArrayList<>();
        PageInfo<Content> page = contentService.getContentsNotFeatureData(0, 4);

        contents = page.getList();

        for (Content content : contents) {
            if (!PortalUtil.isContentPublished(content)) {
                continue;
            }

            dataInfos.add(content);
        }

        homeInfo.setHomePageDatas(dataInfos);
        homeInfo.setPage(page);

        return homeInfo;
    }
}