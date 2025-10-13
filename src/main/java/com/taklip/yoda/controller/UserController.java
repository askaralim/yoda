package com.taklip.yoda.controller;

import com.taklip.yoda.model.User;
import com.taklip.yoda.model.UserAuthority;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.vo.UserSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    SiteService siteService;

    @GetMapping
    public String showUsers(Map<String, Object> model) {
        List<User> users = userService.getUsers();

        model.put("users", users);
        model.put("searchForm", new UserSearchForm());

        return "controlpanel/user/list";
    }

    @GetMapping("/add")
    public String setupForm(Map<String, Object> model) {
        User user = new User();

        user.setEnabled(true);
        // user.getAuthorities().add(new UserAuthority(user.getId(), "ROLE_USER"));

        model.put("user", user);

        return "controlpanel/user/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            @ModelAttribute User user, @RequestParam MultipartFile photo,
            BindingResult result,
            RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        // String role = request.getParameter("userRole");

        // if (StringUtils.isBlank(role)) {
        // role = StringPool.BLANK;
        // }

        if (null == user.getId()) {
            userService.save(user);
        } else {
            // userService.update(user, photo);
        }

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/user/" + user.getId() + "/edit", model);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView setupForm(@PathVariable long id) {
        User user = userService.getUser(id);

        return new ModelAndView("controlpanel/user/form", "user", user);
    }

    @GetMapping("/search")
    public String search(
            @ModelAttribute UserSearchForm form, Map<String, Object> model) {
        List<User> users = userService.search(
                form.getUserId(), form.getUsername(), form.getRole(),
                form.getEnabled());

        model.put("users", users);
        model.put("searchForm", form);

        return "controlpanel/user/list";
    }

    @GetMapping("/remove")
    public String removeUsers(
            @RequestParam String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            userService.delete(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/user/list";
    }
}