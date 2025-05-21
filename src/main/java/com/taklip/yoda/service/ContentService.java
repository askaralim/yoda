package com.taklip.yoda.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Content;

public interface ContentService extends IService<Content> {
    void create(Content content, Long categoryId) throws Exception;

    void deleteContent(Long contentId);

    void deleteContents(List<Long> ids);

    void deleteContentFromCache(Long id);

    Content getContentById(Long contentId);

    ContentDTO getContentDetail(Long contentId);

    int getContentHitCounter(long contentId);

    List<Content> getContents(String title);

    List<Content> getContents();

    List<ContentDTO> getContentsFeatureData();

    Page<ContentDTO> getContentsNotFeatureData(Integer offset, Integer limit);

    Page<Content> getContents(Integer offset, Integer limit);

    List<Content> getContentByUserId(Long userId);

    void increaseContentHitCounter(Long id);

    List<Content> search(Long siteId, String title, Boolean published, String updateBy, String createBy,
            String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd)
            throws ParseException;

    void updateContent(Content content);

    Content updateContent(Content content, Long categoryId) throws Exception;

    Content updateContentImage(long siteId, Long contentId, MultipartFile featureImage);

    void resetHitCounter(Long contentId);
}