package com.taklip.yoda.api;

import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.util.AuthenticatedUtil;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class ItemApiController extends PortalBaseController {
	@Autowired
	ItemService itemService;

	@GetMapping("/{itemId}")
	public ResponseEntity<Item> showItem(
			@PathVariable("itemId") Long itemId) {
		Item item = itemService.getItem(itemId);

		return new ResponseEntity(item, HttpStatus.OK);
	}

	@GetMapping("/topItems")
	public ResponseEntity<List<Item>> topItems() {
		List<Item> items = itemService.getItemsTopViewed(8);

		return new ResponseEntity(items, HttpStatus.OK);
	}
}