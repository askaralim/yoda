package com.yoda.content.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.model.Comment;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;

@Controller
public class CommentController {
	@Autowired
	ContentService contentService;

	@RequestMapping(value="/controlpanel/comments", method = RequestMethod.GET)
	public String showComments(
		Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Site site = PortalUtil.getSiteFromSession(request);

		List<Comment> comments = contentService.getCommentsBySiteId(site.getSiteId().intValue());

		model.put("comments", comments);

		return "controlpanel/content/comments";
	}

	@RequestMapping(value = "/controlpanel/comments/{id}", method = RequestMethod.GET)
	public ModelAndView viewComment(@PathVariable("id") int id) {
		Comment comment = contentService.getComment(id);

		return new ModelAndView(
			"controlpanel/content/comment", "comment", comment);
	}

	@RequestMapping(value = "/comments/new", method = RequestMethod.POST)
	public String addComment(HttpServletRequest request, @ModelAttribute Comment comment) {
		comment.setSiteId(PortalUtil.getSiteFromSession(request).getSiteId().intValue());
		comment.setCreateDate(new Date());
		comment.setUser(PortalUtil.getAuthenticatedUser());

		contentService.addComment(comment);

		return "redirect:/content/" + comment.getContent().getContentId();
	}

	@RequestMapping(value = "/controlpanel/comments/remove")
	public String removeComments(
			@RequestParam("ids") String ids,
			HttpServletRequest request) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			contentService.deleteComment(Integer.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/comments";
	}
}
