package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.TermService;
import com.taklip.yoda.util.AuthenticatedUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author askar
 */
@Controller
@RequestMapping("/term")
public class PortalTermController extends PortalBaseController {
    private final Logger logger = LoggerFactory.getLogger(PortalTermController.class);

    // not cool, temporary
    private volatile int termsViewCount = 12_580;
    private volatile int termsEditCount = 526;

    @Autowired
    TermService termService;

    @GetMapping
    public ModelAndView showTerms(@RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        PageInfo<Term> page = termService.getTerms(offset, 4);

        for (Term term : page.getList()) {
            shortenTermDescription(term);
        }

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
    public PageInfo<Term> showPagination(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        PageInfo<Term> page = termService.getTerms(offset, 4);

        for (Term term : page.getList()) {
            shortenTermDescription(term);
        }

        return page;
    }

    @GetMapping("/{id}")
    public ModelAndView showTerm(
            @PathVariable("id") Long id, HttpServletRequest request) {
        Site site = getSite();

        Term term = termService.getTerm(id);

        ModelMap model = new ModelMap();

        model.put("site", site);
        model.put("term", term);

        model.put("pageTitle", "【" + term.getTitle() + "】 - " + site.getSiteName());
        model.put("keywords", term.getTitle());
        model.put("description", term.getTitle());
        model.put("url", request.getRequestURL().toString());
        model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

        return new ModelAndView("portal/term/term", model);
    }

    private Term shortenTermDescription(Term term) {
        String desc = term.getDescription();

        if (desc.length() > 200) {
            desc = desc.substring(0, 200);

            if (desc.indexOf("img") > 0) {
                desc = desc.substring(0, desc.indexOf("<img"));
            }

            desc = desc.trim().concat("...");

            term.setDescription(desc);
        }

        return term;
    }
}