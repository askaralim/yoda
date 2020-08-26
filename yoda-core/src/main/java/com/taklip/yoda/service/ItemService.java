package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author askar
 */
public interface ItemService {
    void save(Item item);

    void add(Item item);

    Item update(Item item);

    Item getItem(Long itemId);

    int getItemHitCounter(Long itemId);

    void increaseItemHitCounter(Long itemId);

    void updateItemRating(Long itemId, int rating);

    List<Item> getItems();

    PageInfo<Item> getItems(Integer offset, Integer limit);

    List<Item> getItemsByContentId(Long contentId);

    List<Item> getItemsByBrandId(Long brandId);

    List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);

    List<Item> getItemsTopViewed(int count);

    List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

    void remove(Long itemId);

    Item updateItemImage(Long id, MultipartFile file);
}
