package com.taklip.yoda.client;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.vo.ContentSearchVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContentServiceFallback implements ContentServiceFeignClient {
    @Override
    public ContentDTO createContent(ContentDTO contentDTO) {
        log.warn("Content service is unavailable. Fallback triggered for createContent");
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public ContentDTO updateContent(Long id, ContentDTO contentDTO) {
        log.warn("Content service is unavailable. Fallback triggered for updateContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public ContentDTO getContentById(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for getContentById with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public void deleteContent(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for deleteContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public Page<ContentDTO> searchContents(ContentSearchVO searchVO) {
        log.warn("Content service is unavailable. Fallback triggered for searchContents");
        return new Page<>();
    }
    
    @Override
    public Page<ContentDTO> getContentsByCategory(Long categoryId, Integer limit) {
        log.warn("Content service is unavailable. Fallback triggered for getContentsByCategory with categoryId: {}", categoryId);
        return new Page<>();
    }

    @Override
    public Page<ContentDTO> getPublishedContents(Integer siteId, Integer limit) {
        log.warn("Content service is unavailable. Fallback triggered for getPublishedContents");
        return new Page<>();
    }
    
    @Override
    public Page<ContentDTO> getContentsByTags(String tags, Integer limit) {
        log.warn("Content service is unavailable. Fallback triggered for getContentsByTags with tags: {}", tags);
        return new Page<>();
    }

    @Override
    public void increaseHitCounter(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for increaseHitCounter with id: {}", id);
        // Don't throw exception for non-critical operations
    }

    @Override
    public void resetHitCounter(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for resetHitCounter with id: {}", id);
        // Don't throw exception for non-critical operations
    }

    @Override
    public int getHitCounter(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for getHitCounter with id: {}", id);
        return 0;
    }
    
    @Override
    public void publishContent(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for publishContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public void unpublishContent(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for unpublishContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public void featureContent(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for featureContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public void unfeatureContent(Long id) {
        log.warn("Content service is unavailable. Fallback triggered for unfeatureContent with id: {}", id);
        throw new RuntimeException("Content service is temporarily unavailable");
    }
    
    @Override
    public String getHealth() {
        log.warn("Content service is unavailable. Fallback triggered for health check");
        return "{\"status\":\"DOWN\",\"details\":{\"content-service\":{\"status\":\"DOWN\",\"details\":{\"error\":\"Service unavailable\"}}}}";
    }

    @Override
    public Page<ContentDTO> getContentByPage(Integer offset, Integer limit) {
        log.warn("Content service is unavailable. Fallback triggered for getContentByPage with offset: {}, limit: {}", offset, limit);
        return new Page<>();
    }

    @Override
    public void deleteContents(List<Long> ids) {
        log.warn("Content service is unavailable. Fallback triggered for deleteContents with ids: {}", ids);
        // Don't throw exception for non-critical operations
    }

    @Override
    public Page<ContentDTO> getContentsByFeatureData(Boolean featureData, Integer offset,
            Integer limit) {
        log.warn("Content service is unavailable. Fallback triggered for getContentsByFeatureData with featureData: {}, offset: {}, limit: {}", featureData, offset, limit);
        return new Page<>();
    }

    @Override
    public Page<ContentDTO> getContentsByUserId(Long userId) {
        log.warn("Content service is unavailable. Fallback triggered for getContentsByUserId with userId: {}", userId);
        return new Page<>();
    }

    @Override
    public Page<ContentDTO> getContentsByTitle(String title) {
        log.warn("Content service is unavailable. Fallback triggered for getContentsByTitle with title: {}", title);
        return new Page<>();
    }

    @Override
    public List<ContentDTO> getContents() {
        log.warn("Content service is unavailable. Fallback triggered for getContents");
        return new ArrayList<>();
    }
}
