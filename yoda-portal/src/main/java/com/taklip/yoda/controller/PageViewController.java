package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.PageViewData;
import com.taklip.yoda.service.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author askar
 */
@Controller
public class PageViewController {
    @Autowired
    PageViewService pageViewService;

    @GetMapping("/controlpanel/pageview")
    public String showPageViews(Map<String, Object> model, @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        PageInfo<PageViewData> page = pageViewService.getPageViewDatas(offset * 10, 10);

        model.put("page", page);

        return "controlpanel/pageview/list";
    }

    @GetMapping("/controlpanel/pageview/{id}")
    public ModelAndView viewComment(@PathVariable("id") int id) {
        PageViewData pageView = pageViewService.getPageViewData(id);

        return new ModelAndView("controlpanel/pageview/view", "pageView", pageView);
    }
}