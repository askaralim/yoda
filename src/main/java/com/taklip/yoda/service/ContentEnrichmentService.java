package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.vo.ContentSearchVO;

import java.util.List;
import java.util.Map;

/**
 * Service responsible for enriching ContentDTO with related data from local services.
 * This follows the Facade pattern to provide a clean interface for content enrichment.
 */
public interface ContentEnrichmentService {
    
    /**
     * Enriches a single ContentDTO with related data
     */
    ContentDTO enrichContent(ContentDTO contentDTO);
    
    /**
     * Enriches a list of ContentDTOs with related data
     */
    List<ContentDTO> enrichContents(List<ContentDTO> contentDTOs);
    
    /**
     * Enriches a list of ContentDTOs with related data using batch processing for better performance
     */
    List<ContentDTO> enrichContentsBatch(List<ContentDTO> contentDTOs);
    
    /**
     * Enriches a page of ContentDTOs with related data
     */
    Page<ContentDTO> enrichContentPage(Page<ContentDTO> contentPage);
    
    /**
     * Gets enriched content by ID from remote service and populates with local data
     */
    ContentDTO getEnrichedContentById(Long id);
    
    /**
     * Gets enriched content by natural key from remote service and populates with local data
     */
    ContentDTO getEnrichedContentByNaturalKey(String naturalKey);
    
    /**
     * Searches contents from remote service and enriches results with local data
     */
    Map<String, Object> searchEnrichedContents(ContentSearchVO searchVO);
    
    /**
     * Gets enriched contents by category from remote service and populates with local data
     */
    List<ContentDTO> getEnrichedContentsByCategory(Long categoryId, Integer limit);
    
    /**
     * Gets enriched featured contents from remote service and populates with local data
     */
    Page<ContentDTO> getEnrichedFeaturedContents(Integer offset, Integer limit, Boolean featureData);
    
    /**
     * Gets enriched published contents from remote service and populates with local data
     */
    Page<ContentDTO> getEnrichedPublishedContents(Integer offset, Integer limit);
    
    /**
     * Enriches content with minimal data (only essential fields) for list views
     */
    ContentDTO enrichContentMinimal(ContentDTO contentDTO);
    
    /**
     * Enriches a list of contents with minimal data for list views
     */
    List<ContentDTO> enrichContentsMinimal(List<ContentDTO> contentDTOs);
}
