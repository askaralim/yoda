package com.taklip.yoda.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;

public interface ContentService {
        void create(ContentDTO content) throws Exception;

        void update(ContentDTO content) throws Exception;

        void deleteContent(Long contentId);

        void deleteContents(List<Long> ids);

        ContentDTO getContentById(Long contentId);

        Page<ContentDTO> getContentsByTitle(String title);

        Page<ContentDTO> getContentsByFeatureData(Boolean featureData, Integer offset,
                        Integer limit);

        List<ContentDTO> getContents();

        Page<ContentDTO> getContentsByPage(Integer offset, Integer limit);

        Page<ContentDTO> getContentByUserId(Long userId);

        int getHitCounter(long contentId);

        void increaseHitCounter(Long id);

        void resetHitCounter(Long contentId);

        Page<ContentDTO> searchContents(String title, Boolean published);

        void updateContent(ContentDTO content);

        ContentDTO updateContent(ContentDTO content, Long categoryId);

        ContentDTO updateContentImage(long siteId, Long contentId, MultipartFile featureImage);

        /**
         * Enriches a single ContentDTO with related data
         */
        ContentDTO enrichContent(ContentDTO contentDTO);

        /**
         * Enriches a list of ContentDTOs with related data
         */
        List<ContentDTO> enrichContents(List<ContentDTO> contentDTOs);

        /**
         * Enriches a page of ContentDTOs with related data
         */
        Page<ContentDTO> enrichContentPage(Page<ContentDTO> contentPage);

        /**
         * Gets enriched contents by category from remote service and populates with local data
         */
        Page<ContentDTO> getContentsByCategory(Long categoryId, Integer limit);

        /**
         * Gets enriched published contents from remote service and populates with local data
         */
        Page<ContentDTO> getPublishedContents(Integer offset, Integer limit);

        /**
         * Enriches content with minimal data (only essential fields) for list views
         */
        ContentDTO enrichContentMinimal(ContentDTO contentDTO);

        /**
         * Enriches a list of contents with minimal data for list views
         */
        List<ContentDTO> enrichContentsMinimal(List<ContentDTO> contentDTOs);
}
