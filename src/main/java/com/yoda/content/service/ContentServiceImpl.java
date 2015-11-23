package com.yoda.content.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.category.model.Category;
import com.yoda.category.persistence.CategoryMapper;
import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.content.model.ContentBrand;
import com.yoda.content.persistence.CommentMapper;
import com.yoda.content.persistence.ContentBrandMapper;
import com.yoda.content.persistence.ContentMapper;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.kernal.util.FileUploader;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.persistence.MenuMapper;
import com.yoda.util.Format;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
//	@Autowired
//	private CategoryDAO categoryDAO;
	@Autowired
	private CategoryMapper categoryMapper;

//	@Autowired
//	private ContentDAO contentDAO;

//	@Autowired
//	private ContentBrandDAO contentBrandDAO;

	@Autowired
	private ContentBrandMapper contentBrandMapper;

	@Autowired
//	private CommentDAO commentDAO;
	private CommentMapper commentMapper;

//	@Autowired
//	private HomePageDAO homePageDAO;

	@Autowired
//	private MenuDAO menuDAO;
	private MenuMapper menuMapper;

	@Autowired
	HomePageService homePageService;

//	@Autowired
//	HomePageMapper homePageMapper;

	@Autowired
	ContentMapper contentMapper;

	public void addContent(int siteId, Content content, Integer categoryId) {
		try {
			content.setNaturalKey(Utility.encode(content.getTitle()));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

//		content.setCreateBy(user);
//		content.setCreateDate(new Date());
		content.setHitCounter(0);
		content.setSiteId(siteId);
		content.setScore(0);
//		content.setUpdateBy(user);
//		content.setUpdateDate(new Date());

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

//		contentDAO.save(content);

		content.preInsert();

		contentMapper.insert(content);

		HomePage homePage = getHomePage(siteId, content.getContentId());

		if (content.isHomePage()) {
			if (homePage == null) {
				homePageService.add(siteId, false, content);
			}
		}
		else {
			if (homePage != null) {
				homePageService.delete(homePage);
			}
		}

		new ContentIndexer().createIndex(content);
	}

	public Content addContent(
			int siteId, String naturalKey,
			String title, String shortDescription, String description,
			String pageTitle, Integer categoryId, String publishDate, String expireDate,
			boolean isPublished) throws Exception {
		Content content = new Content();

		content.setSiteId(siteId);
		content.setNaturalKey(Utility.encode(naturalKey));
		content.setTitle(title);
		content.setShortDescription(shortDescription);
		content.setDescription(description);
		content.setPageTitle(pageTitle);
		content.setPublishDate(Format.getDate(publishDate));
		content.setExpireDate(Format.getDate(expireDate));
//		content.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		content.setUpdateDate(new Date());
		content.setPublished(isPublished);
		content.setHitCounter(0);
		content.setScore(0);
//		content.setCreateBy(PortalUtil.getAuthenticatedUser());
//		content.setCreateDate(new Date());

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

		content.preUpdate();

//		contentDAO.save(content);
		contentMapper.insert(content);

		new ContentIndexer().createIndex(content);

		return content;
	}

	public void addComment(Comment comment) {
		commentMapper.insert(comment);
	}

	public void addContentBrand(ContentBrand contentBrand) {
		contentBrand.preInsert();

		contentBrandMapper.insert(contentBrand);
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

		List<HomePage> homePages = homePageService.getHomePages(content.getSiteId());

		for (HomePage homePage : homePages) {
			if (homePage.getContent() != null) {
				if (content.getContentId().longValue() == homePage.getContent().getContentId().longValue()) {
//					homePageDAO.delete(homePage);
					homePageService.delete(homePage);
				}
			}
		}

		Iterator iterator = (Iterator)content.getMenus().iterator();

		while (iterator.hasNext()) {
			Menu menu = (Menu) iterator.next();

			menu.setContent(null);

			menuMapper.update(menu);
		}

		new ContentIndexer().deleteIndex(content.getContentId());

//		contentDAO.delete(content);
		contentMapper.delete(content);
	}

//	public void deleteContent(Long contentId) {
//		Content content = contentDAO.getById(contentId);
//
//		deleteContent(content);
//	}

	public void deleteComment(int commentId) {
		Comment comment = commentMapper.getById(commentId);

		commentMapper.delete(comment);
	}

	@Deprecated
	public void deleteContentImage(int siteId, Long contentId) {
		Content content = getContent(contentId);

		FileUploader fileUpload = FileUploader.getInstance();

		fileUpload.deleteFile(content.getFeaturedImage());

		content.setFeaturedImage(StringPool.BLANK);
		content.setUpdateBy(PortalUtil.getAuthenticatedUser());
		content.setUpdateDate(new Date());

		contentMapper.update(content);
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
	public List<Content> getContentByUserId(Long userId) {
//		return contentDAO.getByUserId(userId);
		return contentMapper.getContentsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Content getContent(Long contentId) {
//		return contentDAO.getContentById(siteId, contentId);
		return contentMapper.getById(contentId);
	}

//	@Transactional(readOnly = true)
//	public Content getContent(int siteId, String contentNaturalKey) {
//		return contentDAO.getContentBySiteId_NaturalKey(siteId, contentNaturalKey);
//	}

	@Transactional(readOnly = true)
	public ContentBrand getContentBrand(long contentBrandId) {
		return contentBrandMapper.getById(contentBrandId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(int siteId) {
//		return contentDAO.getContents(siteId);
		return contentMapper.getContentsBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(String title) {
//		return contentDAO.getContents(siteId, contentTitle);
		return contentMapper.getContentsByTitle(title);
	}

	@Transactional(readOnly = true)
	public Comment getComment(int commentId) {
		return commentMapper.getById(commentId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getComments(long contentId) {
		return commentMapper.getCommentsByContentId(contentId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getCommentsBySiteId(int siteId) {
		return commentMapper.getCommentsBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<Comment> getCommentsByUserId(long userId) {
		return commentMapper.getCommentsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public List<Content> search(
			int siteId, String title, Boolean published,
			String updateBy, String createBy, String publishDateStart,
			String publishDateEnd, String expireDateStart, String expireDateEnd)
		throws ParseException {
		return contentMapper.search(
			siteId, title, published, createBy, updateBy, publishDateStart,
			publishDateEnd, expireDateStart, expireDateEnd);

//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//		Query query = null;
//
//		title = title.trim();
//
//		String sql = "select content from Content content where siteId = :siteId ";
//
//		if (Validator.isNotNull(title) && title.length() > 0) {
//			sql += "and title like :title ";
//		}
//
//		if (Validator.isNotNull(published)) {
//			sql += "and published = :published ";
//		}
//
//		sql += "and publishDate between :publishDateStart and :publishDateEnd ";
//		sql += "and expireDate between :expireDateStart and :expireDateEnd ";
//
//		if (Validator.isNotNull(updateBy)) {
//			sql += "and updateBy = :updateBy ";
//		}
//
//		if (Validator.isNotNull(createBy)) {
//			sql += "and createBy = :createBy ";
//		}
//
//		query = contentDAO.getSession().createQuery(sql);
//
//		Date highDate = dateFormat.parse("31-12-2999");
//		Date lowDate = dateFormat.parse("01-01-1900");
//		Date date = null;
//
//		query.setLong("siteId", siteId);
//
//		if (Validator.isNotNull(title) && title.length() > 0) {
//			query.setString("title", "%" + title + "%");
//		}
//
//		if (Validator.isNotNull(published)) {
//			query.setBoolean("published", published);
//		}
//
//		if (publishDateStart.length() > 0) {
//			date = dateFormat.parse(publishDateStart);
//			query.setDate("publishDateStart", date);
//		}
//		else {
//			query.setDate("publishDateStart", lowDate);
//		}
//
//		if (publishDateEnd.length() > 0) {
//			date = dateFormat.parse(publishDateEnd);
//			query.setDate("publishDateEnd", date);
//		}
//		else {
//			query.setDate("publishDateEnd", highDate);
//		}
//
//		if (expireDateStart.length() > 0) {
//			date = dateFormat.parse(expireDateStart);
//			query.setDate("expireDateStart", date);
//		}
//		else {
//			query.setDate("expireDateStart", lowDate);
//		}
//
//		if (expireDateEnd.length() > 0) {
//			date = dateFormat.parse(expireDateEnd);
//			query.setDate("expireDateEnd", date);
//		}
//		else {
//			query.setDate("expireDateEnd", highDate);
//		}
//
//		if (Validator.isNotNull(updateBy)) {
//			query.setString("updateBy", updateBy);
//		}
//
//		if (Validator.isNotNull(createBy)) {
//			query.setString("createBy", createBy);
//		}
////		if (selectedSections != null) {
////			int index = 0;
////
////			for (int i = 0; i < selectedSections.length; i++) {
////				Long sectionIds[] = sectionService.getSectionIdTreeList(
////					siteId, Format.getLong(selectedSections[i]));
////
////				for (int j = 0; j < sectionIds.length; j++) {
////					query.setLong(
////						"selectedSection" + index++, sectionIds[j].longValue());
////				}
////			}
////		}
//
//		return query.list();
	}

	public void updateContent(Content content) {
//		contentDAO.update(content);

		contentMapper.update(content);
	}

	public Content updateContent(int siteId, Content content, Integer categoryId)
		throws Exception {
//		User user = PortalUtil.getAuthenticatedUser();

		Content contentDb = this.getContent(content.getContentId());

		contentDb.setNaturalKey(Utility.encode(content.getTitle()));
		contentDb.setTitle(content.getTitle());
		contentDb.setShortDescription(content.getShortDescription());
		contentDb.setDescription(content.getDescription());
		contentDb.setPageTitle(content.getPageTitle());
		contentDb.setPublishDate(content.getPublishDate());
		contentDb.setExpireDate(content.getExpireDate());
		contentDb.setPublished(content.isPublished());
//		contentDb.setHomePage(content.isHomePage());
//		contentDb.setUpdateBy(user);
//		contentDb.setUpdateDate(new Date());

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			contentDb.setCategory(category);
		}

		contentDb.preUpdate();

//		contentDAO.update(contentDb);
		contentMapper.update(contentDb);

		HomePage homePage = getHomePage(siteId, content.getContentId());

		if (content.isHomePage()) {
			if (homePage == null) {
//				homePageMapper.
				homePageService.add(siteId, false, content);
			}
		}
		else {
			if (homePage != null) {
				homePageService.delete(homePage);
			}
		}

		new ContentIndexer().updateIndex(content);

		return contentDb;
	}

	public Content updateContent(
		int siteId, Long contentId, String title,
		String shortDescription, String description) throws Exception {
		Content content = getContent(contentId);

		return this.updateContent(
			contentId, siteId, content.getNaturalKey(), title, shortDescription,
			description, content.getPageTitle(), null, String.valueOf(content.getPublishDate()),
			String.valueOf(content.getExpireDate()), content.isPublished());	
	}

	public Content updateContent(
		Long contentId, int siteId, String naturalKey,
		String title, String shortDescription, String description,
		String pageTitle, Integer categoryId, String publishDate, String expireDate,
		boolean isPublished) throws Exception {
		Content content = contentMapper.getById(contentId);

		content.setContentId(contentId);
		content.setSiteId(siteId);
		content.setNaturalKey(Utility.encode(naturalKey));
		content.setTitle(title);
		content.setShortDescription(shortDescription);
		content.setDescription(description);
		content.setPageTitle(pageTitle);
		content.setPublishDate(Format.getDate(publishDate));
		content.setExpireDate(Format.getDate(expireDate));
//		content.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		content.setUpdateDate(new Date());
		content.setPublished(isPublished);

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

//		contentDAO.update(content);

		content.preUpdate();

		contentMapper.update(content);

		new ContentIndexer().updateIndex(content);

		return content;
	}

	public Content updateContentImage(
			int siteId, Long contentId, MultipartFile image) {
		Content content = getContent(contentId);

		FileUploader fileUpload = FileUploader.getInstance();

		fileUpload.deleteFile(content.getFeaturedImage());

		String imagePath = fileUpload.saveFile(image);

		content.setFeaturedImage(imagePath);
//		content.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		content.setUpdateDate(new Date());

//		contentDAO.update(content);

		content.preUpdate();

		contentMapper.update(content);

		return content;
	}

	public ContentBrand updateContentBrand(ContentBrand contentBrand) {
		contentBrand.preUpdate();

		contentBrandMapper.update(contentBrand);

		return contentBrand;
	}

	private HomePage getHomePage(int siteId, Long contentId) {
		List<HomePage> homePages = homePageService.getHomePages(siteId);

		for (HomePage homePage : homePages) {
			Content homePageContent = homePage.getContent();

			if (homePageContent != null) {
				if (homePageContent.getContentId().longValue() == contentId.longValue()) {
					return homePage;
				}
			}
		}

		return null;
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