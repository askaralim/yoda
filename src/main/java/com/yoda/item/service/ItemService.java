package com.yoda.item.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.item.model.Item;
import com.yoda.kernal.model.Pagination;

public interface ItemService {
	void save(Item item);

	Item getItem(int itemId);

//	Item getItem(int siteId, int itemId);
//
//	Item getItem(int siteId, String itemNaturalKey);

	Item update(Item item);

	@Deprecated
	Item update(int id, Brand brand, Integer categoryId, Long contentId, String description, String level, String name, int price);

	List<Item> getItems(int siteId);

	Pagination<Item> getItems(int siteId, RowBounds rowBounds);

	List<Item> getItemsByContentId(long contentId);

	List<Item> getItemsByBrandId(int brandId);

	List<Item> getItemsByContentIdAndBrandId(long contentId, int brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

	void remove(int itemId);

	Item updateItemImage(int id, MultipartFile file);
}
