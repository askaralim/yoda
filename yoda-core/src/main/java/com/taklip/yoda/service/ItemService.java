package com.taklip.yoda.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;

public interface ItemService {
	void save(Item item);

	void add(Item item);

	Item update(Item item);

	Item getItem(Long itemId);

	int getItemHitCounter(Long itemId);

	void increaseItemHitCounter(Long itemId);

	void updateItemRating(Long itemId, int rating);

	List<Item> getItems();

	Pagination<Item> getItems(RowBounds rowBounds);

	List<Item> getItemsByContentId(Long contentId);

	List<Item> getItemsByBrandId(Long brandId);

	List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

	void remove(Long itemId);

	Item updateItemImage(Long id, MultipartFile file);
}
