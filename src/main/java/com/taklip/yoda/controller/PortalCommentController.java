package com.taklip.yoda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;

import com.taklip.yoda.common.util.AuthenticatedUtil;
import com.taklip.yoda.common.util.SiteUtil;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller(value = "/comment")
public class PortalCommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/new")
    public String addComment(HttpServletRequest request, @ModelAttribute CommentDTO comment) {
        comment.setSiteId(SiteUtil.getDefaultSite().getId());
        comment.setUserId(AuthenticatedUtil.getAuthenticatedUser().getId());
        comment.setDescription(HtmlUtils.htmlEscape(comment.getDescription()));

        commentService.create(comment);

        return "redirect:/content/" + comment.getContentId();
    }
}
