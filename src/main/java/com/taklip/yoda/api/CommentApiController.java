package com.taklip.yoda.api;

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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.CommentDTO;
import com.taklip.yoda.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/comment")
@Tag(name = "Comment Management", description = "Comment management API endpoints")
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    @Operation(summary = "Get comments", description = "Get comments by page")
    public ResponseEntity<Page<CommentDTO>> getComments(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(commentService.getByPage(offset, limit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get comment", description = "Get comment by comment id")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @PostMapping
    @Operation(summary = "Create comment", description = "Create comment")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.create(comment));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update comment", description = "Update comment by comment id")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id,
            @RequestBody CommentDTO comment) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.update(comment));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete comment", description = "Delete comment by comment id")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
