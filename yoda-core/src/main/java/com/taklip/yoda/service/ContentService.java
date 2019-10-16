package com.taklip.yoda.service;

import java.text.ParseException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.ContentUserRate;
import com.taklip.yoda.model.Pagination;

public interface ContentService {
	void addContent(Content content, Long categoryId) throws Exception;

	void addComment(Comment comment);

	void saveContentBrand(ContentBrand contentBrand);

	void addContentBrand(ContentBrand contentBrand);

	void addContentContributor(ContentContributor contentContributor);

	void deleteContent(Content content);

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

	Pagination<Content> getContentsNotFeatureData(int offset, int limit);

	Pagination<Content> getContents(RowBounds rowBounds);

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