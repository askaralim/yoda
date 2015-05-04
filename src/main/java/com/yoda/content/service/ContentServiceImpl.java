package com.yoda.content.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.content.dao.CommentDAO;
import com.yoda.content.dao.ContentDAO;
import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.homepage.dao.HomePageDAO;
import com.yoda.homepage.model.HomePage;
import com.yoda.menu.dao.MenuDAO;
import com.yoda.menu.model.Menu;
import com.yoda.util.Format;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	@Autowired
	private ContentDAO contentDAO;

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private HomePageDAO homePageDAO;

	@Autowired
	private MenuDAO menuDAO;

	public Content addContent(
			int siteId, Long userId, String naturalKey,
			String title, String shortDescription, String description,
			String pageTitle, String publishDate, String expireDate,
			Long updateBy, boolean isPublished) throws Exception {
		Content content = new Content();

		content.setSiteId(siteId);
		content.setNaturalKey(Utility.encode(naturalKey));
		content.setTitle(title);
		content.setShortDescription(shortDescription);
		content.setDescription(description);
		content.setPageTitle(pageTitle);
		content.setPublishDate(Format.getDate(publishDate));
		content.setExpireDate(Format.getDate(expireDate));
		content.setUpdateBy(updateBy);
		content.setUpdateDate(new Date());
		content.setPublished(isPublished);
		content.setHitCounter(0);
		content.setScore(0);
		content.setCreateBy(userId);
		content.setCreateDate(new Date());

		contentDAO.save(content);

		return content;
	}

	public void addComment(Comment comment) {
		commentDAO.save(comment);
	}

	public void deleteContent(Content content) {
//		ContentImage contentImage = content.getImage();
//		if (contentImage != null) {
//			contentImageDAO.delete(contentImage);
//		}

//		Iterator iterator = content.getImages().iterator();
//
//		while (iterator.hasNext()) {
//			contentImage = (ContentImage)iterator.next();
//
//			contentImageDAO.delete(contentImage);
//		}

		List<HomePage> homePages = homePageDAO.getBySiteId(content.getSiteId());

		for (HomePage homePage : homePages) {
			if (homePage.getContent() != null) {
				if (content.getContentId() == homePage.getContent().getContentId()) {
					homePageDAO.delete(homePage);
				}
			}
		}

		Iterator iterator = (Iterator)content.getMenus().iterator();

		while (iterator.hasNext()) {
			Menu menu = (Menu) iterator.next();

			menu.setContent(null);

			menuDAO.update(menu);
		}

		contentDAO.delete(content);
	}

	public void deleteContent(Long contentId) {
		Content content = contentDAO.getById(contentId);

		deleteContent(content);
	}

	public void deleteComment(int commentId) {
		Comment comment = commentDAO.getById(commentId);

		commentDAO.delete(comment);
	}

