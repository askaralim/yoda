package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SolutionService;
import com.taklip.yoda.util.AuthenticatedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/solution")
public class PortalSolutionController extends PortalBaseController {
    private final Logger logger = LoggerFactory.getLogger(PortalSolutionController.class);

    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ModelAndView showSolutions(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                      @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        PageInfo<Solution> page = solutionService.getSolutions(offset, limit);

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();
        setUserLoginStatus(model);

        model.put("currentUser", currentUser);
        model.put("site", site);
        model.put("page", page);

        return new ModelAndView("portal/solution/solutions", model);
    }

    @ResponseBody
    @GetMapping("/page")
    public PageInfo<Solution> showPagination(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "3") Integer limit) {
        PageInfo<Solution> page = solutionService.getSolutions(offset, limit);

        return page;
    }

    @GetMapping("/{id}")
    public ModelAndView showSolution(
            @PathVariable("id") Long id, HttpServletRequest request) {
        Site site = getSite();

        Solution solution = solutionService.getSolution(id);

        ModelMap model = new ModelMap();

        model.put("site", site);
        model.put("solution", solution);

        model.put("pageTitle", "【" + solution.getTitle() + "】 - " + site.getSiteName());
        model.put("keywords", solution.getTitle());
        model.put("description", solution.getDescription());
        model.put("url", request.getRequestURL().toString());
        model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

        return new ModelAndView("portal/solution/solution", model);
    }
}