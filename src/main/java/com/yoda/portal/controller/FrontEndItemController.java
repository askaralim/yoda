package com.yoda.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/item")
public class FrontEndItemController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontEndItemController.class);

	@Autowired
	ItemService itemService;

	@RequestMapping(value="/{itemId}", method = RequestMethod.GET)
	public ModelAndView showItem(
			@PathVariable("itemId") int itemId,
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		Site site = getSite(request);

		Item item = itemService.getItem(itemId);

//		item.setHitCounter(item.getHitCounter() + 1);

		kafkaTemplate.send(Constants.KAFKA_TOPIC_REDIS_INCR, Constants.REDIS_ITEM_HIT_COUNTER, String.valueOf(itemId));

//		itemService.update(item);

		getHorizontalMenu(request, response, model);

		model.put("site", site);
		model.put("item", item);

		model.put("pageTitle", "【"+ item.getName() +"】" + item.getBrand().getName() + " " + item.getName() + "，产品报价、介绍 - " + site.getSiteName());
		model.put("keywords", item.getBrand().getName() + "," + item.getName() + "," + item.getName() + "介绍," + item.getName() + "怎么样");
		model.put("description", item.getBrand().getName() + " " + item.getName() + "详细介绍。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + item.getImagePath());

		User currentUser = PortalUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		String backURL = request.getParameter("backURL");

		model.put("backURL", backURL);

		pageView(request, Constants.PAGE_TYPE_ITEM, item.getId(), item.getName());
//		PageViewUtil.viewPage(request, Constants.PAGE_TYPE_ITEM, item.getId(), item.getName());

		return new ModelAndView("portal/item/item", model);
	}
}