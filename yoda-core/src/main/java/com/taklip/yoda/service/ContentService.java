package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface ContentService {
    void addContent(Content content, Long categoryId) throws Exception;

    void addComment(Comment comment);

    void saveContentBrand(ContentBrand contentBrand);

    void addContentBrand(ContentBrand contentBrand);

    void addContentContributor(ContentContributor contentContributor);

    void deleteContent(Content content);

    void deleteContentFromCache(Content content);

    void deleteComment(Long commentId);

    void updateContent(Content content);

    Content updateContent(Content content, Long categoryId) throws Exception;

    void increaseContentHitCounter(Long id);

    Content getContent(Long contentId);

    ContentBrand getContentBrand(long id);

    List<ContentBrand> getContentBrands();

    int getContentHitCounter(long contentId);

    List<ContentBrand> getContentBrandByBrandId(long brandId);

    ContentContributor getContentContributor(long Id);

    List<ContentContributor> getContentContributor(long contentId, long userId);

    Comment getComment(Long commentId);

    List<Content> getContents(String title);

    List<Content> getContents();

    List<Content> getContentsFeatureData();

    PageInfo<Content> getContentsNotFeatureData(Integer offset, Integer limit);

    PageInfo<Content> getContents(Integer offset, Integer limit);

    List<Comment> getComments(long contentId);

    List<Comment> getCommentsBySiteId(int siteId);

    List<Comment> getCommentsByUserId(long userId);

    List<Content> search(int siteId, String title, Boolean published, String updateBy, String createBy, String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd) throws ParseException;

    Content updateContentImage(int siteId, Long contentId, MultipartFile featureImage);

//	Content updateDefaultContentImage(int siteId, Long userId, Long contentId, Long defaultImageId);

    List<Content> getContentByUserId(Long userId);

    ContentBrand updateContentBrand(ContentBrand contentBrand);

    void saveContentUserRate(Long contentId, String thumb);

    Integer getContentRate(Long contentId);

    ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId);

    void saveContent(Content content, Long categoryId) throws Exception;
}