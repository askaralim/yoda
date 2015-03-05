package com.yoda.item.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.service.UserService;

@Controller
public class ItemController {
	@Autowired
	UserService userService;

	@Autowired
	ItemService itemService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/items", method = RequestMethod.GET)
	public String showItems(
		Map<String, Object> model,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		Site site = PortalUtil.getSiteFromSession(request);

		List<Item> items = itemService.getItems(site.getSiteId());

		model.put("items", items);

		return "controlpanel/items/list";
	}

	@RequestMapping(value="/controlpanel/items/remove")
	public String removeItems(@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			itemService.remove(Integer.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/items";
	}
}