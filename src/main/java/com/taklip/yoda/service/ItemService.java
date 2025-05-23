package com.taklip.yoda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.model.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author askar
 */
public interface ItemService extends IService<Item> {
    Item create(Item item);

    Item update(Item item);

    ItemDTO getItemDetail(Long itemId);

    Item getItem(Long itemId);

    int getItemHitCounter(Long itemId);

    void increaseItemHitCounter(Long itemId);

    void updateItemRating(Long itemId, int rating);

    List<Item> getItems();

    Page<ItemDTO> getItems(Integer offset, Integer limit);

    List<Item> getItemsByContentId(Long contentId);

    List<Item> getItemsByBrandId(Long brandId);

    List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId);

    List<Item> getItemsTopViewed(int count);

    List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc);

    // List<ItemDTO> getItemDTOsByContentId(Long contentId);

    void remove(Long itemId);

    Item updateItemImage(Long id, MultipartFile file);
}