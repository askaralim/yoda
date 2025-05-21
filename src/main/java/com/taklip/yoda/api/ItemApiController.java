package com.taklip.yoda.api;

import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/item")
public class ItemApiController extends PortalBaseController {
    @Autowired
    ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> showItem(@PathVariable Long itemId) {
        Item item = itemService.getItem(itemId);

        return new ResponseEntity(item, HttpStatus.OK);
    }

    @GetMapping("/topItems")
    public ResponseEntity<List<Item>> topItems() {
        List<Item> items = itemService.getItemsTopViewed(8);

        return new ResponseEntity(items, HttpStatus.OK);
    }
}