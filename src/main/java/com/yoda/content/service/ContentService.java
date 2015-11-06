package com.yoda.content.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.content.model.ContentBrand;

public interface ContentService {
	void addContent(int siteId, Content content, Integer categoryId);

	Content addContent(
			int siteId, String naturalKey, String title,
			String shortDescription, String description, String pageTitle,
			Integer categoryId, String publishDate, String expireDate,
			boolean isPublished) throws Exception;

	void addComment(Comment comment);

	void addContentBrand(ContentBrand contentBrand);

	void deleteContent(Content content);

//	void deleteContent(Long contentId);

	void deleteComment(int commentId);

	void updateContent(Content content);

	Content updateContent(int siteId, Content content, Integer categoryId) throws Exception;

	Content updateContent(
			Long contentId, int siteId, String naturalKey, String title,
			String shortDescription, String description, String pageTitle,
			Integer categoryId, String publishDate, String expireDate,
			boolean isPublished) throws Exception;

	Content getContent(Long contentId);

//	Content getContent(String naturalKey);

	ContentBrand getContentBrand(long contentBrandId);

	Comment getComment(int commentId);

	List<Content> getContents(String title);

	List<Content> getContents(int siteId);

	List<Comment> getComments(long contentId);

	List<Comment> getCommentsBySiteId(int siteId);

	List<Comment> getCommentsByUserId(long userId);

	List<Content> search(int siteId, String title, Boolean published, String updateBy, String createBy, String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd) throws ParseException;

//	Content updateContentImage(int siteId, Long userId, Long contentId, ContentImage contentImage);

	Content updateContentImage(int siteId, Long contentId, MultipartFile featureImage);

//	Content updateDefaultContentImage(int siteId, Long userId, Long contentId, Long defaultImageId);

	List<Content> getContentByUserId(Long userId);

	Content updateContent(int siteId, Long contentId, String title, String shortDescription, String description) throws Exception;

	ContentBrand updateContentBrand(ContentBrand contentBrand);
}