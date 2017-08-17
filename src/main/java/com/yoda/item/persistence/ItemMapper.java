package com.yoda.item.persistence;

import java.util.List;

import com.yoda.item.model.Item;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ItemMapper extends BaseMapper<Item> {
	List<Item> getItemsBySiteId(int siteId);

	List<Item> getItemsByContentId(Long contentId);

	List<Item> getItemsByBrandId(int brandId);

	List<Item> getItemsTopViewed(int count);

	List<Item> search(String title);

	List<Item> getItemsByContentIdAndBrandId(Long contentId, int brandId);
}