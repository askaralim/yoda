package com.yoda.item.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.item.model.Item;

public interface ItemService {
	void save(int siteId, Item item);

	Item getItem(int itemId);

	Item getItem(int siteId, int itemId);

	Item getItem(int siteId, String itemNaturalKey);

	Item update(Item item);

	Item update(int id, Brand brand, Integer categoryId, Long contentId, String description, String level, String name, int price);

	List<Item> getItems(int siteId);

	List<Item> getItemsByContentId(long contentId);

	List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

	void remove(int itemId);

	Item updateItemImage(int id, MultipartFile file);
}
