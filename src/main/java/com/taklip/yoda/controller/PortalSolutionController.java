package com.taklip.yoda.controller;

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
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SolutionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/solution")
@Slf4j
public class PortalSolutionController extends PortalBaseController {
    @Autowired
    SolutionService solutionService;

    @GetMapping
    public ModelAndView showSolutions(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "3") Integer limit) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        Page<Solution> page = solutionService.getSolutions(offset, limit);

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();
        setUserLoginStatus(model);

        model.put("currentUser", currentUser);
        model.put("site", site);
        model.put("page", page);

        return new ModelAndView("portal/solution/solutions", model);
    }

    @ResponseBody
    @GetMapping("/page")
    public Page<Solution> showPagination(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "3") Integer limit) {
        Page<Solution> page = solutionService.getSolutions(offset, limit);

        return page;
    }

    @GetMapping("/{id}")
    public ModelAndView showSolution(
            @PathVariable Long id, HttpServletRequest request) {
        Site site = getSite();

        SolutionDTO solution = solutionService.getSolutionDetail(id);

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