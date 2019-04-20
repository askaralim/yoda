package com.taklip.yoda.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;

public interface ItemService {
	void save(Item item);

	Item getItem(int itemId);

	Item update(Item item);

	void updateItemHitCounter(int itemId, int hitCounter);

	void updateItemRating(int itemId, int rating);

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
