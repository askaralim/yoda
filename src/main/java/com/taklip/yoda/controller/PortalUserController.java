package com.taklip.yoda.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.Validator;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.Post;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.PostService;
import com.taklip.yoda.service.UserFollowRelationService;
import com.taklip.yoda.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/user")
public class PortalUserController extends PortalBaseController {
    private final Logger logger = LoggerFactory.getLogger(PortalUserController.class);

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserFollowRelationService userFollowRelationService;

    @Autowired
    protected PostService postService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @GetMapping("/{id}")
    public ModelAndView setupForm(
            @PathVariable long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        User user = userService.getUser(id);

        if (null == user) {
            return new ModelAndView("/404", "requestURL", id);
        }

        Page<Post> page = postService.getPostsByUser(id, 0, 10);
        List<Content> contents = contentService.getContentByUserId(user.getId());
        Long followerCount = userFollowRelationService.getUserFollowerCount(user.getId());
        Long followeeCount = userFollowRelationService.getUserFolloweeCount(user.getId());

        model.put("user", user);
        model.put("contents", contents);
        model.put("page", page);
        model.put("followerCount", followerCount);
        model.put("followeeCount", followeeCount);

        setUserLoginStatus(model);

        model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
        model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
        model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
        model.put("url", request.getRequestURL().toString());
        model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
        model.put("site", site);

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        model.put("currentUser", currentUser);

        if (currentUser != null) {
            Boolean isFollowing = userFollowRelationService.isFollowing(currentUser.getId(), user.getId());
            model.put("isFollowing", isFollowing);
        }

        return new ModelAndView("portal/user/profile", model);
    }

    @GetMapping("/settings")
    public ModelAndView setupForm(HttpServletRequest request) {
        Site site = getSite();

        ModelMap model = new ModelMap();

        User user = AuthenticatedUtil.getAuthenticatedUser();

        if (null == user) {
            return new ModelAndView("redirect:/login", model);
        }

        model.put("user", user);
        model.put("tab", "basic");

        setUserLoginStatus(model);

        model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
        model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
        model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
        model.put("url", request.getRequestURL().toString());
        model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
        model.put("site", site);

        return new ModelAndView("portal/user/settings", model);
    }

    @PostMapping("/settings")
    public ModelAndView update(
            @ModelAttribute User user,
            @RequestParam MultipartFile photo) {
        ModelMap model = new ModelMap();

        Site site = getSite();

        setUserLoginStatus(model);

        model.put("site", site);

        User userDb = userService.update(user, photo);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDb, userDb.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.put("success", "success");
        model.put("user", userDb);

        return new ModelAndView("portal/user/settings", model);
    }

    @GetMapping("/register")
    public String setupForm(@ModelAttribute User user) {
        return "portal/user/register";
    }

    @PostMapping("/register")
    public ModelAndView submit(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
        ModelAndView model = new ModelAndView();

        User userDb = userService.getUserByUsername(username);

        if (null != userDb) {
            model.addObject("error", "duplicate-username");

            model.setViewName("portal/user/register");

            return model;
        }

        if (!Validator.isEmailAddress(email)) {
            model.addObject("error", "invalid-email");

            model.setViewName("portal/user/register");

            return model;
        }

        userDb = userService.getUserByEmail(email);

        if (null != userDb) {
            model.addObject("error", "duplicate-email");

            model.setViewName("portal/user/register");

            return model;
        }

        try {
            User user = new User();
            user.setPassword(password);
            user.setUsername(username);
            user.setEmail(email);
            userService.save(user);
        } catch (Exception e) {
            logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email
                    + e.getMessage());
        }

        try {
            UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email, password);

            Authentication result = authenticationManager.authenticate(authResult);

            // redirect into secured main page if authentication successful
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
                model.setViewName("redirect:/");

                return model;
            }
        } catch (Exception e) {
            logger.debug("Problem authenticating user" + username, e);
        }

        model.setViewName("redirect:/");

        return model;
    }

    @ResponseBody
    @PostMapping("/register/ajax")
    public String ajaxRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {
        User userDb = userService.getUserByUsername(username);

        RequestContext requestContext = new RequestContext(request);

        JSONObject jsonResult = new JSONObject();

        try {
            if (null != userDb) {
                jsonResult.put("error", requestContext.getMessage("duplicate-username"));
            }

            if (!Validator.isEmailAddress(email)) {
                jsonResult.put("error", requestContext.getMessage("invalid-email"));
            }

            userDb = userService.getUserByEmail(email);

            if (null != userDb) {
                jsonResult.put("error", requestContext.getMessage("duplicate-email"));
            }
        } catch (JSONException e) {
            logger.error(e.getMessage());
        }

        if (jsonResult.isEmpty()) {
            try {
                User user = new User();
                user.setPassword(password);
                user.setUsername(username);
                user.setEmail(email);
                userService.save(user);
            } catch (Exception e) {
                logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email
                        + e.getMessage());
            }

            try {
                UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email,
                        password);

                Authentication result = authenticationManager.authenticate(authResult);

                // redirect into secured main page if authentication successful
                if (result.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(result);
                }
            } catch (Exception e) {
                logger.debug("Problem authenticating user" + username, e);
            }
        }

        String jsonString = jsonResult.toString();

        return jsonString;
    }

    @PostMapping("/post/new")
    public String addPost(@ModelAttribute Post post) {

        post.setDescription(HtmlUtils.htmlEscape(post.getDescription()));

        postService.save(post);

        return "redirect:/user/" + post.getUserId();
    }

    @ResponseBody
    @GetMapping("/post/page")
    public String showPagination(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        Page<Post> page = postService.getPostsByUser(userId, offset, limit);

        JSONArray array = new JSONArray();

        SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        for (Post post : page.getRecords()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", post.getId());
            jsonObject.put("description", post.getDescription());
            jsonObject.put("createTime", datetimeformat.format(post.getCreateTime()));

            array.add(jsonObject);
        }

        return array.toString();
    }

    @ResponseBody
    @PostMapping("/follow")
    public ResponseEntity<Object> follow(
            @RequestParam Long userId,
            @RequestParam Long loginUserId) {

        userFollowRelationService.follow(loginUserId, userId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/unfollow")
    public ResponseEntity<Object> unfollow(
            @RequestParam Long userId,
            @RequestParam Long loginUserId) {

        userFollowRelationService.unFollow(loginUserId, userId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}