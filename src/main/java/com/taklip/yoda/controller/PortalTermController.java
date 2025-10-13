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
import com.taklip.yoda.dto.TermDTO;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.TermService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Controller
@RequestMapping("/term")
@Slf4j
public class PortalTermController extends PortalBaseController {
    // not cool, temporary
    private volatile int termsViewCount = 12_580;
    private volatile int termsEditCount = 526;

    @Autowired
    private TermService termService;

    @GetMapping
    public ModelAndView showTerms(@RequestParam(defaultValue = "0") Integer offset) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        Page<TermDTO> page = termService.getByPage(new Page<>(offset, 4));

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        setUserLoginStatus(model);

        model.put("currentUser", currentUser);
        model.put("termsCount", page.getTotal());

        // not cool, temporary
        termsViewCount += 1;
        model.put("termsEditCount", termsEditCount);
        model.put("termsViewCount", termsViewCount);
        model.put("site", site);
        model.put("page", page);

        return new ModelAndView("portal/term/terms", model);
    }

    @ResponseBody
    @GetMapping("/page")
    public Page<TermDTO> showPagination(@RequestParam(defaultValue = "0") Integer offset) {
        return termService.getByPage(new Page<>(offset, 4));
    }

    @GetMapping("/{id}")
    public ModelAndView showTerm(
            @PathVariable Long id, HttpServletRequest request) {
        Site site = getSite();

        TermDTO termDTO = termService.getTermDetail(id);

        ModelMap model = new ModelMap();

        model.put("site", site);
        model.put("term", termDTO);

        model.put("pageTitle", "【" + termDTO.getTitle() + "】 - " + site.getSiteName());
        model.put("keywords", termDTO.getTitle());
        model.put("description", termDTO.getDescription());
        model.put("url", request.getRequestURL().toString());
        model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

        return new ModelAndView("portal/term/term", model);
    }
}