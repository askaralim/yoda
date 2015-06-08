package com.yoda.content.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.category.model.Category;
import com.yoda.category.service.CategoryService;
import com.yoda.content.ContentEditValidator;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.util.Format;

@Controller
@RequestMapping("/controlpanel/content/add")
public class ContentAddController {
	@Autowired
	CategoryService categoryService;

	@Autowired
	ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(Map<String, Object> model) {
		Content content = new Content();

		content.setPublished(true);
		content.setHomePage(false);
		content.setPublishDate(new Date());
		content.setExpireDate(Format.getHighDate());

		List<Category> categories = categoryService.getCategories();

		model.put("categories", categories);
		model.put("content", content);

		return new ModelAndView("controlpanel/content/edit", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(
			@ModelAttribute Content content,
			@RequestParam("categoryId") Integer categoryId,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		new ContentEditValidator().validate(content, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			List<Category> categories = categoryService.getCategories();

			model.put("categories", categories);

			return new ModelAndView("controlpanel/content/edit", model);
		}

		Site site = PortalUtil.getSiteFromSession(request);

		contentService.addContent(site.getSiteId(), content, categoryId);

//		Indexer.getInstance(siteId).removeContent(content);
//		Indexer.getInstance(siteId).indexContent(content);

//		copyProperties(command, content);
//		createAdditionalInfo(user, content, command);

//		model.put("success", "success");

		return new ModelAndView("redirect:/controlpanel/content/" + content.getContentId() + "/edit", model);
	}
}