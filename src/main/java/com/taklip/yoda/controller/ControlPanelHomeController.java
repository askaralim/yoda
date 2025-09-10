package com.taklip.yoda.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.DateUtil;
import com.taklip.yoda.common.util.PortalUtil;
import com.taklip.yoda.common.util.Validator;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.vo.ControlPanelHomeCommand;
import com.taklip.yoda.vo.SystemStatistics;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/controlpanel/home")
public class ControlPanelHomeController {
    @Autowired
    SiteService siteService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ModelAndView showPanel(
            HttpServletRequest request,HttpServletResponse response){
        User user = AuthenticatedUtil.getAuthenticatedUser();

        Site site = siteService.getSite(user.getLastVisitSiteId());

        ControlPanelHomeCommand homePageCommand = new ControlPanelHomeCommand();

        try {
            initInfo(homePageCommand, request, site, user);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        homePageCommand.setUserId(user.getId());
        homePageCommand.setUserName(user.getUsername());
        homePageCommand.setEmail(user.getEmail());
        homePageCommand.setAddressLine1(user.getAddressLine1());
        homePageCommand.setAddressLine2(user.getAddressLine2());
        homePageCommand.setCityName(user.getCityName());
        homePageCommand.setPassword("");
        homePageCommand.setSiteId(site.getId());
        homePageCommand.setSiteName(site.getSiteName());
        homePageCommand.setStateName(user.getStateName());
        homePageCommand.setCountryName(user.getCountryName());
        homePageCommand.setZipCode(user.getZipCode());
        homePageCommand.setPhone(user.getPhone());
        homePageCommand.setTabName("password");

        ModelAndView mav = new ModelAndView(
            "controlpanel/home/home", "homePageCommand", homePageCommand);

        return mav;
    }

    @PostMapping
    public ModelAndView processSubmit(
            @ModelAttribute ControlPanelHomeCommand homePageCommand,
            BindingResult result, SessionStatus status,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        User user = AuthenticatedUtil.getAuthenticatedUser();

        Site site = siteService.getSite(user.getLastVisitSiteId());

        try {
            initInfo(homePageCommand, request, site, user);

            homePageCommand.setSiteName(site.getSiteName());

            String cmd = homePageCommand.getCmd();

            if (cmd.equals("password")) {
                password(homePageCommand, result);
            }
            else if (cmd.equals("update")) {
                update(homePageCommand, result);
            }
            else if (cmd.equals("switchSite")) {
                switchSite(request, homePageCommand, user, result);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (result.hasErrors()) {
            mav.addObject("homePageCommand", homePageCommand);

            mav.setViewName("controlpanel/home/home");

            return mav;
        }

        status.setComplete();

        mav.addObject("homePageCommand", homePageCommand);
        mav.addObject("success", "success");

        mav.setViewName("controlpanel/home/home");

        return mav;
    }

    public void initInfo(
            ControlPanelHomeCommand homePageCommand,
            HttpServletRequest httpServletRequest, Site userSite, User user)
        throws Exception {

        Date userLastLoginDatetime = user.getLastLoginDate();

        if (userLastLoginDatetime != null) {
            homePageCommand.setLastLoginDatetime(
                DateUtil.getFullDatetime(userLastLoginDatetime));
        }

        SystemStatistics statistics = new SystemStatistics();

        homePageCommand.setServerStats(statistics.getServerStats());
        homePageCommand.setThreadStats(statistics.getThreadStats());
        homePageCommand.setJvmStats(statistics.getJvmStats());


        List<Site> sites = new ArrayList<Site>();

        Iterator iterator = null;

        if (PortalUtil.isAdminRole(user)) {
            iterator = siteService.getSites().iterator();

            while (iterator.hasNext()) {
                Site site = (Site)iterator.next();
                sites.add(site);
            }

            homePageCommand.setSites(sites);
        }
        else {
            // iterator = user.getSites().iterator();

            // while (iterator.hasNext()) {
            //     Site site = (Site)iterator.next();
            //     sites.add(site);
            // }

            // homePageCommand.setSites(sites);
        }
    }

    public void password(
            ControlPanelHomeCommand homePageCommand, BindingResult result) {

        if (StringUtils.isBlank(homePageCommand.getPassword())) {
            result.rejectValue("password", "password-required");
        }
        else if (StringUtils.isNotBlank(homePageCommand.getPassword())) {
            if (!homePageCommand.getPassword().equals(homePageCommand.getVerifyPassword())) {
                result.rejectValue("password", "password-not-match");
            }

            if (Validator.isValidPassword(homePageCommand.getPassword())) {
                result.rejectValue("password", "invalid-password");
            }
        }

        if (result.hasErrors()) {
            homePageCommand.setTabName("password");

            return;
        }

        User user = userService.getUser(homePageCommand.getUserId());

        String hashedPassword = passwordEncoder.encode(homePageCommand.getPassword());

        user.setPassword(hashedPassword);

        userService.updateById(user);
    }

    public void switchSite(
            HttpServletRequest request, ControlPanelHomeCommand homePageCommand,
            User user, BindingResult result)
        throws Exception {

        long siteId = homePageCommand.getSiteId();

        Site site = null;

        if (user.getLastVisitSiteId() == siteId) {
            return;
        }

        // if (!PortalUtil.isAdminRole(user)) {
        //     Iterator iterator = user.getSites().iterator();

        //     boolean found = false;

        //     while (iterator.hasNext()) {
        //         site = (Site)iterator.next();

        //         if (site.getSiteId() == homePageCommand.getSiteId()) {
        //             found = true;

        //             break;
        //         }
        //     }

        //     if (!found) {
        //         logger.equals(
        //             "Security violated - unable to switch site: userId = "
        //             + user.getId() + ", siteId = " + siteId);

        //         throw new Exception(
        //                 "Unable to switch site: userId = " + user.getId()
        //                 + ", siteId = " + siteId);
        //     }

        // }

        user.setLastVisitSiteId(siteId);

        userService.updateById(user);

        try {
            initInfo(homePageCommand, request, site, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(
            ControlPanelHomeCommand homePageCommand, BindingResult result) {

        if (StringUtils.isBlank(homePageCommand.getUserName())) {
            result.rejectValue("userName", "error.string.required");
        }

        if (result.hasErrors()) {
            homePageCommand.setTabName("profile");
            return;
        }

//		State state = stateService.getState(
//			homePageCommand.getSiteId(),
//			homePageCommand.getStateCode());
//
//		Country country = countryService.getCountry(
//			homePageCommand.getSiteId(),
//			homePageCommand.getCountryCode());

        User user = userService.getUser(homePageCommand.getUserId());

        user.setUsername(homePageCommand.getUserName());
        user.setEmail(homePageCommand.getEmail());
        user.setPhone(homePageCommand.getPhone());
        user.setAddressLine1(homePageCommand.getAddressLine1());
        user.setAddressLine2(homePageCommand.getAddressLine2());
        user.setCityName(homePageCommand.getCityName());
//		user.setStateName(state.getStateName());
//		user.setCountryName(country.getCountryName());
        user.setZipCode(homePageCommand.getZipCode());
        // user.setUpdateBy(user);
        // user.setUpdateTime(LocalDateTime.now());

        userService.updateById(user);
    }
}