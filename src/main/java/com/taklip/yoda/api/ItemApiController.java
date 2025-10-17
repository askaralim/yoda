package com.taklip.yoda.api;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/item")
public class ItemApiController extends PortalBaseController {
    @Autowired
    ItemService itemService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemDetail(id));
    }

    @GetMapping("/topItems")
    public ResponseEntity<List<ItemDTO>> getTopItems() {
        return ResponseEntity.ok(itemService.getItemsTopViewed(8));
    }

    @GetMapping
    public ResponseEntity<Page<ItemDTO>> getItems(@RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(itemService.getItems(offset, limit));
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO item) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.update(item));
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Void> uploadImage(@RequestParam MultipartFile file,
            @PathVariable Long id) {
        itemService.updateItemImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/rating")
    public String score(@PathVariable Long id, @RequestParam String thumb) {
        Item item = itemService.getItem(id);

        int rating = 0;

        if (thumb.equals("up")) {
            rating = item.getRating() + 1;
        } else if (thumb.equals("down")) {
            rating = item.getRating() - 1;
        }

        item.setRating(rating);

        itemService.updateItemRating(id, rating);

        JSONObject jsonResult = new JSONObject();

        jsonResult.put("rating", rating);

        return jsonResult.toString();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
