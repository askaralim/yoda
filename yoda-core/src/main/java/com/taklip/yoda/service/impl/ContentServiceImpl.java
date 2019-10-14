package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.taklip.jediorder.service.IdService;
import com.taklip.yoda.elasticsearch.ContentIndexer;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.CategoryMapper;
import com.taklip.yoda.mapper.CommentMapper;
import com.taklip.yoda.mapper.ContentBrandMapper;
import com.taklip.yoda.mapper.ContentContributorMapper;
import com.taklip.yoda.mapper.ContentMapper;
import com.taklip.yoda.mapper.ContentUserRateMapper;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.ContentUserRate;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.DateUtil;

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
	private ImageUploader imageUpload;

	@Autowired
	private IdService idService;

	@Autowired
	private FileService fileService;

	@Override
	public void saveContent(Content content, Long categoryId) throws Exception {
		if (null == content.getId()) {
			this.addContent(content, categoryId);
		}
		else {
			this.updateContent(content, categoryId);
		}
	}

	public void addContent(Content content, Long categoryId) throws Exception {
		content.setId(idService.generateId());
		content.setNaturalKey(encode(content.getTitle()));
		content.setHitCounter(0);
		content.setScore(0);

		if (null != categoryId) {
			Category category = categoryMapper.getById(categoryId);

			content.setCategory(category);
		}

		content.preInsert();

		contentMapper.insert(content);

		if (!content.isFeatureData() && content.isPublished() && content.isHomePage()) {
			this.setContentNotFeatureDatasIntoCache(content.getId());
			redisService.incr(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);
		}
		else if (content.isFeatureData() && content.isPublished() && content.isHomePage()) {
			this.setContentFeatureDatasIntoCache(content.getId());
		}

		this.setContentIntoCache(content);

		new ContentIndexer().createIndex(content);
	}

	public void addComment(Comment comment) {
		commentMapper.insert(comment);
	}

	public void saveContentBrand(ContentBrand contentBrand) {
		if (null == contentBrand.getId()) {
			this.addContentBrand(contentBrand);
		}
		else {
			this.updateContentBrand(contentBrand);
		}

		deleteContentFromCache(contentBrand.getContentId());
	}

	public void addContentBrand(ContentBrand contentBrand) {
		contentBrand.preInsert();

		contentBrandMapper.insert(contentBrand);

		this.setContentBrandIntoCache(contentBrand);
	}

	public ContentBrand updateContentBrand(ContentBrand contentBrand) {
		contentBrand.preUpdate();

		contentBrandMapper.update(contentBrand);

		deleteContentBrandFromCache(contentBrand.getId());

		return contentBrand;
	}

	public void deleteContent(Content content) {
		new ContentIndexer().deleteIndex(content.getId());

		contentMapper.delete(content);
	}

	public void deleteComment(Long commentId) {
		Comment comment = commentMapper.getById(commentId);

		commentMapper.delete(comment);
	}

	@Transactional(readOnly = true)
	public List<Content> getContentByUserId(Long userId) {
		return contentMapper.getContentsByUserId(userId);
	}

	@Transactional(readOnly = true)
	public Content getContent(Long id) {
		Content content = getContentFromCache(id);

		if (null != content) {
			return content;
		}

		content = contentMapper.getById(id);

		for (ContentBrand cb : content.getContentBrands()) {
			cb.setDescription(cb.getDescription().replace("<img src=\"/upload", "<img src=\"/yoda/upload"));
		}

		this.setContentIntoCache(content);

		return content;
	}

	@Transactional(readOnly = true)
	public Content getSimpleContent(Long id) {
		Content content = getSimpleContentFromCache(id);

		if (null != content) {
			return content;
		}

		content = contentMapper.getById(id);

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
	public List<Content> getContents() {
		return contentMapper.getContents();
	}

	@Transactional(readOnly = true)
	public Pagination<Content> getContents(RowBounds rowBounds) {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Content> contents = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ContentMapper.getContents", params, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ContentMapper.getContentsCount", params);

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
				this.setContentFeatureDatasIntoCache(content.getId());
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

			contents = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ContentMapper.getContentsByFeatureData", params, new RowBounds(offset, limit));

			List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ContentMapper.getContentsByFeatureDataCount", params);

			page = new Pagination<Content>(offset, count.get(0), limit, contents);

			setContentNotFeatureDataCountIntoCache(count.get(0));

			for (Content content : contents) {
				this.setContentNotFeatureDatasIntoCache(content.getId());
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
	public Integer getContentRate(Long id) {
		String rateCached = redisService.get(Constants.REDIS_CONTENT_RATE + ":" + id);

		if (StringUtils.isNoneBlank(rateCached) && !"nil".equalsIgnoreCase(rateCached)) {
			return Integer.valueOf(rateCached);
		}

		Integer rate = contentUserRateMapper.getContentRate(id);

		if (rate == null) {
			rate = 0;
		}

		redisService.set(Constants.REDIS_CONTENT_RATE + ":" + id, String.valueOf(rate), 3600);

		return rate;
	}

	@Transactional(readOnly = true)
	public ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId) {
		return contentUserRateMapper.getContentUserRateByContentIdAndUserId(contentId, userId);
	}

	@Transactional(readOnly = true)
	public Comment getComment(Long commentId) {
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
		User loginUser = AuthenticatedUtil.getAuthenticatedUser();

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
	}

	public void updateContent(Content content) {
		content.preUpdate();
		contentMapper.update(content);
		deleteContentFromCache(content.getId());
	}

	public Content updateContent(Content content, Long categoryId)
		throws Exception {
		Content contentDb = contentMapper.getById(content.getId());

		contentDb.setNaturalKey(encode(content.getTitle()));
		contentDb.setTitle(content.getTitle());
		contentDb.setShortDescription(content.getShortDescription());
		contentDb.setDescription(content.getDescription());
		contentDb.setPageTitle(content.getPageTitle());
		contentDb.setPublishDate(content.getPublishDate());
		contentDb.setExpireDate(content.getExpireDate());
		contentDb.setPublished(content.isPublished());
		contentDb.setFeatureData(content.isFeatureData());

		if (null != categoryId) {
			Category category = categoryMapper.getById(categoryId);

			contentDb.setCategory(category);
		}

		contentDb.preUpdate();

		contentMapper.update(contentDb);

		new ContentIndexer().updateIndex(content);

		deleteContentFromCache(content.getId());

		return contentDb;
	}

	public void increaseContentHitCounter(Long id) {
		Content content = contentMapper.getById(id);

		content.setHitCounter(content.getHitCounter() + 1);

		contentMapper.update(content);

		this.setContentHitCounterIntoCached(id, content.getHitCounter());
	}

	public void addContentContributor(ContentContributor contentContributor) {
		contentContributor.preInsert();

		contentContributorMapper.insert(contentContributor);

		this.setContentContributorIntoCache(contentContributor);
	}

	public Content updateContentImage(
			int siteId, Long id, MultipartFile image) {
		Content content = contentMapper.getById(id);

		imageUpload.deleteImage(content.getFeaturedImage());

		try {
			String imagePath = fileService.save(ContentTypeEnum.CONTENT.getType(), id, image);

			content.setFeaturedImage(imagePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		content.preUpdate();

		contentMapper.update(content);

		return content;
	}

	private Content getContentFromCache(Long contentId) {
		Content content = null;

		String key = Constants.REDIS_CONTENT + ":" + contentId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() > 0) {
			content = new Content();

			String id = redisService.getMap(key, "id");
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

			content.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			content.setTitle(StringUtils.isNoneBlank(title) && !"nil".equalsIgnoreCase(title) ? title : null);
			content.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
			content.setNaturalKey(StringUtils.isNoneBlank(naturalKey) && !"nil".equalsIgnoreCase(naturalKey) ? naturalKey : null);
			content.setPageTitle(StringUtils.isNoneBlank(pageTitle) && !"nil".equalsIgnoreCase(pageTitle) ? pageTitle : null);
			content.setExpireDate(StringUtils.isNoneBlank(expireDate) && !"nil".equalsIgnoreCase(expireDate) ? DateUtil.getFullDatetime(expireDate) : null);
			content.setFeatureData(StringUtils.isNoneBlank(isFeature) && !"nil".equalsIgnoreCase(isFeature) ? Boolean.valueOf(isFeature) : null);
			content.setHomePage(StringUtils.isNoneBlank(isHomePage) && !"nil".equalsIgnoreCase(isHomePage) ? Boolean.valueOf(isHomePage) : null);
			content.setPublishDate(StringUtils.isNoneBlank(publishDate) && !"nil".equalsIgnoreCase(publishDate) ? DateUtil.getFullDatetime(publishDate) : null);
			content.setPublished(StringUtils.isNoneBlank(isPublished) && !"nil".equalsIgnoreCase(isPublished) ? Boolean.valueOf(isPublished) : null);
			content.setShortDescription(StringUtils.isNoneBlank(shortDescription) && !"nil".equalsIgnoreCase(shortDescription) ? shortDescription : null);
			content.setFeaturedImage(StringUtils.isNoneBlank(featuredImage) && !"nil".equalsIgnoreCase(featuredImage) ? featuredImage : null);

			if (StringUtils.isNoneBlank(categoryId) && !"nil".equalsIgnoreCase(categoryId)) {
				Category category = categoryService.getCategory(Long.valueOf(categoryId));
				content.setCategory(category);
			}

			if (StringUtils.isNoneBlank(createById) && !"nil".equalsIgnoreCase(createById)) {
				User user = new User();
				user.setUserId(Long.valueOf(createById));
				user.setUsername(createByUsername);
				user.setProfilePhotoSmall(createByUserImage);
				content.setCreateBy(user);
			}

			if (StringUtils.isNoneBlank(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(updateBy));
				content.setUpdateBy(user);
			}

			content.setCreateDate(StringUtils.isNoneBlank(createDate) && !"nil".equalsIgnoreCase(createDate) ? DateUtil.getFullDatetime(createDate) : null);
			content.setUpdateDate(StringUtils.isNoneBlank(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? DateUtil.getFullDatetime(updateDate) : null);

			content.setHitCounter(getContentHitCounter(contentId));
			content.setScore(getContentRate(content.getId()));

			List<ContentBrand> contentBrands = new ArrayList<>();
			List<String> contentBrandIds = redisService.getList(Constants.REDIS_CONTENT_BRAND_LIST + ":" + content.getId());

			if (null != contentBrandIds && !contentBrandIds.isEmpty()) {
				for (String contentBrandId : contentBrandIds) {
					ContentBrand contentBrand = this.getContentBrand(Long.valueOf(contentBrandId));

					contentBrands.add(contentBrand);
				}
			}

			content.setContentBrands(contentBrands);

			List<ContentContributor> contentContributors = new ArrayList<>();
			List<String> contentContributorIds = redisService.getList(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getId());

			if (null != contentContributorIds) {
				for (String ccId : contentContributorIds) {
					ContentContributor cc = this.getContentContributorFromCache(Long.valueOf(ccId));

					contentContributors.add(cc);
				}
			}

			content.setContentContributors(contentContributors);
		}

		return content;
	}

	private Content getSimpleContentFromCache(Long contentId) {
		Content content = null;

		String key = Constants.REDIS_CONTENT + ":" + contentId;

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

			content.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			content.setTitle(StringUtils.isNoneBlank(title) && !"nil".equalsIgnoreCase(title) ? title : null);
			content.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
			content.setNaturalKey(StringUtils.isNoneBlank(naturalKey) && !"nil".equalsIgnoreCase(naturalKey) ? naturalKey : null);
			content.setPageTitle(StringUtils.isNoneBlank(pageTitle) && !"nil".equalsIgnoreCase(pageTitle) ? pageTitle : null);
			content.setExpireDate(StringUtils.isNoneBlank(expireDate) && !"nil".equalsIgnoreCase(expireDate) ? DateUtil.getFullDatetime(expireDate) : null);
			content.setFeatureData(StringUtils.isNoneBlank(isFeature) && !"nil".equalsIgnoreCase(isFeature) ? Boolean.valueOf(isFeature) : null);
			content.setHomePage(StringUtils.isNoneBlank(isHomePage) && !"nil".equalsIgnoreCase(isHomePage) ? Boolean.valueOf(isHomePage) : null);
			content.setPublishDate(StringUtils.isNoneBlank(publishDate) && !"nil".equalsIgnoreCase(publishDate) ? DateUtil.getFullDatetime(publishDate) : null);
			content.setPublished(StringUtils.isNoneBlank(isPublished) && !"nil".equalsIgnoreCase(isPublished) ? Boolean.valueOf(isPublished) : null);
			content.setShortDescription(StringUtils.isNoneBlank(shortDescription) && !"nil".equalsIgnoreCase(shortDescription) ? shortDescription : null);
			content.setFeaturedImage(StringUtils.isNoneBlank(featuredImage) && !"nil".equalsIgnoreCase(featuredImage) ? featuredImage : null);

			content.setHitCounter(getContentHitCounter(contentId));
			content.setScore(getContentRate(contentId));
		}

		return content;
	}

	private ContentBrand getContentBrandFromCache(long contentBrandId) {
		ContentBrand contentBrand = null;

		String key = Constants.REDIS_CONTENT_BRAND + ":" + contentBrandId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() > 0) {
			contentBrand = new ContentBrand();

			String id = redisService.getMap(key, "id");
			String brandId = redisService.getMap(key, "brandId");
			String brandLogo = redisService.getMap(key, "brandLogo");
			String brandName = redisService.getMap(key, "brandName");
			String contentId = redisService.getMap(key, "contentId");
			String description = redisService.getMap(key, "description");

			contentBrand.setBrandId(StringUtils.isNoneBlank(brandId) && !"nil".equalsIgnoreCase(brandId) ? Long.valueOf(brandId) : null);
			contentBrand.setBrandLogo(StringUtils.isNoneBlank(brandLogo) && !"nil".equalsIgnoreCase(brandLogo) ? brandLogo : null);
			contentBrand.setBrandName(StringUtils.isNoneBlank(brandName) && !"nil".equalsIgnoreCase(brandName) ? brandName : null);
			contentBrand.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			contentBrand.setContentId(StringUtils.isNoneBlank(contentId) && !"nil".equalsIgnoreCase(contentId) ? Long.valueOf(contentId) : null);
			contentBrand.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
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

			contentContributor.setContentId(StringUtils.isNoneBlank(contentId) && !"nil".equalsIgnoreCase(contentId) ? Long.valueOf(contentId) : null);
			contentContributor.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			contentContributor.setProfilePhotoSmall(StringUtils.isNoneBlank(profilePhotoSmall) && !"nil".equalsIgnoreCase(profilePhotoSmall) ? profilePhotoSmall : null);
			contentContributor.setUserId(StringUtils.isNoneBlank(userId) && !"nil".equalsIgnoreCase(userId) ? Long.valueOf(userId) : null);
			contentContributor.setUsername(StringUtils.isNoneBlank(username) && !"nil".equalsIgnoreCase(username) ? username : null);
		}

		return contentContributor;
	}

	private void deleteContentFromCache(Long id) {
		redisService.delete(Constants.REDIS_CONTENT + ":" + id);
		redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":"  + id);
		redisService.delete(Constants.REDIS_CONTENT_BRAND_LIST + ":"  + id);
		redisService.delete(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST);
		redisService.delete(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST);
		redisService.delete(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);
	}

	private void deleteContentBrandFromCache(Long id) {
		redisService.delete(Constants.REDIS_CONTENT_BRAND + ":" + id);
	}

	private void setContentIntoCache(Content content) {
		Map<String, String> value = new HashMap<>();

		value.put("description", content.getDescription());
		value.put("featuredImage", content.getFeaturedImage());
		value.put("naturalKey", content.getNaturalKey());
		value.put("pageTitle", content.getPageTitle());

		if (null != content.getCategory()) {
			value.put("categoryId", String.valueOf(content.getCategory().getId()));
		}

		value.put("shortDescription", content.getShortDescription());
		value.put("title", content.getTitle());
		value.put("id", String.valueOf(content.getId()));
		value.put("isFeature", String.valueOf(content.isFeatureData()));
		value.put("isHomePage", String.valueOf(content.isHomePage()));
		value.put("isPublished", String.valueOf(content.isPublished()));
		value.put("expireDate", DateUtil.getFullDatetime(content.getExpireDate()));
		value.put("publishDate", DateUtil.getFullDatetime(content.getPublishDate()));
		value.put("updateBy", String.valueOf(content.getUpdateBy().getUserId()));
		value.put("updateDate", DateUtil.getFullDatetime(content.getUpdateDate()));
		value.put("createById", String.valueOf(content.getCreateBy().getUserId()));
		value.put("createByUsername", content.getCreateBy().getUsername());
		value.put("createByUserImage", content.getCreateBy().getProfilePhotoSmall());
		value.put("createDate", DateUtil.getFullDatetime(content.getCreateDate()));

		redisService.setMap(Constants.REDIS_CONTENT + ":" + content.getId(), value);

		setContentHitCounterIntoCached(content.getId(), content.getHitCounter());

		redisService.delete(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":"  + content.getId());

		for (ContentContributor cc : content.getContentContributors()) {
			redisService.listRightPushAll(Constants.REDIS_CONTENT_CONTRIBUROR_LIST + ":" + content.getId(), String.valueOf(cc.getId()));
		}

		redisService.delete(Constants.REDIS_CONTENT_BRAND_LIST + ":"  + content.getId());

		for (ContentBrand cb : content.getContentBrands()) {
			redisService.listRightPushAll(Constants.REDIS_CONTENT_BRAND_LIST + ":" + content.getId(), String.valueOf(cb.getId()));
		}
	}

	private void setContentBrandIntoCache(ContentBrand contentBrand) {
		Map<String, String> value = new HashMap<>();

		value.put("contentBrandId", String.valueOf(contentBrand.getId()));
		value.put("brandId", String.valueOf(contentBrand.getBrandId()));
		value.put("brandLogo", contentBrand.getBrandLogo());
		value.put("brandName", contentBrand.getBrandName());
		value.put("contentId", String.valueOf(contentBrand.getContentId()));
		value.put("description", contentBrand.getDescription());

		redisService.setMap(Constants.REDIS_CONTENT_BRAND + ":" + contentBrand.getId(), value);
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

	private void setContentFeatureDatasIntoCache(long id) {
		redisService.listRightPushAll(Constants.REDIS_CONTENT_FEATURE_DATA_ID_LIST, String.valueOf(id));
	}

	private List<String> getContentNotFeatureDatasFromCache(int offset, int limit) {
		List<String> ids = redisService.getList(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, offset, offset + limit - 1);

		return ids;
	}

	private void setContentNotFeatureDatasIntoCache(long id) {
		redisService.listRightPushAll(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST, String.valueOf(id));
	}

	private Integer getContentNotFeatureDataCountFromCache() {
		String count = redisService.get(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST);

		if (StringUtils.isNoneBlank(count)) {
			return Integer.valueOf(count);
		}
		else {
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("featureData", false);

			List<Integer> countList = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ContentMapper.getContentsByFeatureDataCount", params);

			setContentNotFeatureDataCountIntoCache(countList.get(0));

			return countList.get(0);
		}
	}

	private void setContentNotFeatureDataCountIntoCache(int count) {
		redisService.set(Constants.REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST, String.valueOf(count));
	}

	public int getContentHitCounter(long id) {
		String hit = redisService.get(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + id);

		if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
			return Integer.valueOf(hit);
		}
		else {
			Content content = contentMapper.getById(id);

			setContentHitCounterIntoCached(content.getId(), content.getHitCounter());

			return content.getHitCounter();
		}
	}

	private void setContentHitCounterIntoCached(long id, int hitCounter) {
		redisService.set(Constants.REDIS_CONTENT_HIT_COUNTER + ":" + id, String.valueOf(hitCounter));
	}

	public String encode(String input)
		throws UnsupportedEncodingException {
		input = input.replaceAll("/", "_*_");
		return URLEncoder.encode(input, "UTF-8");
	}
}