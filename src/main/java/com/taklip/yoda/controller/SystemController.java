package com.taklip.yoda.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.taklip.yoda.common.contant.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SystemController {
    @GetMapping("/controlpanel/system/edit")
    public String showPanel(
            HttpServletRequest request, HttpServletResponse response) {
        // User user = PortalUtil.getAuthenticatedUser();
        //
        // String loginMessage = AdminLookup.lookUpAdmin(request, response);
        //
        // if (Validator.isNotNull(loginMessage)) {
        // ModelMap modelMap = new ModelMap();
        //
        // modelMap.put("loginMessage", loginMessage);
        //
        // return "controlpanel/system/edit";
        // }

        return "controlpanel/system/edit";
    }

    @PostMapping("/controlpanel/system/edit")
    public String reset(
            HttpServletRequest request, HttpServletResponse response) {
        // String filename =
        // Utility.getServletLocation(this.getServlet().getServletContext()) +
        // Constants.CONFIG_COMPLETED_FILENAME;
        // String filename = ServletContextUtil.getRealPath() +
        // Constants.CONFIG_COMPLETED_FILENAME;
        String filename = request.getServletContext().getRealPath(Constants.CONFIG_COMPLETED_FILENAME);

        File file = new File(filename);

        if (null != file) {
            file.delete();
        }

        return "controlpanel/system/done";
    }
}
