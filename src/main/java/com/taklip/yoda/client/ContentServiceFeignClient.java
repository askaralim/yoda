package com.taklip.yoda.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.vo.ContentSearchVO;

@FeignClient(name = "yoda-content-service",
                url = "${yoda.services.content.url:http://localhost:8082}",
                fallback = ContentServiceFallback.class)
public interface ContentServiceFeignClient {
        @PostMapping("/api/v1/content")
        ContentDTO createContent(@RequestBody ContentDTO contentDTO);

        @PutMapping("/api/v1/content/{id}")
        ContentDTO updateContent(@PathVariable Long id, @RequestBody ContentDTO contentDTO);

        @GetMapping("/api/v1/content/{id}")
        ContentDTO getContentById(@PathVariable Long id);

        @GetMapping("/api/v1/content/page")
        Page<ContentDTO> getContentByPage(@RequestParam Integer offset,
                        @RequestParam Integer limit);

        @DeleteMapping("/api/v1/content/{id}")
        void deleteContent(@PathVariable Long id);

        @DeleteMapping("/api/v1/content")
        void deleteContents(@RequestBody List<Long> ids);

        @PostMapping("/api/v1/content/search")
        Page<ContentDTO> searchContents(@RequestBody ContentSearchVO searchVO);

        @GetMapping("/api/v1/content/category/{categoryId}")
        Page<ContentDTO> getContentsByCategory(@PathVariable Long categoryId,
                        @RequestParam Integer limit);

        @GetMapping("/api/v1/content/featured")
        Page<ContentDTO> getContentsByFeatureData(@RequestParam Boolean featureData,
                        @RequestParam Integer offset, @RequestParam Integer limit);

        @GetMapping("/api/v1/content/published")
        Page<ContentDTO> getPublishedContents(@RequestParam Integer offset,
                        @RequestParam Integer limit);

        @GetMapping("/api/v1/content/tags")
        Page<ContentDTO> getContentsByTags(@RequestParam String tags, @RequestParam Integer limit);

        @PostMapping("/api/v1/content/{id}/increase-hit-counter")
        void increaseHitCounter(@PathVariable Long id);

        @PostMapping("/api/v1/content/{id}/reset-hit-counter")
        void resetHitCounter(@PathVariable Long id);

        @GetMapping("/api/v1/content/{id}/hit-counter")
        int getHitCounter(@PathVariable Long id);

        @PostMapping("/api/v1/content/{id}/publish")
        void publishContent(@PathVariable Long id);

        @PostMapping("/api/v1/content/{id}/unpublish")
        void unpublishContent(@PathVariable Long id);

        @PostMapping("/api/v1/content/{id}/feature")
        void featureContent(@PathVariable Long id);

        @PostMapping("/api/v1/content/{id}/unfeature")
        void unfeatureContent(@PathVariable Long id);

        @GetMapping("/actuator/health")
        String getHealth();

        @GetMapping("/api/v1/content/user/{userId}")
        Page<ContentDTO> getContentsByUserId(@PathVariable Long userId);

        @GetMapping("/api/v1/content/title/{title}")
        Page<ContentDTO> getContentsByTitle(@PathVariable String title);

        @GetMapping("/api/v1/content/all")
        List<ContentDTO> getContents();
}
