package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.common.util.ChineseNaturalKeyGenerator;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.CommentService;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.ContentContributorService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ContentServiceClient;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.vo.ContentSearchVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private ContentBrandService contentBrandService;

    @Autowired
    private ContentContributorService contentContributorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageUploader imageUpload;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContentServiceClient contentServiceClient;

    @Autowired
    private ChineseNaturalKeyGenerator naturalKeyGenerator;

    @Override
    public ContentDTO create(ContentDTO content) {
        content.setNaturalKey(naturalKeyGenerator.generateNaturalKey(content.getTitle()));
        content.setHitCounter(0);
        content.setScore(0);

        return contentServiceClient.createContent(content);

        // if (!content.isFeatureData() && content.isPublished() && content.isHomePage()) {
        // this.setContentNotFeatureDatasIntoCache(content.getId());
        // redisService.incr(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);
        // } else if (content.isFeatureData() && content.isPublished() && content.isHomePage()) {
        // this.setContentFeatureDatasIntoCache(content.getId());
        // }

        // this.setContentIntoCache(content);
    }

    @Override
    public ContentDTO update(ContentDTO content) {
        content.setNaturalKey(naturalKeyGenerator.generateNaturalKey(content.getTitle()));

        return contentServiceClient.updateContent(content.getId(), content);
    }

    @Override
    public void deleteContent(Long contentId) {
        contentServiceClient.deleteContent(contentId);
    }

    @Override
    public void deleteContents(List<Long> ids) {
        contentServiceClient.deleteContents(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> getContentByUserId(Long userId, Integer offset, Integer limit) {
        return contentServiceClient.getContentsByUserId(userId, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentDTO getContentById(Long id) {
        // Let the exception propagate to allow Feign fallback to work
        ContentDTO contentDTO = contentServiceClient.getContentById(id);
        return enrichContent(contentDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentDTO> getContents() {
        return contentServiceClient.getContents();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> getContentsByPage(Integer offset, Integer limit) {
        return contentServiceClient.getContentByPage(offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> getContentsByTitle(String title) {
        return contentServiceClient.getContentsByTitle(title);
    }

    @Override
    public Page<ContentDTO> getContentsByCategory(Long categoryId, Integer limit) {
        try {
            Page<ContentDTO> contents =
                    contentServiceClient.getContentsByCategory(categoryId, limit);
            return enrichContentPage(contents);
        } catch (Exception e) {
            log.error("Error getting enriched contents by category {}: {}", categoryId,
                    e.getMessage(), e);
            throw new RuntimeException("Failed to get enriched contents by category", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> getContentsByFeatureData(Boolean featureData, Integer offset,
            Integer limit) {
        try {
            Page<ContentDTO> contentPage =
                    contentServiceClient.getFeaturedContents(featureData, offset, limit);
            log.info("getFeaturedContents size: {}", contentPage.getRecords().size());
            // return enrichContentPage(contentPage);
            return contentPage;
        } catch (Exception e) {
            log.error("Error getting enriched featured contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get enriched featured contents", e);
        }
    }

    @Override
    public Page<ContentDTO> getPublishedContents(Integer offset, Integer limit) {
        try {
            Page<ContentDTO> contentPage = contentServiceClient.getPublishedContents(offset, limit);
            return enrichContentPage(contentPage);
        } catch (Exception e) {
            log.error("Error getting enriched published contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get enriched published contents", e);
        }
    }

    @Override
    public void updateContent(ContentDTO content) {
        content.setNaturalKey(naturalKeyGenerator.generateNaturalKey(content.getTitle()));
        contentServiceClient.updateContent(content.getId(), content);
    }

    @Override
    public ContentDTO updateContent(ContentDTO content, Long categoryId) {
        content.setNaturalKey(naturalKeyGenerator.generateNaturalKey(content.getTitle()));

        content.setCategoryId(categoryId);

        contentServiceClient.updateContent(content.getId(), content);

        return content;
    }

    @Override
    public void increaseHitCounter(Long id) {
        contentServiceClient.increaseHitCounter(id);
    }

    @Override
    public ContentDTO updateContentImage(Long id, MultipartFile image) {
        try {
            if (image.getBytes().length <= 0) {
                throw new RuntimeException("Image is empty");
            }

            if (StringUtils.isEmpty(image.getName())) {
                throw new RuntimeException("Image name is empty");
            }
        } catch (IOException e) {
            log.error("Error updating content image: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update content image", e);
        }

        ContentDTO content = contentServiceClient.getContentById(id);

        imageUpload.deleteImage(content.getFeaturedImage());

        try {
            String imagePath = fileService.save(ContentTypeEnum.CONTENT.getType(), id, image);

            content.setFeaturedImage(imagePath);
            contentServiceClient.updateContent(content.getId(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public void resetHitCounter(Long contentId) {
        contentServiceClient.resetHitCounter(contentId);
    }

    @Override
    public int getHitCounter(long contentId) {
        return contentServiceClient.getHitCounter(contentId);
    }

    @Override
    public ContentDTO enrichContent(ContentDTO contentDTO) {
        if (contentDTO == null) {
            return null;
        }

        try {
            // Populate category
            if (contentDTO.getCategoryId() != null) {
                contentDTO
                        .setCategory(categoryService.getCategoryDetail(contentDTO.getCategoryId()));
            }

            // Populate user data
            if (contentDTO.getCreateBy() != null) {
                contentDTO.setCreateByUser(userService.getUserDetail(contentDTO.getCreateBy()));
            }
            if (contentDTO.getUpdateBy() != null) {
                contentDTO.setUpdateByUser(userService.getUserDetail(contentDTO.getUpdateBy()));
            }

            // Populate related data
            populateRelatedData(contentDTO);

            return contentDTO;
        } catch (Exception e) {
            log.error("Error enriching content with id {}: {}", contentDTO.getId(), e.getMessage(),
                    e);
            // Return original contentDTO if enrichment fails
            return contentDTO;
        }
    }

    @Override
    public List<ContentDTO> enrichContents(List<ContentDTO> contentDTOs) {
        if (CollectionUtils.isEmpty(contentDTOs)) {
            return contentDTOs;
        }

        return contentDTOs.stream().map(this::enrichContent).collect(Collectors.toList());
    }

    @Override
    public Page<ContentDTO> enrichContentPage(Page<ContentDTO> contentPage) {
        if (contentPage == null || CollectionUtils.isEmpty(contentPage.getRecords())) {
            return contentPage;
        }

        List<ContentDTO> enrichedRecords = enrichContents(contentPage.getRecords());
        contentPage.setRecords(enrichedRecords);
        return contentPage;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> searchContents(String title, Boolean published) {
        try {
            Page<ContentDTO> searchResult = contentServiceClient
                    .searchContents(new ContentSearchVO(null, title, null, null, null, null, null,
                            null, null, null, null, null, null, null, null, null, null, null));
            return enrichContentPage(searchResult);
        } catch (Exception e) {
            log.error("Error searching enriched contents: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to search enriched contents", e);
        }
    }

    @Override
    public ContentDTO enrichContentMinimal(ContentDTO contentDTO) {
        if (contentDTO == null) {
            return null;
        }

        try {
            // Only populate essential fields for list views
            if (contentDTO.getCategoryId() != null) {
                contentDTO
                        .setCategory(categoryService.getCategoryDetail(contentDTO.getCategoryId()));
            }

            // Populate only createBy user (not updateBy for minimal view)
            if (contentDTO.getCreateBy() != null) {
                contentDTO.setCreateByUser(userService.getUserDetail(contentDTO.getCreateBy()));
            }

            return contentDTO;
        } catch (Exception e) {
            log.error("Error enriching content minimal with id {}: {}", contentDTO.getId(),
                    e.getMessage(), e);
            return contentDTO;
        }
    }

    @Override
    public List<ContentDTO> enrichContentsMinimal(List<ContentDTO> contentDTOs) {
        if (CollectionUtils.isEmpty(contentDTOs)) {
            return contentDTOs;
        }

        return contentDTOs.stream().map(this::enrichContentMinimal).collect(Collectors.toList());
    }

    /**
     * Populates related data for a content (items, comments, brands, contributors)
     */
    private void populateRelatedData(ContentDTO contentDTO) {
        if (contentDTO.getId() == null) {
            return;
        }

        try {
            // Populate items
            List<Item> items = itemService.getItemsByContentId(contentDTO.getId());
            contentDTO.setItems(modelConvertor.convertToItemDTOs(items));

            // Populate content brands
            List<ContentBrand> contentBrands =
                    contentBrandService.getContentBrandsByContentId(contentDTO.getId());
            contentDTO.setBrands(modelConvertor.convertToContentBrandDTOs(contentBrands));

            // Populate content contributors
            List<ContentContributor> contentContributors =
                    contentContributorService.getContentContributorsByContentId(contentDTO.getId());
            contentDTO.setContributors(
                    modelConvertor.convertToContentContributorDTOs(contentContributors));

        } catch (Exception e) {
            log.warn("Error populating related data for content {}: {}", contentDTO.getId(),
                    e.getMessage());
            // Don't throw exception for non-critical data population
        }
    }
}
