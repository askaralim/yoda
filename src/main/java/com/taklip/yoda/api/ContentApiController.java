package com.taklip.yoda.api;

import java.util.List;
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
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.service.ContentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
@Slf4j
public class ContentApiController {

    private final ContentService contentService;

    @GetMapping
    public ResponseEntity<Page<ContentDTO>> getAllContent(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        Page<ContentDTO> contentPage = contentService.getContentsByPage(offset, limit);
        return ResponseEntity.ok(contentPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContentDTO> getContentById(@PathVariable Long id) {
        try {
            ContentDTO content = contentService.getContentById(id);
            if (content == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            log.error("Error getting content by id {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ContentDTO> createContent(
            @Valid @RequestBody ContentCreateRequest request) {
        try {
            // ContentDTO content = convertToEntity(request);
            // contentService.create(content);
            // return ResponseEntity.ok(content);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContentDTO> updateContent(@PathVariable Long id,
            @Valid @RequestBody ContentUpdateRequest request) {
        // Content existingContent = contentService.getContentById(id);
        // if (existingContent != null) {
        // updateContentFromRequest(existingContent, request);
        // try {
        // Content updatedContent = contentService.updateContent(existingContent, 1L);
        // return ResponseEntity.ok(convertToDTO(updatedContent));
        // } catch (Exception e) {
        // return ResponseEntity.badRequest().build();
        // }
        // }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        // Content content = contentService.getContentById(id);
        // if (content != null) {
        // contentService.deleteContent(id);
        // return ResponseEntity.noContent().build();
        // }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContentDTO>> searchContent(@RequestParam String q) {
        // List<Content> results = contentService.getContents(q);
        // List<ContentDTO> dtoList = results.stream()
        // .map(this::convertToDTO)
        // .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(null);
    }

    // Helper methods for conversion
    private ContentDTO convertToDTO(Content content) {
        ContentDTO dto = new ContentDTO();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setDescription(content.getDescription());
        dto.setShortDescription(content.getShortDescription());
        dto.setPageTitle(content.getPageTitle());
        dto.setFeaturedImage(content.getFeaturedImage());
        dto.setHitCounter(content.getHitCounter());
        dto.setScore(content.getScore());
        dto.setPublished(content.isPublished());
        dto.setFeatureData(content.isFeatureData());
        return dto;
    }

    private Content convertToEntity(ContentCreateRequest request) {
        Content content = new Content();
        content.setTitle(request.getTitle());
        content.setDescription(request.getDescription());
        content.setShortDescription(request.getShortDescription());
        return content;
    }

    private void updateContentFromRequest(Content content, ContentUpdateRequest request) {
        if (request.getTitle() != null) {
            content.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            content.setDescription(request.getDescription());
        }
        if (request.getShortDescription() != null) {
            content.setShortDescription(request.getShortDescription());
        }
    }

    // Request/Response DTOs
    public static class ContentCreateRequest {
        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        private String title;

        @Size(max = 1000, message = "Description must be less than 1000 characters")
        private String description;

        @Size(max = 500, message = "Short description must be less than 500 characters")
        private String shortDescription;

        // Getters and setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }
    }

    public static class ContentUpdateRequest {
        private String title;
        private String description;
        private String shortDescription;

        // Getters and setters
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }
    }
}
