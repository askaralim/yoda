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
import com.yoda.content.dao.ContentImageDAO;
import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.content.model.ContentImage;
import com.yoda.homepage.dao.HomePageDAO;
import com.yoda.homepage.model.HomePage;
import com.yoda.menu.dao.MenuDAO;
import com.yoda.menu.model.Menu;
import com.yoda.util.Format;
import com.yoda.util.Utility;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	@Autowired
	private ContentDAO contentDAO;

	@Autowired
	private CommentDAO commentDAO;

	@Autowired
	private ContentImageDAO contentImageDAO;

	@Autowired
	private HomePageDAO homePageDAO;

	@Autowired
	private MenuDAO menuDAO;

	public Content addContent(
			Long siteId, Long userId, String naturalKey,
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
		content.setHitCounter(0l);
		content.setScore(0l);
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

	public Content deleteContentImage(
			Long siteId, Long userId, Long contentId, Long imageId) {
		Content content = getContent(siteId, contentId);

		ContentImage defaultImage = content.getImage();

		if (imageId != null) {
			if (defaultImage != null && (defaultImage.getImageId() == imageId)) {

				content.setImage(null);

				updateContent(content);

				contentImageDAO.delete(defaultImage);

				defaultImage = null;
			}
			else {
				ContentImage contentImage = contentImageDAO.getContentImageBySId_Id(siteId, imageId);

				contentImageDAO.delete(contentImage);
			}
		}

		return content;
	}

	@Transactional(readOnly = true)
	public List<Content> getContent(Long userId) {
		return contentDAO.getByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Content getContent(Long siteId, Long contentId) {
		return contentDAO.getContentById(siteId, contentId);
	}

	@Transactional(readOnly = true)
	public Content getContent(Long siteId, String contentNaturalKey) {
		return contentDAO.getContentBySiteId_NaturalKey(siteId, contentNaturalKey);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(Long siteId) {
		return contentDAO.getContents(siteId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(Long siteId, String contentTitle) {
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
			Long siteId, String title, String published,
			String updateBy, String createBy,
			String publishDateStart, String publishDateEnd,
			String expireDateStart, String expireDateEnd) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Query query = null;

		String sql = "select content from Content content where siteId = :siteId ";

		if (title.length() > 0) {
			sql += "and title like :contentTitle ";
		}

		if (!published.equals("*")) {
			sql += "and published = :published ";
		}

		sql += "and publishDate between :contentPublishOnStart and :contentPublishOnEnd ";
		sql += "and expireDate between :contentExpireOnStart and :contentExpireOnEnd ";

		if (!updateBy.equals("All")) {
			sql += "and updateBy = :recUpdateBy ";
		}

		if (!createBy.equals("All")) {
			sql += "and createBy = :recCreateBy ";
		}

//		String selectedSections[] = command.getSrSelectedSections();
//
//		if (selectedSections != null) {
//
//			sql += "and sectionId in (";
//
//			int index = 0;
//
//			for (int i = 0; i < selectedSections.length; i++) {
//				Long sectionIds[] = sectionService.getSectionIdTreeList(
//					siteId, Format.getLong(selectedSections[i]));
//
//				for (int j = 0; j < sectionIds.length; j++) {
//
//					if (index > 0) {
//						sql += ",";
//					}
//
//					sql += ":selectedSection" + index++;
//				}
//
//				sql += ") ";
//			}
//		}

		query = contentDAO.getSession().createQuery(sql);

		Date highDate = dateFormat.parse("31-12-2999");
		Date lowDate = dateFormat.parse("01-01-1900");
		Date date = null;

		query.setLong("siteId", siteId);

		if (title.length() > 0) {
			query.setString("contentTitle", "%" + title + "%");
		}
		if (!published.equals("*")) {
			query.setString("published", published);
		}
		if (publishDateStart.length() > 0) {
			date = dateFormat.parse(publishDateStart);
			query.setDate("contentPublishOnStart", date);
		} else {
			query.setDate("contentPublishOnStart", lowDate);
		}
		if (publishDateEnd.length() > 0) {
			date = dateFormat.parse(publishDateEnd);
			query.setDate("contentPublishOnEnd", date);
		} else {
			query.setDate("contentPublishOnEnd", highDate);
		}
		if (expireDateStart.length() > 0) {
			date = dateFormat.parse(expireDateStart);
			query.setDate("contentExpireOnStart", date);
		} else {
			query.setDate("contentExpireOnStart", lowDate);
		}
		if (expireDateEnd.length() > 0) {
			date = dateFormat.parse(expireDateEnd);
			query.setDate("contentExpireOnEnd", date);
		} else {
			query.setDate("contentExpireOnEnd", highDate);
		}
		if (!updateBy.equals("All")) {
			query.setString("recUpdateBy", updateBy);
		}
		if (!createBy.equals("All")) {
			query.setString("recCreateBy", createBy);
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
		Long siteId, Long userId, Long contentId, String title,
		String shortDescription, String description) throws Exception  {
		Content content = getContent(siteId, contentId);

		return this.updateContent(
			contentId, siteId, content.getNaturalKey(), title, shortDescription,
			description, content.getPageTitle(), String.valueOf(content.getPublishDate()),
			String.valueOf(content.getExpireDate()), userId, content.isPublished());	
	}

	public Content updateContent(
		Long contentId, Long siteId, String naturalKey,
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
			Long siteId, Long userId, Long contentId, String featuredImage) {
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