//	public Content deleteContentImage(
//			int siteId, Long userId, Long contentId, Long imageId) {
//		Content content = getContent(siteId, contentId);
//
//		ContentImage defaultImage = content.getImage();
//
//		if (imageId != null) {
//			if (defaultImage != null && (defaultImage.getImageId() == imageId)) {
//
//				content.setImage(null);
//
//				updateContent(content);
//
//				contentImageDAO.delete(defaultImage);
//
//				defaultImage = null;
//			}
//			else {
//				ContentImage contentImage = contentImageDAO.getContentImageBySId_Id(siteId, imageId);
//
//				contentImageDAO.delete(contentImage);
//			}
//		}
//
//		return content;
//	}

	@Transactional(readOnly = true)
	public List<Content> getContent(Long userId) {
		return contentDAO.getByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Content getContent(int siteId, Long contentId) {
		return contentDAO.getContentById(siteId, contentId);
	}

	@Transactional(readOnly = true)
	public Content getContent(int siteId, String contentNaturalKey) {
		return contentDAO.getContentBySiteId_NaturalKey(siteId, contentNaturalKey);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(int siteId) {
		return contentDAO.getContents(siteId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(int siteId, String contentTitle) {
		return contentDAO.getContents(siteId, contentTitle);
	}

	@Transactional(readOnly = true)
	public Comment getComment(int commentId) {
		return commentDAO.getById(commentId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getComments(long contentId) {
		return commentDAO.getCommentsByContentId(contentId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getCommentsBySiteId(int siteId) {
		return commentDAO.getCommentsBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getCommentsByUserId(long userId) {
		return commentDAO.getCommentsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public List<Content> search(
			int siteId, String title, Boolean published,
			String updateBy, String createBy, String publishDateStart,
			String publishDateEnd, String expireDateStart, String expireDateEnd)
		throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Query query = null;

		String sql = "select content from Content content where siteId = :siteId ";

		if (Validator.isNotNull(title) && title.length() > 0) {
			sql += "and title like : title ";
		}

		if (Validator.isNotNull(published)) {
			sql += "and published = :published ";
		}

		sql += "and publishDate between :publishDateStart and :publishDateEnd ";
		sql += "and expireDate between :expireDateStart and :expireDateEnd ";

		if (Validator.isNotNull(updateBy)) {
			sql += "and updateBy = :updateBy ";
		}

		if (Validator.isNotNull(createBy)) {
			sql += "and createBy = :createBy ";
		}

		query = contentDAO.getSession().createQuery(sql);

		Date highDate = dateFormat.parse("31-12-2999");
		Date lowDate = dateFormat.parse("01-01-1900");
		Date date = null;

		query.setLong("siteId", siteId);

		if (Validator.isNotNull(title) && title.length() > 0) {
			query.setString("title", "%" + title + "%");
		}

		if (Validator.isNotNull(published)) {
			query.setBoolean("published", published);
		}

		if (publishDateStart.length() > 0) {
			date = dateFormat.parse(publishDateStart);
			query.setDate("publishDateStart", date);
		}
		else {
			query.setDate("publishDateStart", lowDate);
		}

		if (publishDateEnd.length() > 0) {
			date = dateFormat.parse(publishDateEnd);
			query.setDate("publishDateEnd", date);
		}
		else {
			query.setDate("publishDateEnd", highDate);
		}

		if (expireDateStart.length() > 0) {
			date = dateFormat.parse(expireDateStart);
			query.setDate("expireDateStart", date);
		}
		else {
			query.setDate("expireDateStart", lowDate);
		}

		if (expireDateEnd.length() > 0) {
			date = dateFormat.parse(expireDateEnd);
			query.setDate("expireDateEnd", date);
		}
		else {
			query.setDate("expireDateEnd", highDate);
		}

		if (Validator.isNotNull(updateBy)) {
			query.setString("updateBy", updateBy);
		}

		if (Validator.isNotNull(createBy)) {
			query.setString("createBy", createBy);
		}
//		if (selectedSections != null) {
//			int index = 0;
//
//			for (int i = 0; i < selectedSections.length; i++) {
//				Long sectionIds[] = sectionService.getSectionIdTreeList(
//					siteId, Format.getLong(selectedSections[i]));
//
//				for (int j = 0; j < sectionIds.length; j++) {
//					query.setLong(
//						"selectedSection" + index++, sectionIds[j].longValue());
//				}
//			}
//		}

		return query.list();
	}

	public void updateContent(Content content) {
		contentDAO.update(content);
	}

	public Content updateContent(
		int siteId, Long userId, Long contentId, String title,
		String shortDescription, String description) throws Exception  {
		Content content = getContent(siteId, contentId);

		return this.updateContent(
			contentId, siteId, content.getNaturalKey(), title, shortDescription,
			description, content.getPageTitle(), String.valueOf(content.getPublishDate()),
			String.valueOf(content.getExpireDate()), userId, content.isPublished());	
	}

	public Content updateContent(
		Long contentId, int siteId, String naturalKey,
		String title, String shortDescription, String description,
		String pageTitle, String publishDate, String expireDate,
		Long updateBy, boolean isPublished) throws Exception {
		Content content = contentDAO.getContentById(siteId, contentId);

		content.setContentId(contentId);
		content.setSiteId(siteId);
		content.setNaturalKey(Utility.encode(naturalKey));
		content.setTitle(title);
		content.setShortDescription(shortDescription);
		content.setDescription(description);
		content.setPageTitle(pageTitle);
		content.setPublishDate(Format.getDate(publishDate));
		content.setExpireDate(Format.getDate(expireDate));
		content.setUpdateBy(updateBy);
		content.setUpdateDate(new Date());
		content.setPublished(isPublished);

		contentDAO.update(content);

		return content;
	}

//	public Content updateContentImage(
//			Long siteId, Long userId, Long contentId, ContentImage contentImage) {
//		Content content = getContent(siteId, contentId);
//
////		if (content.getImage() == null) {
//		content.setImage(contentImage);
////		}
////		else {
////			content.getImages().add(contentImage);
////		}
//
//		content.setUpdateBy(userId);
//		content.setUpdateDate(new Date());
//
//		contentDAO.update(content);
//
//		return content;
//	}

	public Content updateContentImage(
			int siteId, Long userId, Long contentId, String featuredImage) {
		Content content = getContent(siteId, contentId);

		content.setFeaturedImage(featuredImage);

		content.setUpdateBy(userId);
		content.setUpdateDate(new Date());

		contentDAO.update(content);

		return content;
	}

//	public Content updateDefaultContentImage(
//			Long siteId, Long userId, Long contentId, Long defaultImageId) {
//		Content content = getContent(siteId, contentId);
//
//		ContentImage contentImage = contentImageDAO.getContentImageBySId_Id(siteId, defaultImageId);
//
////		ContentImage currentImage = content.getImage();
//
////		if (currentImage != null) {
////			content.getImages().add(currentImage);
////		}
//
//		content.setImage(contentImage);
////		content.getImages().remove(contentImage);
//		content.setUpdateBy(userId);
//		content.setUpdateDate(new Date());
//
//		contentDAO.update(content);
//
//		return content;
//	}

//	public Content deleteContentImage(
//			Long siteId, Long userId, Long contentId, Long[] imageIds) {
//		Content content = getContent(siteId, contentId);
//
//		ContentImage defaultImage = content.getImage();
//
//		if (imageIds != null) {
//			for (int i = 0; i < imageIds.length; i++) {
//				if (defaultImage != null && (defaultImage.getImageId() == imageIds[i])) {
//
//					content.setImage(null);
//
//					updateContent(content);
//
//					contentImageDAO.delete(defaultImage);
//
//					defaultImage = null;
//				}
//				else {
//					ContentImage contentImage = contentImageDAO.getContentImageBySId_Id(siteId, imageIds[i]);
//
//					contentImageDAO.delete(contentImage);
//				}
//			}
//		}
//
//		content.setRecUpdateBy(userId);
//		content.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//
//		if (content.getImage() == null) {
//			Set<ContentImage> images = content.getImages();
//
//			if (!images.isEmpty()) {
//				ContentImage contentImage = (ContentImage) images.iterator().next();
//
//				content.setImage(contentImage);
//
//				images.remove(contentImage);
//			}
//		}
//
//		return content;
//	}
}