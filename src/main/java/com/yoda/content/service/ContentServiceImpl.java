package com.yoda.content.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.service.BrandService;
import com.yoda.category.model.Category;
import com.yoda.category.persistence.CategoryMapper;
import com.yoda.category.service.CategoryService;
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
import com.yoda.item.service.ItemService;
import com.yoda.kernal.elasticsearch.ContentIndexer;
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.redis.RedisService;
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
	private static Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

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

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private RedisService redisService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private BrandService brandService;

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

//		HomePage homePage = getHomePage(siteId, content.getContentId());
//
//		if (content.isHomePage()) {
//			if (homePage == null) {
//				homePageService.add(siteId, false, content);
//			}
//		}
//		else {
//			if (homePage != null) {
//				homePageService.delete(homePage);
//			}
//		}

		if (!content.isFeatureData() && content.isPublished() && content.isHomePage()) {
			this.setContentNotFeatureDatasIntoCache(content.getContentId());
			redisService.incr(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);
		}
		else if (content.isFeatureData() && content.isPublished() && content.isHomePage()) {
			this.setContentFeatureDatasIntoCache(content.getContentId());
		}

		this.setContentIntoCache(content);

		new ContentIndexer().createIndex(content);
	}

	/* not used */
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
		content.setFeatureData(false);

		if (Validator.isNotNull(categoryId)) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

		content.preUpdate();

		contentMapper.insert(content);

		this.setContentIntoCache(content);

		new ContentIndexer().createIndex(content);

		return content;
	}

	public void addComment(Comment comment) {
		commentMapper.insert(comment);
	}

	public void addContentBrand(ContentBrand contentBrand) {
		contentBrand.preInsert();

		contentBrandMapper.insert(contentBrand);

		this.setContentBrandIntoCache(contentBrand);
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
		Content content = getContentFromCache(contentId);

		if (null != content) {
			return content;
		}

		content = contentMapper.getById(contentId);

		this.setContentIntoCache(content);

		return content;
	}

	@Transactional(readOnly = true)
	private Content getSimpleContent(Long contentId) {
		Content content = getSimpleContentFromCache(contentId);

		if (null != content) {
			return content;
		}

		content = contentMapper.getById(contentId);

		this.setContentIntoCache(content);

		return content;
	}

	@Transactional(readOnly = true)
	public ContentBrand getContentBrand(long contentBrandId) {
		ContentBrand contentBrand = getContentBrandFromCache(contentBrandId);

		if (null != contentBrand) {
			return contentBrand;
		}

		contentBrand = contentBrandMapper.getById(contentBrandId);

		this.setContentBrandIntoCache(contentBrand);

		return contentBrand;
	}

	@Transactional(readOnly = true)
	public List<ContentBrand> getContentBrandByBrandId(long brandId) {
		return contentBrandMapper.getByBrandId(brandId);
	}

	@Transactional(readOnly = true)
	public ContentContributor getContentContributor(long id) {
		ContentContributor contentContributor = this.getContentContributorFromCache(id);

		if (null != contentContributor) {
			return contentContributor;
		}

		contentContributor = contentContributorMapper.getById(id);

		setContentContributorIntoCache(contentContributor);

		return contentContributor;
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
	public Pagination<Content> getContents(int siteId, RowBounds rowBounds) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("siteId", siteId);

		List<Content> contents = sqlSessionTemplate.selectList("com.yoda.content.persistence.ContentMapper.getContentsBySiteId", params, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.yoda.content.persistence.ContentMapper.getContentsBySiteIdCount", params);

		Pagination<Content> page = new Pagination<Content>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), contents);

		return page;
	}

	@Transactional(readOnly = true)
	public List<Content> getContents(String title) {
		return contentMapper.getContentsByTitle(title);
	}

	@Transactional(readOnly = true)
	public List<Content> getContentsFeatureData() {
		List<Content> contents = new ArrayList<>();

		List<String> ids = this.getContentFeatureDatasFromCache();

		if (null == ids || ids.isEmpty()) {
			contents = contentMapper.getContentsByFeatureData(true);

			for (Content content : contents) {
				this.setContentFeatureDatasIntoCache(content.getContentId());
			}
		}
		else {
			for (String id : ids) {
				Content content = this.getSimpleContent(Long.valueOf(id));

				contents.add(content);
			}
		}

		return contents;
	}

	@Transactional(readOnly = true)
	public Pagination<Content> getContentsNotFeatureData(int offset, int limit) {
		List<Content> contents = new ArrayList<>();
		Pagination<Content> page = new Pagination<Content>();

		List<String> ids = this.getContentNotFeatureDatasFromCache(offset, limit);

		if (null == ids || ids.isEmpty()) {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("featureData", false);

			contents = sqlSessionTemplate.selectList("com.yoda.content.persistence.ContentMapper.getContentsByFeatureData", params, new RowBounds(offset, limit));

			List<Integer> count = sqlSessionTemplate.selectList("com.yoda.content.persistence.ContentMapper.getContentsByFeatureDataCount", params);

			page = new Pagination<Content>(offset, count.get(0), limit, contents);

			setContentNotFeatureDataCountIntoCache(count.get(0));

			for (Content content : contents) {
				this.setContentNotFeatureDatasIntoCache(content.getContentId());
			}
		}
		else {
			for (String id : ids) {
				Content content = this.getSimpleContent(Long.valueOf(id));

				contents.add(content);

				page = new Pagination<Content>(offset, getContentNotFeatureDataCountFromCache(), limit, contents);
			}
		}

		return page;
	}

	@Transactional(readOnly = true)
	public Integer getContentRate(Long contentId) {
		String rateCached = redisService.get(Constants.REDIS_CONTENT_RATE + ":" + contentId);

		if (StringUtils.isNotEmpty(rateCached) && !"nil".equalsIgnoreCase(rateCached)) {
			return Integer.valueOf(rateCached);
		}

		Integer rate = contentUserRateMapper.getContentRate(contentId);

		if (rate == null) {
			rate = 0;
		}

		redisService.set(Constants.REDIS_CONTENT_RATE + ":" + contentId, String.valueOf(rate), 3600);

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
		Content contentDb = contentMapper.getById(content.getContentId());

		contentDb.setNaturalKey(Utility.encode(content.getTitle()));
		contentDb.setTitle(content.getTitle());
		contentDb.setShortDescription(content.getShortDescription());
		contentDb.setDescription(content.getDescription());
		contentDb.setPageTitle(content.getPageTitle());
		contentDb.setPublishDate(content.getPublishDate());
		contentDb.setExpireDate(content.getExpireDate());
		contentDb.setPublished(content.isPublished());
		contentDb.setFeatureData(content.isFeatureData());

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

		resetContentNotFeatureDataListIntoCache();
		this.setContentIntoCache(contentDb);

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
		content.setPublishDate(Format.getFullDatetime(publishDate));
		content.setExpireDate(Format.getFullDatetime(expireDate));
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

	public void updateContentHitCounter(int id, int hitCounter) {
		Content content = contentMapper.getById(id);
		content.setHitCounter(hitCounter);
		contentMapper.update(content);
	}

	public void addContentContributor(ContentContributor contentContributor) {
		contentContributor.preInsert();

		contentContributorMapper.insert(contentContributor);

		this.setContentContributorIntoCache(contentContributor);
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

	private Content getContentFromCache(Long contentId) {
		Content content = null;

		String key = Constants.REDIS_CONTENT + ":" + contentId;

		try {
			Map<String, String> map = redisService.getMap(key);

			if (null != map && map.size() > 0) {
				content = new Content();

				String id = redisService.getMap(key, "contentId");
				String title = redisService.getMap(key, "title");
				String description = redisService.getMap(key, "description");
				String shortDescription = redisService.getMap(key, "shortDescription");
				String featuredImage = redisService.getMap(key, "featuredImage");
				String naturalKey = redisService.getMap(key, "naturalKey");
				String pageTitle = redisService.getMap(key, "pageTitle");
				String categoryId = redisService.getMap(key, "categoryId");
				String isHomePage = redisService.getMap(key, "isHomePage");
				String isFeature = redisService.getMap(key, "isFeature");
				String isPublished = redisService.getMap(key, "isPublished");
				String expireDate = redisService.getMap(key, "expireDate");
				String publishDate = redisService.getMap(key, "publishDate");
				String createById = redisService.getMap(key, "createById");
				String createByUsername = redisService.getMap(key, "createByUsername");
				String createByUserImage = redisService.getMap(key, "createByUserImage");
				String createDate = redisService.getMap(key, "createDate");
				String updateBy = redisService.getMap(key, "updateBy");
				String updateDate = redisService.getMap(key, "updateDate");

				content.setContentId(StringUtils.isNotEmpty(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
				content.setTitle(StringUtils.isNotEmpty(title) && !"nil".equalsIgnoreCase(title) ? title : null);
				content.setDescription(StringUtils.isNotEmpty(description) && !"nil".equalsIgnoreCase(description) ? description : null);
				content.setNaturalKey(StringUtils.isNotEmpty(naturalKey) && !"nil".equalsIgnoreCase(naturalKey) ? naturalKey : null);
				content.setPageTitle(StringUtils.isNotEmpty(pageTitle) && !"nil".equalsIgnoreCase(pageTitle) ? pageTitle : null);
				content.setExpireDate(StringUtils.isNotEmpty(expireDate) && !"nil".equalsIgnoreCase(expireDate) ? Format.getFullDatetime(expireDate) : null);
				content.setFeatureData(StringUtils.isNotEmpty(isFeature) && !"nil".equalsIgnoreCase(isFeature) ? Boolean.valueOf(isFeature) : null);
				content.setHomePage(StringUtils.isNotEmpty(isHomePage) && !"nil".equalsIgnoreCase(isHomePage) ? Boolean.valueOf(isHomePage) : null);
				content.setPublishDate(StringUtils.isNotEmpty(publishDate) && !"nil".equalsIgnoreCase(publishDate) ? Format.getFullDatetime(publishDate) : null);
				content.setPublished(StringUtils.isNotEmpty(isPublished) && !"nil".equalsIgnoreCase(isPublished) ? Boolean.valueOf(isPublished) : null);
				content.setShortDescription(StringUtils.isNotEmpty(shortDescription) && !"nil".equalsIgnoreCase(shortDescription) ? shortDescription : null);
				content.setFeaturedImage(StringUtils.isNotEmpty(featuredImage) && !"nil".equalsIgnoreCase(featuredImage) ? featuredImage : null);

				if (StringUtils.isNoneBlank(categoryId) && !"nil".equalsIgnoreCase(categoryId)) {
					Category category = categoryService.getCategory(Integer.valueOf(categoryId));
					content.setCategory(category);
				}

				if (StringUtils.isNotEmpty(createById) && !"nil".equalsIgnoreCase(createById)) {
					User user = new User();
					user.setUserId(Long.valueOf(createById));
					user.setUsername(createByUsername);
					user.setProfilePhotoSmall(createByUserImage);
					content.setCreateBy(user);
				}

				if (StringUtils.isNotEmpty(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
					User user = new User();
					user.setUserId(Long.valueOf(updateBy));
					content.setUpdateBy(user);
				}

				content.setCreateDate(StringUtils.isNotEmpty(createDate) && !"nil".equalsIgnoreCase(createDate) ? Format.getFullDatetime(createDate) : null);
				content.setUpdateDate(StringUtils.isNotEmpty(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? Format.getFullDatetime(updateDate) : null);

				content.setHitCounter(getContentHitCounterFromCached(contentId));
				content.setScore(getContentRate(content.getContentId()));

				List<ContentBrand> contentBrands = new ArrayList<>();
				List<String> contentBrandIds = redisService.getList(Constants.REDIS_CONTENT_BRAND_LIST + ":" + content.getContentId());

				if (null != contentBrandIds && !contentBrandIds.isEmpty()) {
					for (String contentBrandId : contentBrandIds) {
						ContentBrand contentBrand = new ContentBrand();
						contentBrand = this.getContentBrand(Long.valueOf(contentBrandId));

						contentBrands.add(contentBrand);
					}
				}

				content.setContentBrands(contentBrands);

				List<ContentContributor> contentContributors = new ArrayList<>();
				List<String> contentContributorIds = redisService.getList(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getContentId());

				if (null != contentContributorIds) {
					for (String ccId : contentContributorIds) {
						ContentContributor cc = new ContentContributor();
						cc = this.getContentContributorFromCache(Long.valueOf(ccId));

						contentContributors.add(cc);
					}
				}

				content.setContentContributors(contentContributors);

//				List<Item> items = itemService.getItemsByContentId(content.getContentId());
//				content.setItems(items);
			}
		}
		catch (ParseException e) {
			logger.warn(e.getMessage());
		}

		return content;
	}

	private Content getSimpleContentFromCache(Long contentId) {
		Content content = null;

		String key = Constants.REDIS_CONTENT + ":" + contentId;

		try {
			Map<String, String> map = redisService.getMap(key);

			if (null != map && map.size() > 0) {
				content = new Content();

				String id = redisService.getMap(key, "contentId");
				String title = redisService.getMap(key, "title");
				String description = redisService.getMap(key, "description");
				String shortDescription = redisService.getMap(key, "shortDescription");
				String featuredImage = redisService.getMap(key, "featuredImage");
				String naturalKey = redisService.getMap(key, "naturalKey");
				String pageTitle = redisService.getMap(key, "pageTitle");
				String isHomePage = redisService.getMap(key, "isHomePage");
				String isFeature = redisService.getMap(key, "isFeature");
				String isPublished = redisService.getMap(key, "isPublished");
				String expireDate = redisService.getMap(key, "expireDate");
				String publishDate = redisService.getMap(key, "publishDate");

				content.setContentId(StringUtils.isNotEmpty(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
				content.setTitle(StringUtils.isNotEmpty(title) && !"nil".equalsIgnoreCase(title) ? title : null);
				content.setDescription(StringUtils.isNotEmpty(description) && !"nil".equalsIgnoreCase(description) ? description : null);
				content.setNaturalKey(StringUtils.isNotEmpty(naturalKey) && !"nil".equalsIgnoreCase(naturalKey) ? naturalKey : null);
				content.setPageTitle(StringUtils.isNotEmpty(pageTitle) && !"nil".equalsIgnoreCase(pageTitle) ? pageTitle : null);
				content.setExpireDate(StringUtils.isNotEmpty(expireDate) && !"nil".equalsIgnoreCase(expireDate) ? Format.getFullDatetime(expireDate) : null);
				content.setFeatureData(StringUtils.isNotEmpty(isFeature) && !"nil".equalsIgnoreCase(isFeature) ? Boolean.valueOf(isFeature) : null);
				content.setHomePage(StringUtils.isNotEmpty(isHomePage) && !"nil".equalsIgnoreCase(isHomePage) ? Boolean.valueOf(isHomePage) : null);
				content.setPublishDate(StringUtils.isNotEmpty(publishDate) && !"nil".equalsIgnoreCase(publishDate) ? Format.getFullDatetime(publishDate) : null);
				content.setPublished(StringUtils.isNotEmpty(isPublished) && !"nil".equalsIgnoreCase(isPublished) ? Boolean.valueOf(isPublished) : null);
				content.setShortDescription(StringUtils.isNotEmpty(shortDescription) && !"nil".equalsIgnoreCase(shortDescription) ? shortDescription : null);
				content.setFeaturedImage(StringUtils.isNotEmpty(featuredImage) && !"nil".equalsIgnoreCase(featuredImage) ? featuredImage : null);

				content.setHitCounter(getContentHitCounterFromCached(contentId));
				content.setScore(getContentRate(contentId));
			}
		}
		catch (ParseException e) {
			logger.error(e.getMessage());
		}

		return content;
	}

	private ContentBrand getContentBrandFromCache(long contentBrandId) {
		ContentBrand contentBrand = null;

		String key = Constants.REDIS_CONTENT_BRAND + ":" + contentBrandId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() > 0) {
			contentBrand = new ContentBrand();

			String id = redisService.getMap(key, "contentBrandId");
			String brandId = redisService.getMap(key, "brandId");
			String brandLogo = redisService.getMap(key, "brandLogo");
			String brandName = redisService.getMap(key, "brandName");
			String contentId = redisService.getMap(key, "contentId");
			String description = redisService.getMap(key, "description");

			contentBrand.setBrandId(StringUtils.isNotEmpty(brandId) && !"nil".equalsIgnoreCase(brandId) ? Integer.valueOf(brandId) : null);
			contentBrand.setBrandLogo(StringUtils.isNotEmpty(brandLogo) && !"nil".equalsIgnoreCase(brandLogo) ? brandLogo : null);
			contentBrand.setBrandName(StringUtils.isNotEmpty(brandName) && !"nil".equalsIgnoreCase(brandName) ? brandName : null);
			contentBrand.setContentBrandId(StringUtils.isNotEmpty(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			contentBrand.setContentId(StringUtils.isNotEmpty(contentId) && !"nil".equalsIgnoreCase(contentId) ? Long.valueOf(contentId) : null);
			contentBrand.setDescription(StringUtils.isNotEmpty(description) && !"nil".equalsIgnoreCase(description) ? description : null);
		}

		return contentBrand;
	}

	private ContentContributor getContentContributorFromCache(long contentContributorId) {
		ContentContributor contentContributor = null;

		String key = Constants.REDIS_CONTENT_CONTRIBUROR + ":" + contentContributorId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() > 0) {
			contentContributor = new ContentContributor();

			String id = redisService.getMap(key, "id");
			String contentId = redisService.getMap(key, "contentId");
			String profilePhotoSmall = redisService.getMap(key, "profilePhotoSmall");
			String userId = redisService.getMap(key, "userId");
			String username = redisService.getMap(key, "username");

			contentContributor.setContentId(StringUtils.isNotEmpty(contentId) && !"nil".equalsIgnoreCase(contentId) ? Long.valueOf(contentId) : null);
			contentContributor.setId(StringUtils.isNotEmpty(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			contentContributor.setProfilePhotoSmall(StringUtils.isNotEmpty(profilePhotoSmall) && !"nil".equalsIgnoreCase(profilePhotoSmall) ? profilePhotoSmall : null);
			contentContributor.setUserId(StringUtils.isNotEmpty(userId) && !"nil".equalsIgnoreCase(userId) ? Long.valueOf(userId) : null);
			contentContributor.setUsername(StringUtils.isNotEmpty(username) && !"nil".equalsIgnoreCase(username) ? username : null);
		}

		return contentContributor;
	}

	private void setContentIntoCache(Content content) {
		Map<String, String> value = new HashMap<>();

		value.put("description", content.getDescription());
		value.put("featuredImage", content.getFeaturedImage());
		value.put("naturalKey", content.getNaturalKey());
		value.put("pageTitle", content.getPageTitle());

		if (null != content.getCategory()) {
			value.put("categoryId", String.valueOf(content.getCategory().getCategoryId()));
		}

		value.put("shortDescription", content.getShortDescription());
		value.put("title", content.getTitle());
		value.put("contentId", String.valueOf(content.getContentId()));
		value.put("isFeature", String.valueOf(content.isFeatureData()));
		value.put("isHomePage", String.valueOf(content.isHomePage()));
		value.put("isPublished", String.valueOf(content.isPublished()));
		value.put("expireDate", Format.getFullDatetime(content.getExpireDate()));
		value.put("publishDate", Format.getFullDatetime(content.getPublishDate()));
		value.put("updateBy", String.valueOf(content.getUpdateBy().getUserId()));
		value.put("updateDate", Format.getFullDatetime(content.getUpdateDate()));
		value.put("createById", String.valueOf(content.getCreateBy().getUserId()));
		value.put("createByUsername", content.getCreateBy().getUsername());
		value.put("createByUserImage", content.getCreateBy().getProfilePhotoSmall());
		value.put("createDate", Format.getFullDatetime(content.getCreateDate()));

		redisService.setMap(Constants.REDIS_CONTENT + ":" + content.getContentId(), value);

		setContentHitCounterIntoCached(content.getContentId(), content.getHitCounter());

		redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":"  + content.getContentId());

		for (ContentContributor cc : content.getContentContributors()) {
			redisService.listRightPushAll(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getContentId(), String.valueOf(cc.getId()));
		}

		redisService.delete(Constants.REDIS_CONTENT_BRAND_LIST + ":"  + content.getContentId());

		for (ContentBrand cb : content.getContentBrands()) {
			redisService.listRightPushAll(Constants.REDIS_CONTENT_BRAND_LIST + ":" + content.getContentId(), String.valueOf(cb.getContentBrandId()));
		}
	}

	private void setContentBrandIntoCache(ContentBrand contentBrand) {
		Map<String, String> value = new HashMap<>();

		value.put("contentBrandId", String.valueOf(contentBrand.getContentBrandId()));
		value.put("brandId", String.valueOf(contentBrand.getBrandId()));
		value.put("brandLogo", contentBrand.getBrandLogo());
		value.put("brandName", contentBrand.getBrandName());
		value.put("contentId", String.valueOf(contentBrand.getContentId()));
		value.put("description", contentBrand.getDescription());

		redisService.setMap(Constants.REDIS_CONTENT_BRAND + ":" + contentBrand.getContentBrandId(), value);
	}

	private void setContentContributorIntoCache(ContentContributor contentContributor) {
		Map<String, String> value = new HashMap<>();

		value.put("id", String.valueOf(contentContributor.getId()));
		value.put("contentId", String.valueOf(contentContributor.getContentId()));
		value.put("profilePhotoSmall", contentContributor.getProfilePhotoSmall());
		value.put("userId", String.valueOf(contentContributor.getUserId()));
		value.put("username", contentContributor.getUsername());

		redisService.setMap(Constants.REDIS_CONTENT_CONTRIBUROR + ":" + contentContributor.getId(), value);
	}

	private List<String> getContentFeatureDatasFromCache() {
		List<String> ids = redisService.getList(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST);

		return ids;
	}

	private void setContentFeatureDatasIntoCache(long contentId) {
		redisService.listRightPushAll(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST, String.valueOf(contentId));
	}

	private List<String> getContentNotFeatureDatasFromCache(int offset, int limit) {
		List<String> ids = redisService.getList(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, offset, offset + limit - 1);

		return ids;
	}

	private void setContentNotFeatureDatasIntoCache(long contentId) {
		redisService.listRightPushAll(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, String.valueOf(contentId));
	}

	private void resetContentNotFeatureDataListIntoCache() {
		List<Content> contents = contentMapper.getContentsByFeatureData(false);
		List<String> contentIds = new ArrayList<>();

		for (Content content : contents) {
			contentIds.add(String.valueOf(content.getContentId()));
		}

		redisService.setList(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, contentIds, 0);
		setContentNotFeatureDataCountIntoCache(contentIds.size());
	}

	private Integer getContentNotFeatureDataCountFromCache() {
		String count = redisService.get(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);

		if (StringUtils.isNotEmpty(count)) {
			return Integer.valueOf(count);
		}
		else {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("featureData", false);

			List<Integer> countList = sqlSessionTemplate.selectList("com.yoda.content.persistence.ContentMapper.getContentsByFeatureDataCount", params);

			setContentNotFeatureDataCountIntoCache(countList.get(0));

			return countList.get(0);
		}
	}

	private void setContentNotFeatureDataCountIntoCache(int count) {
		redisService.set(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST, String.valueOf(count));
	}

	private int getContentHitCounterFromCached(long contentId) {
		String hit = redisService.get(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + contentId);

		if (StringUtils.isNotEmpty(hit) && !"nil".equalsIgnoreCase(hit)) {
			return Integer.valueOf(hit);
		}
		else {
			Content content = contentMapper.getById(contentId);

			setContentHitCounterIntoCached(content.getContentId(), content.getHitCounter());

			return content.getHitCounter();
		}
	}

	private void setContentHitCounterIntoCached(long contentId, int hitCounter) {
		redisService.set(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + contentId, String.valueOf(hitCounter));
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