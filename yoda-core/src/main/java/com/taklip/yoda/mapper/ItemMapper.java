package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Item;

public interface ItemMapper extends BaseMapper<Item> {
	List<Item> getItemsBySiteId(int siteId);

	List<Item> getItemsByContentId(Long contentId);

	List<Item> getItemsByBrandId(Long brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(String title);

	List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);
}