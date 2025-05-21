package com.taklip.yoda.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.CommentService;
import com.taklip.yoda.common.util.SiteUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/controlpanel/comment")
    public String showComments(
            Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Site site = SiteUtil.getDefaultSite();

        List<Comment> comments = commentService.getCommentsBySiteId(site.getId());

        model.put("comments", comments);

        return "controlpanel/content/comments";
    }

    @GetMapping("/controlpanel/comment/{id}")
    public ModelAndView viewComment(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);

        return new ModelAndView(
                "controlpanel/content/comment", "comment", comment);
    }

    @PostMapping("/controlpanel/comment/remove")
    public String removeComments(@RequestParam String ids) {
        String[] arrIds = ids.split(",");
        commentService.removeByIds(Arrays.asList(arrIds));

        return "redirect:/controlpanel/comment";
    }
}