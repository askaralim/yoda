package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ContentMapper;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.CommentService;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.ContentContributorService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ContentUserRateService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private ContentUserRateService contentUserRateService;

    @Autowired
    private RedisService redisService;

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
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Override
    public void create(Content content, Long categoryId) throws Exception {
        content.setNaturalKey(encode(content.getTitle()));
        content.setHitCounter(0);
        content.setScore(0);

        if (null != categoryId) {
            content.setCategoryId(categoryId);
        }

        this.save(content);

        if (!content.isFeatureData() && content.isPublished() && content.isHomePage()) {
            this.setContentNotFeatureDatasIntoCache(content.getId());
            redisService.incr(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);
        } else if (content.isFeatureData() && content.isPublished() && content.isHomePage()) {
            this.setContentFeatureDatasIntoCache(content.getId());
        }

        this.setContentIntoCache(content);
    }

    @Override
    public void deleteContent(Long contentId) {
        this.removeById(contentId);
        deleteContentFromCache(contentId);
    }

    @Override
    public void deleteContents(List<Long> ids) {
        this.removeByIds(ids);
        ids.stream().forEach(id -> deleteContentFromCache(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> getContentByUserId(Long userId) {
        return baseMapper.getContentsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ContentDTO getContentDetail(Long id) {
        // Content content = getContentFromCache(id);

        // if (null != content) {
        // return modelConvertor.convertToContentDTO(content);
        // }

        Content content = baseMapper.selectById(id);

        log.info("Content: {}", JSON.toJSONString(content));

        this.setContentIntoCache(content);

        ContentDTO contentDTO = modelConvertor.convertToContentDTO(content);

        List<Item> items = itemService.getItemsByContentId(content.getId());
        List<Comment> comments = commentService.getCommentsByContentId(content.getId());
        List<ContentBrand> contentBrands = contentBrandService.getContentBrandsByContentId(content.getId());
        List<ContentContributor> contentContributors = contentContributorService
                .getContentContributorsByContentId(content.getId());

        contentDTO.setBrands(modelConvertor.convertToContentBrandDTOs(contentBrands));
        contentDTO.setCategory(modelConvertor.convertToCategoryDTO(categoryService.getById(content.getCategoryId())));
        contentDTO.setComments(modelConvertor.convertToCommentDTOs(comments));
        contentDTO.setContributors(modelConvertor.convertToContentContributorDTOs(contentContributors));
        contentDTO.setItems(modelConvertor.convertToItemDTOs(items));
        contentDTO.setCreateBy(modelConvertor.convertToUserDTO(userService.getById(content.getCreateBy())));
        contentDTO.setUpdateBy(modelConvertor.convertToUserDTO(userService.getById(content.getUpdateBy())));

        return contentDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Content getContentById(Long id) {
        return baseMapper.getContentById(id);
    }

    @Transactional(readOnly = true)
    public List<ContentDTO> getSimpleContent(List<Long> ids) {
        List<ContentDTO> contentDTOs = new ArrayList<>();

        for (Long id : ids) {
            Content content = getSimpleContentFromCache(id);

            if (null != content) {
                contentDTOs.add(modelConvertor.convertToContentDTO(content));
            }
        }

        if (!CollectionUtils.isEmpty(contentDTOs)) {
            return contentDTOs;
        }

        List<Content> contents = baseMapper.getContentByIds(ids);

        for (Content content : contents) {
            this.setContentIntoCache(content);
        }

        return contents.stream().map(modelConvertor::convertToContentDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> getContents() {
        return baseMapper.getContents();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Content> getContents(Integer offset, Integer limit) {
        return this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Content>().orderByDesc(Content::getUpdateTime));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> getContents(String title) {
        return baseMapper.getContentsByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContentDTO> getContentsFeatureData() {
        List<ContentDTO> contentDTOs = new ArrayList<>();

        List<Content> contentList = baseMapper.getContentsByFeatureData(new Page<>(0, 10), true).getRecords();

        // for (Content content : contentList) {
        // this.setContentFeatureDatasIntoCache(content.getId());
        // }

        for (Content content : contentList) {
            contentDTOs.add(modelConvertor.convertToContentDTO(content));
        }

        return contentDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> getContentsNotFeatureData(Integer offset, Integer limit) {
        Page<Content> page = new Page<>(offset, limit);
        Page<ContentDTO> pageDTO = new Page<>(offset, limit);

        // List<ContentDTO> contentDTOs = new ArrayList<>();

        // List<String> ids = this.getContentNotFeatureDatasFromCache(offset, limit);

        // if (CollectionUtils.isEmpty(ids)) {
        page = baseMapper.getContentsByFeatureData(page, false);

        List<Content> contents = page.getRecords();

        // setContentNotFeatureDataCountIntoCache(page.getTotal());

        // for (Content content : contents) {
        // this.setContentNotFeatureDatasIntoCache(content.getId());
        // }

        pageDTO.setRecords(contents.stream().map(modelConvertor::convertToContentDTO).collect(Collectors.toList()));
        pageDTO.setTotal(page.getTotal());
        // } else {
        // contentDTOs =
        // this.getSimpleContent(ids.stream().map(Long::valueOf).collect(Collectors.toList()));

        // pageDTO.setTotal(getContentNotFeatureDataCountFromCache());
        // pageDTO.setRecords(contentDTOs);
        // }

        return pageDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Content> search(
            Long siteId, String title, Boolean published,
            String updateBy, String createBy, String publishDateStart,
            String publishDateEnd, String expireDateStart, String expireDateEnd) {
        return baseMapper.search(
                siteId, title, published, createBy, updateBy, publishDateStart,
                publishDateEnd, expireDateStart, expireDateEnd);
    }

    @Override
    public void updateContent(Content content) {
        this.updateById(content);

        deleteContentFromCache(content.getId());
    }

    @Override
    public Content updateContent(Content content, Long categoryId)
            throws Exception {
        Content contentDb = this.getById(content.getId());

        contentDb.setNaturalKey(encode(content.getTitle()));
        contentDb.setTitle(content.getTitle());
        contentDb.setShortDescription(content.getShortDescription());
        contentDb.setDescription(content.getDescription());
        contentDb.setPageTitle(content.getPageTitle());
        contentDb.setPublishDate(content.getPublishDate());
        contentDb.setExpireDate(content.getExpireDate());
        contentDb.setPublished(content.isPublished());
        contentDb.setFeatureData(content.isFeatureData());

        if (null != categoryId) {
            contentDb.setCategoryId(categoryId);
        }

        this.updateById(contentDb);

        // new ContentIndexer().updateIndex(content);

        deleteContentFromCache(contentDb.getId());

        return contentDb;
    }

    @Override
    public void increaseContentHitCounter(Long id) {
        Content content = this.getById(id);

        content.setHitCounter(content.getHitCounter() + 1);

        this.updateById(content);

        this.setContentHitCounterIntoCached(id, content.getHitCounter());
    }

    @Override
    public Content updateContentImage(long siteId, Long id, MultipartFile image) {
        Content content = this.getById(id);

        imageUpload.deleteImage(content.getFeaturedImage());

        try {
            String imagePath = fileService.save(ContentTypeEnum.CONTENT.getType(), id, image);

            content.setFeaturedImage(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.updateById(content);

        deleteContentFromCache(content.getId());

        return content;
    }

    @Override
    public void resetHitCounter(Long contentId) {
        this.update(new LambdaUpdateWrapper<Content>().set(Content::getHitCounter, 0).eq(Content::getId, contentId));

        deleteContentFromCache(contentId);
    }

    private Content getContentFromCache(Long contentId) {
        Content content = null;

        Map<String, String> map = redisService.getMap(Constants.REDIS_CONTENT + ":" + contentId);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            content = objectMapper.convertValue(map, Content.class);
            content.setHitCounter(getContentHitCounter(contentId));
            content.setScore(contentUserRateService.getTotalRateByContentId(content.getId()));

        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to Content: {}", e.getMessage());
            return null;
        }

        // if (null != map && map.size() > 0) {

        // if (StringUtils.isNoneBlank(categoryId) &&
        // !"nil".equalsIgnoreCase(categoryId)) {
        // Category category = categoryService.getCategory(Long.valueOf(categoryId));
        // content.setCategory(category);
        // }

        // List<ContentBrand> contentBrands = new ArrayList<>();
        // List<String> contentBrandIds = redisService
        // .getList(Constants.REDIS_CONTENT_BRAND_LIST + ":" + content.getId());

        // if (null != contentBrandIds && !contentBrandIds.isEmpty()) {
        // for (String contentBrandId : contentBrandIds) {
        // ContentBrand contentBrand =
        // contentBrandService.getById(Long.valueOf(contentBrandId));

        // contentBrands.add(contentBrand);
        // }
        // }

        // content.setContentBrands(contentBrands);

        // List<ContentContributor> contentContributors = new ArrayList<>();
        // List<String> contentContributorIds = redisService
        // .getList(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getId());

        // if (null != contentContributorIds) {
        // for (String ccId : contentContributorIds) {
        // ContentContributor cc =
        // contentContributorService.getById(Long.valueOf(ccId));

        // contentContributors.add(cc);
        // }
        // }

        // content.setContentContributors(contentContributors);
        // }

        return content;
    }

    private Content getSimpleContentFromCache(Long contentId) {
        Map<String, String> map = redisService.getMap(Constants.REDIS_CONTENT + ":" + contentId);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            return objectMapper.convertValue(map, Content.class);
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to Content: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteContentFromCache(Long id) {
        redisService.delete(Constants.REDIS_CONTENT + ":" + id);
        redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + id);
        redisService.delete(Constants.REDIS_CONTENT_BRAND_LIST + ":" + id);
        redisService.delete(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST);
        redisService.delete(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST);
        redisService.delete(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);

        // for (ContentBrand cb : content.getContentBrands()) {
        // deleteContentBrandFromCache(cb.getId());
        // }

        // for (ContentContributor cc : content.getContentContributors()) {
        // deleteContentContributorFromCache(cc.getId());
        // }
    }

    private void setContentIntoCache(Content content) {
        if (Objects.isNull(content)) {
            return;
        }

        try {
            Map<String, String> map = objectMapper.convertValue(content, new TypeReference<Map<String, String>>() {
            });

            redisService.setMap(Constants.REDIS_CONTENT + ":" + content.getId(), map);
        } catch (IllegalArgumentException e) {
            log.error("Failed to cache content {}: {}", content.getId(), e.getMessage());
        }

        // setContentHitCounterIntoCached(content.getId(), content.getHitCounter());

        // redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" +
        // content.getId());

        // for (ContentContributor cc : content.getContentContributors()) {
        // redisService.listRightPushAll(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":"
        // + content.getId(),
        // String.valueOf(cc.getId()));
        // setContentContributorIntoCache(cc);
        // }

        // redisService.delete(Constants.REDIS_CONTENT_BRAND_LIST + ":" +
        // content.getId());

        // for (ContentBrand cb : content.getContentBrands()) {
        // redisService.listRightPushAll(Constants.REDIS_CONTENT_BRAND_LIST + ":" +
        // content.getId(),
        // String.valueOf(cb.getId()));
        // setContentBrandIntoCache(cb);
        // }
    }

    private List<String> getContentFeatureDatasFromCache() {
        List<String> ids = redisService.getList(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST);

        return ids;
    }

    private void setContentFeatureDatasIntoCache(long id) {
        redisService.listRightPushAll(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST, String.valueOf(id));
    }

    private List<String> getContentNotFeatureDatasFromCache(Integer offset, Integer limit) {
        List<String> ids = redisService.getList(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, offset,
                offset + limit - 1);

        return ids;
    }

    private void setContentNotFeatureDatasIntoCache(long id) {
        redisService.listRightPushAll(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, String.valueOf(id));
    }

    private Long getContentNotFeatureDataCountFromCache() {
        String count = redisService.get(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);

        if (StringUtils.isNoneBlank(count)) {
            return Long.valueOf(count);
        } else {
            Long total = baseMapper.selectCount(new LambdaQueryWrapper<Content>().eq(Content::isFeatureData, false));

            setContentNotFeatureDataCountIntoCache(total);

            return total;
        }
    }

    private void setContentNotFeatureDataCountIntoCache(long count) {
        redisService.set(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST, String.valueOf(count));
    }

    @Override
    public int getContentHitCounter(long id) {
        String hit = redisService.get(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + id);

        if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
            return Integer.valueOf(hit);
        } else {
            Content content = this.getById(id);

            setContentHitCounterIntoCached(content.getId(), content.getHitCounter());

            return content.getHitCounter();
        }
    }

    private void setContentHitCounterIntoCached(long id, int hitCounter) {
        redisService.set(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + id, String.valueOf(hitCounter));
    }

    public String encode(String input)
            throws UnsupportedEncodingException {
        input = input.replaceAll("/", "_*_");
        return URLEncoder.encode(input, "UTF-8");
    }
}