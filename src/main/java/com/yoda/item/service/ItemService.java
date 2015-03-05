package com.yoda.item.service;

import java.util.List;

import com.yoda.item.model.Item;

public interface ItemService {
	void save(Long siteId, Item item);

	Item getItem(int itemId);

	Item getItem(long siteId, int itemId);

	Item getItem(long siteId, String itemNaturalKey);

	Item update(Item item);

	Item update(int id, String brand, String description, String level, String name, int price);

	List<Item> getItems(long siteId);

	List<Item> search(long siteId, String itemNum, String itemUpcCd, String itemShortDesc);

	void remove(int itemId);

	Item updateItemImage(int id, String savedPath);
}
