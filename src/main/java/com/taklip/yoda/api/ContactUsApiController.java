package com.taklip.yoda.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.model.ContactUs;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContactUsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/v1/contactus")
@Slf4j
public class ContactUsApiController extends PortalBaseController {
    @Autowired
    private ContactUsService contactUsService;

    @GetMapping
    public ModelAndView showContactUs(
            HttpServletRequest request, HttpServletResponse response) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        List<ContactUs> contactUsList = contactUsService.getContactUs(site.getId(), true);

        setUserLoginStatus(model);

        model.put("contactUsList", contactUsList);
        model.put("site", site);
        model.put("pageTitle", site.getSiteName() + " - " + "Contact Us");
        model.put("keywords", "");
        model.put("description", "");
        model.put("url", request.getRequestURL().toString());
        model.put("image", "");

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        model.put("currentUser", currentUser);

        CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            model.put("_csrf", csrfToken);
        }

        return new ModelAndView("portal/contactUs/contactUs", model);
    }
}