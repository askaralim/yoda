package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Item;

public interface ItemMapper extends BaseMapper<Item> {
	List<Item> getItems();

	List<Item> getItemsByContentId(Long contentId);

	List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);

	List<Item> getItemsByBrandId(Long brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(String title);
}