package com.taklip.yoda.api;

import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
public class ContentApiController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@GetMapping
	public ResponseEntity<Pagination<Content>> list(@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Content> page = contentService.getContentsNotFeatureData(offset, 4);

		return new ResponseEntity(page, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Content> getContent(@PathVariable("id") String id) {
		Content content = contentService.getContent(Long.valueOf(id));

		List<Item> items = itemService.getItemsByContentId(content.getId());
		List<Comment> comments = contentService.getComments(content.getId());

		for (Item item : items) {
			shortenItemDescription(item);
		}

		String desc = content.getDescription();

		content.setDescription(desc.replace("img src", "img data-src"));
		content.setItems(items);
		content.setComments(comments);

		return new ResponseEntity(content, HttpStatus.OK);
	}

	private Item shortenItemDescription(Item item) {
		String desc = item.getDescription();

		if (desc.length() > 200) {
			desc = desc.substring(0, 200);

			if (desc.indexOf("img") > 0) {
				desc = desc.substring(0, desc.indexOf("<img"));
			}

			item.setDescription(desc);
		}

		return item;
	}
}