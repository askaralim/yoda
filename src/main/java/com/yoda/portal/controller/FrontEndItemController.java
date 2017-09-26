package com.yoda.portal.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PageViewUtil;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.portal.content.data.SiteInfo;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/item")
public class FrontEndItemController extends BaseFrontendController {
	@Autowired
	ItemService itemService;

	@RequestMapping(value="/{itemId}", method = RequestMethod.GET)
	public ModelAndView showBrand(
			@PathVariable("itemId") int itemId,
			Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		Site site = getSite(request);

		SiteInfo siteInfo = getSite(site);

		Item item = itemService.getItem(itemId);

		item.setHitCounter(item.getHitCounter() + 1);

		itemService.update(item);

		String horizontalMenu = getHorizontalMenu(request, response);

		model.put("horizontalMenu", horizontalMenu);
		model.put("siteInfo", siteInfo);
		model.put("item", item);

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		String backURL = request.getParameter("backURL");

		model.put("backURL", backURL);

		PageViewUtil.viewPage(request, Constants.PAGE_TYPE_ITEM, item.getId(), item.getName());

		return new ModelAndView("/portal/item/item", model);
	}

	Logger logger = Logger.getLogger(FrontEndItemController.class);
}