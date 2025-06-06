package com.taklip.yoda.controller;

import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.support.PageViewHandler;

import lombok.extern.slf4j.Slf4j;

import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.PortalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

@Slf4j
public class PortalBaseController {
    @Autowired
    protected UserService userService;

    @Autowired
    protected SiteService siteService;

    @Autowired
    protected PageViewHandler pageViewHandler;

    public void setUserLoginStatus(ModelMap model) {
        User loginUser = AuthenticatedUtil.getAuthenticatedUser();

        if (loginUser != null) {
            model.put("userLogin", true);
            model.put("userId", loginUser.getId());
            model.put("username", loginUser.getUsername());

            if (PortalUtil.isAdminRole(loginUser)) {
                model.put("roleAdmin", true);
            }
        }

        // csrf is disabled
        // CsrfToken csrfToken =
        // (CsrfToken)request.getAttribute(CsrfToken.class.getName());

        // if (csrfToken != null) {
        // model.put("_csrf", csrfToken);
        // }
    }

    public Site getSite() {
        return siteService.getSites().get(0);
    }
}