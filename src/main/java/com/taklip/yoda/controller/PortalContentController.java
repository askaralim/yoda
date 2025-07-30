package com.taklip.yoda.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.contant.StringPool;
import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.PortalUtil;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentUserRate;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ContentUserRateService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/content")
@Slf4j
public class PortalContentController extends PortalBaseController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentUserRateService contentUserRateService;

    @GetMapping("/{id}")
    public ModelAndView showContent(
            @PathVariable Long id, HttpServletRequest request) {
        Site site = getSite();

        ModelMap model = new ModelMap();
        ContentDTO content = contentService.getContentDetail(id);

        if (null == content || !PortalUtil.isContentPublished(content.getPublished(), content.getPublishDate(),
                content.getExpireDate())) {
            model.put("pageTitle", site.getSiteName() + " - " + "Page not found");
            model.put("keywords", StringPool.BLANK);
            model.put("description", StringPool.BLANK);
            model.put("url", request.getRequestURL().toString());
            model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

            return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
        }

        formatContent(request, model, content);

        setUserLoginStatus(model);

        model.put("site", site);

        pageViewHandler.add(request, ContentTypeEnum.CONTENT.getType(), content.getTitle(), content.getId());

        return new ModelAndView("portal/content", model);
    }

    @GetMapping("/{id}/edit")
    public ModelAndView setupForm(
            @PathVariable String id, HttpServletRequest request) {
        Site site = getSite();

        ContentDTO content;

        try {
            content = contentService.getContentDetail(Long.valueOf(id));
        } catch (Exception e) {
            log.error(e.getMessage());

            return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
        }

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        if ((currentUser == null) || (currentUser.getId() != content.getCreateBy().getId())) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();

        setUserLoginStatus(model);

        model.put("user", currentUser);
        model.put("content", content);
        model.put("site", site);

        return new ModelAndView("portal/user/contentEdit", model);
    }

    @GetMapping("/add")
    public ModelAndView setupForm() {
        ModelMap model = new ModelMap();

        User currentUser = AuthenticatedUtil.getAuthenticatedUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:/login");
        }

        Site site = getSite();

        setUserLoginStatus(model);

        model.put("user", currentUser);
        model.put("site", site);

        Content content = new Content();

        model.put("content", content);

        return new ModelAndView("portal/user/contentEdit", model);
    }

    @PostMapping("/save")
    public ModelAndView submitUpdate(
            @Valid Content content, BindingResult result)
            throws Exception {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");

            return new ModelAndView("portal/user/contentEdit", model);
        }

        contentService.updateContent(content, null);

        model.put("success", "success");

        return new ModelAndView("redirect:/content/" + content.getId() + "/edit", model);
    }

    @ResponseBody
    @PostMapping("/{id}/rate")
    public String score(
            @PathVariable Long id, @RequestParam String thumb) {

        contentUserRateService.create(id, thumb);

        int score = contentUserRateService.getTotalRateByContentId(id);

        JSONObject jsonResult = new JSONObject();

        try {
            jsonResult.put("score", score);
        } catch (JSONException e) {
            log.error("JSON: {}", jsonResult, e);
        }

        return jsonResult.toString();
    }

    @ResponseBody
    @GetMapping("/page")
    public Page<ContentDTO> showPagination(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "4") Integer limit) {
        return contentService.getContentsNotFeatureData(offset, limit);
    }

    public void formatContent(
            HttpServletRequest request, ModelMap model, ContentDTO content) {
        Site site = getSite();

        try {
            // CsrfToken csrfToken =
            // (CsrfToken)request.getAttribute(CsrfToken.class.getName());
            //
            // if (csrfToken != null) {
            // model.put("_csrf", csrfToken);
            // }

            // List<Item> items = itemService.getItemsByContentId(content.getId());
            // List<Comment> comments = getComments(content.getId());

            // for (Item item : items) {
            // shortenItemDescription(item);
            // }

            // String desc = content.getDescription();

            // content.setDescription(desc.replace("img src", "img data-src"));
            // content.setItems(items);

            model.put("content", content);
            model.put("comments", content.getComments());
            model.put("date", new Date());
            model.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));

            User loginUser = AuthenticatedUtil.getAuthenticatedUser();

            if (loginUser != null) {
                ContentUserRate rate = contentUserRateService.getByContentIdAndUserId(content.getId(),
                        loginUser.getId());

                if (rate != null) {
                    if (rate.getScore() == 1) {
                        model.put("isUserLike", true);
                    } else if (rate.getScore() == -1) {
                        model.put("isUserDislike", true);
                    }
                }

                model.put("userLogin", true);
            } else {
                model.put("userLogin", false);
            }

            model.put("pageTitle", "如何选购" + content.getTitle() + " - " + site.getSiteName());
            model.put("keywords",
                    "如何选购" + content.getTitle() + "," + content.getTitle() + "怎么选" + "," + content.getTitle() + "品牌推荐");
            model.put("description", content.getShortDescription());
            model.put("url", request.getRequestURL().toString());
            model.put("image", "http://" + site.getDomainName() + content.getFeaturedImage());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }
}