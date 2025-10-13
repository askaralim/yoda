package com.taklip.yoda.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.client.ContentServiceFeignClient;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.vo.ContentSearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContentServiceClient {

    @Autowired
    private ContentServiceFeignClient contentServiceFeignClient;

    public void deleteContents(List<Long> ids) {
        contentServiceFeignClient.deleteContents(ids);
    }

    public Page<ContentDTO> getContentByPage(Integer offset, Integer limit) {

        return contentServiceFeignClient.getContentByPage(offset, limit);
    }

    /**
     * Create new content
     */
    public ContentDTO createContent(ContentDTO contentDTO) {
        try {
            log.debug("Creating content via Feign client: {}", contentDTO.getTitle());
            return contentServiceFeignClient.createContent(contentDTO);
        } catch (Exception e) {
            log.error("Error creating content: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create content", e);
        }
    }

    /**
     * Update existing content
     */
    public ContentDTO updateContent(Long id, ContentDTO contentDTO) {
        try {
            log.debug("Updating content via Feign client with id: {}", id);
            return contentServiceFeignClient.updateContent(id, contentDTO);
        } catch (Exception e) {
            log.error("Error updating content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update content", e);
        }
    }

    /**
     * Get content by ID
     */
    public ContentDTO getContentById(Long id) {
        log.debug("Getting content via Feign client with id: {}", id);
        try {
            return contentServiceFeignClient.getContentById(id);
        } catch (Exception e) {
            log.error("🔥 MANUAL FALLBACK TRIGGERED! Content service unavailable for getContentById with id {}: {}", id, e.getMessage());
            // Manual fallback - return null to indicate service unavailable
            return null;
        }
    }

    /**
     * Delete content
     */
    public void deleteContent(Long id) {
        try {
            log.debug("Deleting content via Feign client with id: {}", id);
            contentServiceFeignClient.deleteContent(id);
        } catch (Exception e) {
            log.error("Error deleting content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete content", e);
        }
    }

    /**
     * Search contents
     */
    public Page<ContentDTO> searchContents(ContentSearchVO searchVO) {
        try {
            log.debug("Searching contents via Feign client");
            return contentServiceFeignClient.searchContents(searchVO);
        } catch (Exception e) {
            log.error("Error searching contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to search contents", e);
        }
    }

    /**
     * Get contents by user ID
     */
    public Page<ContentDTO> getContentsByUserId(Long userId, Integer offset, Integer limit) {
        try {
            log.debug("Getting contents by user ID via Feign client: userId={}", userId);
            return contentServiceFeignClient.getContentsByUserId(userId, offset, limit);
        } catch (Exception e) {
            log.error("Error getting contents by user ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Failed to get contents by user ID", e);
        }
    }

    /**
     * Get contents by category
     */
    public Page<ContentDTO> getContentsByCategory(Long categoryId, Integer limit) {
        try {
            log.debug("Getting contents by category via Feign client: categoryId={}, limit={}",
                    categoryId, limit);
            return contentServiceFeignClient.getContentsByCategory(categoryId, limit);
        } catch (Exception e) {
            log.error("Error getting contents by category {}: {}", categoryId, e.getMessage(), e);
            throw new RuntimeException("Failed to get contents by category", e);
        }
    }

    /**
     * Get featured contents
     */
    public Page<ContentDTO> getFeaturedContents(Boolean featureData, Integer offset,
            Integer limit) {
        try {
            log.debug("Getting {} via Feign client: offset={}, limit={}", featureData ? "featured" : "no featured", offset,
                    limit);
            return contentServiceFeignClient.getContentsByFeatureData(featureData, offset, limit);
        } catch (Exception e) {
            log.error("Error getting {} contents: {}", featureData ? "featured" : "no featured", e.getMessage(), e);
            throw new RuntimeException("Failed to get featured contents", e);
        }
    }

    /**
     * Get published contents
     */
    public Page<ContentDTO> getPublishedContents(Integer offset, Integer limit) {
        try {
            log.debug("Getting published contents via Feign client: offset={}, limit={}", offset,
                    limit);
            return contentServiceFeignClient.getPublishedContents(offset, limit);
        } catch (Exception e) {
            log.error("Error getting published contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get published contents", e);
        }
    }

    /**
     * Get contents by tags
     */
    public Page<ContentDTO> getContentsByTags(String tags, Integer limit) {
        try {
            log.debug("Getting contents by tags via Feign client: tags={}, limit={}", tags, limit);
            return contentServiceFeignClient.getContentsByTags(tags, limit);
        } catch (Exception e) {
            log.error("Error getting contents by tags {}: {}", tags, e.getMessage(), e);
            throw new RuntimeException("Failed to get contents by tags", e);
        }
    }

    /**
     * Update hit counter
     */
    public void increaseHitCounter(Long id) {
        try {
            log.debug("Updating hit counter via Feign client with id: {}", id);
            contentServiceFeignClient.increaseHitCounter(id);
        } catch (Exception e) {
            log.error("Error increasing hit counter for content with id {}: {}", id, e.getMessage(),
                    e);
            // Don't throw exception for non-critical operations
        }
    }

    /**
     * Reset hit counter
     */
    public void resetHitCounter(Long id) {
        try {
            log.debug("Resetting hit counter via Feign client with id: {}", id);
            contentServiceFeignClient.resetHitCounter(id);
        } catch (Exception e) {
            log.error("Error resetting hit counter for content with id {}: {}", id, e.getMessage(),
                    e);
            // Don't throw exception for non-critical operations
        }
    }

    /**
     * Get hit counter
     */
    public int getHitCounter(Long id) {
        try {
            log.debug("Getting hit counter via Feign client with id: {}", id);
            return contentServiceFeignClient.getHitCounter(id);
        } catch (Exception e) {
            log.error("Error getting hit counter for content with id {}: {}", id, e.getMessage(),
                    e);
            return 0;
        }
    }

    /**
     * Publish content
     */
    public void publishContent(Long id) {
        try {
            log.debug("Publishing content via Feign client with id: {}", id);
            contentServiceFeignClient.publishContent(id);
        } catch (Exception e) {
            log.error("Error publishing content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to publish content", e);
        }
    }

    /**
     * Unpublish content
     */
    public void unpublishContent(Long id) {
        try {
            log.debug("Unpublishing content via Feign client with id: {}", id);
            contentServiceFeignClient.unpublishContent(id);
        } catch (Exception e) {
            log.error("Error unpublishing content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to unpublish content", e);
        }
    }

    /**
     * Feature content
     */
    public void featureContent(Long id) {
        try {
            log.debug("Featuring content via Feign client with id: {}", id);
            contentServiceFeignClient.featureContent(id);
        } catch (Exception e) {
            log.error("Error featuring content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to feature content", e);
        }
    }

    /**
     * Unfeature content
     */
    public void unfeatureContent(Long id) {
        try {
            log.debug("Unfeaturing content via Feign client with id: {}", id);
            contentServiceFeignClient.unfeatureContent(id);
        } catch (Exception e) {
            log.error("Error unfeaturing content with id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to unfeature content", e);
        }
    }

    /**
     * Check if content service is healthy
     */
    public boolean isHealthy() {
        try {
            log.debug("Checking content service health via Feign client");
            String healthResponse = contentServiceFeignClient.getHealth();
            return healthResponse.contains("\"status\":\"UP\"");
        } catch (Exception e) {
            log.warn("Content service health check failed: {}", e.getMessage());
            return false;
        }
    }

    public Page<ContentDTO> getContentsByTitle(String title) {
        try {
            log.debug("Getting contents by title via Feign client: title={}", title);
            return contentServiceFeignClient.getContentsByTitle(title);
        } catch (Exception e) {
            log.error("Error getting contents by title {}: {}", title, e.getMessage(), e);
            throw new RuntimeException("Failed to get contents by title", e);
        }
    }

    public List<ContentDTO> getContents() {
        try {
            log.debug("Getting contents via Feign client");
            return contentServiceFeignClient.getContents();
        } catch (Exception e) {
            log.error("Error getting contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get contents", e);
        }
    }
}
