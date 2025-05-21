package com.taklip.yoda.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.PageView;
import com.taklip.yoda.service.PageViewService;

/**
 * @author askar
 */
@Controller
public class PageViewController {
    @Autowired
    PageViewService pageViewService;

    @GetMapping("/controlpanel/pageview")
    public String showPageViews(Map<String, Object> model,
            @RequestParam(defaultValue = "0") Integer offset) {
        Page<PageView> page = pageViewService.getPageViews(offset * 10, 10);

        model.put("page", page);

        return "controlpanel/pageview/list";
    }

    @GetMapping("/controlpanel/pageview/{id}")
    public ModelAndView viewComment(@PathVariable int id) {
        PageView pageView = pageViewService.getPageView(id);

        return new ModelAndView("controlpanel/pageview/view", "pageView", pageView);
    }
}