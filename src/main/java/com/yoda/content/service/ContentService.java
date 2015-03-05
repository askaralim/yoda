package com.yoda.content.service;

import java.text.ParseException;
import java.util.List;

import com.yoda.content.model.Content;

public interface ContentService {
	Content addContent(
			Long siteId, Long userId, String naturalKey, String title,
			String shortDescription, String description, String pageTitle,
			String publishDate, String expireDate, Long updateBy,
			boolean isPublished) throws Exception;

	void deleteContent(Content content);

	void deleteContent(Long contentId);

	void updateContent(Content content);

	Content updateContent(
			Long contentId, Long siteId, String naturalKey, String title,
			String shortDescription, String description, String pageTitle,
			String publishDate, String expireDate, Long updateBy,
			boolean isPublished) throws Exception;

	Content getContent(Long siteId, Long contentId);

	Content getContent(Long siteId, String naturalKey);

	List<Content> getContents(Long siteId, String title);

	List<Content> getContents(Long siteId);

	List<Content> search(
			Long siteId, String title, String published, String updateBy,
			String createBy, String publishDateStart, String publishDateEnd,
			String expireDateStart, String expireDateEnd) throws ParseException;

//	Content updateContentImage(Long siteId, Long userId, Long contentId, ContentImage contentImage);

	Content updateContentImage(Long siteId, Long userId, Long contentId, String featureImage);

//	Content updateDefaultContentImage(Long siteId, Long userId, Long contentId, Long defaultImageId);

	Content deleteContentImage(Long siteId, Long userId, Long contentId, Long imageId);

	List<Content> getContent(Long userId);

	Content updateContent(Long siteId, Long contentId, Long userId, String title, String shortDescription, String description) throws Exception;
}