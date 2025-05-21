package com.taklip.yoda.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.service.CommentService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;

@RestController
@RequestMapping("/api/v1/content")
public class ContentApiController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ItemService itemService;

    @Autowired
    protected CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<ContentDTO>> list(@RequestParam(defaultValue = "0") Integer offset) {
        Page<ContentDTO> page = contentService.getContentsNotFeatureData(offset, 4);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDTO> getContent(@PathVariable String id) {
        ContentDTO content = contentService.getContentDetail(Long.valueOf(id));

        // List<ItemDTO> items = itemService.getItemDTOsByContentId(content.getId());
        // List<CommentDTO> comments =
        // commentService.getCommentsByContentId(content.getId());

        // for (ItemDTO item : items) {
        // shortenItemDescription(item);
        // }

        // String desc = content.getDescription();

        // content.setDescription(desc.replace("img src", "img data-src"));
        // content.setItems(items);
        // content.setComments(comments);

        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}