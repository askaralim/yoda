package com.yoda.item.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.model.Pagination;
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

	@RequestMapping(value="/controlpanel/item", method = RequestMethod.GET)
	public String showItems(
		Map<String, Object> model, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Site site = PortalUtil.getSiteFromSession(request);

		String offset = request.getParameter("offset");

		int offsetInt = 0;

		if (!StringUtil.isEmpty(offset)) {
			offsetInt = Integer.valueOf(offset) * 10;
		}

		Pagination<Item> page = itemService.getItems(site.getSiteId(), new RowBounds(offsetInt, 10));

		model.put("page", page);

		return "controlpanel/item/list";
	}

	@RequestMapping(value="/controlpanel/item/remove")
	public String removeItems(@RequestParam("ids") String ids) {
		String[] arrIds = ids.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			itemService.remove(Integer.valueOf(arrIds[i]));
		}

		return "redirect:/controlpanel/item";
	}
}