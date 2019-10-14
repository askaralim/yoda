package com.taklip.yoda.service.impl;

import java.io.IOException;
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
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ItemMapper;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.DateUtil;
import com.taklip.yoda.util.ExtraFieldUtil;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private BrandService brandService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private IdService idService;

	@Autowired
	ImageUploader imageUpload;

	@Autowired
	FileService fileService;


	@Transactional(readOnly = true)
	public Item getItem(Long itemId) {
		Item item = getItemFromCached(itemId);

		if (null == item) {
			item = itemMapper.getById(itemId);
			this.setItemIntoCached(item);
		}

		item.setExtraFieldList(ExtraFieldUtil.getExtraFields(item));
		item.setBuyLinkList(ExtraFieldUtil.getBuyLinks(item));

		return item;
	}

	@Transactional(readOnly = true)
	public Pagination<Item> getItems(RowBounds rowBounds) {
		Map<String, Object> params = new HashMap<String, Object>();

		List<Item> items = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ItemMapper.getItems", params, rowBounds);

		List<Integer> count = sqlSessionTemplate.selectList("com.taklip.yoda.mapper.ItemMapper.getItemsCount", params);

		Pagination<Item> page = new Pagination<Item>(rowBounds.getOffset(), count.get(0), rowBounds.getLimit(), items);

		return page;
	}

	@Transactional(readOnly = true)
	public List<Item> getItemsByContentId(Long contentId) {
		List<Item> items = new ArrayList<>();
		List<String> ids = redisService.getList(Constants.REDIS_CONTENT_ITEM_LIST + ":" + contentId);

		if (null != ids && !ids.isEmpty()) {
			for (String id : ids) {
				Item item = getItem(Long.valueOf(id));

				if (null != item) {
					items.add(item);
				}
			}
		}
		else {
			items = itemMapper.getItemsByContentId(contentId);
			ids = new ArrayList<>(); 

			for (Item item : items) {
				item.setExtraFieldList(ExtraFieldUtil.getExtraFields(item));
				item.setBuyLinkList(ExtraFieldUtil.getBuyLinks(item));

				ids.add(String.valueOf(item.getId()));
			}

			redisService.setList(Constants.REDIS_CONTENT_ITEM_LIST + ":" + contentId, ids);
		}

		return items;
	}

	@Transactional(readOnly = true)
	public List<Item> getItemsByBrandId(Long brandId) {
		List<Item> items = itemMapper.getItemsByBrandId(brandId);

		return items;
	}

	@Transactional(readOnly = true)
	public List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId) {
		List<Item> items = itemMapper.getItemsByContentIdAndBrandId(contentId, brandId);

		return items;
	}

	public List<Item> getItemsTopViewed(int count) {
		List<String> itemIds = getItemsTopViewedListFromCache(count);
		List<Item> items = new ArrayList<>();

		if (null == itemIds || itemIds.isEmpty()) {
			items = itemMapper.getItemsTopViewed(count);

			itemIds = new ArrayList<String>();

			for (Item item : items) {
				itemIds.add(String.valueOf(item.getId()));
			}

			this.setItemsTopViewedListIntoCache(itemIds);
		}
		else {
			for (String itemId : itemIds) {
				Item item = this.getItem(Long.valueOf(itemId));

				items.add(item);
			}
		}

		return items;
	}

	public List<Item> search(
			int siteId, String itemNum, String itemUpcCd,
			String itemShortDesc) {
		return null;
	}

	public void save(Item item) {
		if (null == item.getId()) {
			this.add(item);
		}
		else {
			this.update(item);
		}
	}

	public void add(Item item) {
		item.setId(idService.generateId());
		item.preInsert();

		itemMapper.insert(item);

		this.setItemIntoCached(item);

		if (item.getContentId() != null & item.getContentId() != 0) {
			redisService.listRightPushAll(Constants.REDIS_CONTENT_ITEM_LIST + ":" + item.getContentId(), String.valueOf(item.getId()));
		}
	}

	public Item update(Item item) {
		Item itemDB = itemMapper.getById(item.getId());

		itemDB.setBrand(item.getBrand());
		itemDB.setCategoryId(item.getCategoryId());
		itemDB.setContentId(item.getContentId());
		itemDB.setDescription(item.getDescription());
		itemDB.setHitCounter(item.getHitCounter());
		itemDB.setLevel(item.getLevel());
		itemDB.setName(item.getName());
		itemDB.setPrice(item.getPrice());
		itemDB.setExtraFields(item.getExtraFields());
		itemDB.setBuyLinks(item.getBuyLinks());
		itemDB.setImagePath(item.getImagePath());

		itemDB.preUpdate();

		itemMapper.update(itemDB);
		logger.info("update item: " + item.getId() + " " + item.getImagePath());
		deleteItemFromCached(item.getId());

		return itemDB;
	}

	public void increaseItemHitCounter(Long itemId) {
		Item itemDB = itemMapper.getById(itemId);

		itemDB.setHitCounter(itemDB.getHitCounter() + 1);

		itemMapper.update(itemDB);

		setItemHitCounterIntoCached(itemId, itemDB.getHitCounter());
	}

	public void updateItemRating(Long itemId, int rating) {
		Item itemDB = itemMapper.getById(itemId);
		itemDB.setRating(rating);
		itemMapper.update(itemDB);
	}

	public Item updateItemImage(Long id, MultipartFile file) {
		Item item = itemMapper.getById(id);

		imageUpload.deleteImage(item.getImagePath());

		try {
			String imagePath = fileService.save(ContentTypeEnum.ITEM.getType(), id, file);

			item.setImagePath(imagePath);
		}
		catch (IOException e) {
			logger.warn(e.getMessage());
		}

		item.preUpdate();

		itemMapper.update(item);

		setItemIntoCached(item);

		return item;
	}

	public void remove(Long itemId) {
		Item item = itemMapper.getById(itemId);

		itemMapper.delete(item);
	}

	private List<String> getItemsTopViewedListFromCache(int count) {
		List<String> ids = redisService.getList(Constants.REDIS_ITEM_TOP_VIEW_LIST, 0, count - 1);

		return ids;
	}

	private long setItemsTopViewedListIntoCache(List<String> ids) {
		long result = redisService.setList(Constants.REDIS_ITEM_TOP_VIEW_LIST, ids);

		return result;
	}

	private void deleteItemFromCached(Long itemId) {
		redisService.delete(Constants.REDIS_ITEM + ":" + itemId);
	}

	private Item getItemFromCached(Long itemId) {
		Item item = null;

		String key = Constants.REDIS_ITEM + ":" + itemId;

		Map<String, String> map = redisService.getMap(key);

		if (null != map && map.size() >0) {
			item = new Item();

			String id = redisService.getMap(key, "id");
			String buyLinks = redisService.getMap(key, "buyLinks");
			String categoryId = redisService.getMap(key, "categoryId");
			String contentId = redisService.getMap(key, "contentId");
			String brandId = redisService.getMap(key, "brandId");
			String description = redisService.getMap(key, "description");
			String extraFields = redisService.getMap(key, "extraFields");
			String imagePath = redisService.getMap(key, "imagePath");
			String level = redisService.getMap(key, "level");
			String name = redisService.getMap(key, "name");
			String price = redisService.getMap(key, "price");
			String siteId = redisService.getMap(key, "siteId");
			String createBy = redisService.getMap(key, "createBy");
			String createDate = redisService.getMap(key, "createDate");
			String updateBy = redisService.getMap(key, "updateBy");
			String updateDate = redisService.getMap(key, "updateDate");

			item.setId(StringUtils.isNoneBlank(id) && !"nil".equalsIgnoreCase(id) ? Long.valueOf(id) : null);
			item.setBuyLinks(StringUtils.isNoneBlank(buyLinks) && !"nil".equalsIgnoreCase(buyLinks) ? buyLinks : null);
			item.setCategoryId(StringUtils.isNoneBlank(categoryId) && !"nil".equalsIgnoreCase(categoryId) ? Integer.valueOf(categoryId) : null);
			item.setContentId(StringUtils.isNoneBlank(contentId) && !"nil".equalsIgnoreCase(contentId) ? Long.valueOf(contentId) : null);
			item.setDescription(StringUtils.isNoneBlank(description) && !"nil".equalsIgnoreCase(description) ? description : null);
			item.setExtraFields(StringUtils.isNoneBlank(extraFields) && !"nil".equalsIgnoreCase(extraFields) ? extraFields : null);
			item.setImagePath(StringUtils.isNoneBlank(imagePath) && !"nil".equalsIgnoreCase(imagePath) ? imagePath : null);
			item.setLevel(StringUtils.isNoneBlank(level) && !"nil".equalsIgnoreCase(level) ? level : null);
			item.setName(StringUtils.isNoneBlank(name) && !"nil".equalsIgnoreCase(name) ? name : null);
			item.setPrice(StringUtils.isNoneBlank(price) && !"nil".equalsIgnoreCase(price) ? Integer.valueOf(price) : null);
			item.setSiteId(StringUtils.isNoneBlank(siteId) && !"nil".equalsIgnoreCase(siteId) ? Integer.valueOf(siteId) : null);

			if (StringUtils.isNoneBlank(createBy) && !"nil".equalsIgnoreCase(createBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(createBy));
				item.setCreateBy(user);
			}

			if (StringUtils.isNoneBlank(updateBy) && !"nil".equalsIgnoreCase(updateBy)) {
				User user = new User();
				user.setUserId(Long.valueOf(updateBy));
				item.setUpdateBy(user);
			}

			item.setCreateDate(StringUtils.isNoneBlank(createDate) && !"nil".equalsIgnoreCase(createDate) ? DateUtil.getDate(createDate) : null);
			item.setUpdateDate(StringUtils.isNoneBlank(updateDate) && !"nil".equalsIgnoreCase(updateDate) ? DateUtil.getDate(updateDate) : null);

			Brand brand = brandService.getBrand(Long.valueOf(brandId));

			item.setBrand(brand);

			item.setHitCounter(getItemHitCounter(itemId));
			item.setRating(getItemRateFromCached(itemId));
		}

		return item;
	}

	private void setItemIntoCached(Item item) {
		Map<String, String> value = new HashMap<>();

		value.put("id", String.valueOf(item.getId()));
		value.put("buyLinks", StringUtils.isNoneBlank(item.getBuyLinks()) ? item.getBuyLinks() : StringPool.BLANK);
		value.put("categoryId", String.valueOf(item.getCategoryId()));
		value.put("contentId", String.valueOf(item.getContentId()));
		value.put("brandId", String.valueOf(item.getBrand().getBrandId()));
		value.put("description", item.getDescription());
		value.put("extraFields", StringUtils.isNoneBlank(item.getExtraFields()) ? item.getExtraFields() : StringPool.BLANK);
		value.put("imagePath", item.getImagePath());
		value.put("level", item.getLevel());
		value.put("name", item.getName());
		value.put("price", String.valueOf(item.getPrice()));
		value.put("siteId", String.valueOf(item.getSiteId()));
		value.put("updateBy", String.valueOf(item.getUpdateBy().getUserId()));
		value.put("updateDate", DateUtil.getDate(item.getUpdateDate()));
		value.put("createBy", String.valueOf(item.getCreateBy().getUserId()));
		value.put("createDate", DateUtil.getDate(item.getCreateDate()));

		redisService.setMap(Constants.REDIS_ITEM + ":" + item.getId(), value);

		setItemHitCounterIntoCached(item.getId(), item.getHitCounter());
		setItemRateIntoCached(item.getId(), item.getRating());
	}

	public int getItemHitCounter(Long itemId) {
		String hit = redisService.get(Constants.REDIS_ITEM_HIT_COUNTER + ":" + itemId);

		if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
			return Integer.valueOf(hit);
		}
		else {
			Item item = itemMapper.getById(itemId);

			setItemHitCounterIntoCached(item.getId(), item.getHitCounter());

			return item.getHitCounter();
		}
	}

	private void setItemHitCounterIntoCached(Long itemId, int hitCounter) {
		redisService.set(Constants.REDIS_ITEM_HIT_COUNTER + ":" + itemId, String.valueOf(hitCounter));
	}

	private int getItemRateFromCached(Long itemId) {
		String rating = redisService.get(Constants.REDIS_ITEM_RATE + ":" + itemId);

		if (StringUtils.isNoneBlank(rating) && !"nil".equalsIgnoreCase(rating)) {
			return Integer.valueOf(rating);
		}
		else {
			Item item = itemMapper.getById(itemId);

			setItemRateIntoCached(item.getId(), item.getRating());

			return item.getRating();
		}
	}

	private void setItemRateIntoCached(Long itemId, int rating) {
		redisService.set(Constants.REDIS_ITEM_RATE + ":" + itemId, String.valueOf(rating));
	}
}