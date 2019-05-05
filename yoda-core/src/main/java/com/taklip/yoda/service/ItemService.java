package com.taklip.yoda.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;

public interface ItemService {
	void save(Item item);

	Item getItem(Long itemId);

	Item update(Item item);

	void updateItemHitCounter(Long itemId, int hitCounter);

	void updateItemRating(Long itemId, int rating);

	List<Item> getItems(int siteId);

	Pagination<Item> getItems(int siteId, RowBounds rowBounds);

	List<Item> getItemsByContentId(Long contentId);

	List<Item> getItemsByBrandId(Long brandId);

	List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

	void remove(Long itemId);

	Item updateItemImage(Long id, MultipartFile file);
}
