package com.yoda.item.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.item.ItemValidator;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;

@Controller
public class ItemAddController {
	@Autowired
	ItemService itemService;

	@Autowired
	ContentService contentService;

	@RequestMapping(value = "/controlpanel/{contentId}/item/new", method = RequestMethod.GET)
	public ModelAndView initCreationForm(
			@PathVariable("contentId") long contentId, Map<String, Object> model) {
		User user = PortalUtil.getAuthenticatedUser();

		Content content = contentService.getContent(user.getLastVisitSiteId(), contentId);

		Item item = new Item();

		content.addItem(item);

		model.put("item", item);

		return new ModelAndView("controlpanel/item/form", model);
	}

	@RequestMapping(value = "/controlpanel/{contentId}/item/new", method = RequestMethod.POST)
	public ModelAndView processCreationForm(
			@ModelAttribute("item") Item item, 
			@PathVariable("contentId") long contentId, BindingResult result,
			SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) {
		new ItemValidator().validate(item, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/item/form", model);
		}
		else {
			Site site = PortalUtil.getSiteFromSession(request);

			Content content = contentService.getContent(site.getSiteId(), contentId);

			content.addItem(item);

			this.itemService.save(site.getSiteId(), item);

			status.setComplete();

			return new ModelAndView("redirect:/controlpanel/item/" + item.getId() + "/edit", model);
		}
	}
}