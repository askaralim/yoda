package com.taklip.yoda.controller;

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
import org.springframework.web.util.HtmlUtils;

import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.SiteUtil;

@Controller
public class CommentController {
	@Autowired
	ContentService contentService;

	@RequestMapping(value="/controlpanel/comment", method = RequestMethod.GET)
	public String showComments(
		Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Site site = SiteUtil.getDefaultSite();

		List<Comment> comments = contentService.getCommentsBySiteId(site.getSiteId().intValue());

		model.put("comments", comments);

		return "controlpanel/content/comments";
	}

	@RequestMapping(value = "/controlpanel/comment/{id}", method = RequestMethod.GET)
	public ModelAndView viewComment(@PathVariable("id") Long id) {
		Comment comment = contentService.getComment(id);

		return new ModelAndView(
			"controlpanel/content/comment", "comment", comment);
	}

	@RequestMapping(value = "/comment/new", method = RequestMethod.POST)
	public String addComment(HttpServletRequest request, @ModelAttribute Comment comment) {
		comment.setSiteId(SiteUtil.getDefaultSite().getSiteId().intValue());
		comment.setCreateDate(new Date());
		comment.setUser(AuthenticatedUtil.getAuthenticatedUser());
		comment.setDescription(HtmlUtils.htmlEscape(comment.getDescription()));

		contentService.addComment(comment);

		return "redirect:/content/" + comment.getContentId();
	}

	@RequestMapping(value = "/controlpanel/comment/remove")
	public String removeComments(
			@RequestParam("ids") String ids,
			HttpServletRequest request) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			contentService.deleteComment(Long.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/comment";
	}
}