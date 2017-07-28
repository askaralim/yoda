package com.yoda.content.service;

import java.io.IOException;
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
import com.yoda.content.model.ContentContributor;
import com.yoda.content.model.ContentUserRate;
import com.yoda.content.persistence.CommentMapper;
import com.yoda.content.persistence.ContentBrandMapper;
import com.yoda.content.persistence.ContentContributorMapper;
import com.yoda.content.persistence.ContentMapper;
import com.yoda.content.persistence.ContentUserRateMapper;
import com.yoda.homepage.model.HomePage;
import com.yoda.homepage.service.HomePageService;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.kernal.util.FileUploader;
import com.yoda.kernal.util.ImageUploader;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.model.Menu;
import com.yoda.menu.persistence.MenuMapper;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Format;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ContentBrandMapper contentBrandMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private HomePageService homePageService;

	@Autowired
	private ContentMapper contentMapper;

	@Autowired
	private ContentContributorMapper contentContributorMapper;

	@Autowired
	private ContentUserRateMapper contentUserRateMapper;

	public void addContent(int siteId, Content content, Integer categoryId) {
		try {
			content.setNaturalKey(Utility.encode(content.getTitle()));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		content.setHitCounter(0);
		content.setSiteId(siteId);
		content.setScore(0);

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

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
		content.setPublished(isPublished);
		content.setHitCounter(0);
		content.setScore(0);

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

		content.preUpdate();

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
		List<HomePage> homePages = homePageService.getHomePages(content.getSiteId());

		for (HomePage homePage : homePages) {
			if (homePage.getContent() != null) {
				if (content.getContentId().longValue() == homePage.getContent().getContentId().longValue()) {
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

		contentMapper.delete(content);
	}

	public void deleteComment(int commentId) {
		Comment comment = commentMapper.getById(commentId);

		commentMapper.delete(comment);
	}

	@Deprecated
	public void deleteContentImage(int siteId, Long contentId) {
		Content content = getContent(contentId);

		FileUploader fileUpload = new FileUploader();

		fileUpload.deleteFile(content.getFeaturedImage());

		content.setFeaturedImage(StringPool.BLANK);
		content.setUpdateBy(PortalUtil.getAuthenticatedUser());
		content.setUpdateDate(new Date());

		contentMapper.update(content);
	}

	@Transactional(readOnly = true)
	public List<Content> getContentByUserId(Long userId) {
		return contentMapper.getContentsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Content getContent(Long contentId) {
		return contentMapper.getById(contentId);
	}

	@Transactional(readOnly = true)
	public ContentBrand getContentBrand(long contentBrandId) {
		return contentBrandMapper.getById(contentBrandId);
	}

	@Transactional(readOnly = true)
	public List<ContentBrand> getContentBrandByBrandId(long brandId) {
		return contentBrandMapper.getByBrandId(brandId);
	}

	@Transactional(readOnly = true)
	public List<ContentContributor> getContentContributor(long contentId, long userId) {
		return contentContributorMapper.getByContentIdAndUserId(contentId, userId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(int siteId) {
		return contentMapper.getContentsBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(String title) {
		return contentMapper.getContentsByTitle(title);
	}

	@Transactional(readOnly = true)
	public Integer getContentRate(Long contentId) {
		Integer rate = contentUserRateMapper.getContentRate(contentId);

		if (rate == null) {
			return 0;
		}
		return rate;
	}

	@Transactional(readOnly = true)
	public ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId) {
		return contentUserRateMapper.getContentUserRateByContentIdAndUserId(contentId, userId);
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

	public void saveContentUserRate(Long contentId, String thumb) {
		User loginUser = PortalUtil.getAuthenticatedUser();

		if (loginUser == null) {
			return;
		}

		ContentUserRate rate = new ContentUserRate();

		rate.setContentId(contentId);

		ContentUserRate rateDb = getContentUserRateByContentIdAndUserId(contentId, loginUser.getUserId());

		if ((rateDb != null) && thumb.equals(Constants.USER_RATE_THUMB_NEUTRAL)) {
			contentUserRateMapper.delete(rateDb);

			return;
		}
		else if ((rateDb != null) && (rateDb.getScore() == 1) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
			return;
		}
		else if ((rateDb != null) && (rateDb.getScore() == -1) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
			return;
		}
		else if ((rateDb != null) && (rateDb.getScore() == -1) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
			contentUserRateMapper.delete(rateDb);

			return;
		}
		else if ((rateDb != null) && (rateDb.getScore() == 1) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
			contentUserRateMapper.delete(rateDb);

			return;
		}
		else if ((rateDb == null) && thumb.equals(Constants.USER_RATE_THUMB_UP)) {
			rate.setScore(1);
		}
		else if ((rateDb == null) && thumb.equals(Constants.USER_RATE_THUMB_DOWN)) {
			rate.setScore(-1);
		}
		else {
			return;
		}

		rate.preInsert();

		contentUserRateMapper.insert(rate);
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
		contentMapper.update(content);
	}

	public Content updateContent(int siteId, Content content, Integer categoryId)
		throws Exception {
		Content contentDb = this.getContent(content.getContentId());

		contentDb.setNaturalKey(Utility.encode(content.getTitle()));
		contentDb.setTitle(content.getTitle());
		contentDb.setShortDescription(content.getShortDescription());
		contentDb.setDescription(content.getDescription());
		contentDb.setPageTitle(content.getPageTitle());
		contentDb.setPublishDate(content.getPublishDate());
		contentDb.setExpireDate(content.getExpireDate());
		contentDb.setPublished(content.isPublished());

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			contentDb.setCategory(category);
		}

		contentDb.preUpdate();

		contentMapper.update(contentDb);

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
		content.setPublished(isPublished);

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

		content.preUpdate();

		contentMapper.update(content);

		new ContentIndexer().updateIndex(content);

		return content;
	}

	public void addContentContributor(ContentContributor contentContributor) {
		contentContributor.preInsert();

		contentContributorMapper.insert(contentContributor);
	}

	public Content updateContentImage(
			int siteId, Long contentId, MultipartFile image) {
		Content content = getContent(contentId);

		ImageUploader imageUpload = new ImageUploader();

		imageUpload.deleteImage(content.getFeaturedImage());

		try {
			String imagePath = imageUpload.uploadContentImage(image.getInputStream(), image.getOriginalFilename());

			content.setFeaturedImage(imagePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

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