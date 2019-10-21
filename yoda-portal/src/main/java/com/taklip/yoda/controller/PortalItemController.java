package com.taklip.yoda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.util.AuthenticatedUtil;

@Controller
@RequestMapping("/item")
public class PortalItemController extends PortalBaseController {
	private final Logger logger = LoggerFactory.getLogger(PortalItemController.class);

	@Autowired
	ItemService itemService;

	@RequestMapping(value="/{itemId}", method = RequestMethod.GET)
	public ModelAndView showItem(
			@PathVariable("itemId") Long itemId, HttpServletRequest request) {
		ModelMap model = new ModelMap();

		Site site = getSite();

		Item item = itemService.getItem(itemId);

		setUserLoginStatus(model);

		model.put("site", site);
		model.put("item", item);

		model.put("pageTitle", "【"+ item.getName() +"】" + item.getBrand().getName() + " " + item.getName() + "，产品报价、介绍 - " + site.getSiteName());
		model.put("keywords", item.getBrand().getName() + "," + item.getName() + "," + item.getName() + "介绍," + item.getName() + "怎么样");
		model.put("description", item.getBrand().getName() + " " + item.getName() + "详细介绍。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + item.getImagePath());

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		String backURL = request.getParameter("backURL");

		model.put("backURL", backURL);

		pageViewHandler.add(request, ContentTypeEnum.ITEM.getType(), item.getName(), item.getId());

		return new ModelAndView("portal/item/item", model);
	}
}